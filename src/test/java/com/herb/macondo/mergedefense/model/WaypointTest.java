package com.herb.macondo.mergedefense.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WaypointTest {
    @Test
    void storesCoordinates() {
        Waypoint waypoint = new Waypoint(12.5, 34.75);

        assertEquals(12.5, waypoint.getX());
        assertEquals(34.75, waypoint.getY());
    }
}
