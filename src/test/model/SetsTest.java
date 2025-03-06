package model;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SetsTest {
    private Sets set;

    @BeforeEach
    void setUp() {
        set = new Sets("Bench Press");
    }

    @Test
    void testConstructor() {
        assertNotNull(set);
        assertEquals("Bench Press", set.getName());
        assertEquals(0, set.getSetsDone());
        assertEquals(0, set.getReps());
        assertEquals(0, set.getWeight());
    }


    @Test
    void testDoSet() {
        set.addSets(1);
        assertEquals(set.getSetsDone(), 1);
    }

    @Test
    void testMultipleSets() {
        set.addSets(1);
        set.addSets(1);
        set.addSets(1);
        assertEquals(set.getSetsDone(), 3);
    }

    @Test
    void testAddReps() {
        set.addReps(8);
        assertEquals(set.getReps(), 8);
    }

    @Test
    void testAddWeight() {
        set.addWeight(45);
        assertEquals(set.getWeight(), 45);
    }

    @Test
    void testGetSetsDone() {
        set.addSets(1);
        assertEquals(1, set.getSetsDone());
    }

    @Test
    void testGetReps() {
        set.addReps(10);
        assertEquals(10, set.getReps());
    }

    @Test
    void testGetWeight() {
        set.addWeight(100);
        assertEquals(100, set.getWeight());
    }

    @Test
    void testToJson() {
        set.addSets(1);
        set.addReps(10);
        set.addWeight(100);

        JSONObject json = set.toJson();
        assertNotNull(json);
        assertEquals("Bench Press", json.getString("name"));
        assertEquals(10, json.getInt("reps"));
        assertEquals(1, json.getInt("sets"));
        assertEquals(100, json.getInt("weight"));
    }

}
