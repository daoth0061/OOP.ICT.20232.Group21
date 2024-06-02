package GUI;

import DataStructure.MyArrayList;
import DataStructure.MyLinkedList;
import DataStructure.MyQueue;
import DataStructure.MyStack;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class MainGUI extends Application {

    // Data structures
    private MyArrayList<Integer> arrayList;
    private MyLinkedList<Integer> linkedList;
    private MyStack<Integer> stack;
    private MyQueue<Integer> queue;

    // GUI components
    private BorderPane mainMenuLayout;
    private GridPane demonstrationPane;
    private TextArea outputArea;
    private Stage primaryStage;
    private ChoiceBox<String> operationChoiceBox;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        // Initialize data structures
        arrayList = new MyArrayList<>();
        linkedList = new MyLinkedList<>();
        stack = new MyStack<>();
        queue = new MyQueue<>();

        // Main menu components
        Label titleLabel = new Label("Data Structure Demo");
        ChoiceBox<String> structureChoiceBox = new ChoiceBox<>();
        structureChoiceBox.getItems().addAll("List", "Stack", "Queue");
        structureChoiceBox.setValue("List"); // Default selection
        Button quitButton = new Button("Quit");

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

        // Main menu layout
        GridPane mainMenuPane = new GridPane();
        mainMenuPane.setPadding(new Insets(10));
        mainMenuPane.setHgap(10);
        mainMenuPane.setVgap(10);
        mainMenuPane.addRow(0, titleLabel);
        mainMenuPane.addRow(1, new Label("Select Data Structure:"), structureChoiceBox);
        mainMenuPane.addRow(2, quitButton);
        mainMenuLayout = new BorderPane();
        mainMenuLayout.setCenter(mainMenuPane);

        // Demonstration layout
        demonstrationPane.addRow(0, new Label("Select Operation:"), operationChoiceBox, executeButton);
        demonstrationPane.addRow(1, outputArea);
        BorderPane demonstrationLayout = new BorderPane();
        demonstrationLayout.setCenter(demonstrationPane);

        // Set actions for buttons
        quitButton.setOnAction(e -> confirmQuit());
        executeButton.setOnAction(e -> executeOperation(structureChoiceBox.getValue(), operationChoiceBox.getValue()));

        // Show main menu
        primaryStage.setScene(new Scene(mainMenuLayout, 400, 300));
        primaryStage.setTitle("Data Structure Demo");
        primaryStage.show();
    }

    private void executeOperation(String dataStructure, String operation) {
        outputArea.clear();
        switch (dataStructure) {
            case "List":
                executeListOperation(operation);
                break;
            case "Stack":
                executeStackOperation(operation);
                break;
            case "Queue":
                executeQueueOperation(operation);
                break;
        }
    }

    private void executeListOperation(String operation) {
        outputArea.appendText("List Operations:\n");
        switch (operation) {
            case "Create":
                createList(arrayList);
                break;
            case "Insert":
                insertElement(arrayList);
                break;
            case "Sort":
                sortList(arrayList);
                break;
            case "Find":
                findElement(arrayList);
                break;
            case "Delete":
                deleteElement(arrayList);
                break;
        }
    }

    private void executeStackOperation(String operation) {
        outputArea.appendText("Stack Operations:\n");
        switch (operation) {
            case "Create":
                createList(stack);
                break;
            case "Insert":
                insertElement(stack);
                break;
            case "Sort":
                // Stack does not support sorting
                outputArea.appendText("Stack does not support sorting.\n");
                break;
            case "Find":
                findElement(stack);
                break;
            case "Delete":
                deleteElement(stack);
                break;
        }
    }

    private void executeQueueOperation(String operation) {
        outputArea.appendText("Queue Operations:\n");
        switch (operation) {
            case "Create":
                createList(queue);
                break;
            case "Insert":
                insertElement(queue);
                break;
            case "Sort":
                // Queue does not support sorting
                outputArea.appendText("Queue does not support sorting.\n");
                break;
            case "Find":
                findElement(queue);
                break;
            case "Delete":
                deleteElement(queue);
                break;
        }
    }

    private <E> void createList(MyList<E> list) {
        outputArea.appendText("Creating list...\n");
        for (int i = 1; i <= 5; i++) {
            list.add((E) Integer.valueOf(i));
        }
        outputArea.appendText("List: " + list.toString() + "\n");
    }

    private <E> void insertElement(MyList<E> list) {
        outputArea.appendText("Inserting element 10 at index 2...\n");
        list.add(2, (E) Integer.valueOf(10));
        outputArea.appendText("List: " + list.toString() + "\n");
    }

    private <E extends Comparable<E>> void sortList(MyList<E> list) {
        outputArea.appendText("Sorting list...\n");
        list.sort();
        outputArea.appendText("List: " + list.toString() + "\n");
    }

    private <E> void findElement(MyList<E> list) {
        outputArea.appendText("Finding element 3...\n");
        int index = list.indexOf((E) Integer.valueOf(3));
        if (index != -1) {
            outputArea.appendText("Element 3 found at index " + index + "\n");
        } else {
            outputArea.appendText("Element 3 not found\n");
        }
    }

    private <E> void deleteElement(MyList<E> list) {
        outputArea.appendText("Deleting element at index 1...\n");
        list.remove(1);
        outputArea.appendText("List: " + list.toString() + "\n");
    }

    private void confirmQuit() {
        // Show confirmation dialog to confirm quit
        // If confirmed, close the application
        primaryStage.close();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
