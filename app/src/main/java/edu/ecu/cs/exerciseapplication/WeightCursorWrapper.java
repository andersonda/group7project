package edu.ecu.cs.exerciseapplication;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.Date;
import java.util.UUID;

import edu.ecu.cs.exerciseapplication.database.ExerciseDBSchema.WeightTable;

/**
 * Created by danderson on 10/25/17.
 */

public class WeightCursorWrapper extends CursorWrapper {
    public WeightCursorWrapper(Cursor cursor){
        super(cursor);
    }

    public Weight getWeight(){
        String uuidString = getString(getColumnIndex(WeightTable.Cols.UUID));
        Double kg = getDouble(getColumnIndex(WeightTable.Cols.WEIGHT));
        long date = getLong(getColumnIndex(WeightTable.Cols.DATE));

        Weight weight = new Weight(UUID.fromString(uuidString));
        weight.setmWeight(kg);
        weight.setmLogTime(new Date(date));

        return weight;
    }
}
