package com.herb.macondo.mergedefense.model;

import javafx.scene.paint.Color;

public enum TowerType {
    LEVEL1(1, 10, 100, 1.0, Color.YELLOW),
    LEVEL2(2, 20, 120, 0.9, Color.GREEN),
    LEVEL3(3, 35, 140, 0.8, Color.BLUE),
    LEVEL4(4, 55, 160, 0.7, Color.PURPLE),
    LEVEL5(5, 80, 180, 0.6, Color.RED);

    private final int level;
    private final double damage;
    private final double range;
    private final double attackCooldown;
    private final Color color;

    TowerType(int level, double damage, double range, double attackCooldown, Color color) {
        this.level = level;
        this.damage = damage;
        this.range = range;
        this.attackCooldown = attackCooldown;
        this.color = color;
    }

    public int getLevel() { return level; }
    public double getDamage() { return damage; }
    public double getRange() { return range; }
    public double getAttackCooldown() { return attackCooldown; }
    public Color getColor() { return color; }

    public static TowerType fromLevel(int level) {
        for (TowerType t : values()) {
            if (t.level == level) return t;
        }
        return LEVEL1;
    }
}
