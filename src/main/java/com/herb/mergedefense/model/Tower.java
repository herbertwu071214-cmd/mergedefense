package com.herb.mergedefense.model;

import java.util.List;

public class Tower {
    private int row;
    private int col;
    private TowerType type;
    private double attackTimer;

    public Tower(int row, int col, TowerType type) {
        this.row = row;
        this.col = col;
        this.type = type;
        this.attackTimer = 0;
    }

    public int getRow() { return row; }
    public int getCol() { return col; }
    public TowerType getType() { return type; }
    public double getAttackTimer() { return attackTimer; }
    public void reduceAttackTimer(double deltaTime) { attackTimer -= deltaTime; }
    public boolean canAttack() { return attackTimer <= 0; }
    public void setAttackTimer(double time) { attackTimer = time; }

    public Enemy findNearestEnemy(List<Enemy> enemies, double rangeMultiplier) {
        double towerCenterX = col * 60 + 100 + 30;
        double towerCenterY = row * 60 + 100 + 30;
        Enemy nearest = null;
        double minDist = type.getRange() * rangeMultiplier;
        for (Enemy e : enemies) {
            double dx = e.getX() - towerCenterX;
            double dy = e.getY() - towerCenterY;
            double dist = Math.hypot(dx, dy);
            if (dist < minDist) {
                minDist = dist;
                nearest = e;
            }
        }
        return nearest;
    }
}
