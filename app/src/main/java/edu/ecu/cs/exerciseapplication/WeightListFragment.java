package edu.ecu.cs.exerciseapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by danderson on 10/25/17.
 */

public class WeightListFragment extends Fragment{

    private static final String DIALOG_DATE = "DialogDate";

    private static final int REQUEST_WEIGHT = 0;

    private RecyclerView mWeightRecyclerView;
    private WeightAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weight_list, container, false);

        mWeightRecyclerView = view.findViewById(R.id.weight_recycler_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setReverseLayout(true);
        mWeightRecyclerView.setLayoutManager(layoutManager);

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
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_weight_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.new_weight:
                FragmentManager manager = getFragmentManager();
                LogWeightFragment dialog = LogWeightFragment.newInstance();
                dialog.setTargetFragment(WeightListFragment.this, REQUEST_WEIGHT);
                dialog.show(manager, DIALOG_DATE);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateUI() {
        WeightHistory history = WeightHistory.get(getActivity());
        List<Weight> weights = history.getWeights();

        if(mAdapter == null){
            mAdapter = new WeightAdapter(weights);
            mWeightRecyclerView.setAdapter(mAdapter);
        } else{
            mAdapter.setWeights(weights);
            mAdapter.notifyDataSetChanged();
        }
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
            mDateTextView.setText(mWeight.getmLogTime().toString());
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
