package com.herb.macondo.mergedefense.model;

public class GameModel {
    private int gridWidth = 10;
    private int gridHeight = 6;
    private double cellSize = 60;
    private double boardOffsetX = 100;
    private double boardOffsetY = 100;
    private Path path;

    public GameModel() {
        this.path = new Path();
    }

    public int getGridWidth() { return gridWidth; }
    public int getGridHeight() { return gridHeight; }
    public double getCellSize() { return cellSize; }
    public double getBoardOffsetX() { return boardOffsetX; }
    public double getBoardOffsetY() { return boardOffsetY; }
    public Path getPath() { return path; }
}
