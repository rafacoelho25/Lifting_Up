package ui;

import model.Exercise;
import model.Sets;
import model.Workout;
import model.WorkoutLog;
import persistance.WorkoutJsonReader;
import persistance.JsonWriter;
import persistance.WorkoutLogJsonReader;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

// Represents the LiftingUp application
public class LiftingUp {

    private Workout user;
    private ArrayList<Workout> listOfWorkout;

    private Scanner input;
    private boolean closeApp;
    private WorkoutLog log;
    private WorkoutLog savedLog;
    private JsonWriter wkJsonWriter;
    private JsonWriter wlJsonWriter;
    private WorkoutJsonReader workoutJsonReader;
    private WorkoutLogJsonReader wlJsonReader;
    private static final String WK_JSON_STORE = "./data/myFile.json";
    private static final String WL_JSON_STORE = "./data/workoutLog.json";

    // EFFECTS: starts the application
    public LiftingUp() throws FileNotFoundException {
        runLiftingUp();
    }


    // REQUIRES: closeApp must start as false,log must be empty, listOfWorkout must be empty
    // MODIFIES: this
    // EFFECTS: initializes the application and its parameters
    private void runLiftingUp() {
        closeApp = false;
        String command;
        input = new Scanner(System.in);
        log = new WorkoutLog();
        savedLog = new WorkoutLog();
        listOfWorkout = new ArrayList<>();
        wkJsonWriter = new JsonWriter(WK_JSON_STORE);
        workoutJsonReader = new WorkoutJsonReader(WK_JSON_STORE);
        wlJsonWriter = new JsonWriter(WL_JSON_STORE);
        wlJsonReader = new WorkoutLogJsonReader(WL_JSON_STORE);


        System.out.println("Welcome to Lifting Up!");

        while (!closeApp) {
            startMenu();
            command = input.next();

            processInput(command);

        }
        System.out.println("See you on your next workout!");
    }



    //EFFECTS: prints the application menu in the console
    private void startMenu() {
        System.out.println("\nWhat do you wanna do?");
        System.out.println("\t1. Create my workout!");
        System.out.println("\t2. Start my workout!");
        System.out.println("\t3. Save my workouts!");
        System.out.println("\t4. Load my workouts");
        System.out.println("\t5. Save my last workout report");
        System.out.println("\t6 Load my last workout report");
        System.out.println("\t7. Close Lifting Up!");

    }

    // REQUIRES: user input must be an integer
    // MODIFIES: closeApp
    // EFFECTS: processes user input and initializes the method related to their selected option, returns error if user
    // inputs an invalid selection
    private void processInput(String command) {
        if (command.equals("1")) {
            createWorkout();
        } else if (command.equals("2")) {
            startWorkout(listOfWorkout);
        } else if (command.equals("7")) {
            closeApp = true;
        } else if (command.equals("3")) {
            saveWorkout();
        } else if (command.equals("4")) {
            loadWorkout();
        } else if (command.equals("5")) {
            saveWorkoutLog();
        } else if (command.equals("6")) {
            loadWorkoutLog();
        } else {
            System.out.println("Invalid Input!");
        }
    }


    // EFFECTS: saves workouts to file
    private void saveWorkout() {
        try {
            wkJsonWriter.open();
            wkJsonWriter.writeWorkouts(listOfWorkout);
            wkJsonWriter.close();
            System.out.println("Saved workouts!");

        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file!");
        }
    }

    private void saveWorkoutLog() {
        try {
            wlJsonWriter.open();
            wlJsonWriter.writeWorkoutLog(log);
            wlJsonWriter.close();
            System.out.println("Saved last workout report!");

        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file!");
        }
    }

    // MODIFIES: this
    // EFFECTS: loads ListOfWorkout from file
    private void loadWorkout() {
        try {
            listOfWorkout = workoutJsonReader.read();
            System.out.println("Loaded workouts from file!");
        } catch (IOException e) {
            System.out.println("Unable to read from file!");
        }
    }

    private void loadWorkoutLog() {
        try {
            savedLog = wlJsonReader.read();
            System.out.println("Here's how your last workout went!");
            System.out.println(savedLog.getWorkoutLog());
        } catch (IOException e) {
            System.out.println("Unable to read from file!");
        }
    }

    // REQUIRES: name has non-zero length
    // MODIFIES: this
    // EFFECTS: creates a new workout, with a name and a list of exercises
    private void createWorkout() {
        String name;

        System.out.println("Choose a name for your workout");
        name = input.next();

        user = new Workout(name);

        System.out.println("Add the exercises for your workout");
        creatingWorkout();
    }

    // REQUIRES: the typed name must be a string
    // MODIFIES: this
    // EFFECTS: starts a new workout based on user selection, returns error message if workout is not found
    private void startWorkout(ArrayList<Workout> workoutList) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Type the name of the workout to start!");

        for (int i = 0; i < workoutList.size(); i++) {
            System.out.println((i + 1) + ": " + workoutList.get(i).getName());
        }

        String workoutName = scanner.nextLine();
        Workout selectedWorkout = null;

        for (Workout workout : workoutList) {
            if (workout.getName().equals(workoutName)) {
                selectedWorkout = workout;
                break;
            }
        }

        if (selectedWorkout == null) {
            System.out.println("Workout not found!");
            return;

        }

        System.out.println("Let's begin " + selectedWorkout.getName());
        workingOut(selectedWorkout);
    }


    // REQUIRES: user cannot add the same exercise to the workout twice
    // MODIFIES: this
    // EFFECTS: adds exercises to the created workout, each exercise can only be added once
    private void creatingWorkout() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Enter an exercise or type 'done' to finish creating your workout");
            String input = scanner.nextLine();

            if (input.equals("done")) {
                break;
            }
            Exercise e = new Exercise(input);
            if (user.containsExercise(e)) {
                System.out.println("You already added that exercise");
            } else {
                user.addExercise(e);
            }
        }
        listOfWorkout.add(user);
        System.out.println("Workout " + user.getName() + " created!");
        System.out.println(user.getWorkout());
    }

    //EFFECTS: initializes the methods related to the workout itself
    public void workingOut(Workout workout) {
        exercisesToSets(workout, log);
        performWorkout(log);
    }

    //EFFECTS: changes the exercises in Workout into Sets and adds them to log
    //MODIFIES: exercises
    public void exercisesToSets(Workout wk, WorkoutLog wkLog) {
        ArrayList<Sets> setList = new ArrayList<>();
        for (Exercise exercise : wk.getExercises()) {
            Sets set = new Sets(exercise.getName());
            setList.add(set);
        }
        addSetsToWorkoutLog(setList, wkLog);
    }

    //EFFECTS: adds sets to a WorkoutLog
    public void addSetsToWorkoutLog(ArrayList<Sets> sets, WorkoutLog wkl) {
        for (Sets set : sets) {
            wkl.addSet(set);
        }
    }

    // REQUIRES: user inputs must be integers
    // MODIFIES: this
    // adds the sets done, weight used and repetitions done for each WorkoutLog exercise, prints the Workout Log once
    // all the sets, reps and weight have been added
    public void performWorkout(WorkoutLog wkl) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Sets> sets = wkl.getSets();
        for (Sets set : sets) {
            System.out.println("Performing " + set.getName());
            System.out.println("Sets done: ");
            int numSets = scanner.nextInt();
            System.out.print("Number of reps done: ");
            int numReps = scanner.nextInt();
            System.out.print("Weight used (in lbs): ");
            int weight = scanner.nextInt();
            set.addSets(numSets);
            set.addReps(numReps);
            set.addWeight(weight);
        }
        System.out.println("Great Job! Here's your workout report!");
        System.out.println(log.getWorkoutLog());
    }
}
