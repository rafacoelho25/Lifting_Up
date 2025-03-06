package model;

import org.json.JSONObject;

// Represents a set done for a specific exercise, with how many repetitions were done,
// the weight used and the set number
public class Sets extends Exercise {
    private int reps;                      // number of repetitions done
    private int weight;                   // how much weight was used (in lbs)
    private int setsDone;                // number of sets done


    //REQUIRES: reps, weight and setsDone are >= 0
    //EFFECTS: creates a set for an exercise
    public Sets(String exerciseName) {
        super(exerciseName);
        this.reps = 0;
        this.weight = 0;
        this.setsDone = 0;
    }

    //MODIFIES: this
    // EFFECTS: increase the number of sets done by 1
    public void addSets(int numSets) {
        this.setsDone += numSets;
        EventLog.getInstance().logEvent(new Event(this.reps + " sets were done for " + this.getName() + "."));
    }

    //REQUIRES: repsDone must be a positive integer
    //MODIFIES: this
    //EFFECTS: add how many repetitions the user did for that specific exercise
    public void addReps(int repsDone) {
        this.reps += repsDone;
        EventLog.getInstance().logEvent(new Event(this.reps + " reps were done for " + this.getName() + "."));
    }

    //REQUIRES: addWeight must be a positive integer
    //MODIFIES: this
    //EFFECTS: add how much weight the user used for that specific exercise
    public void addWeight(int weightUsed) {
        this.weight += weightUsed;
        EventLog.getInstance().logEvent(new Event("The weight used for "
                + this.getName() + " was " + this.weight + " pounds."));
    }

    // EFFECTS: Gets the number of sets done
    public int getSetsDone() {
        return this.setsDone;
    }

    // EFFECTS: gets the number of repetitions done
    public int getReps() {
        return this.reps;
    }

    // EFFECTS: gets the weight used during the exercise
    public int getWeight() {
        return this.weight;
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", this.getName());
        json.put("reps", this.getReps());
        json.put("sets", this.getSetsDone());
        json.put("weight", this.getWeight());
        return json;
    }


}
