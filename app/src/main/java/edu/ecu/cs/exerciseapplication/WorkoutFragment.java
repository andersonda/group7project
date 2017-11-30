package edu.ecu.cs.exerciseapplication;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;

/**
 * Created by hunter on 9/16/17.
 */

public class WorkoutFragment extends Fragment {

    private static final String ARG_WORKOUT_ID = "edu.ecu.cd.exerciseapplication.workout_id";
    private Workout mWorkout;
    private TextView mWorkoutName;
    private TextView mWorkoutDate;
    private Button mAddNewExercise;
    private RecyclerView mExerciseRecyclerView;
    private ExerciseAdapter mExerciseAdapter;

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

        List<Exercise> exercises = WorkoutData.getWorkoutData(getActivity()).getExercises(workoutId);
        mWorkout.setExerciseList(exercises);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_workout, container, false);

        mWorkoutName = v.findViewById(R.id.workout_name);
        mWorkoutName.setText(mWorkout.getName());
        mWorkoutName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mWorkout.setName(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mWorkoutDate = v.findViewById(R.id.workout_date);
        mWorkoutDate.setKeyListener(null);
        mWorkoutDate.setText(formatDate());

        mExerciseRecyclerView = v.findViewById(R.id.exercise_recycler_view);
        mExerciseRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return v;
    }

    private void updateUI() {
        List<Exercise> exercises = mWorkout.getExerciseList();
        Log.i("Size",String.valueOf(exercises.size()));
        for(Exercise exercise : exercises){
            Log.i("Exercise",exercise.getName());
            Log.i("Exercise",String.format("%d",exercise.getSet()));
        }
        if (mExerciseAdapter == null) {
            mExerciseAdapter = new ExerciseAdapter(exercises);
            mExerciseRecyclerView.setAdapter(mExerciseAdapter);
        } else {
            mExerciseAdapter.setExercises(exercises);
            mExerciseAdapter.notifyDataSetChanged();
        }
    }

    private class ExerciseHolder extends RecyclerView.ViewHolder {
        private Exercise mExercise;
        private EditText mExerciseName;
        private EditText mExerciseSets;
        private EditText mExerciseReps;
        private EditText mExerciseCalories;

        public ExerciseHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_exercise, parent, false));

            mExerciseName = itemView.findViewById(R.id.exercise_name);
            mExerciseName.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    mExercise.setName(charSequence.toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            mExerciseSets = itemView.findViewById(R.id.exercise_sets);
            mExerciseSets.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (!charSequence.toString().equals("")) {
                        mExercise.setSet(Integer.parseInt(charSequence.toString()));
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            mExerciseReps = itemView.findViewById(R.id.exercise_reps);
            mExerciseReps.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (!charSequence.toString().equals("")) {
                        mExercise.setReps(Integer.parseInt(charSequence.toString()));
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            mExerciseCalories = itemView.findViewById(R.id.exercise_calories);
            mExerciseCalories.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (!charSequence.toString().equals("")) {
                        mExercise.setCalories(Float.parseFloat(charSequence.toString()));
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        }

        public void bind(Exercise exercise) {
            mExercise = exercise;
            mExerciseName.setText(mExercise.getName());
            mExerciseSets.setText(String.format("%d",mExercise.getSet()));
            mExerciseReps.setText(String.format("%d",mExercise.getReps()));
            mExerciseCalories.setText(String.format("%.2f",mExercise.getCalories()));
        }
    }


    private class ExerciseAdapter extends RecyclerView.Adapter<ExerciseHolder> {
        private List<Exercise> mExercises;

        public ExerciseAdapter(List<Exercise> exercises) {
            mExercises = exercises;
        }

        @Override
        public ExerciseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new ExerciseHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(ExerciseHolder holder, int position) {
            Exercise exercise = mExercises.get(position);
            holder.bind(exercise);
        }

        @Override
        public int getItemCount() {
            return mExercises.size();
        }

        public void setExercises(List<Exercise> exercises) {
            mExercises = exercises;
        }
    }


    @Override
    public void onResume()
    {
        super.onResume();
        updateUI();
    }

    private String formatDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yy");
        String formattedDate = dateFormat.format(mWorkout.getDate());
        return formattedDate;
    }
}
