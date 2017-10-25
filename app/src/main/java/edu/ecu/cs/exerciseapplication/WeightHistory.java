package edu.ecu.cs.exerciseapplication;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * Created by danderson on 10/25/17.
 */

public class WeightHistory {
    private static WeightHistory sWeightHistory;

    private List<Weight> mWeights;

    public static WeightHistory get(Context context){
        if(sWeightHistory == null){
            sWeightHistory = new WeightHistory(context);
        }
        return sWeightHistory;
    }

    private WeightHistory(Context context) {
        mWeights = new ArrayList<>();

        // dummy data to be replaced with database data
        Random r = new Random();
        for(int i = 0; i < 100; i++){
            Weight weight = new Weight(r.nextDouble());
            mWeights.add(weight);
        }
    }

    public List<Weight> getWeights() {
        return mWeights;
    }

    public Weight getWeight(UUID id){
        for(Weight weight : mWeights){
            if(weight.getmId().equals(id)){
                return weight;
            }
        }
        return null;
    }
}
