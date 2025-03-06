package model;

// Represents an exercise having a name and a target muscle

import org.json.JSONObject;
import persistance.Writable;

import java.util.Objects;

public class Exercise implements Writable {
    private final String name;                   // name of the exercise

    // REQUIRES: exerciseName has non-zero length
    // EFFECTS: creates a new exercise

    public Exercise(String exerciseName) {
        this.name = exerciseName;

    }

    // EFFECTS: get the name of the exercise
    public String getName() {
        return this.name;
    }

    // EFFECTS: used in order to consider two Exercise objects with the same name as the same object
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Exercise other = (Exercise) obj;
        return Objects.equals(name, other.name);
    }

    //EFFECTS: returns the hash value of the input object
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        return json;
    }
}

