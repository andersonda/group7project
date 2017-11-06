package edu.ecu.cs.exerciseapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static edu.ecu.cs.exerciseapplication.ExerciseDBSchema.*;

/**
 * Created by delta on 11/6/2017.
 */

public class StepHistory {
    private static StepHistory sStepHistory;

    private SQLiteDatabase mDatabase;

    public static StepHistory get(Context context){
        if(sStepHistory == null){
            sStepHistory = new StepHistory(context);
        }
        return sStepHistory;
    }

    private StepHistory(Context context){
        mDatabase = new ExerciseDBHelper(context.getApplicationContext()).getWritableDatabase();
    }

    public List<Steps> getSteps(){
        List<Steps> steps = new ArrayList<>();

        try (StepsCursorWrapper cursor = querySteps(null, null)) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                steps.add(cursor.getSteps());
                cursor.moveToNext();
            }
        }
        return steps;
    }

    public List<Steps> getStepsForDayOfWeek(String dayOfWeek){
        List<Steps> steps = new ArrayList<>();
        String whereClause = "day = ?";
        String[] whereArgs = new String[]{dayOfWeek};

        try (StepsCursorWrapper cursor = querySteps(whereClause, whereArgs)) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                steps.add(cursor.getSteps());
                cursor.moveToNext();
            }
        }

        return steps;
    }

    public Steps getStepsEntry(UUID id){

        try (StepsCursorWrapper cursor = querySteps(
                StepsTable.Cols.UUID + " = ? ",
                new String[]{id.toString()}
        )) {
            if (cursor.getCount() == 0) {
                return null;
            }

            cursor.moveToFirst();
            return cursor.getSteps();
        }
    }

    private static ContentValues getContentValues(Steps steps){
        ContentValues values = new ContentValues();
        values.put(StepsTable.Cols.UUID, steps.getID().toString());
        values.put(StepsTable.Cols.STEPS, steps.getNumSteps());
        values.put(StepsTable.Cols.DATE, steps.getDate().getTime());
        values.put(StepsTable.Cols.DAY, steps.getDayOfWeek());

        return values;
    }

    public void addSteps(Steps steps){
        ContentValues values = getContentValues(steps);
        mDatabase.insert(StepsTable.NAME, null, values);
    }

    private StepsCursorWrapper querySteps(String whereClause, String[] whereArgs){
        Cursor cursor = mDatabase.query(
                StepsTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );

        return new StepsCursorWrapper(cursor);
    }

    /**
     * clear all steps history besides the most recent entry
     * @param id
     */
    public void clearStepsHistory(UUID id){
        String whereClause = StepsTable.Cols.UUID + " != ?";
        String[] whereArgs = new String[]{id.toString()};
        mDatabase.delete(StepsTable.NAME, whereClause, whereArgs);
    }
}
