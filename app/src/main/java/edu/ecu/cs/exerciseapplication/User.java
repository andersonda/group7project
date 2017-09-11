package edu.ecu.cs.exerciseapplication;

/**
 * Created by danderson on 9/11/17.
 */

public class User {

    private String mFirstName;
    private String mLastName;

    /**
     * weight in kilograms
     */
    private double mWeight;

    /**
     * height in meters
     */
    private double mHeight;
    private int mAge;

    private static final double KG_TO_LB = 2.20462;
    private static final double M_TO_IN = 39.3701;


    public String getmFirstName() {
        return mFirstName;
    }

    public void setmFirstName(String mFirstName) {
        this.mFirstName = mFirstName;
    }

    public String getmLastName() {
        return mLastName;
    }

    public void setmLastName(String mLastName) {
        this.mLastName = mLastName;
    }

    public double getmWeight() {
        return mWeight;
    }


    /**
     * coverts mass in kilograms to mass in pounds
     * @return
     */
    public double getmWeightInPounds(){
        return KG_TO_LB * mWeight;
    }

    public void setmWeight(double mWeight) {
        this.mWeight = mWeight;
    }

    public double getmHeight() {
        return mHeight;
    }

    public ImperialHeight getmHeightImperial(){
        int totalInches = (int)(M_TO_IN * mHeight);
        return new ImperialHeight(totalInches / 12, totalInches % 12);
    }

    public void setmHeight(double mHeight) {
        this.mHeight = mHeight;
    }

    public int getmAge() {
        return mAge;
    }

    public void setmAge(int mAge) {
        this.mAge = mAge;
    }

    /**
     * BMI = weight (in kilograms) divided by height (in centimeters) squared.
     * @param height
     * @param weight
     * @return
     */
    public double calculateBMI(double height, double weight){
        return mWeight / (mHeight * mHeight);
    }

    private class ImperialHeight{
        private int mFeet;
        private int mInches;

        public ImperialHeight(int mFeet, int mInches) {
            this.mFeet = mFeet;
            this.mInches = mInches;
        }

        public int getmFeet() {
            return mFeet;
        }

        public void setmFeet(int mFeet) {
            this.mFeet = mFeet;
        }

        public int getmInches() {
            return mInches;
        }

        public void setmInches(int mInches) {
            this.mInches = mInches;
        }
    }
}
