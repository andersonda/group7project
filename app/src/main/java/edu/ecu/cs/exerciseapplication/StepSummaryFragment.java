package edu.ecu.cs.exerciseapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Date;

/**
 * Created by danderson on 11/25/17.
 */

public class StepSummaryFragment extends Fragment {

    private Date mDate;

    private TextView mDateTextView;
    private TextView mGoalTextView;
    private RecyclerView mStepsRecyclerView;

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

        return v;
    }
}
