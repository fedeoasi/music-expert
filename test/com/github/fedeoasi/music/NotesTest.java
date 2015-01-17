package com.github.fedeoasi.music;

import org.junit.Test;

import static org.junit.Assert.*;

public class NotesTest {
    Notes n = new Notes();

    @Test
    public void isNaturalTest() {
        assertTrue(n.isNatural("A"));
        assertTrue(n.isNatural("G"));
        assertTrue(n.isNatural("C"));
        assertFalse(n.isNatural("C#"));
        assertFalse(n.isNatural("Gb"));
        assertFalse(n.isNatural("C##"));
    }

    @Test
    public void nextNaturalTest() {
        assertEquals("C", n.nextNatural("B"));
        assertEquals("F", n.nextNatural("E"));
        assertEquals("D", n.nextNatural("C#"));
        assertEquals("F", n.nextNatural("E#"));
        assertEquals("C", n.nextNatural("Bb"));
    }

    @Test
    public void distanceTest() {
        assertEquals(2, n.distance("C", "D"));
        assertEquals(0, n.distance("Bb", "Bb"));
        assertEquals(0, n.distance("C#", "Db"));
        assertEquals(10, n.distance("Bb", "Ab"));
        assertEquals(10, n.distance("D", "C"));
        assertEquals(4, n.distance("F", "A"));
    }

    @Test
    public void getIndexTest() {
        assertEquals(0, n.getIndex("A"));
    }
}