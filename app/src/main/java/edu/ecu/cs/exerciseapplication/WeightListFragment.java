package edu.ecu.cs.exerciseapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
 * Created by danderson on 10/25/17.
 */

public class WeightListFragment extends Fragment{

    private static final String DIALOG_DATE = "DialogDate";
    private static final String DIALOG_CLEAR = "DialogClear";

    private static final int REQUEST_WEIGHT = 0;
    private static final int REQUEST_CLEAR = 1;

    private RecyclerView mWeightRecyclerView;
    private WeightAdapter mAdapter;

    private GraphView mGraphView;
    private PointsGraphSeries<DataPoint> mSeries = new PointsGraphSeries<>();

    private final android.os.Handler mHandler = new android.os.Handler();
    private Runnable mUpdateGraph;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_graph_list, container, false);

        mWeightRecyclerView = view.findViewById(R.id.weight_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setReverseLayout(true);
        mWeightRecyclerView.setLayoutManager(layoutManager);

        mGraphView = view.findViewById(R.id.weight_graph);
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != Activity.RESULT_OK){
            return;
        }

        if(requestCode == REQUEST_WEIGHT){
            WeightHistory.get(getActivity()).addWeight(
                    new Weight((Double)data.getSerializableExtra(LogWeightFragment.EXTRA_WEIGHT)));

            updateUI();
        }

        if(requestCode == REQUEST_CLEAR){
            WeightHistory history = WeightHistory.get(getActivity());
            List<Weight> weights = history.getWeights();
            WeightHistory.get(getActivity()).clearWeightHistory(weights.get(weights.size() - 1).getmId());
            updateUI();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_weight_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        FragmentManager manager = getFragmentManager();
        switch(item.getItemId()){
            case R.id.new_weight:
                LogWeightFragment logDialog = LogWeightFragment.newInstance();
                logDialog.setTargetFragment(WeightListFragment.this, REQUEST_WEIGHT);
                logDialog.show(manager, DIALOG_DATE);
                return true;
            case R.id.delete_all:
                ClearWeightHistoryFragment clearDialog = ClearWeightHistoryFragment.newInstance();
                clearDialog.setTargetFragment(WeightListFragment.this, REQUEST_CLEAR);
                clearDialog.show(manager, DIALOG_CLEAR);

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateUI() {
        WeightHistory history = WeightHistory.get(getActivity());
        List<Weight> weights = history.getWeights();

        final DataPoint[] points = new DataPoint[weights.size()];
        for(int i = 0; i < weights.size(); i++){
            Date date = weights.get(i).getmLogTime();
            Double weight = weights.get(i).getmWeight();
            points[i] = new DataPoint(date, weight);
        }

        mUpdateGraph = new Runnable() {
            @Override
            public void run() {
                mSeries.resetData(points);
                mHandler.post(this);
            }
        };

        mHandler.post(mUpdateGraph);

        mGraphView.getViewport().setMinX(weights.get(0).getmLogTime().getTime());
        mGraphView.getViewport().setMaxX(weights.get(weights.size() - 1).getmLogTime().getTime());
        mGraphView.getViewport().setXAxisBoundsManual(true);
        mGraphView.getGridLabelRenderer().setHumanRounding(false);

        if(mAdapter == null){
            mAdapter = new WeightAdapter(weights);
            mWeightRecyclerView.setAdapter(mAdapter);
        } else{
            mAdapter.setWeights(weights);
            mAdapter.notifyDataSetChanged();
        }

        mWeightRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                // Call smooth scroll
                mWeightRecyclerView.smoothScrollToPosition(mAdapter.getItemCount());
            }
        });
    }

    private class WeightHolder extends RecyclerView.ViewHolder{
        private TextView mWeightTextView;
        private TextView mDateTextView;
        private ImageView mWeightIcon;

        private Weight mWeight;
        private Double mLastWeight;

        public WeightHolder(LayoutInflater inflater, ViewGroup parent, Double lastWeight){
            super(inflater.inflate(R.layout.list_item_weight, parent, false));
            mWeightTextView = itemView.findViewById(R.id.weight_number);
            mDateTextView = itemView.findViewById(R.id.weight_date);
            mWeightIcon = itemView.findViewById(R.id.weight_icon);
            mLastWeight = lastWeight;
        }

        public void bind(Weight weight){
            mWeight = weight;
            mWeightTextView.setText("" + mWeight.getmWeight());
            String format = "MMMM dd, yyyy";
            String formattedDate = DateFormat.format(format, mWeight.getmLogTime()).toString();
            mDateTextView.setText(formattedDate);
            if(weight.getmWeight() < mLastWeight){
                mWeightIcon.setBackgroundResource(R.mipmap.ic_weight_decrease);
            }
            else{
                mWeightIcon.setBackgroundResource(R.mipmap.ic_weight_increase);
            }
        }
    }

    private class WeightAdapter extends RecyclerView.Adapter<WeightHolder>{
        private List<Weight> mWeights;

        private Double lastWeight = 0.0;

        public WeightAdapter(List<Weight> weights){
            mWeights = weights;
        }

        @Override
        public WeightHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            return new WeightHolder(layoutInflater, parent, lastWeight);
        }

        @Override
        public void onBindViewHolder(WeightHolder holder, int position) {
            Weight weight = mWeights.get(position);
            lastWeight = weight.getmWeight();
            holder.bind(weight);
        }

        @Override
        public int getItemCount() {
            return mWeights.size();
        }

        public void setWeights(List<Weight> weights){
            mWeights = weights;
        }
    }
}
