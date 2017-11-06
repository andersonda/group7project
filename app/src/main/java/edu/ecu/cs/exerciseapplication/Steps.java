package edu.ecu.cs.exerciseapplication;

import android.text.format.DateFormat;

import java.util.Date;
import java.util.UUID;

/**
 * Created by delta on 11/6/2017.
 */

public class Steps {
    private UUID mID;
    private int mNumSteps;
    private Date mDate;
    private String mDayOfWeek;

    public Steps(int numSteps){
        this.mNumSteps = numSteps;
        this.mID = UUID.randomUUID();
        this.mDate = new Date();
        this.mDayOfWeek = DateFormat.format("EEEE", mDate).toString();
    }

    public Steps(UUID uuid){
        this.mID = uuid;
    }

    public UUID getID() {
        return mID;
    }

    public int getNumSteps() {
        return mNumSteps;
    }

    public void setNumSteps(int numSteps) {
        mNumSteps = numSteps;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public String getDayOfWeek() {
        return mDayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        mDayOfWeek = dayOfWeek;
    }

    public boolean meetsGoal(int stepGoal){
        return mNumSteps >= stepGoal;
    }
}
