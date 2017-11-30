package edu.ecu.cs.exerciseapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

/**
 * Created by delta on 11/6/2017.
 */

public class StepsListActivity extends SingleFragmentActivity {
    @Override
    public Fragment createFragment() {
        return new StepsListFragment();
    }

    public static Intent newIntent(Context context){
        Intent intent = new Intent(context, StepsListActivity.class);
        return intent;
    }
}
