package Persistence;

import model.Exercise;
import model.Workout;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistance.WorkoutJsonReader;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class WorkoutReaderTest {
    private Workout wk1;
    private Workout wk2;


    @BeforeEach
    void runBefore() {
        wk1 = new Workout("Monday");
        wk2 = new Workout("Wednesday");
        Exercise e1 = new Exercise("Chest Press");
        Exercise e2 = new Exercise("Squat");
        wk1.addExercise(e1);
        wk1.addExercise(e2);
        wk2.addExercise(e1);
        wk2.addExercise(e2);
    }

    @Test
    void testReaderNonExistentFile() {
        WorkoutJsonReader reader = new WorkoutJsonReader("./data/noSuchFile.json");
        ArrayList workouts = new ArrayList<>();
        try {
             workouts = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyWorkRoom() {
        WorkoutJsonReader reader = new WorkoutJsonReader("./data/emptyWorkouts.json");
        try {
            ArrayList<Workout> workouts = reader.read();
            assertEquals(0, workouts.size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
