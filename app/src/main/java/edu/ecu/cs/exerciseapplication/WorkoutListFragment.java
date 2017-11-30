package edu.ecu.cs.exerciseapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;

/**
 * Created by hunter on 11/5/17.
 */

public class WorkoutListFragment extends Fragment
    {
        private static final int REQUEST_WORKOUT = 1;
        private static final int REQUEST_NEW_WORKOUT = 2;

        private Button mCreateNewWorkout;
        private RecyclerView mWorkoutRecyclerView;
        private WorkoutAdapter mAdapter;

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
        {
            View view = inflater.inflate(R.layout.fragment_workout_list, container, false);

            mWorkoutRecyclerView = view.findViewById(R.id.workout_recycler_view);
            mWorkoutRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

            mCreateNewWorkout = view.findViewById(R.id.new_workout_button);
            mCreateNewWorkout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Workout workout = new Workout();
                    WorkoutData.getWorkoutData(getActivity()).addWorkout(workout);
                    Intent intent = WorkoutNewActivity.newIntent(getActivity(),workout.getId());
                    startActivity(intent);
                }
            });

            updateUI();

            return view;
        }

    private void updateUI() {
        List<Workout> workouts = WorkoutData.getWorkoutData(getActivity()).getWorkouts();

        if (mAdapter == null) {
            mAdapter = new WorkoutAdapter(workouts);
            mWorkoutRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setWorkouts(workouts);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
        updateUI();
    }



    private class WorkoutHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener
    {
        private Workout mWorkout;
        private TextView mWorkoutNameTextView;
        private TextView mDateTextView;

        public WorkoutHolder(LayoutInflater inflater, ViewGroup parent)
        {
            super(inflater.inflate(R.layout.list_item_workout, parent, false));

            mWorkoutNameTextView = itemView.findViewById(R.id.workout_name);
            mDateTextView = itemView.findViewById(R.id.workout_date);

            itemView.setOnClickListener(this);
        }

        public void bind(Workout Workout)
        {
            mWorkout = Workout;
            mWorkoutNameTextView.setText(mWorkout.getName());
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yy");
            String formattedDate = dateFormat.format(mWorkout.getDate());
            mDateTextView.setText(formattedDate);
        }

        @Override
        public void onClick(View v)
        {
            Intent intent = WorkoutActivity.newIntent(getActivity(), mWorkout.getId());
            startActivityForResult(intent,REQUEST_NEW_WORKOUT);
        }
    }

    private class WorkoutAdapter extends RecyclerView.Adapter<WorkoutHolder>
    {
        private List<Workout> mWorkouts;

        public WorkoutAdapter(List<Workout> Workouts)
        {
            mWorkouts = Workouts;
        }

        @Override
        public WorkoutHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new WorkoutHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(WorkoutHolder holder, int position)
        {
            Workout Workout = mWorkouts.get(position);
            holder.bind(Workout);
        }

        @Override
        public int getItemCount()
        {
            return mWorkouts.size();
        }

        public void setWorkouts(List<Workout> workouts){
            mWorkouts = workouts;
        }
    }
}
