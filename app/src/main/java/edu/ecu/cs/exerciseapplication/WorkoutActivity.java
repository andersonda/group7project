package edu.ecu.cs.exerciseapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.UUID;

/**
 * Created by SesanA on 9/21/2017.
 */

public class WorkoutActivity extends SingleFragmentActivity {
    private static final String EXTRA_WORKOUT_ID_2="edu.ecu.cs.exerciseapplication.workout_id_2";

    @Override
    protected Fragment createFragment() {
        UUID workoutId = (UUID) getIntent().getSerializableExtra(EXTRA_WORKOUT_ID_2);
        return WorkoutFragment.newInstance(workoutId);
    }

    public static Intent newIntent(Context context, UUID id){
        Intent intent = new Intent(context,WorkoutListActivity.class);
        intent.putExtra(EXTRA_WORKOUT_ID_2, id);
        return intent;
    }
}
