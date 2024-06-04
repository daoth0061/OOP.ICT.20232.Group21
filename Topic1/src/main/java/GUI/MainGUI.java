package GUI;

import DataStructure.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class MainGUI extends Application {

    // Data structures
    private AbstractList<Integer> currentDataStructure;

    // GUI components
    private BorderPane mainMenuLayout;
    private BorderPane demonstrationLayout;
    private GridPane demonstrationPane;
    private TextArea outputArea;
    private ChoiceBox<String> operationChoiceBox;
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        // Initialize main menu
        initializeMainMenu();

        // Initialize demonstration layout
        initializeDemonstrationLayout();

        // Show main menu
        primaryStage.setScene(new Scene(mainMenuLayout, 800, 600));
        primaryStage.setTitle("Data Structure Demonstration");
        primaryStage.show();
    }

    private void initializeMainMenu() {
        mainMenuLayout = new BorderPane();
        GridPane mainMenuPane = new GridPane();
        mainMenuPane.setPadding(new Insets(10));
        mainMenuPane.setHgap(10);
        mainMenuPane.setVgap(10);

        Label titleLabel = new Label("Data Structure Demonstration");
        mainMenuPane.add(titleLabel, 0, 0);

        Button proceedButton = new Button("Proceed");
        proceedButton.setOnAction(e -> showDemonstrationLayout());
        mainMenuPane.add(proceedButton, 0, 1);

        Button helpButton = new Button("Help");
        helpButton.setOnAction(e -> showHelp());
        mainMenuPane.add(helpButton, 0, 2);

        Button quitButton = new Button("Quit");
        quitButton.setOnAction(e -> quitApplication());
        mainMenuPane.add(quitButton, 0, 3);

        mainMenuLayout.setCenter(mainMenuPane);
    }

    private void showDemonstrationLayout() {
        primaryStage.setScene(new Scene(demonstrationLayout, 800, 600));
    }

    private void initializeDemonstrationLayout() {
        demonstrationLayout = new BorderPane();
        demonstrationPane = new GridPane();
        demonstrationPane.setPadding(new Insets(10));
        demonstrationPane.setHgap(10);
        demonstrationPane.setVgap(10);

        ChoiceBox<String> structureChoiceBox = new ChoiceBox<>();
        structureChoiceBox.getItems().addAll("List", "Queue", "Stack");
        demonstrationPane.add(new Label("Choose Data Structure:"), 0, 0);
        demonstrationPane.add(structureChoiceBox, 1, 0);

        Button createButton = new Button("Create");

        createButton.setOnAction(e -> {
            String selectedStructure = structureChoiceBox.getValue();
            switch (selectedStructure) {
                case "List":
                    currentDataStructure = new MyList<>();
                    break;
                case "Queue":
                    currentDataStructure = new MyQueue<>();
                    break;
                case "Stack":
                    currentDataStructure = new MyStack<>();
                    break;
            }
            outputArea.appendText("Created " + selectedStructure + "\n");
        });
        demonstrationPane.add(createButton, 2, 0);

        operationChoiceBox = new ChoiceBox<>();
        operationChoiceBox.getItems().addAll("Insert", "Sort", "Find", "Delete", "Get", "Set");
        demonstrationPane.add(new Label("Choose Operation:"), 0, 1);
        demonstrationPane.add(operationChoiceBox, 1, 1);

        Button executeButton = new Button("Execute");
        executeButton.setOnAction(e -> executeOperation());
        demonstrationPane.add(executeButton, 2, 1);

        outputArea = new TextArea();
        outputArea.setEditable(false);
        demonstrationLayout.setCenter(outputArea);
        demonstrationLayout.setTop(demonstrationPane);
    }

    private void executeOperation() {
        if (currentDataStructure == null) {
            outputArea.appendText("Please create a data structure first.\n");
            return;
        }

        String selectedOperation = operationChoiceBox.getValue();
        switch (selectedOperation) {
            case "Insert":
                TextInputDialog insertDialog = new TextInputDialog();
                insertDialog.setHeaderText("Insert Element");
                insertDialog.setContentText("Please enter the element to insert:");
                insertDialog.showAndWait().ifPresent(input -> {
                    try {
                        Integer element = Integer.parseInt(input);
                        currentDataStructure.insert(element);
                        outputArea.appendText("Inserted " + element + "\n");
                    } catch (NumberFormatException e) {
                        outputArea.appendText("Invalid input. Please enter an integer.\n");
                    }
                });
                break;
            case "Sort":
                currentDataStructure.sort();
                outputArea.appendText("Sorted Data Structure\n");
                break;
            case "Find":
                TextInputDialog findDialog = new TextInputDialog();
                findDialog.setHeaderText("Find Element");
                findDialog.setContentText("Please enter the element to find:");
                findDialog.showAndWait().ifPresent(input -> {
                    try {
                        Integer element = Integer.parseInt(input);
                        int index = currentDataStructure.find(element);
                        outputArea.appendText("Element " + element + " found at index: " + index + "\n");
                    } catch (NumberFormatException e) {
                        outputArea.appendText("Invalid input. Please enter an integer.\n");
                    }
                });
                break;
            case "Delete":
                TextInputDialog deleteDialog = new TextInputDialog();
                deleteDialog.setHeaderText("Delete Element");
                deleteDialog.setContentText("Please enter the element to delete:");
                deleteDialog.showAndWait().ifPresent(input -> {
                    try {
                        Integer element = Integer.parseInt(input);
                        currentDataStructure.delete(element);
                        outputArea.appendText("Deleted " + element + "\n");
                    } catch (NumberFormatException e) {
                        outputArea.appendText("Invalid input. Please enter an integer.\n");
                    }
                });
                break;
            case "Get":
                TextInputDialog getDialog = new TextInputDialog();
                getDialog.setHeaderText("Get Element");
                getDialog.setContentText("Please enter the index:");
                getDialog.showAndWait().ifPresent(input -> {
                    try {
                        int index = Integer.parseInt(input);
                        Integer element = currentDataStructure.get(index);
                        outputArea.appendText("Element at index " + index + " is " + element + "\n");
                    } catch (NumberFormatException e) {
                        outputArea.appendText("Invalid input. Please enter an integer.\n");
                    } catch (IndexOutOfBoundsException e) {
                        outputArea.appendText("Index out of bounds.\n");
                    }
                });
                break;
            case "Set":
                TextInputDialog setDialog = new TextInputDialog();
                setDialog.setHeaderText("Set Element");
                setDialog.setContentText("Please enter the index and element to set (comma-separated):");
                setDialog.showAndWait().ifPresent(input -> {
                    try {
                        String[] parts = input.split(",");
                        if (parts.length != 2) {
                            throw new IllegalArgumentException("Please provide both index and element.");
                        }
                        int index = Integer.parseInt(parts[0].trim());
                        Integer element = Integer.parseInt(parts[1].trim());
                        currentDataStructure.set(index, element);
                        outputArea.appendText("Set element at index " + index + " to " + element + "\n");
                    } catch (NumberFormatException e) {
                        outputArea.appendText("Invalid input. Please enter valid integers.\n");
                    } catch (IndexOutOfBoundsException e) {
                        outputArea.appendText("Index out of bounds.\n");
                    } catch (IllegalArgumentException e) {
                        outputArea.appendText(e.getMessage() + "\n");
                    }
                });
                break;
        }
    }

    private void showHelp() {
        Alert helpAlert = new Alert(Alert.AlertType.INFORMATION);
        helpAlert.setTitle("Help");
        helpAlert.setHeaderText("Basic Usage");
        helpAlert.setContentText("""
                1. Select a data structure from the dropdown.
                2. Click 'Create' to create the selected data structure.
                3. Choose an operation from the dropdown.
                4. Click 'Execute' to perform the operation.
                5. Follow the prompts to provide necessary inputs.""");
        helpAlert.showAndWait();
    }

    private void quitApplication() {
        Alert quitAlert = new Alert(Alert.AlertType.CONFIRMATION);
        quitAlert.setTitle("Quit");
        quitAlert.setHeaderText("Confirm Quit");
        quitAlert.setContentText("Are you sure you want to quit?");
        quitAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                primaryStage.close();
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
