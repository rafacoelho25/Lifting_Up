package model;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class WorkoutTest {
    private Workout workout;

    @BeforeEach
    void setUp() {
        workout = new Workout("Sample Workout");
    }

    @Test
    void testConstructor() {
        assertNotNull(workout);
        assertEquals("Sample Workout", workout.getName());
    }

    @Test
    void testAddExercise() {
        Exercise exercise = new Exercise("Push-ups");
        workout.addExercise(exercise);
        assertEquals(1, workout.getNumOfExercises());
    }

    @Test
    void testGetNumOfExercises() {
        Exercise exercise = new Exercise("Push-ups");
        workout.addExercise(exercise);
        assertEquals(1, workout.getNumOfExercises());
    }

    @Test
    void testGetExercises() {
        Exercise exercise = new Exercise("Push-ups");
        workout.addExercise(exercise);

        ArrayList<Exercise> exercises = workout.getExercises();
        assertEquals(1, exercises.size());
        assertTrue(exercises.contains(exercise));
    }

    @Test
    void testContainsExercise() {
        Exercise exercise = new Exercise("Push-ups");
        workout.addExercise(exercise);
        assertTrue(workout.containsExercise(exercise));
    }

    @Test
    void testGetWorkout() {
        Exercise exercise = new Exercise("Push-ups");
        workout.addExercise(exercise);

        String expected = "Exercise #0\nPush-ups\n";
        assertEquals(expected, workout.getWorkout());
    }

    @Test
    void testToJson() {
        Exercise exercise = new Exercise("Push-ups");
        workout.addExercise(exercise);

        JSONObject json = workout.toJson();
        assertNotNull(json);

        assertEquals("Sample Workout", json.getString("name"));

        JSONArray jsonArray = json.getJSONArray("exercises");
        assertEquals(1, jsonArray.length());

        JSONObject jsonExercise = jsonArray.getJSONObject(0);
        assertEquals("Push-ups", jsonExercise.getString("name"));
    }
}
