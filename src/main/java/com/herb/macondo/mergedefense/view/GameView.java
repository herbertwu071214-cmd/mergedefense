package com.herb.macondo.mergedefense.view;

import com.herb.macondo.mergedefense.model.GameModel;
import com.herb.macondo.mergedefense.model.Path;
import com.herb.macondo.mergedefense.model.Waypoint;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.List;

public class GameView {
    private Canvas canvas;

    public GameView(Canvas canvas) {
        this.canvas = canvas;
    }

    public void render(GameModel model) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        double offsetX = model.getBoardOffsetX();
        double offsetY = model.getBoardOffsetY();
        double cellSize = model.getCellSize();

        gc.setStroke(Color.GRAY);
        gc.setLineWidth(1);

        for (int row = 0; row < model.getGridHeight(); row++) {
            for (int col = 0; col < model.getGridWidth(); col++) {
                double x = offsetX + col * cellSize;
                double y = offsetY + row * cellSize;
                gc.strokeRect(x, y, cellSize, cellSize);
            }
        }

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
    }
}
