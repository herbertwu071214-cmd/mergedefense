package com.herb.mergedefense.model;

import javafx.scene.paint.Color;

public enum EnemyType {
    NORMAL(30, 50, 20, Color.ORANGERED),
    FAST(20, 80, 15, Color.YELLOW),
    ARMORED(60, 30, 30, Color.GRAY),
    BOSS(200, 40, 100, Color.DARKRED);

    private final double health;
    private final double speed;
    private final int reward;
    private final Color color;

    EnemyType(double health, double speed, int reward, Color color) {
        this.health = health;
        this.speed = speed;
        this.reward = reward;
        this.color = color;
    }

    public double getHealth() { return health; }
    public double getSpeed() { return speed; }
    public int getReward() { return reward; }
    public Color getColor() { return color; }
}
