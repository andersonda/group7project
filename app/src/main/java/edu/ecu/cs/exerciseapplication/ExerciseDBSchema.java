package edu.ecu.cs.exerciseapplication;

/**
 * Created by danderson on 10/3/17.
 */

public class ExerciseDBSchema {
    public static final class UserTable{
        public static final String NAME = "user";

        public static final class Cols{
            public static final String FIRST = "first";
            public static final String LAST = "last";
            public static final String AGE = "age";
            public static final String WEIGHT = "weight";
            public static final String HEIGHT = "height";
            public static final String GENDER = "gender";
        }
    }

    public static final class WeightTable{
        public static final String NAME = "weight";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String WEIGHT = "weight";
            public static final String DATE = "date";
        }
    }
}
