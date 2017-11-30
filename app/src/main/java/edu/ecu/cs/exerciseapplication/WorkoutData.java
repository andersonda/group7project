package edu.ecu.cs.exerciseapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import edu.ecu.cs.exerciseapplication.database.ExerciseDBHelper;
import edu.ecu.cs.exerciseapplication.database.ExerciseDBSchema;
import edu.ecu.cs.exerciseapplication.database.ExerciseDBSchema.ExerciseTable;
import edu.ecu.cs.exerciseapplication.database.ExerciseDBSchema.WorkoutTable;

import static edu.ecu.cs.exerciseapplication.R.drawable.workout;

/**
 * Created by hunter on 11/5/17.
 */

public class WorkoutData
{
    /** The singleton holding the {@link Workout} information */
    private static WorkoutData sWorkoutData;

    /** The list of {@link Workout} objects */
    private Context mContext;
    private SQLiteDatabase mDatabase;

    /**
     * Return the WorkoutData singleton
     *
     * @param context the context for the call
     * @return the Workout singleton
     */
    public static WorkoutData getWorkoutData(Context context)
    {
        if (sWorkoutData == null) {
            sWorkoutData = new WorkoutData(context);
        }
        return sWorkoutData;
    }

    /**
     * Create a new instance of the WorkoutData
     *
     * @param context the context for the call
     */
    private WorkoutData(Context context)
    {
        mContext = context.getApplicationContext();
        mDatabase = new ExerciseDBHelper(mContext).getWritableDatabase();

    }

    /**
     * Return the list of {@link Workout} objects
     *
     * @return the list of {@link Workout} objects
     */
    public List<Workout> getWorkouts()
    {
        List<Workout> Workouts = new ArrayList<>();

        ExerciseCursorWrapper cursor =  queryWorkouts(null, null);

        try{
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                Workouts.add(cursor.getWorkout());
                cursor.moveToNext();
            }
        }finally {
            cursor.close();
        }

        return Workouts;
    }

    public List<Exercise> getExercises(UUID workoutId)
    {
        List<Exercise> exercises = new ArrayList<>();

        ExerciseCursorWrapper cursor =  queryExercises(ExerciseTable.Cols.WORKOUT_UUID + " = ? ",new String[]{workoutId.toString()});

        try{
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                exercises.add(cursor.getExercise());
                cursor.moveToNext();
            }
        }finally {
            cursor.close();
        }

        return exercises;
    }

    /**
     * Given an id, return the {@link Workout} with that id, or null if
     * no {@link Workout} is found.
     *
     * @param id the id to look up
     *
     * @return the {@link Workout} with that id, or null if none exists
     */
    public Workout getWorkout(UUID id)
    {
        ExerciseCursorWrapper cursor = queryWorkouts(
                WorkoutTable.Cols.UUID + " = ? ",
                new String[]{id.toString()}
        );

        try {
            if(cursor.getCount()==0){
                return null;
            }

            cursor.moveToFirst();
            return cursor.getWorkout();
        }finally {
            cursor.close();
        }
    }

    public void addExercse(Exercise exercise){
        ContentValues values = getContentExerciseValues(exercise);

        mDatabase.insert(ExerciseTable.NAME, null, values);
    }

    public void addWorkout(Workout workout){
        ContentValues values = getContentValues(workout);

        mDatabase.insert(WorkoutTable.NAME, null, values);
    }

    private static ContentValues getContentValues(Workout workout){
        ContentValues values = new ContentValues();
        values.put(WorkoutTable.Cols.UUID, workout.getId().toString());
        values.put(WorkoutTable.Cols.WORKOUT_NAME, workout.getName());
        values.put(WorkoutTable.Cols.WORKOUT_DATE, workout.getDate().getTime());
        return values;
    }

    private static ContentValues getContentExerciseValues(Exercise exercise){
        ContentValues values = new ContentValues();
        values.put(ExerciseTable.Cols.WORKOUT_UUID, exercise.getWorkoutId().toString());
        values.put(ExerciseTable.Cols.EXERCISE, exercise.getName());
        values.put(ExerciseTable.Cols.SETS, exercise.getSet());
        values.put(ExerciseTable.Cols.REPS, exercise.getReps());
        values.put(ExerciseTable.Cols.CALORIES, exercise.getCalories());
        return values;
    }

    public void updateWorkout(Workout workout){
        String uuidString = workout.getId().toString();
        ContentValues values = getContentValues(workout);

        mDatabase.update(WorkoutTable.NAME, values, WorkoutTable.Cols.UUID + " = ?", new String[]{uuidString});
    }

    private ExerciseCursorWrapper queryWorkouts(String whereClause, String[] whereArgs){
        Cursor cursor = mDatabase.query(
                WorkoutTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        return new ExerciseCursorWrapper(cursor);
    }

    private ExerciseCursorWrapper queryExercises(String whereClause, String[] whereArgs){
        Cursor cursor = mDatabase.query(
                ExerciseTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        return new ExerciseCursorWrapper(cursor);
    }

    public void deleteWorkout(Workout workout) {
        mDatabase.delete(WorkoutTable.NAME,WorkoutTable.Cols.UUID + " = ?", new String[]{workout.getId().toString()});
    }
}
