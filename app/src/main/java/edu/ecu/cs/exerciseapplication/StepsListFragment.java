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
import android.widget.ImageView;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.PointsGraphSeries;

import java.util.Date;
import java.util.List;

/**
 * Created by delta on 11/6/2017.
 */

public class StepsListFragment extends Fragment {

    private static final String DIALOG_DAY = "DialogDay";
    private static final String DIALOG_CLEAR = "DialogClear";

    private static final int REQUEST_DAY = 0;
    private static final int REQUEST_CLEAR = 1;

    private RecyclerView mStepsRecyclerView;
    private StepsAdapter mAdapter;

    private GraphView mGraphView;
    private PointsGraphSeries<DataPoint> mSeries = new PointsGraphSeries<>();

    private final android.os.Handler mHandler = new android.os.Handler();
    private Runnable mUpdateGraph;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_graph_list, container, false);

        mStepsRecyclerView = view.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setReverseLayout(true);
        mStepsRecyclerView.setLayoutManager(layoutManager);

        mGraphView = view.findViewById(R.id.graph_view);
        mGraphView.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getActivity()));
        mGraphView.addSeries(mSeries);
        mSeries.setShape(PointsGraphSeries.Shape.POINT);
        GridLabelRenderer gridLabelRenderer = mGraphView.getGridLabelRenderer();
        gridLabelRenderer.setNumHorizontalLabels(3);
        gridLabelRenderer.setNumVerticalLabels(5);
        gridLabelRenderer.setPadding(48);

        updateUI();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI() {
        StepHistory history = StepHistory.get(getActivity());
        List<Steps> steps = history.getSteps();

        final DataPoint[] points = new DataPoint[steps.size()];
        for(int i = 0; i < steps.size(); i++){
            Date date = steps.get(i).getDate();
            int stepsCount = steps.get(i).getNumSteps();
            points[i] = new DataPoint(date, stepsCount);
        }

        mUpdateGraph = new Runnable() {
            @Override
            public void run() {
                mSeries.resetData(points);
                mHandler.post(this);
            }
        };

        mHandler.post(mUpdateGraph);

        mGraphView.getViewport().setMinX(steps.get(0).getDate().getTime());
        mGraphView.getViewport().setMaxX(steps.get(steps.size() - 1).getDate().getTime());
        mGraphView.getViewport().setXAxisBoundsManual(true);
        mGraphView.getGridLabelRenderer().setHumanRounding(false);

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
    }

    private class StepsHolder extends RecyclerView.ViewHolder{
        private TextView mStepsTextView;
        private TextView mDateTextView;
        private ImageView mStepsIcon;

        private Steps mSteps;

        public StepsHolder(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.list_item_weight, parent, false));
            mStepsTextView = itemView.findViewById(R.id.weight_number);
            mDateTextView = itemView.findViewById(R.id.weight_date);
            mStepsIcon = itemView.findViewById(R.id.weight_icon);
        }

        public void bind(Steps steps){
            mSteps = steps;
            mStepsTextView.setText("" + mSteps.getNumSteps());
            String format = "MMMM dd, yyyy";
            String formattedDate = DateFormat.format(format, mSteps.getDate()).toString();
            mDateTextView.setText(formattedDate);

            int stepGoal = StepHistory.get(getActivity()).getDailyStepGoal();
            if(steps.getNumSteps() < stepGoal){
                mStepsIcon.setBackgroundResource(R.mipmap.ic_x);
            }
            else{
                mStepsIcon.setBackgroundResource(R.mipmap.ic_check);
            }
        }
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
            Steps steps = mSteps.get(position);
            holder.bind(steps);
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
