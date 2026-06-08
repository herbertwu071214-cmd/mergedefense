package com.herb.macondo.mergedefense.model;

public class GameModel {
    private int gridWidth = 10;
    private int gridHeight = 6;
    private double cellSize = 60;
    private double boardOffsetX = 100;
    private double boardOffsetY = 100;
    private Path path;
    private Tower[][] towers;

    public GameModel() {
        this.path = new Path();
        this.towers = new Tower[gridHeight][gridWidth];
    }

    public int getGridWidth() { return gridWidth; }
    public int getGridHeight() { return gridHeight; }
    public double getCellSize() { return cellSize; }
    public double getBoardOffsetX() { return boardOffsetX; }
    public double getBoardOffsetY() { return boardOffsetY; }
    public Path getPath() { return path; }

    public boolean isCellOnPath(int row, int col) {
        return path.isCellOnPath(row, col, this);
    }

    public boolean canPlaceTower(int row, int col) {
        return row >= 0 && row < gridHeight && col >= 0 && col < gridWidth
                && towers[row][col] == null
                && !isCellOnPath(row, col);
    }

    public void placeTower(int row, int col, TowerType type) {
        if (canPlaceTower(row, col)) {
            towers[row][col] = new Tower(row, col, type);
        }
    }

    public Tower getTower(int row, int col) {
        if (row >= 0 && row < gridHeight && col >= 0 && col < gridWidth) {
            return towers[row][col];
        }
        return null;
    }

    public void mergeTowers(int row1, int col1, int row2, int col2) {
        Tower t1 = getTower(row1, col1);
        Tower t2 = getTower(row2, col2);
        if (t1 == null || t2 == null) return;
        if (t1.getType().getLevel() != t2.getType().getLevel()) return;
        int currentLevel = t1.getType().getLevel();
        if (currentLevel >= 5) return;

        towers[row1][col1] = null;
        towers[row2][col2] = null;

        TowerType newType = TowerType.fromLevel(currentLevel + 1);
        towers[row1][col1] = new Tower(row1, col1, newType);
    }
}
