package com.herb.macondo.mergedefense.model;

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
    public void setAttackTimer(double timer) { attackTimer = timer; }
    public void reduceAttackTimer(double deltaTime) { attackTimer -= deltaTime; }
    public void resetAttackTimer() { attackTimer = type.getAttackCooldown(); }
    public boolean canAttack() { return attackTimer <= 0; }
    public void upgrade() {
        int nextLevel = type.getLevel() + 1;
        if (nextLevel <= 5) {
            type = TowerType.fromLevel(nextLevel);
        }
    }
}
