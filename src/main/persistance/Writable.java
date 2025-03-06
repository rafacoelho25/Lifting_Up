package persistance;

import org.json.JSONObject;

public interface Writable {
    // EFFECTS: returns this as JSON object
    // Inspired by JsonSerializationDemo https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    JSONObject toJson();
}
