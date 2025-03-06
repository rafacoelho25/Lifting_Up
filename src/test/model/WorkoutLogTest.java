package model;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class WorkoutLogTest {
    private Sets s1;
    private Sets s2;
    private WorkoutLog wl;

    @BeforeEach
    void setup() {
        s1 = new Sets("Bench Press");
        s2= new Sets("Triceps Extension");
        wl = new WorkoutLog();
    }

    @Test
    void testConstructor() {
        assertNotNull(wl);
        assertEquals(0, wl.getNumOfSets());
    }

    @Test
    void testAddSets() {
        wl.addSet(s1);
        assertEquals(wl.getNumOfSets(), 1);
        wl.addSet(s2);
        assertEquals(wl.getNumOfSets(), 2);

    }

    @Test
    void testGetNumOfSets() {
        wl.addSet(s1);
        assertEquals(1, wl.getNumOfSets());
    }

    @Test
    void testGetSets() {
        wl.addSet(s1);

        ArrayList<Sets> listOfSets = wl.getSets();
        assertEquals(1, listOfSets.size());
        assertTrue(listOfSets.contains(s1));
    }

    @Test
    void testGetWorkoutLog() {
        s1.addSets(1);
        s1.addReps(10);
        s1.addWeight(100);
        wl.addSet(s1);

        String expected = "\nBench Press Sets: 1 Reps: 10 Weight: 100\n";
        assertEquals(expected, wl.getWorkoutLog());
    }


    @Test
    void testToJson() {
        s1.addSets(1);
        s1.addReps(10);
        s1.addWeight(100);
        wl.addSet(s1);

        JSONObject json = wl.toJson();
        assertNotNull(json);

        JSONArray jsonArray = json.getJSONArray("sets");
        assertEquals(1, jsonArray.length());

        JSONObject jsonSets = jsonArray.getJSONObject(0);
        assertEquals("Bench Press", jsonSets.getString("name"));
        assertEquals(10, jsonSets.getInt("reps"));
        assertEquals(1, jsonSets.getInt("sets"));
        assertEquals(100, jsonSets.getInt("weight"));
    }





}
