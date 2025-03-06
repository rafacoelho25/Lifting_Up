package Persistence;

import model.WorkoutLog;
import org.junit.jupiter.api.Test;
import persistance.WorkoutLogJsonReader;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class WorkoutLogReaderTest {

    @Test
    void testNonExistentFile() {
        WorkoutLogJsonReader reader = new WorkoutLogJsonReader("./data/noSuchFile.json");
        try {
            WorkoutLog wl = reader.read();
            fail("IOException expected");
        } catch (IOException e) {

        }
    }

    @Test
    void testReaderEmptyWl() {
        WorkoutLogJsonReader reader = new WorkoutLogJsonReader("./data/emptyWorkoutLog.json");
        try {
            WorkoutLog wl = reader.read();
            assertEquals(0, wl.getNumOfSets());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

}
