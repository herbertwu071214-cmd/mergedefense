package com.herb.macondo.mergedefense.view;

import com.herb.macondo.mergedefense.model.GameModel;
import com.herb.macondo.mergedefense.model.Path;
import com.herb.macondo.mergedefense.model.Tower;
import com.herb.macondo.mergedefense.model.Waypoint;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.List;

public class GameView {
    private Canvas canvas;
    private int highlightRow = -1;
    private int highlightCol = -1;
    private double flashTimer = 0;

    public GameView(Canvas canvas) {
        this.canvas = canvas;
    }

    public void setHighlight(int row, int col) {
        this.highlightRow = row;
        this.highlightCol = col;
    }

    public void startFlash() {
        this.flashTimer = 0.2;
    }

    public void render(GameModel model) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        double offsetX = model.getBoardOffsetX();
        double offsetY = model.getBoardOffsetY();
        double cellSize = model.getCellSize();

        // Draw grid
        gc.setStroke(Color.GRAY);
        gc.setLineWidth(1);
        for (int row = 0; row < model.getGridHeight(); row++) {
            for (int col = 0; col < model.getGridWidth(); col++) {
                double x = offsetX + col * cellSize;
                double y = offsetY + row * cellSize;
                gc.strokeRect(x, y, cellSize, cellSize);
            }
        }

        // Draw path
        Path path = model.getPath();
        List<Waypoint> waypoints = path.getWaypoints();
        gc.setStroke(Color.GRAY);
        gc.setLineWidth(12);
        for (int i = 0; i < waypoints.size() - 1; i++) {
            Waypoint wp1 = waypoints.get(i);
            Waypoint wp2 = waypoints.get(i + 1);
            gc.strokeLine(wp1.getX(), wp1.getY(), wp2.getX(), wp2.getY());
        }
        gc.setLineWidth(1);
        gc.setStroke(Color.WHITE);
        for (int i = 0; i < waypoints.size() - 1; i++) {
            Waypoint wp1 = waypoints.get(i);
            Waypoint wp2 = waypoints.get(i + 1);
            gc.strokeLine(wp1.getX(), wp1.getY(), wp2.getX(), wp2.getY());
        }

        // Draw towers
        for (int row = 0; row < model.getGridHeight(); row++) {
            for (int col = 0; col < model.getGridWidth(); col++) {
                Tower t = model.getTower(row, col);
                if (t != null) {
                    double x = offsetX + col * cellSize;
                    double y = offsetY + row * cellSize;
                    double radius = cellSize / 2.5;
                    gc.setFill(t.getType().getColor());
                    gc.fillOval(x + cellSize/2 - radius, y + cellSize/2 - radius, radius*2, radius*2);
                    gc.setFill(Color.WHITE);
                    gc.fillText(String.valueOf(t.getType().getLevel()), x + cellSize/2 - 4, y + cellSize/2 + 5);
                }
            }
        }

        // Highlight dragged tower
        if (highlightRow != -1 && highlightCol != -1) {
            Tower t = model.getTower(highlightRow, highlightCol);
            if (t != null) {
                double x = offsetX + highlightCol * cellSize;
                double y = offsetY + highlightRow * cellSize;
                gc.setStroke(Color.YELLOW);
                gc.setLineWidth(3);
                gc.strokeRect(x, y, cellSize, cellSize);
                gc.setLineWidth(1);
            }
        }

        // Flash effect on merge
        if (flashTimer > 0) {
            flashTimer -= 1.0/60.0;
            gc.setFill(Color.rgb(255, 255, 255, 0.5));
            for (int row = 0; row < model.getGridHeight(); row++) {
                for (int col = 0; col < model.getGridWidth(); col++) {
                    if (model.getTower(row, col) != null) {
                        double x = offsetX + col * cellSize;
                        double y = offsetY + row * cellSize;
                        gc.fillRect(x, y, cellSize, cellSize);
                    }
                }
            }
        }
    }
}
