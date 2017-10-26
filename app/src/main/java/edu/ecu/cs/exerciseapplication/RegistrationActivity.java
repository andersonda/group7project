package edu.ecu.cs.exerciseapplication;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

public class RegistrationActivity extends SingleFragmentActivity {
    @Override
    public Fragment createFragment(){
        return new RegistrationFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SQLiteDatabase mDatabase = new ExerciseDBHelper(getApplicationContext()).getReadableDatabase();
        try (UserCursorWrapper cursorWrapper = UserCursorWrapper.queryUser(mDatabase)) {
            if(cursorWrapper.getCount() == 1){
                Intent intent = ProfileActivity.newIntent(getApplicationContext());
                startActivity(intent);
                finish();
            }
        }
    }
}
