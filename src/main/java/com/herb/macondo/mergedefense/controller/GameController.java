package com.herb.macondo.mergedefense.controller;

import com.herb.macondo.mergedefense.input.InputHandler;
import com.herb.macondo.mergedefense.model.GameModel;
import com.herb.macondo.mergedefense.model.Tower;
import com.herb.macondo.mergedefense.model.TowerType;
import com.herb.macondo.mergedefense.view.GameView;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;

public class GameController {
    private GameModel model;
    private GameView view;
    private InputHandler input;
    private AnimationTimer gameLoop;
    private boolean dragStartedOnTower = false;

    public GameController(GameModel model, GameView view, Scene scene) {
        this.model = model;
        this.view = view;
        this.input = new InputHandler();
        attachInputHandlers(scene);
    }

    private void attachInputHandlers(Scene scene) {
        scene.setOnMousePressed(e -> input.mousePressed(e));
        scene.setOnMouseReleased(e -> input.mouseReleased(e));
        scene.setOnMouseMoved(e -> input.mouseMoved(e));
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
        if (input.isMousePressed() && input.getPressedButton() == MouseButton.PRIMARY && !dragStartedOnTower) {
            double mouseX = input.getLastMouseX();
            double mouseY = input.getLastMouseY();
            int col = (int)((mouseX - model.getBoardOffsetX()) / model.getCellSize());
            int row = (int)((mouseY - model.getBoardOffsetY()) / model.getCellSize());
            if (model.canPlaceTower(row, col)) {
                model.placeTower(row, col, TowerType.LEVEL1);
            }
            input.mouseReleased(null);
        }
    }

    private void handleMergeDrag() {
        if (input.isMousePressed() && !dragStartedOnTower) {
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
}
