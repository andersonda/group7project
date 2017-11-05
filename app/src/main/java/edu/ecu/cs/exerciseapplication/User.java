package edu.ecu.cs.exerciseapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.UUID;

import java.io.File;
import java.util.UUID;

/**
 * Created by danderson on 9/11/17.
 */

public class User{

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

    private boolean mIsMale;

    private static final double KG_TO_LB = 2.20462;
    private static final double M_TO_IN = 39.3701;

    public User(String mFirstName, String mLastName, double mWeight, double mHeight, int mAge, boolean mIsMale) {
        this.mFirstName = mFirstName;
        this.mLastName = mLastName;
        this.mWeight = mWeight;
        this.mHeight = mHeight;
        this.mAge = mAge;
        this.mIsMale = mIsMale;
    }

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

    public double getmWeightImperial(){
        return convertKGtoLB(mWeight);
    }

    public void setmWeight(double mWeight) {
        this.mWeight = mWeight;
    }

    public double getmHeight() {
        return mHeight;
    }

    public ImperialHeight getmHeightImperial(){
        return convertMtoF(mHeight);
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

    public boolean ismIsMale() {
        return mIsMale;
    }

    public void setmIsMale(boolean mIsMale) {
        this.mIsMale = mIsMale;
    }

    /**
     * BMI = weight(in kilograms) divided by height(in meters) squared.
     * @param height
     * @param weight
     * @return
     */
    public double calculateBMI(double height, double weight){
        return mWeight / (mHeight * mHeight);
    }

    private static class ImperialHeight{
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

    // conversion functions. these may be better in their own "measurement" class eventually

    public static ImperialHeight convertMtoF(double meters){
        int totalInches = (int)(meters * M_TO_IN);
        int feet = totalInches / 12;
        int inches = totalInches % 12;
        return new ImperialHeight(feet, inches);
    }

    public static double convertFtoM(ImperialHeight iH){
        int totalInches = iH.getmFeet() * 12 + iH.getmInches();
        return totalInches / M_TO_IN;
    }

    public static double convertKGtoLB(double kg){
        return kg * KG_TO_LB;
    }

    public static double convertLBtoKG(double lb){
        return lb / KG_TO_LB;
    }

    private String getPhotoFilename(){
        return "profile_picture.jpg";
    }

    public File getPhotoFile(Context context){
        File filesDir = context.getFilesDir();
        return new File(filesDir, this.getPhotoFilename());
    }
}
