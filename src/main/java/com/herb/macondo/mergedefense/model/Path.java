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
}
