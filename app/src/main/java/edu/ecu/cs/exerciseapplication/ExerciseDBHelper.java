package edu.ecu.cs.exerciseapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import edu.ecu.cs.exerciseapplication.ExerciseDBSchema.UserTable;

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
                + UserTable.Cols.GENDER + ")"

        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
