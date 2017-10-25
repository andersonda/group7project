package edu.ecu.cs.exerciseapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by danderson on 10/25/17.
 */

public class WeightListFragment extends Fragment{

    private RecyclerView mWeightRecyclerView;
    private WeightAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weight_list, container, false);

        mWeightRecyclerView = view.findViewById(R.id.weight_recycler_view);
        mWeightRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    private void updateUI() {
        WeightHistory history = WeightHistory.get(getActivity());
        List<Weight> weights = history.getWeights();

        mAdapter = new WeightAdapter(weights);
        mWeightRecyclerView.setAdapter(mAdapter);
    }

    private class WeightHolder extends RecyclerView.ViewHolder{
        private TextView mWeightTextView;
        private TextView mDateTextView;

        private Weight mWeight;

        public WeightHolder(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.list_item_weight, parent, false));
            mWeightTextView = itemView.findViewById(R.id.weight_number);
            mDateTextView = itemView.findViewById(R.id.weight_date);
        }

        public void bind(Weight weight){
            mWeight = weight;
            mWeightTextView.setText("" + mWeight.getmWeight());
            mDateTextView.setText(mWeight.getmLogTime().toString());
        }
    }

    private class WeightAdapter extends RecyclerView.Adapter<WeightHolder>{
        private List<Weight> mWeights;

        public WeightAdapter(List<Weight> weights){
            mWeights = weights;
        }

        @Override
        public WeightHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            return new WeightHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(WeightHolder holder, int position) {
            Weight weight = mWeights.get(position);
            holder.bind(weight);
        }

        @Override
        public int getItemCount() {
            return mWeights.size();
        }
    }
}
