package edu.ecu.cs.exerciseapplication;

import java.util.Date;
import java.util.UUID;

/**
 * Created by danderson on 10/25/17.
 */

public class Weight {
    private UUID mId;
    private Date mLogTime;
    private Double mWeight;


    public Weight(Double weight){
        this.mId = UUID.randomUUID();
        this.mLogTime = new Date();
        this.mWeight = weight;
    }

    public Weight(){
        this.mId = UUID.randomUUID();
        this.mLogTime = new Date();
        this.mWeight = 0.0;
    }

    public Weight(UUID id){
        mId = id;
        mLogTime = new Date();
    }

    public UUID getmId() {
        return mId;
    }

    public Date getmLogTime() {
        return mLogTime;
    }

    public Double getmWeight() {
        return mWeight;
    }

    public void setmLogTime(Date mLogTime) {
        this.mLogTime = mLogTime;
    }

    public void setmWeight(Double mWeight) {
        this.mWeight = mWeight;
    }
}
