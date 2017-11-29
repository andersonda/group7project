package edu.ecu.cs.exerciseapplication;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by hunter on 9/16/17.
 */

public class HomeFragment extends Fragment {
    Button mProfileButton;
    Button mWorkoutButton;
    Button mStepsButton;
    Button mLogWeightButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_home,container,false);

        mProfileButton = v.findViewById(R.id.profile_button);
        mProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = ProfileActivity.newIntent(getActivity());
                startActivity(intent);
            }
        });

        mWorkoutButton = v.findViewById(R.id.workout_button);
        mWorkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = WorkoutListActivity.newIntent(getActivity());
                startActivity(intent);
            }
        });

        mStepsButton = v.findViewById(R.id.steps_button);
        mStepsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = StepsListActivity.newIntent(getActivity());
                startActivity(intent);
            }
        });

        mLogWeightButton = v.findViewById(R.id.weight_button);
        mLogWeightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = WeightListActivity.newIntent(getActivity());
                startActivity(intent);
            }
        });
        return v;
    }
}
