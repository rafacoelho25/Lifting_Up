package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Set;

// Represents a list of Sets
public class WorkoutLog {

    private final ArrayList<Sets> listOfSets;


    public WorkoutLog() {
        listOfSets = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds a Set to the Workout Log
    public void addSet(Sets s) {
        listOfSets.add(s);
    }

    // EFFECTS: return the size of the Workout Log
    public int getNumOfSets() {
        return listOfSets.size();
    }

    //EFFECTS: returns a list of the sets in the Workout Log
    public ArrayList<Sets> getSets() {
        return listOfSets;
    }

    //EFFECTS: return the workout log with all the specific sets, reps and weight for each set of exercises
    public String getWorkoutLog() {
        String wl = "";
        for (int i = 0; i < getNumOfSets(); i++) {
            wl += "\n" + listOfSets.get(i).getName() + " Sets: "
                    + listOfSets.get(i).getSetsDone() + " Reps: "
                    + listOfSets.get(i).getReps() + " Weight: "
                    + listOfSets.get(i).getWeight() + "\n";
        }
        return wl;
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("sets", setsToJson());
        return json;
    }

    private JSONArray setsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Sets s : listOfSets) {
            jsonArray.put(s.toJson());
        }
        return jsonArray;
    }



}
