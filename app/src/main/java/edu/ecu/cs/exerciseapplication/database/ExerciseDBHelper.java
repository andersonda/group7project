package edu.ecu.cs.exerciseapplication.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import edu.ecu.cs.exerciseapplication.Workout;
import edu.ecu.cs.exerciseapplication.database.ExerciseDBSchema.ExerciseTable;
import edu.ecu.cs.exerciseapplication.database.ExerciseDBSchema.StepsTable;
import edu.ecu.cs.exerciseapplication.database.ExerciseDBSchema.UserTable;
import edu.ecu.cs.exerciseapplication.database.ExerciseDBSchema.WeightTable;
import edu.ecu.cs.exerciseapplication.database.ExerciseDBSchema.WorkoutTable;


/**
 * Created by danderson on 10/3/17.
 */

public class ExerciseDBHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "exercise.db";

    public ExerciseDBHelper(Context context){
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + UserTable.NAME + "("
                + UserTable.Cols.FIRST + ", "
                + UserTable.Cols.LAST + ", "
                + UserTable.Cols.AGE + ", "
                + UserTable.Cols.WEIGHT + ", "
                + UserTable.Cols.HEIGHT + ", "
                + UserTable.Cols.GENDER + ", "
                + UserTable.Cols.STEP_GOAL + ")"
        );

        sqLiteDatabase.execSQL("create table " + ExerciseTable.NAME + "("
                + " _id integer primary key autoincrement, "
                + ExerciseTable.Cols.WORKOUT_UUID + ", "
                + ExerciseTable.Cols.EXERCISE + ", "
                + ExerciseTable.Cols.REPS + ", "
                + ExerciseTable.Cols.SETS + ", "
                + ExerciseTable.Cols.CALORIES + ")"
        );
        sqLiteDatabase.execSQL("create table " + WeightTable.NAME + "("
                + WeightTable.Cols.UUID + ", "
                + WeightTable.Cols.WEIGHT + ", "
                + WeightTable.Cols.DATE + ")"

        );
        sqLiteDatabase.execSQL("create table " + StepsTable.NAME + "("
                + StepsTable.Cols.UUID + ", "
                + StepsTable.Cols.STEPS + ", "
                + StepsTable.Cols.DATE + ")"
        );
        sqLiteDatabase.execSQL("create table " + WorkoutTable.NAME + "("
                + " _id integer primary key autoincrement, "
                + WorkoutTable.Cols.UUID + ", "
                + WorkoutTable.Cols.WORKOUT_NAME + ", "
                + WorkoutTable.Cols.WORKOUT_DATE + ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
