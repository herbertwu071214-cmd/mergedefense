package com.herb.mergedefense.model;

public enum Upgrade {
    DAMAGE("Damage", 1.0, 50, 0.2, 10),
    ATTACK_SPEED("Attack Speed", 1.0, 50, -0.07, 10),
    RANGE("Range", 1.0, 50, 0.1, 10),
    COIN_MULTIPLIER("Coin Multiplier", 1.0, 100, 0.2, 5);

    private final String name;
    private double currentMultiplier;
    private int currentLevel;
    private final int baseCost;
    private final double incrementPerLevel;
    private final int maxLevel;

    Upgrade(String name, double initialMultiplier, int baseCost, double incrementPerLevel, int maxLevel) {
        this.name = name;
        this.currentMultiplier = initialMultiplier;
        this.currentLevel = 0;
        this.baseCost = baseCost;
        this.incrementPerLevel = incrementPerLevel;
        this.maxLevel = maxLevel;
    }

    public String getName() { return name; }
    public double getCurrentMultiplier() { return currentMultiplier; }
    public int getCurrentLevel() { return currentLevel; }
    public int getCost() { return (int)(baseCost * (currentLevel + 1)); }
    public boolean isMaxLevel() { return currentLevel >= maxLevel; }

    public void upgrade() {
        if (!isMaxLevel()) {
            currentLevel++;
            currentMultiplier += incrementPerLevel;
            if (ATTACK_SPEED.equals(this) && currentMultiplier < 0.3) currentMultiplier = 0.3;
            if (COIN_MULTIPLIER.equals(this) && currentMultiplier > 3.0) currentMultiplier = 3.0;
        }
    }

    public static void resetAll() {
        for (Upgrade u : values()) {
            u.currentLevel = 0;
            switch (u) {
                case DAMAGE: u.currentMultiplier = 1.0; break;
                case ATTACK_SPEED: u.currentMultiplier = 1.0; break;
                case RANGE: u.currentMultiplier = 1.0; break;
                case COIN_MULTIPLIER: u.currentMultiplier = 1.0; break;
            }
        }
    }
}
