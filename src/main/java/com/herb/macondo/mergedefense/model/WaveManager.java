package com.herb.macondo.mergedefense.model;

import java.util.Random;

public class WaveManager {
    private int currentWave;
    private int enemiesRemainingInWave;
    private int enemiesToSpawn;
    private double spawnTimer;
    private double spawnDelay;
    private boolean waveInProgress;
    private boolean bossWave;
    private Random random;

    public WaveManager() {
        this.currentWave = 1;
        this.spawnDelay = 1.5;
        this.random = new Random();
        this.waveInProgress = false;
        this.bossWave = false;
    }

    public void startWave() {
        waveInProgress = true;
        enemiesToSpawn = calculateEnemiesCount();
        enemiesRemainingInWave = enemiesToSpawn;
        spawnTimer = 0;
        bossWave = (currentWave % 5 == 0);
    }

    private int calculateEnemiesCount() {
        int base = 5;
        int extra = (currentWave - 1) * 1;
        return Math.min(base + extra, 30);
    }

    public void update(double deltaTime, GameModel model) {
        if (!waveInProgress) return;
        if (enemiesToSpawn > 0) {
            spawnTimer += deltaTime;
            if (spawnTimer >= spawnDelay) {
                spawnTimer = 0;
                spawnEnemy(model);
                enemiesToSpawn--;
            }
        }
        if (enemiesToSpawn == 0 && model.getEnemies().isEmpty()) {
            waveInProgress = false;
            currentWave++;
        }
    }

    private void spawnEnemy(GameModel model) {
        EnemyType type = chooseEnemyType();
        model.spawnEnemy(type);
    }

    private EnemyType chooseEnemyType() {
        if (bossWave && enemiesRemainingInWave == calculateEnemiesCount() - enemiesToSpawn + 1) {
            return EnemyType.BOSS;
        }
        double r = random.nextDouble();
        if (currentWave >= 10) {
            if (r < 0.5) return EnemyType.NORMAL;
            else if (r < 0.75) return EnemyType.FAST;
            else return EnemyType.ARMORED;
        } else if (currentWave >= 5) {
            if (r < 0.6) return EnemyType.NORMAL;
            else if (r < 0.85) return EnemyType.FAST;
            else return EnemyType.ARMORED;
        } else {
            if (r < 0.8) return EnemyType.NORMAL;
            else return EnemyType.FAST;
        }
    }

    public int getCurrentWave() { return currentWave; }
    public boolean isWaveInProgress() { return waveInProgress; }
}
