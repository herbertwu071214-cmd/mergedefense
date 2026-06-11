package com.herb.mergedefense.model;

public class Projectile {
    private double x;
    private double y;
    private double targetX;
    private double targetY;
    private double damage;
    private double speed;
    private boolean active;

    public Projectile(double startX, double startY, double targetX, double targetY, double damage) {
        this.x = startX;
        this.y = startY;
        this.targetX = targetX;
        this.targetY = targetY;
        this.damage = damage;
        this.speed = 300;
        this.active = true;
    }

    public void update(double deltaTime) {
        double dx = targetX - x;
        double dy = targetY - y;
        double distance = Math.hypot(dx, dy);
        if (distance <= 1) {
            active = false;
            return;
        }
        double step = speed * deltaTime;
        if (step >= distance) {
            x = targetX;
            y = targetY;
            active = false;
        } else {
            dx /= distance;
            dy /= distance;
            x += dx * step;
            y += dy * step;
        }
    }

    public double getX() { return x; }
    public double getY() { return y; }
    public double getDamage() { return damage; }
    public boolean isActive() { return active; }
}
