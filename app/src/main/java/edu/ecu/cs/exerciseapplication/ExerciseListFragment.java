package edu.ecu.cs.exerciseapplication;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;

/**
 * Created by hunter on 11/5/17.
 */

public class ExerciseListFragment extends Fragment{

    private RecyclerView mExerciseRecyclerView;
    private ExerciseAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_workout, container, false);

        mExerciseRecyclerView = (RecyclerView) view.findViewById(R.id.exercise_recycler_view);
        mExerciseRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    private void updateUI(){
        //TODO get exercises from workout
//        List<Exercise> exercises = Workout.getExerciseList();
//
//        if (mAdapter == null) {
//            mAdapter = new ExerciseAdapter(exercises);
//            mExerciseRecyclerView.setAdapter(mAdapter);
//        } else {
//            mAdapter.notifyDataSetChanged();
//        }
    }

//    @Override
//    public void onResume()
//    {
//        super.onResume();
//        updateUI();
//    }


    private class ExerciseHolder extends RecyclerView.ViewHolder
    {
        private Exercise mExercise;
        private TextView mExerciseNameTextView;
        private TextView mSetsTextView;
        private TextView mRepsTextView;
        private TextView mCaloriesTextView;

        public ExerciseHolder(LayoutInflater inflater, ViewGroup parent)
        {
            super(inflater.inflate(R.layout.list_item_exercise, parent, false));

            mExerciseNameTextView = (TextView) itemView.findViewById(R.id.exercise_name);
            mSetsTextView = (TextView) itemView.findViewById(R.id.exercise_sets);
            mRepsTextView = (TextView) itemView.findViewById(R.id.exercise_reps);
            mCaloriesTextView = (TextView) itemView.findViewById(R.id.exercise_calories);
        }

        public void bind(Exercise exercise) {
            mExercise = exercise;
            mExerciseNameTextView.setText(mExercise.getName());
            mSetsTextView.setText(mExercise.getSet());
            mRepsTextView.setText(mExercise.getReps());
            mCaloriesTextView.setText(String.format("%.2f", mExercise.getCalories()));
        }
    }

    private class ExerciseAdapter extends RecyclerView.Adapter<ExerciseHolder>
    {
        private List<Exercise> mExercises;

        public ExerciseAdapter(List<Exercise> exercises)
        {
            mExercises = exercises;
        }

        @Override
        public ExerciseHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new ExerciseHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(ExerciseHolder holder, int position)
        {
            Exercise exercise = mExercises.get(position);
            holder.bind(exercise);
        }

        @Override
        public int getItemCount()
        {
            return mExercises.size();
        }
    }


}
