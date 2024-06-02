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
    private MyList<Integer> list;
    private MyStack<Integer> stack;
    private MyQueue<Integer> queue;

    // GUI components
    private BorderPane mainMenuLayout;
    private BorderPane demonstrationLayout;
    private GridPane demonstrationPane;
    private TextArea outputArea;
    private ChoiceBox<String> operationChoiceBox;
    private Stage primaryStage;

    // Current selected data structure
    private enum DataStructureType { LIST, STACK, QUEUE }
    private DataStructureType currentDataStructureType;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        // Initialize data structures
        list = new MyList<Integer>();
        stack = new MyStack<>();
        queue = new MyQueue<>();

        // Initialize main menu
        initializeMainMenu();

        // Initialize demonstration layout
        initializeDemonstrationLayout();

        // Show main menu
        primaryStage.setScene(new Scene(mainMenuLayout, 400, 300));
        primaryStage.setTitle("Data Structure Demo");
        primaryStage.show();
    }

    private void initializeMainMenu() {
        // Main menu components
        Label titleLabel = new Label("Data Structure Demo");
        ChoiceBox<String> structureChoiceBox = new ChoiceBox<>();
        structureChoiceBox.getItems().addAll("List", "Stack", "Queue");
        structureChoiceBox.setValue("List"); // Default selection
        Button proceedButton = new Button("Proceed");
        Button helpButton = new Button("Help");
        Button quitButton = new Button("Quit");

        // Main menu layout
        GridPane mainMenuPane = new GridPane();
        mainMenuPane.setPadding(new Insets(10));
        mainMenuPane.setHgap(10);
        mainMenuPane.setVgap(10);
        mainMenuPane.addRow(0, titleLabel);
        mainMenuPane.addRow(1, new Label("Select Data Structure:"), structureChoiceBox);
        mainMenuPane.addRow(2, proceedButton);
        mainMenuPane.addRow(3, helpButton);
        mainMenuPane.addRow(4, quitButton);
        mainMenuLayout = new BorderPane();
        mainMenuLayout.setCenter(mainMenuPane);

        // Set actions for buttons
        proceedButton.setOnAction(e -> {
            String selectedStructure = structureChoiceBox.getValue();
            switch (selectedStructure) {
                case "List":
                    currentDataStructureType = DataStructureType.LIST;
                    break;
                case "Stack":
                    currentDataStructureType = DataStructureType.STACK;
                    break;
                case "Queue":
                    currentDataStructureType = DataStructureType.QUEUE;
                    break;
            }
            showDemonstrationLayout();
        });
        helpButton.setOnAction(e -> showHelp());
        quitButton.setOnAction(e -> confirmQuit());
    }

    private void initializeDemonstrationLayout() {
        // Demonstration components
        demonstrationPane = new GridPane();
        demonstrationPane.setPadding(new Insets(10));
        demonstrationPane.setHgap(10);
        demonstrationPane.setVgap(10);
        outputArea = new TextArea();
        outputArea.setEditable(false);
        outputArea.setWrapText(true);
        operationChoiceBox = new ChoiceBox<>();
        operationChoiceBox.getItems().addAll("Create", "Insert", "Sort", "Find", "Delete");
        operationChoiceBox.setValue("Create"); // Default selection
        Button executeButton = new Button("Execute");
        Button backButton = new Button("Back");

        // Demonstration layout
        demonstrationPane.addRow(0, new Label("Select Operation:"), operationChoiceBox, executeButton);
        demonstrationPane.addRow(1, outputArea);
        demonstrationLayout = new BorderPane();
        demonstrationLayout.setCenter(demonstrationPane);
        demonstrationLayout.setBottom(backButton);

        // Set actions for buttons
        executeButton.setOnAction(e -> executeOperation(operationChoiceBox.getValue()));
        backButton.setOnAction(e -> showMainMenu());
    }

    private void showMainMenu() {
        primaryStage.setScene(new Scene(mainMenuLayout, 400, 300));
    }

    private void showDemonstrationLayout() {
        primaryStage.setScene(new Scene(demonstrationLayout, 600, 400));
    }

    private void showHelp() {
        Alert helpAlert = new Alert(Alert.AlertType.INFORMATION);
        helpAlert.setTitle("Help");
        helpAlert.setHeaderText("Data Structure Demonstration Help");
        helpAlert.setContentText("This application allows you to demonstrate basic operations on List, Stack, and Queue data structures.\n\n" +
                "1. Select a data structure from the main menu.\n" +
                "2. In the demonstration screen, select an operation to perform (Create, Insert, Sort, Find, Delete).\n" +
                "3. The results of the operation will be displayed in the output area.\n" +
                "4. You can go back to the main menu at any time by clicking the Back button.");
        helpAlert.showAndWait();
    }

    private void confirmQuit() {
        Alert quitAlert = new Alert(Alert.AlertType.CONFIRMATION);
        quitAlert.setTitle("Confirm Quit");
        quitAlert.setHeaderText("Are you sure you want to quit?");
        quitAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                primaryStage.close();
            }
        });
    }

    private void executeOperation(String operation) {
        outputArea.clear();
        switch (operation) {
            case "Create":
                createDataStructure();
                break;
            case "Insert":
                insertElement();
                break;
            case "Sort":
                sortDataStructure();
                break;
            case "Find":
                findElement();
                break;
            case "Delete":
                deleteElement();
                break;
        }
    }

    private void createDataStructure() {
        outputArea.appendText("Creating data structure...\n");
        switch (currentDataStructureType) {
            case LIST:
                list.clear();
                for (int i = 1; i <= 5; i++) {
                    list.add(i);
                }
                outputArea.appendText("List: " + list.toString() + "\n");
                break;
            case STACK:
                stack.clear();
                for (int i = 1; i <= 5; i++) {
                    stack.push(i);
                }
                outputArea.appendText("Stack: " + stack.toString() + "\n");
                break;
            case QUEUE:
                queue.clear();
                for (int i = 1; i <= 5; i++) {
                    queue.enqueue(i);
                }
                outputArea.appendText("Queue: " + queue.toString() + "\n");
                break;
        }
    }

    private void insertElement() {
        outputArea.appendText("Inserting element 10...\n");
        switch (currentDataStructureType) {
            case LIST:
                list.add(2, 10);
                outputArea.appendText("List: " + list.toString() + "\n");
                break;
            case STACK:
                stack.push(10);
                outputArea.appendText("Stack: " + stack.toString() + "\n");
                break;
            case QUEUE:
                queue.enqueue(10);
                outputArea.appendText("Queue: " + queue.toString() + "\n");
                break;
        }
    }

    private void sortDataStructure() {
        outputArea.appendText("Sorting data structure...\n");
        switch (currentDataStructureType) {
            case LIST:
                list.sort();
                outputArea.appendText("List: " + list.toString() + "\n");
                break;
            case STACK:
                outputArea.appendText("Stack does not support sorting.\n");
                break;
            case QUEUE:
                outputArea.appendText("Queue does not support sorting.\n");
                break;
        }
    }

    private void findElement() {
        outputArea.appendText("Finding element 3...\n");
        int index;
        switch (currentDataStructureType) {
            case LIST:
                index = list.indexOf(3);
                if (index != -1) {
                    outputArea.appendText("Element 3 found at index " + index + "\n");
                } else {
                    outputArea.appendText("Element 3 not found\n");
                }
                break;
            case STACK:
                index = stack.search(3);
                if (index != -1) {
                    outputArea.appendText("Element 3 found at position " + index + " from the top of the stack\n");
                } else {
                    outputArea.appendText("Element 3 not found\n");
                }
                break;
            case QUEUE:
                index = queue.indexOf(3);
                if (index != -1) {
                    outputArea.appendText("Element 3 found at position " + index + " from the front of the queue\n");
                } else {
                    outputArea.appendText("Element 3 not found\n");
                }
                break;
        }
    }

    private void deleteElement() {
        outputArea.appendText("Deleting element at index 1...\n");
        switch (currentDataStructureType) {
            case LIST:
                list.remove(1);
                outputArea.appendText("List: " + list.toString() + "\n");
                break;
            case STACK:
                if (!stack.isEmpty()) {
                    stack.pop();
                    outputArea.appendText("Stack: " + stack.toString() + "\n");
                } else {
                    outputArea.appendText("Stack is empty.\n");
                }
                break;
            case QUEUE:
                if (!queue.isEmpty()) {
                    queue.dequeue();
                    outputArea.appendText("Queue: " + queue.toString() + "\n");
                } else {
                    outputArea.appendText("Queue is empty.\n");
                }
                break;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
