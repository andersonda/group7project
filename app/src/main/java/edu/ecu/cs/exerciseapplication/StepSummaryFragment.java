package edu.ecu.cs.exerciseapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

/**
 * Created by danderson on 11/25/17.
 */

public class StepSummaryFragment extends Fragment {

    private Date mDate;

    private TextView mDateTextView;
    private TextView mGoalTextView;
    private RecyclerView mStepsRecyclerView;
    private StepsAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDate = (Date) getActivity().getIntent().getSerializableExtra(StepSummaryActivity.EXTRA_DATE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_step_summary, container, false);

        String format = "MMMM dd, yyyy";
        String formattedDate = DateFormat.format(format, mDate).toString();
        mDateTextView = v.findViewById(R.id.date_text_view);
        mDateTextView.setText(getString(R.string.step_summary_for_date, formattedDate));

        mGoalTextView = v.findViewById(R.id.goal_text_view);
        mStepsRecyclerView = v.findViewById(R.id.steps_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setReverseLayout(true);
        mStepsRecyclerView.setLayoutManager(layoutManager);

        updateUI();

        return v;
    }

    private class StepsHolder extends RecyclerView.ViewHolder{

        private TextView mStepsTextView;

        private Steps mSteps;

        public StepsHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_walk, parent, false));
            mStepsTextView = itemView.findViewById(R.id.step_count);
        }

        public void bind(Steps steps){
            mSteps = steps;

            String format = "h:m a";
            String formattedDate = DateFormat.format(format, mSteps.getDate()).toString();
            mStepsTextView.setText(getString(R.string.steps, steps.getNumSteps(), formattedDate));
        }
    }

    private void updateUI(){
        StepHistory history = StepHistory.get(getActivity());
        List<Steps> steps = history.getWalksOnDate(mDate);

        if(mAdapter == null){
            mAdapter = new StepsAdapter(steps);
            mStepsRecyclerView.setAdapter(mAdapter);
        } else{
            mAdapter.setSteps(steps);
            mAdapter.notifyDataSetChanged();
        }

        mStepsRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                // Call smooth scroll
                mStepsRecyclerView.smoothScrollToPosition(mAdapter.getItemCount());
            }
        });

        mGoalTextView.setText(getString(R.string.step_goal_fraction, history.getTotalStepsOnDate(mDate),
                history.getDailyStepGoal()));
    }

    private class StepsAdapter extends RecyclerView.Adapter<StepsHolder>{

        private List<Steps> mSteps;

        public StepsAdapter(List<Steps> steps){
            mSteps = steps;
        }

        @Override
        public StepsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new StepsHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(StepsHolder holder, int position) {
            Steps stepsEntry = mSteps.get(position);
            holder.bind(stepsEntry);
        }

        @Override
        public int getItemCount() {
            return mSteps.size();
        }

        public void setSteps(List<Steps> steps){
            mSteps = steps;
        }
    }
}
