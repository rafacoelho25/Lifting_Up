package model;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExerciseTest {
    private Exercise exercise;

    @BeforeEach
    void setUp() {
        exercise = new Exercise("Bench Press");
    }

    @Test
    void testConstructor() {
        assertNotNull(exercise);
        assertEquals("Bench Press", exercise.getName());
    }

    @Test
    void testGetName() {
        assertEquals("Bench Press", exercise.getName());
    }

    @Test
    void testSameExercise(){
        Exercise e1 = new Exercise("Bench Press");
        Exercise e2 = new Exercise("Squat");

        assertTrue(exercise.equals(e1));
        assertFalse(exercise.equals(e2));
    }

    @Test
    void testHashCode() {
        Exercise e1 = new Exercise("Bench Press");
        Exercise e2 = new Exercise("Squat");

        assertEquals(exercise.hashCode(), e1.hashCode());
        assertNotEquals(exercise.hashCode(), e2.hashCode());
    }

    @Test
    void testToJson() {
        JSONObject json = exercise.toJson();
        assertNotNull(json);
        assertEquals("Bench Press", json.getString("name"));
    }

}