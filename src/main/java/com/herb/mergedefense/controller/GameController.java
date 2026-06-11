package com.herb.mergedefense.controller;

import com.herb.mergedefense.input.InputHandler;
import com.herb.mergedefense.model.GameModel;
import com.herb.mergedefense.model.Tower;
import com.herb.mergedefense.model.TowerType;
import com.herb.mergedefense.model.Upgrade;
import com.herb.mergedefense.view.GameView;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseButton;

public class GameController {
    private GameModel model;
    private GameView view;
    private InputHandler input;
    private AnimationTimer gameLoop;
    private boolean dragStartedOnTower = false;
    private Canvas canvas;
    private Scene scene;
    private boolean placementHandled = false;

    public GameController(GameModel model, GameView view, Scene scene, Canvas canvas) {
        this.model = model;
        this.view = view;
        this.scene = scene;
        this.canvas = canvas;
        this.input = new InputHandler();
        attachInputHandlers(scene);
    }

    private void attachInputHandlers(Scene scene) {
        scene.setOnMousePressed(e -> {
            input.mousePressed(e);
            placementHandled = false;
        });
        scene.setOnMouseReleased(e -> input.mouseReleased(e));
        scene.setOnMouseMoved(e -> input.mouseMoved(e));
        scene.setOnMouseDragged(e -> input.mouseMoved(e));
    }

    public void start() {
        gameLoop = new AnimationTimer() {
            long lastUpdate = 0;
            @Override
            public void handle(long now) {
                if (lastUpdate == 0) {
                    lastUpdate = now;
                    return;
                }
                double deltaTime = (now - lastUpdate) / 1_000_000_000.0;
                update(deltaTime);
                view.render(model);
                lastUpdate = now;
            }
        };
        gameLoop.start();
        model.startGame();
    }

    private void update(double deltaTime) {
        handleTowerPlacement();
        handleMergeDrag();
        model.updateEnemies(deltaTime);
        model.updateTowerAttacks(deltaTime);
        model.processProjectileHits();
        model.getWaveManager().update(deltaTime, model);
    }

    private void handleTowerPlacement() {
        if (input.isMousePressed() && input.getPressedButton() == MouseButton.PRIMARY && !dragStartedOnTower && !placementHandled) {
            double mouseX = input.getLastMouseX();
            double mouseY = input.getLastMouseY();
            
            if (handleUpgradeShopClick(mouseX, mouseY)) {
                placementHandled = true;
                return;
            }
            
            int col = (int)((mouseX - model.getBoardOffsetX()) / model.getCellSize());
            int row = (int)((mouseY - model.getBoardOffsetY()) / model.getCellSize());
            
            if (row >= 0 && row < model.getGridHeight() && col >= 0 && col < model.getGridWidth()) {
                Tower t = model.getTower(row, col);
                if (t == null) {
                    if (model.canPlaceTower(row, col) && model.buyTower()) {
                        model.placeTower(row, col, TowerType.LEVEL1);
                    }
                    placementHandled = true;
                }
            }
        }
    }

    private void handleMergeDrag() {
        if (input.isMousePressed() && !dragStartedOnTower && !placementHandled) {
            double mouseX = input.getLastMouseX();
            double mouseY = input.getLastMouseY();
            int col = (int)((mouseX - model.getBoardOffsetX()) / model.getCellSize());
            int row = (int)((mouseY - model.getBoardOffsetY()) / model.getCellSize());
            Tower t = model.getTower(row, col);
            if (t != null) {
                dragStartedOnTower = true;
                input.setDragging(true);
                input.setDragStart(row, col);
                view.setHighlight(row, col);
            }
        }

        if (!input.isMousePressed() && dragStartedOnTower) {
            if (input.isDragging()) {
                double mouseX = input.getLastMouseX();
                double mouseY = input.getLastMouseY();
                int endCol = (int)((mouseX - model.getBoardOffsetX()) / model.getCellSize());
                int endRow = (int)((mouseY - model.getBoardOffsetY()) / model.getCellSize());
                int startRow = input.getDragStartRow();
                int startCol = input.getDragStartCol();
                if (startRow != -1 && startCol != -1 && (startRow != endRow || startCol != endCol)) {
                    model.mergeTowers(startRow, startCol, endRow, endCol);
                    view.startFlash();
                }
            }
            dragStartedOnTower = false;
            input.clearDragStart();
            view.setHighlight(-1, -1);
        }
    }

    private boolean handleUpgradeShopClick(double mouseX, double mouseY) {
        double startX = canvas.getWidth() - 180;
        double startY = 150;
        double buttonWidth = 160;
        double buttonHeight = 40;
        double spacing = 10;
        Upgrade[] upgrades = model.getUpgrades();
        for (int i = 0; i < upgrades.length; i++) {
            double y = startY + i * (buttonHeight + spacing);
            if (mouseX >= startX && mouseX <= startX + buttonWidth && mouseY >= y && mouseY <= y + buttonHeight) {
                model.purchaseUpgrade(upgrades[i]);
                return true;
            }
        }
        return false;
    }
}
