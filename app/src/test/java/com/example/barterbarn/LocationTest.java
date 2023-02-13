package com.example.barterbarn;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

// Tests against data from https://www.nhc.noaa.gov/gccalc.shtml

public class LocationTest {
    @Test
    public void testNoDistance() {
        Location halifaxAirport = new Location(44.8865, 63.5153,"Halifax");

        assertEquals(
                Location.calculateDistance(halifaxAirport, halifaxAirport),
                0.0,
                0.0
        );
    }

    @Test
    public void testShortDistance() {
        Location citadelHill = new Location(44.6475, 63.5796,"Halifax");
        Location halifaxAirport = new Location(44.8865, 63.5153,"Halifax");

        assertEquals(
                Location.calculateDistance(citadelHill, halifaxAirport),
                27.0,
                1
        );
    }

    @Test
    public void testLongDistance() {
        Location halifaxAirport = new Location(44.8865, 63.5153,"Halifax");
        Location vancouverAirport = new Location(49.1967, 123.1815,"Halifax");

        assertEquals(
                Location.calculateDistance(halifaxAirport, vancouverAirport),
                4425.0,
                5
        );
    }
}
