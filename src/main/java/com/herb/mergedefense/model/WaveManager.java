package com.herb.mergedefense.model;

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
    private double waveBreakTimer;
    private final double waveBreakDuration = 5.0;

    public WaveManager() {
        this.currentWave = 1;
        this.spawnDelay = 1.5;
        this.random = new Random();
        this.waveInProgress = false;
        this.bossWave = false;
        this.waveBreakTimer = 0;
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
        int extra = (currentWave - 1) * 2;
        return Math.min(base + extra, 50);
    }

    public void update(double deltaTime, GameModel model) {
        if (!waveInProgress) {
            waveBreakTimer += deltaTime;
            if (waveBreakTimer >= waveBreakDuration) {
                waveBreakTimer = 0;
                startWave();
            }
            return;
        }

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
            waveBreakTimer = 0;
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
            if (r < 0.4) return EnemyType.NORMAL;
            else if (r < 0.7) return EnemyType.FAST;
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
