package persistance;


import model.Sets;
import model.WorkoutLog;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads WorkoutLog from JSON data stored in file
public class WorkoutLogJsonReader {
    private String source;

    //EFFECTS: constructs reader to read from source file
    public WorkoutLogJsonReader(String source) {
        this.source = source;
    }

    //EFFECTS:reads WorkoutLog from file and returns it
    // throws IOException if an error occurs reading data from file
    public WorkoutLog read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseWorkoutLog(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    private WorkoutLog parseWorkoutLog(JSONObject jsonObject) {
        WorkoutLog wl = new WorkoutLog();
        addSets(wl, jsonObject);
        return wl;

    }

    private void addSets(WorkoutLog wl, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("sets");
        for (Object json : jsonArray) {
            JSONObject nextSet = (JSONObject) json;
            addSet(wl, nextSet);
        }
    }

    private void addSet(WorkoutLog wl, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int reps = jsonObject.getInt("reps");
        int sets = jsonObject.getInt("sets");
        int weight = jsonObject.getInt("weight");

        Sets set = new Sets(name);
        set.addReps(reps);
        set.addSets(sets);
        set.addWeight(weight);
        wl.addSet(set);
    }



}
