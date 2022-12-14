package com.beval.empirejavafx.config;

public class AppConstants {
    public static final boolean DEBUG_MODE = false;
    public static final String DEBUG_MODE_USERNAME = "test1";
    public static final String DEBUG_MODE_PASSWORD = "1234";

    public static final int CASTLE_BUILDING_IMAGE_WIDTH = 60;
    public static final int CASTLE_BUILDING_IMAGE_HEIGHT = 60;
    public static final int MAP_CASTLE_IMAGE_HEIGHT = 60;
    public static final int MAP_CASTLE_IMAGE_WIDTH = 60;

    //Grid row size and column size shouldn't be lower than image width and height
    public static final int CASTLE_GRID_CELL_ROW_SIZE = 60;
    public static final int CASTLE_GRID_ROWS = 16;
    public static final int CASTLE_GRID_CELL_COLUMN_SIZE = 60;
    public static final int CASTLE_GRID_COLUMNS = 27;

    public static final int MAP_GRID_CELL_ROW_SIZE = 60;
    public static final int MAP_GRID_ROWS = 16;
    public static final int MAP_GRID_CELL_COLUMN_SIZE = 60;
    public static final int MAP_GRID_COLUMNS = 27;

    public static final int TIME_BETWEEN_RENDERS = 1000; //in milliseconds

    public static final String START_ANIMATION_URL =
            "https://empire-html5.goodgamestudios.com/default/ModernStartscreenAnimation_small.mp4";

    private AppConstants() {}
}
