package edu.ecu.cs.exerciseapplication;

import android.support.v4.app.Fragment;

/**
 * Created by danderson on 10/25/17.
 */

public class WeightListActivity extends SingleFragmentActivity {
    @Override
    public Fragment createFragment() {
        return new WeightListFragment();
    }
}
