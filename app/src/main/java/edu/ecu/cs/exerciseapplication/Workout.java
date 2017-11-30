package edu.ecu.cs.exerciseapplication;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by SesanA on 9/19/2017.
 */

public class Workout {
    private UUID mId;
    private String mName;
    private float mTotalCalories;
    private Date mDate;

    private List<Exercise> mExerciseList;

    public Workout(){
        mId = UUID.randomUUID();
        mName = "General Workout";
        mExerciseList = new ArrayList<>();
        mTotalCalories = getTotalCalories();
        mDate = new Date();
    }

    public Workout(UUID uuid){
        mId = uuid;
        mName = "";
        mExerciseList = new ArrayList<>();
        mTotalCalories = getTotalCalories();
        mDate = new Date();
    }

    public UUID getId() {
        return mId;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public float getTotalCalories() {
        mTotalCalories = 0;
        for(Exercise ex : mExerciseList){
            mTotalCalories += ex.getCalories();
        }
        return mTotalCalories;
    }

    public List<Exercise> getExerciseList() {
        return mExerciseList;
    }

    public void setExerciseList(List<Exercise> exercises){
        mExerciseList = exercises;
    }

    public void addNewExercise(Exercise newExercise){
        mExerciseList.add(newExercise);
    }
}

