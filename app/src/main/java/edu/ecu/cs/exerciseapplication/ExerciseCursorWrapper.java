package edu.ecu.cs.exerciseapplication;

import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.net.wifi.WifiManager;

import java.util.Date;
import java.util.UUID;

import edu.ecu.cs.exerciseapplication.database.ExerciseDBSchema;
import edu.ecu.cs.exerciseapplication.database.ExerciseDBSchema.ExerciseTable;

/**
 * Created by hunter on 10/22/17.
 */

public class ExerciseCursorWrapper extends CursorWrapper {
    public ExerciseCursorWrapper(Cursor cursor){super(cursor);}

    public Exercise getExercise(){
        String exercise = getString(getColumnIndex(ExerciseTable.Cols.EXERCISE));
        int sets = getInt(getColumnIndex(ExerciseTable.Cols.SETS));
        int reps = getInt(getColumnIndex(ExerciseTable.Cols.REPS));
        float calories = getFloat(getColumnIndex(ExerciseTable.Cols.CALORIES));
        String uuidString = getString(getColumnIndex(ExerciseTable.Cols.WORKOUT_UUID));

        return new Exercise(exercise,sets,reps,calories,UUID.fromString(uuidString));
    }

    public Workout getWorkout(){
        String uuidString = getString(getColumnIndex(ExerciseDBSchema.WorkoutTable.Cols.UUID));
        String workoutName = getString(getColumnIndex(ExerciseDBSchema.WorkoutTable.Cols.WORKOUT_NAME));
        long workoutDate = getLong(getColumnIndex(ExerciseDBSchema.WorkoutTable.Cols.WORKOUT_DATE));

        Workout workout = new Workout(UUID.fromString(uuidString));
        workout.setName(workoutName);
        workout.setDate(new Date(workoutDate));

        return workout;
    }
}
