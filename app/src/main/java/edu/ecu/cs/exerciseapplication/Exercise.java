package edu.ecu.cs.exerciseapplication;

import java.util.UUID;

/**
 * Created by hunter on 10/26/17.
 */

public class Exercise{
    private String name;
    private int set;
    private int reps;
    private float calories;

    public Exercise(String name, int set, int reps, float calories) {
        this.name = name;
        this.set = set;
        this.reps = reps;
        this.calories = calories;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSet() {
        return set;
    }

    public void setSet(int set) {
        this.set = set;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public float getCalories() {
        return calories;
    }

    public void setCalories(float calories) {
        this.calories = calories;
    }
}
