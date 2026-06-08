package com.herb.macondo.mergedefense.model;

import java.util.ArrayList;
import java.util.List;

public class Path {
    private List<Waypoint> waypoints;

    public Path() {
        waypoints = new ArrayList<>();
        definePath();
    }

    private void definePath() {
        waypoints.add(new Waypoint(100, 100));
        waypoints.add(new Waypoint(300, 100));
        waypoints.add(new Waypoint(300, 200));
        waypoints.add(new Waypoint(500, 200));
        waypoints.add(new Waypoint(500, 350));
        waypoints.add(new Waypoint(350, 350));
        waypoints.add(new Waypoint(350, 480));
        waypoints.add(new Waypoint(650, 480));
    }

    public List<Waypoint> getWaypoints() { return waypoints; }
    public Waypoint getStart() { return waypoints.get(0); }
    public Waypoint getEnd() { return waypoints.get(waypoints.size() - 1); }

    public boolean isCellOnPath(int row, int col, GameModel model) {
        double cellSize = model.getCellSize();
        double offsetX = model.getBoardOffsetX();
        double offsetY = model.getBoardOffsetY();
        double cellCenterX = offsetX + col * cellSize + cellSize/2;
        double cellCenterY = offsetY + row * cellSize + cellSize/2;
        for (int i = 0; i < waypoints.size() - 1; i++) {
            Waypoint a = waypoints.get(i);
            Waypoint b = waypoints.get(i + 1);
            double ax = cellCenterX - a.getX();
            double ay = cellCenterY - a.getY();
            double bx = b.getX() - a.getX();
            double by = b.getY() - a.getY();
            double dot = ax * bx + ay * by;
            double len2 = bx * bx + by * by;
            if (len2 == 0) continue;
            double t = dot / len2;
            t = Math.max(0, Math.min(1, t));
            double projX = a.getX() + t * bx;
            double projY = a.getY() + t * by;
            double dist = Math.hypot(cellCenterX - projX, cellCenterY - projY);
            if (dist < cellSize / 2) return true;
        }
        return false;
    }
}

