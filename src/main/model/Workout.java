package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistance.Writable;

import java.util.ArrayList;

//Represents a list of Exercises
public class Workout  implements Writable {

    private final ArrayList<Exercise> listOfExercises;
    private String name;

    // EFFECTS: create a new workout, with a name and a list of Exercises
    public Workout(String name) {
        listOfExercises = new ArrayList<>();
        this.name = name;

    }

    public void setName(String name) {
        this.name = name;
        EventLog.getInstance().logEvent(new Event(this.name + " added to workouts."));
    }

    //EFFECTS: return the exercises in the workout
    public ArrayList<Exercise> getExercises() {
        return listOfExercises;
    }

    // MODIFIES: this
    // EFFECTS: add an exercise to the workout
    public void addExercise(Exercise e) {
        listOfExercises.add(e);
        EventLog.getInstance().logEvent(new Event(e.getName() + " added to " + this.getName()));
    }

    // EFFECTS: return the size of the list
    public int getNumOfExercises() {
        return listOfExercises.size();
    }

    // EFFECTS: checks if a specific exercise is included in the list
    public boolean containsExercise(Exercise e) {
        return listOfExercises.contains(e);
    }

    public void workoutDeleted() {
        EventLog.getInstance().logEvent(new Event(this.name + " removed from workouts."));
    }

    // EFFECTS: return all the exercises in the workout
    public String getWorkout() {
        String wk = "";
        for (int i = 0; i < getNumOfExercises(); i++) {
            wk += "Exercise #" + i + "\n" + listOfExercises.get(i).getName() + "\n";
        }
        return wk;
    }

    // EFFECTS: return the nave given to the workout
    public String getName() {
        return name;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("exercises", exercisesToJson());
        return json;
    }

    //EFFECTS: returns exercises in this workout as JSON array
    private JSONArray exercisesToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Exercise e : listOfExercises) {
            jsonArray.put(e.toJson());
        }
        return jsonArray;
    }


}
