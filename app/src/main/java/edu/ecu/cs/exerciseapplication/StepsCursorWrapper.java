package edu.ecu.cs.exerciseapplication;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.Date;
import java.util.UUID;

import edu.ecu.cs.exerciseapplication.database.ExerciseDBSchema.StepsTable;


/**
 * Created by delta on 11/6/2017.
 */

public class StepsCursorWrapper extends CursorWrapper {
    public StepsCursorWrapper(Cursor cursor){super(cursor);}

    public Steps getSteps(){
        String uuidString = getString(getColumnIndex(StepsTable.Cols.UUID));
        int numSteps = getInt(getColumnIndex(StepsTable.Cols.STEPS));
        long date = getLong(getColumnIndex(StepsTable.Cols.DATE));

        Steps steps = new Steps(UUID.fromString(uuidString));
        steps.setNumSteps(numSteps);
        steps.setDate(new Date(date));

        return steps;
    }
}
