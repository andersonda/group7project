package edu.ecu.cs.exerciseapplication;

import android.support.v4.app.Fragment;

/**
 * Created by SesanA on 9/21/2017.
 */

public class WorkoutActivity extends SingleFragmentActivity {
    @Override
    public Fragment createFragment() {
        return new WorkoutFragment();
    }
}
