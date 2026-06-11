package com.herb.mergedefense.model;

public class Enemy {
    private double x;
    private double y;
    private double health;
    private double maxHealth;
    private EnemyType type;
    private int waypointIndex;
    private boolean alive;

    public Enemy(double startX, double startY, EnemyType type) {
        this.x = startX;
        this.y = startY;
        this.type = type;
        this.health = type.getHealth();
        this.maxHealth = type.getHealth();
        this.waypointIndex = 1;
        this.alive = true;
    }

    public void moveTowards(double targetX, double targetY, double deltaTime) {
        double dx = targetX - x;
        double dy = targetY - y;
        double dist = Math.hypot(dx, dy);
        if (dist > 1) {
            double step = type.getSpeed() * deltaTime;
            dx = dx / dist * step;
            dy = dy / dist * step;
            x += dx;
            y += dy;
        } else {
            x = targetX;
            y = targetY;
        }
    }

    public void takeDamage(double amount) {
        health -= amount;
        if (health <= 0) alive = false;
    }

    public double getX() { return x; }
    public double getY() { return y; }
    public double getHealth() { return health; }
    public double getMaxHealth() { return maxHealth; }
    public EnemyType getType() { return type; }
    public int getWaypointIndex() { return waypointIndex; }
    public void setWaypointIndex(int index) { waypointIndex = index; }
    public boolean isAlive() { return alive; }
    public int getReward() { return type.getReward(); }
}
