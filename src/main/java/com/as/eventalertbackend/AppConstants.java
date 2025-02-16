package com.as.eventalertbackend;

public abstract class AppConstants {

    public static final int LENGTH_8 = 8;
    public static final int LENGTH_25 = 25;
    public static final int LENGTH_50 = 50;
    public static final int LENGTH_100 = 100;
    public static final int LENGTH_1000 = 1000;

    public static final String PHONE_NUMBER_PATTERN = "^[- +()0-9]{10,25}$";

    public static final int MIN_RADIUS = 1;
    public static final int MAX_RADIUS = 10_000;
    public static final int MAX_YEARS_INTERVAL = 2;
    public static final int MAX_PAGE_SIZE = 100;

}
