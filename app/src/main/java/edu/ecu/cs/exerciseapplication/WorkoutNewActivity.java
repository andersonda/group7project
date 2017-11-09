package edu.ecu.cs.exerciseapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.UUID;

/**
 * Created by hunter on 11/5/17.
 */

public class WorkoutNewActivity extends SingleFragmentActivity {

    private static final String EXTRA_WORKOUT_ID = "edu.ecu.cd.exerciseapplication.workout_id";

    public static Intent newIntent(Context packageContext, UUID workoutId) {
        Intent intent = new Intent(packageContext, WorkoutNewActivity.class);
        intent.putExtra(EXTRA_WORKOUT_ID, workoutId);
        return intent;
    }

    @Override
    protected Fragment createFragment(){
        UUID workoutId = (UUID) getIntent().getSerializableExtra(EXTRA_WORKOUT_ID);
        return WorkoutNewFragment.newInstance(workoutId);
    }
}
