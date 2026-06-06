package com.herb.macondo.mergedefense.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

class PathTest {
    @Test
    void definesExpectedRoute() {
        Path path = new Path();

        assertEquals(8, path.getWaypoints().size());
        assertEquals(100, path.getStart().getX());
        assertEquals(100, path.getStart().getY());
        assertEquals(650, path.getEnd().getX());
        assertEquals(480, path.getEnd().getY());
    }

    @Test
    void startAndEndReferenceRouteWaypoints() {
        Path path = new Path();

        assertSame(path.getWaypoints().get(0), path.getStart());
        assertSame(path.getWaypoints().get(path.getWaypoints().size() - 1), path.getEnd());
    }
}
