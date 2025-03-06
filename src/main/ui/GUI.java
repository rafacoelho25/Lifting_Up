package ui;

import model.Exercise;
import model.Sets;
import model.Workout;
import model.WorkoutLog;
import persistance.JsonWriter;
import persistance.WorkoutJsonReader;
import persistance.WorkoutLogJsonReader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

// Constructs a GUI for the application
public class GUI  extends JFrame implements ActionListener {


    private  JPanel menu;
    private JButton b1;
    private JButton b2;
    private JButton b3;
    private JButton b4;
    private JButton b5;
    private JButton b6;
    private JButton b7;
    private JButton b8;

    private static final String WORKOUTS_FILE = "./data/workouts.json";
    private static final String WORKOUT_LOG_FILE = "./data/workoutLog.json";
    private ArrayList<Workout> workouts;

    private WorkoutLog log;
    private WorkoutLog loadedLog;



    // Constructs the GUI and initializes the menu
    public GUI() {
        super("Lifting Up!");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(500,500));
        workouts = new ArrayList<>();
        log = new WorkoutLog();
        loadedLog = new WorkoutLog();
        startMenu();


        JLabel mainImage = new JLabel();
        addImageToLabel(mainImage);
        startMenuButtons();
        addButtons(b1, b2, b3, b4, b5, b6, b7);
        assignButton();
        menu.setVisible(true);

    }

    // EFFECTS: Initializes the main menu
    public void startMenu() {
        menu = new JPanel();
        menu.setBackground(Color.WHITE);
        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
        add(menu);

        JLabel welcome = new JLabel("Welcome to Lifting Up!");
        addLabel(welcome);
        String imagePath = "./data/gym.png";
        int panelWidth = 500;
        int imageHeight = 100;
        ImageIcon imageIcon = getScaledImageIcon(imagePath, panelWidth, imageHeight);
        JLabel imageLabel = new JLabel(imageIcon);
        menu.add(Box.createVerticalGlue());
        menu.add(imageLabel);

        addImageToMenu();

    }

    // EFFECTS: adds an image to the main menu
    public void addImageToMenu() {
        JLabel mainImage = new JLabel();
        addImageToLabel(mainImage);
        menu.add(mainImage);
    }

    // EFFECTS: Returns a scaled ImageIcon given the image file path, desired width, and height.
    private ImageIcon getScaledImageIcon(String imagePath, int width, int height) {
        ImageIcon imageIcon = new ImageIcon(imagePath);
        Image image = imageIcon.getImage();
        Image scaledImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }

    // EFFECTS: Initializes the buttons for the main menu
    public void startMenuButtons() {
        b1 = new JButton("Create my workout!");
        b2 = new JButton("Start my workout!");
        b3 = new JButton("Save my workouts!");
        b4 = new JButton("Load my workouts!");
        b5 = new JButton("Save my last workout!");
        b6 = new JButton("Load my last workout!");
        b7 = new JButton("Close Lifting Up!");
        b8 = new JButton("Delete Workout");
    }


   // MODIFIES: button
    // EFFECTS: adds a button to the panel with styling and basic settings
    public void addButton(JButton button, JPanel panel) {
        button.setFont(new Font("ComicSans", Font.BOLD, 12));
        button.setBackground(Color.BLACK);
        panel.add(button);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    // EFFECTS: adds multiple buttons to the main menu
    public void addButtons(JButton b1, JButton b2, JButton b3, JButton b4, JButton b5, JButton b6, JButton b7) {
        addButton(b1, menu);
        addButton(b2, menu);
        addButton(b3, menu);
        addButton(b4, menu);
        addButton(b5, menu);
        addButton(b6, menu);
        addButton(b7, menu);
        addButton(b8, menu);
    }

    // EFFECTS: adds a label to the main menu panel
    public void addLabel(JLabel label) {
        label.setFont(new Font("ComicSans", Font.BOLD, 40));
        menu.add(label);
    }

    // EFFECTS: adds an image to label
    public void addImageToLabel(JLabel label) {
        label.setIcon(new ImageIcon("image goes here"));
        label.setMinimumSize((new Dimension(20,20)));
        menu.add(label);
    }

    // MODIFIES: this
    // EFFECTS: assigns action listeners and action commands to buttons
    public void assignButton() {
        b1.addActionListener(this);
        b1.setActionCommand("create workout");

        b2.addActionListener(this);
        b2.setActionCommand("start workout");

        b3.addActionListener(this);
        b3.setActionCommand("save workout");

        b4.addActionListener(this);
        b4.setActionCommand("load workout");

        b5.addActionListener(this);
        b5.setActionCommand("save last workout");

        b6.addActionListener(this);
        b6.setActionCommand("load last workout");

        b7.addActionListener(this);
        b7.setActionCommand("exit");

        b8.addActionListener(this);
        b8.setActionCommand("delete workout");

    }

    // EFFECTS: handles the actions for the buttons
    public void actionPerformed(ActionEvent a) {
        if (a.getActionCommand().equals("create workout")) {
            createWorkoutPanel();
        } else if (a.getActionCommand().equals("start workout")) {
            startWorkoutPanel(workouts);
        } else if (a.getActionCommand().equals("save workout")) {
            saveWorkout();
        } else if (a.getActionCommand().equals("load workout")) {
            loadWorkout();
        } else if (a.getActionCommand().equals("save last workout")) {
            saveLastWorkout();
        } else if (a.getActionCommand().equals("load last workout")) {
            loadLastWorkout();
        } else if (a.getActionCommand().equals("exit")) {
            for (model.Event event : model.EventLog.getInstance()) {
                System.out.println(event);
            }
            System.exit(0);
        } else if (a.getActionCommand().equals("delete workout")) {
            deleteWorkout();
        }
    }

    // REQUIRES: workouts is not empty
    // MODIFIES: workouts
    // EFFECTS: removes a workout from workouts
    public void deleteWorkout() {
        if (workouts.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No workouts available to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String[] workoutNames = workouts.stream().map(Workout::getName).toArray(String[]::new);
        String selectedWorkoutName = (String) JOptionPane.showInputDialog(null, "Select the workout to delete:",
                "Delete Workout", JOptionPane.QUESTION_MESSAGE, null, workoutNames, workoutNames[0]);

        if (selectedWorkoutName != null) {
            Optional<Workout> workoutToDelete = workouts.stream()
                    .filter(workout -> workout.getName().equals(selectedWorkoutName))
                    .findFirst();
            if (workoutToDelete.isPresent()) {
                Workout deletedWorkout = workoutToDelete.get();
                deletedWorkout.workoutDeleted();
                workouts.remove(deletedWorkout);
                JOptionPane.showMessageDialog(null, "Workout deleted successfully!",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
            }

        }
    }



    // EFFECTS: generates the workout creation panel with input fields and buttons
    public void createWorkoutPanel() {
        menu.removeAll();

        JLabel wkName = new JLabel("Workout Name:");
        JTextField wkNameField = new JTextField(20);
        JLabel exName = new JLabel("Exercise Name:");
        JTextField exNameField = new JTextField(20);
        JButton addExercise = new JButton("Add Exercise");
        JButton createWorkout = new JButton("Create Workout");
        JButton back = new JButton("Back to the Main Menu");

        Workout newWorkout = new Workout("");
        ArrayList<String> exercises = new ArrayList<>();

        addActionToAddExercise(addExercise, exNameField, exercises);
        addActToCreateWorkout(createWorkout, wkNameField, exNameField, newWorkout, exercises);
        addActionToBackButton(back);

        addComponentsToMenuPanel(wkName, wkNameField, exName, exNameField, addExercise, createWorkout, back);

        menu.revalidate();
        menu.repaint();
    }




    // EFFECTS: adds actionListener to the add exercise button
    private void addActionToAddExercise(JButton button, JTextField textField, ArrayList<String> exList) {
        button.addActionListener(e -> {
            String exName = textField.getText().trim();
            if (!exName.isEmpty()) {
                exList.add(exName);
                textField.setText("");
                JOptionPane.showMessageDialog(null, "Exercise added!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Please enter a valid exercise name.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }


    //EFFECTS; adds actionListener to create workout button
    private void addActToCreateWorkout(JButton b, JTextField wkF, JTextField exF, Workout wk, ArrayList<String> exs) {
        b.addActionListener(e -> {
            String wkName = wkF.getText().trim();
            if (!wkName.isEmpty() && !exs.isEmpty()) {
                wk.setName(wkName);
                for (String exName : exs) {
                    wk.addExercise(new Exercise(exName));
                }
                workouts.add(wk);
                wkF.setText("");
                exF.setText("");
                exs.clear();
                JOptionPane.showMessageDialog(null, "Workout created!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Please enter a valid workout name and add at least one exercise",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

   // EFFECTS: add action to back to the main menu button
    private void addActionToBackButton(JButton button) {
        button.addActionListener(e -> {
            menu.removeAll();
            startMenu();
            menu.revalidate();
            menu.repaint();

            startMenuButtons();
            addButtons(b1, b2, b3, b4, b5, b6, b7);
            assignButton();

        });
    }

    private void addComponentsToMenuPanel(JLabel wkNameLabel, JTextField wkNameField, JLabel exNameLabel,
                                          JTextField exNameField, JButton addEx, JButton createWk, JButton back) {
        menu.add(wkNameLabel);
        menu.add(wkNameField);
        menu.add(exNameLabel);
        menu.add(exNameField);
        menu.add(addEx);
        menu.add(createWk);
        menu.add(back);

    }

    public void startWorkoutPanel(ArrayList<Workout> workouts) {
        menu.removeAll();

        DefaultListModel<String> workoutNamesModel = new DefaultListModel<>();
        for (Workout workout : workouts) {
            workoutNamesModel.addElement(workout.getName());
        }
        JList<String> workoutNames = new JList<>(workoutNamesModel);
        JScrollPane scrollPane = new JScrollPane(workoutNames);

        workoutNames.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        workoutNames.addListSelectionListener(e -> {
            String selectedWkName = workoutNames.getSelectedValue();
            Workout selectedWk = getWorkoutByName(selectedWkName, workouts);
            startSelectedWorkout(selectedWk);
        });



        JButton back = new JButton("Back to the Main Menu");
        addActionToBackButton(back);

        menu.add(scrollPane);
        menu.add(back);

        menu.revalidate();
        menu.repaint();

    }

   // EFFECTS; gets a name and returns a workout with that same name, otherwise null
    private Workout getWorkoutByName(String workoutName, ArrayList<Workout> workouts) {
        for (Workout workout : workouts) {
            if (workout.getName().equals(workoutName)) {
                return workout;
            }
        }
        return null;
    }


    //EFFECTS: starts the selected workout
    private void startSelectedWorkout(Workout selectedWorkout) {
        menu.removeAll();

        JPanel exerciseInputPanel = createExerciseInputPanel(selectedWorkout);
        JButton submitWorkoutButton = new JButton("Submit Workout");
        JButton back = new JButton("Back to the Main Menu");

        addActionToSubmitWorkoutButton(submitWorkoutButton, selectedWorkout, exerciseInputPanel);
        addActionToBackButton(back);

        menu.add(exerciseInputPanel);
        menu.add(submitWorkoutButton);
        menu.add(back);

        menu.revalidate();
        menu.repaint();
    }

    //EFFECTS: creates a panel that receives user input for reps, sets and weight
    private JPanel createExerciseInputPanel(Workout selectedWorkout) {
        JPanel exerciseInputPanel = new JPanel(new GridLayout(selectedWorkout.getExercises().size(), 4));

        for (Exercise exercise : selectedWorkout.getExercises()) {
            JLabel exerciseLabel = new JLabel(exercise.getName());
            JLabel repsLabel = new JLabel("Reps:");
            JTextField repField = new JTextField(5);
            JLabel setsLabel = new JLabel("Sets:");
            JTextField setField = new JTextField(5);
            JLabel weightLabel = new JLabel("Weight(lbs):");
            JTextField weightField = new JTextField(5);

            exerciseInputPanel.add(exerciseLabel);
            exerciseInputPanel.add(repsLabel);
            exerciseInputPanel.add(repField);
            exerciseInputPanel.add(setsLabel);
            exerciseInputPanel.add(setField);
            exerciseInputPanel.add(weightLabel);
            exerciseInputPanel.add(weightField);
        }

        return exerciseInputPanel;
    }

   //EFFECTS: adds actionListener to submit workout button
    private void addActionToSubmitWorkoutButton(JButton submitWk, Workout wk, JPanel exInputPanel) {
        submitWk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                WorkoutLog log = createWorkoutLogFromInput(wk, exInputPanel);
                displayWorkoutSummary(log);
            }
        });
    }

   //EFFECTS: displays a summary of the last workout
    private void displayWorkoutSummary(WorkoutLog log) {
        StringBuilder summary = new StringBuilder();
        summary.append("Workout Summary: \n");

        for (Sets set : log.getSets()) {
            summary.append("\nExercise: " + set.getName() + "\n");
            summary.append("Reps: " + set.getReps() + "\n");
            summary.append("Sets; " + set.getSetsDone() + "\n");
            summary.append("Weight: " + set.getWeight() + "\n");
        }

        JOptionPane.showMessageDialog(null, summary.toString(), "Workout Summary", JOptionPane.INFORMATION_MESSAGE);
    }

    // EFFECTS: creates a WorkoutLog from user input
    private WorkoutLog createWorkoutLogFromInput(Workout wk, JPanel exInputPanel) {

        for (int i = 0; i < wk.getExercises().size(); i++) {
            Exercise exercise = wk.getExercises().get(i);
            Sets set = new Sets((exercise.getName()));

            int reps = Integer.parseInt(((JTextField) exInputPanel.getComponent(7 * i + 2)).getText());
            int sets = Integer.parseInt(((JTextField) exInputPanel.getComponent(7 * i + 4)).getText());
            int weight = Integer.parseInt(((JTextField) exInputPanel.getComponent(7 * i + 6)).getText());

            set.addReps(reps);
            set.addSets(sets);
            set.addWeight(weight);

            log.addSet(set);
        }

        return log;
    }


    // REQUIRES: file is valid
    // MODIFIES: WORKOUTS_FILE
    // EFFECTS: saves last workouts
    public void saveWorkout() {
        try {
            JsonWriter writer = new JsonWriter(WORKOUTS_FILE);
            writer.open();
            writer.writeWorkouts(workouts);
            writer.close();
            JOptionPane.showMessageDialog(null, "Workouts saved!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Unable to save workouts.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // REQUIRES: WORKOUTS_FILE is not empty
    // MODIFIES: workouts
    // EFFECTS; loads workouts from file
    public void loadWorkout() {
        WorkoutJsonReader reader = new WorkoutJsonReader(WORKOUTS_FILE);
        try {
            workouts = reader.read();
            JOptionPane.showMessageDialog(null,
                    "Workouts loaded successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Unable to load workouts", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // REQUIRES: file is valid
    // MODIFIES: WORKOUT_LOG_FILE
    // EFFECTS: saves last workout summary
    public void saveLastWorkout() {
        try {
            JsonWriter writer = new JsonWriter(WORKOUT_LOG_FILE);
            writer.open();
            writer.writeWorkoutLog(log);
            writer.close();
            JOptionPane.showMessageDialog(null, "Workout summary saved!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Unable to save summary.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // REQUIRES: WORKOUT_LOG_FILE is not empty
    // MODIFIES: loadedLog
    // EFFECTS; loads the last WorkoutLog and prints it
    public void loadLastWorkout() {
        WorkoutLogJsonReader reader = new WorkoutLogJsonReader(WORKOUT_LOG_FILE);
        try {
            loadedLog = reader.read();
            displayWorkoutSummary(loadedLog);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Unable to load workouts", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


}
