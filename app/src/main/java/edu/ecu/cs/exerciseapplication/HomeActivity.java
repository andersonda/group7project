package edu.ecu.cs.exerciseapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        FragmentManager fm = getSupportFragmentManager();

        //Create profile fragement on Home activity
        Fragment profileFragment = fm.findFragmentById(R.id.profile_fragment_container);
        if (profileFragment == null) {
            profileFragment = new ProfileFragment();

            fm.beginTransaction()
                    .add(R.id.profile_fragment_container, profileFragment)
                    .commit();
        }

        //Create workout fragment on Home Activity
        Fragment workoutFragment = fm.findFragmentById(R.id.workout_fragment_container);
        if (workoutFragment == null) {
            workoutFragment = new WorkoutFragment();

            fm.beginTransaction()
                    .add(R.id.workout_fragment_container, workoutFragment)
                    .commit();
        }
    }

    public static Intent newIntent(Context context){
        Intent intent = new Intent(context,HomeActivity.class);
        return intent;
    }
}
