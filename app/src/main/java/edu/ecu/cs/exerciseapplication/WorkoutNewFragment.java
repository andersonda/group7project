package edu.ecu.cs.exerciseapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Date;
import java.util.UUID;

/**
 * Created by hunter on 11/5/17.
 */

public class WorkoutNewFragment extends Fragment {

    private static final String ARG_WORKOUT_ID = "edu.ecu.cd.exerciseapplication.workout_id";
    private Workout mWorkout;
    private TextView mWorkoutName;
    private TextView mWorkoutDate;

    public static WorkoutNewFragment newInstance(UUID workoutId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_WORKOUT_ID, workoutId);

        WorkoutNewFragment fragment = new WorkoutNewFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_new_workout, container, false);

        mWorkoutName = v.findViewById(R.id.workout_name);
        mWorkoutName.setText(mWorkout.getName());

        mWorkoutDate = v.findViewById(R.id.workout_date);
        mWorkoutDate.setText(mWorkout.getDate().toString());

        return v;
    }


    @Override
    public void onPause() {
        super.onPause();
        WorkoutData.getWorkoutData(getActivity()).updateWorkout(mWorkout);
    }
}
