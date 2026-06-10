package com.herb.macondo.mergedefense.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GameModel {
    private int gridWidth = 10;
    private int gridHeight = 6;
    private double cellSize = 60;
    private double boardOffsetX = 100;
    private double boardOffsetY = 100;
    private Path path;
    private Tower[][] towers;
    private List<Enemy> enemies;
    private List<Projectile> projectiles;
    private WaveManager waveManager;
    private int coins;
    private int lives;

    public GameModel() {
        this.path = new Path();
        this.towers = new Tower[gridHeight][gridWidth];
        this.enemies = new ArrayList<>();
        this.projectiles = new ArrayList<>();
        this.waveManager = new WaveManager();
        this.coins = 200;
        this.lives = 20;
    }

    public int getGridWidth() { return gridWidth; }
    public int getGridHeight() { return gridHeight; }
    public double getCellSize() { return cellSize; }
    public double getBoardOffsetX() { return boardOffsetX; }
    public double getBoardOffsetY() { return boardOffsetY; }
    public Path getPath() { return path; }
    public List<Enemy> getEnemies() { return enemies; }
    public List<Projectile> getProjectiles() { return projectiles; }
    public WaveManager getWaveManager() { return waveManager; }
    public int getCoins() { return coins; }
    public int getLives() { return lives; }

    public void reduceLives(int amount) {
        lives -= amount;
        if (lives < 0) lives = 0;
    }

    public void addCoins(int amount) {
        coins += amount;
    }

    public boolean buyTower() {
        int cost = 50;
        if (coins >= cost) {
            coins -= cost;
            return true;
        }
        return false;
    }

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

    public void spawnEnemy(EnemyType type) {
        Waypoint start = path.getStart();
        enemies.add(new Enemy(start.getX(), start.getY(), type));
    }

    public void updateEnemies(double deltaTime) {
        List<Waypoint> waypoints = path.getWaypoints();
        for (Enemy e : enemies) {
            int nextIdx = e.getWaypointIndex();
            if (nextIdx >= waypoints.size()) {
                reduceLives(1);
                enemies.remove(e);
                continue;
            }
            Waypoint target = waypoints.get(nextIdx);
            e.moveTowards(target.getX(), target.getY(), deltaTime);
            if (Math.hypot(e.getX() - target.getX(), e.getY() - target.getY()) < 5) {
                e.setWaypointIndex(nextIdx + 1);
            }
        }
    }

    public void updateTowerAttacks(double deltaTime) {
        for (int row = 0; row < gridHeight; row++) {
            for (int col = 0; col < gridWidth; col++) {
                Tower t = towers[row][col];
                if (t != null) {
                    t.reduceAttackTimer(deltaTime);
                    if (t.canAttack()) {
                        Enemy target = t.findNearestEnemy(enemies);
                        if (target != null) {
                            double towerX = boardOffsetX + col * cellSize + cellSize/2;
                            double towerY = boardOffsetY + row * cellSize + cellSize/2;
                            projectiles.add(new Projectile(towerX, towerY, target.getX(), target.getY(), t.getType().getDamage()));
                            t.resetAttackTimer();
                        }
                    }
                }
            }
        }
    }

    public void processProjectileHits() {
        Iterator<Projectile> pIt = projectiles.iterator();
        while (pIt.hasNext()) {
            Projectile p = pIt.next();
            p.update(1.0 / 60.0);
            if (!p.isActive()) {
                pIt.remove();
                continue;
            }
            boolean hit = false;
            Iterator<Enemy> eIt = enemies.iterator();
            while (eIt.hasNext()) {
                Enemy e = eIt.next();
                double dx = p.getX() - e.getX();
                double dy = p.getY() - e.getY();
                if (Math.hypot(dx, dy) < 15) {
                    e.takeDamage(p.getDamage());
                    if (!e.isAlive()) {
                        coins += e.getReward();
                        eIt.remove();
                    }
                    hit = true;
                    break;
                }
            }
            if (hit) pIt.remove();
        }
    }

    public void startGame() {
        waveManager.startWave();
    }
}
