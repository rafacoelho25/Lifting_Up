package persistance;


import model.Workout;
import model.WorkoutLog;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

// Represents a writer that writes a JSON representation of workout to file
// Inspired by JsonSerializationDemo
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if file cannot be opened
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of workout to file
    public void writeWorkouts(ArrayList<Workout> workouts) {
        JSONArray jsonArray = new JSONArray();
        for (Workout wk : workouts) {
            JSONObject json = wk.toJson();
            jsonArray.put(json);
        }
        saveToFile(jsonArray.toString(TAB));
    }

    public void writeWorkoutLog(WorkoutLog wl) {
        JSONObject json = wl.toJson();
        saveToFile((json.toString(TAB)));

    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }
}
