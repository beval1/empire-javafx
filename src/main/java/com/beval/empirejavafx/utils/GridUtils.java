package com.beval.empirejavafx.utils;

import javafx.scene.Node;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;

import static com.beval.empirejavafx.config.AppConstants.*;

public class GridUtils {
    public static void initializeGridCastle(GridPane gridPane) {
        initializeGrid(gridPane, CASTLE_GRID_COLUMNS, CASTLE_GRID_ROWS,
                CASTLE_GRID_CELL_COLUMN_SIZE, CASTLE_GRID_CELL_ROW_SIZE);
    }

    public static void initializeGridMap(GridPane gridPane) {
        initializeGrid(gridPane, MAP_GRID_COLUMNS, MAP_GRID_ROWS,
                MAP_GRID_CELL_COLUMN_SIZE, MAP_GRID_CELL_ROW_SIZE);
    }

    private static void initializeGrid(GridPane grid, int gridColumns, int gridRows, int columnSize, int rowSize) {
        int numCols = gridColumns;
        int numRows = gridRows;

        for (int i = 0; i < numCols; i++) {
            ColumnConstraints colConstraints = new ColumnConstraints(columnSize);
            grid.getColumnConstraints().add(colConstraints);
        }

        for (int i = 0; i < numRows; i++) {
            RowConstraints rowConstraints = new RowConstraints(rowSize);
            grid.getRowConstraints().add(rowConstraints);
        }

        //this is required because you can't trace click of something that doesn't exist on the grid;
        for (int i = 0; i < numCols; i++) {
            for (int j = 0; j < numRows; j++) {
                addPane(i, j, grid);
            }
        }

        if (DEBUG_MODE) {
            grid.setGridLinesVisible(true);
        }
    }

    private static void addPane(int colIndex, int rowIndex, GridPane grid) {
        Pane pane = new Pane();
        grid.add(pane, colIndex, rowIndex);
    }

    public static Node getNodeFromGridPane(int row, int col, GridPane gridPane) {
        for (Node node : gridPane.getChildren()) {
            Integer columnIndex = GridPane.getColumnIndex(node);
            Integer rowIndex = GridPane.getRowIndex(node);
            if (columnIndex != null && rowIndex != null && columnIndex == col && rowIndex == row) {
                return node;
            }
        }
        return null;
    }

    public static void removeNodeByRowColumnIndex(int row, int column, GridPane gridPane) {
        gridPane.getChildren().removeIf(node -> {
            if (node != null) {
                Integer columnIndex = GridPane.getColumnIndex(node);
                Integer rowIndex = GridPane.getRowIndex(node);
                if (columnIndex != null && rowIndex != null) {
                    return columnIndex == column && rowIndex == row;
                }
            }
            return false;
        });
    }

    public static void addNodeByRowColumnIndex(int coordinateY, int coordinateX, GridPane buildingsGrid, Node node) {
        buildingsGrid.add(node, coordinateX, coordinateY);
    }

    private GridUtils() {
    }
}
