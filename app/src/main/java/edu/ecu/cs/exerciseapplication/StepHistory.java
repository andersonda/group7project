package edu.ecu.cs.exerciseapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import edu.ecu.cs.exerciseapplication.database.ExerciseDBHelper;
import edu.ecu.cs.exerciseapplication.database.ExerciseDBSchema.StepsTable;


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

    public List<Steps> getDailyStepsTotals(){
        List<Steps> steps = new ArrayList<>();
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();

        try (StepsCursorWrapper cursor = querySteps(null, null)) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                boolean foundEntry = false;
                for(Steps stepsEntry : steps){
                    cal1.setTime(stepsEntry.getDate());
                    cal2.setTime(cursor.getSteps().getDate());

                    if(cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                            cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)){

                        stepsEntry.setNumSteps(stepsEntry.getNumSteps() + cursor.getSteps().getNumSteps());
                        foundEntry = true;
                        break;
                    }
                }
                if(!foundEntry){
                    steps.add(cursor.getSteps());
                }
                cursor.moveToNext();
            }
        }
        return steps;
    }

    public List<Steps> getWalksOnDate(Date date){
        List<Steps> steps = new ArrayList<>();
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date);
        Calendar cal2 = Calendar.getInstance();

        try (StepsCursorWrapper cursor = querySteps(null, null)) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                cal2.setTime(cursor.getSteps().getDate());
                if(cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                        cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)){
                    steps.add(cursor.getSteps());
                }
                cursor.moveToNext();
            }
        }
        return steps;
    }

    public int getTotalStepsOnDate(Date date){
        List<Steps> steps = getWalksOnDate(date);
        int res = 0;

        for(Steps stepsEntry : steps){
            res += stepsEntry.getNumSteps();
        }

        return res;
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

    public int getDailyStepGoal(){
        try (UserCursorWrapper cursorWrapper = UserCursorWrapper.queryUser(mDatabase)) {
            cursorWrapper.moveToFirst();
            User user = cursorWrapper.getUser();
            return user.getDailyStepGoal();
        }
    }
}
