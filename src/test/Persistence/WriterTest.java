package Persistence;

import model.Exercise;
import model.Sets;
import model.Workout;
import model.WorkoutLog;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistance.WorkoutJsonReader;
import persistance.JsonWriter;
import persistance.WorkoutLogJsonReader;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class WriterTest {


    private Workout wk1;
    private Workout wk2;
    private WorkoutLog wl;


    @BeforeEach
    void runBefore() {
        wk1 = new Workout("Monday");
        wk2 = new Workout("Wednesday");
        wl = new WorkoutLog();
        Exercise e1 = new Exercise("Chest Press");
        Exercise e2 = new Exercise("Squat");
        wk1.addExercise(e1);
        wk1.addExercise(e2);
        wk2.addExercise(e1);
        wk2.addExercise(e2);
    }

    @Test
    void testWkWriterInvalidFile() {
        ArrayList workouts = new ArrayList();
        workouts.add(wk1);
        workouts.add(wk2);
        try {
            JsonWriter writer = new JsonWriter(("./data/ojw\0illegal:fileName...json"));
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {

        }
    }

    @Test
    void testWlWriterInvalidFile() {
        Sets s1 = new Sets("Chest Press");
        wl.addSet(s1);
        try {
            JsonWriter writer = new JsonWriter(("./data/ojw\0illegal:fileName...json"));
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {

        }
    }

    @Test
    void emptyListOfWorkouts() {
        try{
            ArrayList workouts = new ArrayList();
            JsonWriter writer = new JsonWriter("./data/emptyWorkouts.json");
            writer.open();
            writer.writeWorkouts(workouts);
            writer.close();

            WorkoutJsonReader reader = new WorkoutJsonReader("./data/emptyWorkouts.json");
            workouts = reader.read();
            assertEquals(0, workouts.size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void emptyWorkoutLog() {
        try{
            JsonWriter writer = new JsonWriter(("./data/emptyWorkoutLog.json"));
            writer.open();
            writer.writeWorkoutLog(wl);
            writer.close();

            WorkoutLogJsonReader reader = new WorkoutLogJsonReader("./data/emptyWorkoutLog.json");
            wl = reader.read();
            assertEquals(0, wl.getNumOfSets());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }


}
