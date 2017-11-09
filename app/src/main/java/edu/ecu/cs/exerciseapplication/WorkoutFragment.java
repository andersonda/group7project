package edu.ecu.cs.exerciseapplication;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.UUID;

/**
 * Created by hunter on 9/16/17.
 */

public class WorkoutFragment extends Fragment {

    private static final String ARG_WORKOUT_ID = "edu.ecu.cd.exerciseapplication.workout_id";
    private TextView mWorkoutNameTextview;
    private Workout mWorkout;

    public static WorkoutFragment newInstance(UUID workoutId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_WORKOUT_ID, workoutId);

        WorkoutFragment fragment = new WorkoutFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID workoutId = (UUID) getArguments().getSerializable(ARG_WORKOUT_ID);
        mWorkout = WorkoutData.getWorkoutData(getActivity()).getWorkout(workoutId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_workout,container,false);

        mWorkoutNameTextview = v.findViewById(R.id.workout_name);
        mWorkoutNameTextview.setText(mWorkout.getName());

        return v;
    }
}
