package edu.ecu.cs.exerciseapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

public class HomeActivity extends SingleFragmentActivity {

    @Override
    public Fragment createFragment() {
        return new HomeFragment();
    }

    public static Intent newIntent(Context context){
        Intent intent = new Intent(context,HomeActivity.class);
        return intent;
    }
}
