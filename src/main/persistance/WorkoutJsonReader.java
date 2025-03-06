package persistance;

import model.Exercise;
import model.Workout;

import java.io.IOException;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

import model.WorkoutLog;
import org.json.*;



// Represents a reader that reads workout from JSON data stored in file
// Inspired by JsonSerializationDemo
public class WorkoutJsonReader {
    private String source;


    //EFFECTS: constructs reader to read from source file
    public WorkoutJsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads ListOfWorkout from file and returns it;
    // throws IOException if the file can't be read

    public ArrayList<Workout> read() throws IOException {
        String jsonData = readFile(source);
        JSONArray jsonArray = new JSONArray(jsonData);
        return parseListOfWorkout(jsonArray);
    }



    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }
        return contentBuilder.toString();
    }

    private ArrayList<Workout> parseListOfWorkout(JSONArray jsonArray) {
        ArrayList<Workout> listOfWorkout = new ArrayList<>();
        for (Object json : jsonArray) {
            JSONObject jsonObject = (JSONObject) json;
            Workout wk = parseWorkout(jsonObject);
            listOfWorkout.add(wk);
        }
        return listOfWorkout;
    }


    // EFFECTS: parses workout from JSON object and returns it
    private Workout parseWorkout(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Workout wk = new Workout(name);
        addExercises(wk, jsonObject);
        return wk;
    }



    // MODIFIES: wk
    // EFFECTS: parses exercises from JSON object and adds them to workout
    private void addExercises(Workout wk, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("exercises");
        for (Object json : jsonArray) {
            JSONObject nextExercise = (JSONObject) json;
            addExercise(wk, nextExercise);
        }
    }




    // MODIFIES wk
    // EFFECTS: parse exercise from JSON object and adds it to workout
    private void addExercise(Workout wk, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Exercise exercise = new Exercise(name);
        wk.addExercise(exercise);

    }

}
