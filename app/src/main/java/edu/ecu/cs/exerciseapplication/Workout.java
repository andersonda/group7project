package edu.ecu.cs.exerciseapplication;

import java.util.List;

/**
 * Created by SesanA on 9/19/2017.
 */

public class Workout {
    private List<Exercise> mExerciseList;

    private class Exercise{

        private int set;
        private int reps;
        private float calories;

        public Exercise(int set, int reps, float calories) {
            this.set = set;
            this.reps = reps;
            this.calories = calories;
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
}

