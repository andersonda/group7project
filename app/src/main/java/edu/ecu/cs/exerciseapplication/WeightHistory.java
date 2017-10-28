package edu.ecu.cs.exerciseapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import static edu.ecu.cs.exerciseapplication.ExerciseDBSchema.*;

/**
 * Created by danderson on 10/25/17.
 */

public class WeightHistory {
    private static WeightHistory sWeightHistory;

    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static WeightHistory get(Context context){
        if(sWeightHistory == null){
            sWeightHistory = new WeightHistory(context);
        }
        return sWeightHistory;
    }

    private WeightHistory(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new ExerciseDBHelper(mContext).getWritableDatabase();
    }

    public List<Weight> getWeights() {
        List<Weight> weights = new ArrayList<>();

        WeightCursorWrapper cursor = queryWeights(null, null);

        try{
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                weights.add(cursor.getWeight());
                cursor.moveToNext();
            }
        }finally {
            cursor.close();
        }

        return weights;
    }

    public Weight getWeight(UUID id){
        WeightCursorWrapper cursor = queryWeights(
                WeightTable.Cols.UUID + " = ? ",
                new String[]{id.toString()}
        );

        try{
            if(cursor.getCount() == 0){
                return null;
            }

            cursor.moveToFirst();
            return cursor.getWeight();
        }finally {
            cursor.close();
        }
    }

    public void updateWeight(Weight weight){
        String uuidString = weight.getmId().toString();
        ContentValues values = getContentValues(weight);

        mDatabase.update(WeightTable.NAME, values,
                WeightTable.Cols.UUID + " = ?",
                new String[]{uuidString});
    }

    private static ContentValues getContentValues(Weight weight){
        ContentValues values = new ContentValues();
        values.put(WeightTable.Cols.UUID, weight.getmId().toString());
        values.put(WeightTable.Cols.WEIGHT, weight.getmWeight().toString());
        values.put(WeightTable.Cols.DATE, weight.getmLogTime().getTime());

        return values;
    }

    public void addWeight(Weight weight){
        ContentValues values = getContentValues(weight);
        mDatabase.insert(WeightTable.NAME, null, values);

        // also update current weight in user table
        ContentValues newWeight = new ContentValues();
        newWeight.put(UserTable.Cols.WEIGHT, weight.getmWeight());
        mDatabase.update(UserTable.NAME, newWeight, null, null);
    }

    private WeightCursorWrapper queryWeights(String whereClause, String[] whereArgs){
        Cursor cursor = mDatabase.query(
                WeightTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );

        return new WeightCursorWrapper(cursor);
    }

    /**
     * clear all weight history besides the most recent entry
     * @param id
     */
    public void clearWeightHistory(UUID id){
        String whereClause = WeightTable.Cols.UUID + " != ?";
        String[] whereArgs = new String[]{id.toString()};
        mDatabase.delete(WeightTable.NAME, whereClause, whereArgs);
    }
}
