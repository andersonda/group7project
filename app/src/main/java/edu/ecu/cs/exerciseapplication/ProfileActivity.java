package edu.ecu.cs.exerciseapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

public class ProfileActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment(){
        return new ProfileFragment();
    }

    public static Intent newIntent(Context context){
        Intent intent = new Intent(context,ProfileActivity.class);
        return intent;
    }
}
