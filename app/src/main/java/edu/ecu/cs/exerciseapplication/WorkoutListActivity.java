package edu.ecu.cs.exerciseapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

/**
 * Created by hunter on 11/5/17.
 */

public class WorkoutListActivity extends SingleFragmentActivity
{
    @Override
    protected Fragment createFragment()
    {
        return new WorkoutListFragment();
    }

    public static Intent newIntent(Context context){
        Intent intent = new Intent(context,WorkoutListActivity.class);
        return intent;
    }
}
