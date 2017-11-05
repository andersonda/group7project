package edu.ecu.cs.exerciseapplication;

import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;

import static edu.ecu.cs.exerciseapplication.ExerciseDBSchema.*;

/**
 * Created by danderson on 10/12/17.
 */

public class UserCursorWrapper extends CursorWrapper {
    public UserCursorWrapper(Cursor cursor){
        super(cursor);
    }

    public User getUser(){
        String firstName = getString(getColumnIndex(UserTable.Cols.FIRST));
        String lastName = getString(getColumnIndex(UserTable.Cols.LAST));
        double weight = getDouble(getColumnIndex(UserTable.Cols.WEIGHT));
        double height = getDouble(getColumnIndex(UserTable.Cols.HEIGHT));
        int age = getInt(getColumnIndex(UserTable.Cols.AGE));
        boolean isMale = getInt(getColumnIndex(UserTable.Cols.GENDER)) == 1;
        int stepGoal = getInt(getColumnIndex(UserTable.Cols.STEP_GOAL));

        return new User(firstName, lastName, weight, height, age, isMale, stepGoal);
    }

    protected static UserCursorWrapper queryUser(SQLiteDatabase db){

        Cursor cursor = db.query(
                UserTable.NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );

        return new UserCursorWrapper(cursor);
    }
}
