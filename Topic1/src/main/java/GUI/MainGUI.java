package GUI;

import DataStructure.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class MainGUI extends Application {

    // Data structures
    private AbstractList<Integer> currentDataStructure;

    // GUI components
    private BorderPane mainMenuLayout;
    private BorderPane demonstrationLayout;
    private TextArea outputArea;
    private ChoiceBox<String> operationChoiceBox;
    private Stage primaryStage;
    private AnimationController animationController;
    private boolean isDynamicArrayInitialized = false;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        // Initialize main menu
        initializeMainMenu();

        // Initialize demonstration layout
        initializeDemonstrationLayout();

        // Show main menu
        Scene scene = new Scene(mainMenuLayout, 800, 600);
        scene.getStylesheets().add(getClass().getResource("/GUI/style.css").toExternalForm());
        primaryStage.setScene(scene);
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
        titleLabel.getStyleClass().add("title-label");
        mainMenuPane.add(titleLabel, 0, 0);

        // Use icons for buttons
        Button proceedButton = createStyledButton("Proceed");
        ImageView proceedIcon = new ImageView(new Image(getClass().getResourceAsStream("/GUI/proceed_icon.png")));
        proceedIcon.setFitWidth(60); // Adjust the size as needed
        proceedIcon.setFitHeight(40);
        proceedButton.setGraphic(proceedIcon);
        proceedButton.setOnAction(e -> showDemonstrationLayout());
        mainMenuPane.add(proceedButton, 0, 1);

        Button helpButton = createStyledButton("Help");
        ImageView helpIcon = new ImageView(new Image(getClass().getResourceAsStream("/GUI/help_icon.png")));
        helpIcon.setFitWidth(60); // Adjust the size as needed
        helpIcon.setFitHeight(45);
        helpButton.setGraphic(helpIcon);
        helpButton.setOnAction(e -> showHelp());
        mainMenuPane.add(helpButton, 0, 2);

        Button quitButton = createStyledButton("Quit");
        ImageView quitIcon = new ImageView(new Image(getClass().getResourceAsStream("/GUI/quit_icon.png")));
        quitIcon.setFitWidth(45); // Adjust the size as needed
        quitIcon.setFitHeight(45);
        quitButton.setGraphic(quitIcon);
        quitButton.setOnAction(e -> quitApplication());
        mainMenuPane.add(quitButton, 0, 3);

        mainMenuLayout.setCenter(mainMenuPane);
    }
    private Button createStyledButton(String text) {
        Button button = new Button(text);
        button.getStyleClass().add("main-button");
        return button;
    }
    private void showDemonstrationLayout() {
        Scene scene = new Scene(demonstrationLayout, 800, 600);
        scene.getStylesheets().add(getClass().getResource("/GUI/style.css").toExternalForm());
        primaryStage.setScene(scene);
    }

    private void initializeDemonstrationLayout() {
        demonstrationLayout = new BorderPane();
        GridPane demonstrationPane = new GridPane();
        demonstrationPane.setPadding(new Insets(10));
        demonstrationPane.setHgap(10);
        demonstrationPane.setVgap(10);

        // Apply hover effects to buttons
        ChoiceBox<String> structureChoiceBox = new ChoiceBox<>();
        structureChoiceBox.getItems().addAll("List", "Queue", "Stack");
        structureChoiceBox.setTooltip(new Tooltip("Choose Data Structure"));
        demonstrationPane.add(new Label("Choose Data Structure:"), 0, 0);
        demonstrationPane.add(structureChoiceBox, 1, 0);

        // Customize fonts and colors
        ChoiceBox<String> createOptionBox = new ChoiceBox<>();
        createOptionBox.getItems().addAll("Empty", "Random", "Random Sorted");
        createOptionBox.setTooltip(new Tooltip("Choose Creation Option"));
        demonstrationPane.add(new Label("Create Option:"), 0, 1);
        demonstrationPane.add(createOptionBox, 1, 1);

        TextField nField = new TextField();
        nField.setPromptText("N = ");
        nField.setMaxWidth(50);
        demonstrationPane.add(new Label("Number of Elements:"), 0, 2);
        demonstrationPane.add(nField, 1, 2);

        // Add output area
        outputArea = new TextArea();
        outputArea.setEditable(false);
        demonstrationLayout.setCenter(outputArea);
        demonstrationLayout.setTop(demonstrationPane);

        Button createButton = new Button("Create");
        createButton.getStyleClass().add("create-button");
        createButton.setOnAction(e -> {
            String selectedStructure = structureChoiceBox.getValue();
            String createOption = createOptionBox.getValue();
            int n;
            try {
                n = Integer.parseInt(nField.getText());
            } catch (NumberFormatException ex) {
                outputArea.appendText("Invalid number of elements. Please enter an integer.\n");
                return;
            }
            if (selectedStructure != null && createOption != null) {
                createDataStructure(selectedStructure, createOption, n);
                displayDataStructure();
            } else {
                outputArea.appendText("Please select a data structure and a creation option.\n");
            }
        });
        createButton.setTooltip(new Tooltip("Create Data Structure"));
        demonstrationPane.add(createButton, 2, 1);

        operationChoiceBox = new ChoiceBox<>();
        operationChoiceBox.getItems().addAll("Insert", "Sort", "Find", "Delete", "Get", "Set");
        operationChoiceBox.setTooltip(new Tooltip("Choose Operation"));
        demonstrationPane.add(new Label("Choose Operation:"), 0, 3);
        demonstrationPane.add(operationChoiceBox, 1, 3);

        Button executeButton = new Button("Execute");
        executeButton.getStyleClass().add("execute-button");
        executeButton.setOnAction(e -> {
            executeOperation();
            displayDataStructure();
        });
        executeButton.setTooltip(new Tooltip("Execute Operation"));
        demonstrationPane.add(executeButton, 2, 3);

        // Create animation pane
        Pane animationPane = new Pane();
        animationPane.setPrefSize(400, 400);
        demonstrationLayout.setCenter(animationPane);

        // Create AnimationController instance
        animationController = new AnimationController(outputArea, animationPane);

        // Create buttons for animations
        Button stackAnimationButton = createStyledButton("Stack Animation");
        Button queueAnimationButton = createStyledButton("Queue Animation");
        Button listAnimationButton = createStyledButton("List Animation");

        // Set action handlers for buttons
        stackAnimationButton.setOnAction(e -> showStackOperation());
        queueAnimationButton.setOnAction(e -> showQueueOperation());
        listAnimationButton.setOnAction(e -> {
            if (!isDynamicArrayInitialized) {
                animationController.initializeDynamicArray();
                isDynamicArrayInitialized = true;
            }
            showListOperation();
        });

        // Add buttons to demonstration layout
        demonstrationPane.add(stackAnimationButton, 0, 4);
        demonstrationPane.add(queueAnimationButton, 1, 4);
        demonstrationPane.add(listAnimationButton, 2, 4);
    }
    private void showListOperation() {
        Stage operationStage = new Stage();
        operationStage.setTitle("List Operation");

        Label operationLabel = new Label("Choose Operation:");
        Label inputLabel = new Label("Enter number:");
        inputLabel.setVisible(false);

        Button addButton = new Button("Add");
        addButton.getStyleClass().add("operation-button");
        Button deleteButton = new Button("Delete");
        deleteButton.getStyleClass().add("operation-button");
        Button sortButton = new Button("Sort");
        sortButton.getStyleClass().add("operation-button");

        TextField inputField = new TextField();
        inputField.setPromptText("Enter number to add");
        inputField.setVisible(false);

        TextField deleteField = new TextField();
        deleteField.setPromptText("Enter index to delete");
        deleteField.setVisible(false);

        sortButton.setOnAction(e -> {
            animationController.sortDynamicArray();
            operationStage.close();
        });

        addButton.setOnAction(e -> {
            inputLabel.setVisible(true);
            inputLabel.setText("Enter number to add:");
            inputField.setVisible(true);
            deleteField.setVisible(false);
        });

        inputField.setOnAction(e -> {
            try {
                int value = Integer.parseInt(inputField.getText());
                animationController.addElementToDynamicArray(value);
                operationStage.close();
            } catch (NumberFormatException ex) {
                inputField.setText("Invalid number");
            }
        });

        deleteButton.setOnAction(e -> {
            inputLabel.setVisible(true);
            inputLabel.setText("Enter index to delete:");
            inputField.setVisible(false);
            deleteField.setVisible(true);
        });

        deleteField.setOnAction(e -> {
            try {
                int index = Integer.parseInt(deleteField.getText());
                animationController.deleteElementFromDynamicArray(index);
                operationStage.close();
            } catch (NumberFormatException ex) {
                deleteField.setText("Invalid index");
            }
        });

        VBox operationLayout = new VBox(10, operationLabel, addButton, deleteButton, sortButton, inputLabel, inputField, deleteField);
        operationLayout.setPadding(new Insets(15));
        Scene operationScene = new Scene(operationLayout, 300, 250);

        operationStage.setScene(operationScene);
        operationStage.show();
    }

    private void showQueueOperation() {
        Stage operationStage = new Stage();
        operationStage.setTitle("Queue Operation");

        Label operationLabel = new Label("Choose Operation:");
        Label inputLabel = new Label("Enter number to enqueue:");
        inputLabel.setVisible(false);

        Button enqueueButton = new Button("Enqueue");
        enqueueButton.getStyleClass().add("operation-button");
        Button dequeueButton = new Button("Dequeue");
        dequeueButton.getStyleClass().add("operation-button");

        TextField inputField = new TextField();
        inputField.setPromptText("Enter number to enqueue");
        inputField.setVisible(false);

        // Enqueue operation setup
        enqueueButton.setOnAction(e -> {
            inputLabel.setVisible(true);
            inputLabel.setText("Enter number to enqueue: ");
            inputField.setVisible(true);
            inputField.requestFocus();
            dequeueButton.setVisible(false);
        });

        inputField.setOnAction(e -> {
            try {
                int value = Integer.parseInt(inputField.getText());
                animationController.enqueue(value);
                operationStage.close();
            } catch (NumberFormatException ex) {
                inputField.setText("Invalid number");
            }
        });

        // Dequeue operation setup
        dequeueButton.setOnAction(e -> {
            animationController.dequeue();
            operationStage.close();
        });

        VBox operationLayout = new VBox(10, operationLabel, enqueueButton, dequeueButton, inputLabel, inputField);
        operationLayout.setPadding(new Insets(10));
        Scene operationScene = new Scene(operationLayout, 300, 200);

        operationStage.setScene(operationScene);
        operationStage.show();
    }

    private void showStackOperation() {
        Stage operationStage = new Stage();
        operationStage.setTitle("Stack Operation");

        Label operationLabel = new Label("Choose Operation:");
        Label inputLabel = new Label("Enter number to push:");
        inputLabel.setVisible(false);

        Button pushButton = new Button("Push");
        pushButton.getStyleClass().add("operation-button");
        Button popButton = new Button("Pop");
        popButton.getStyleClass().add("operation-button");

        TextField inputField = new TextField();
        inputField.setPromptText("Enter number to push");
        inputField.setVisible(false);

        // Push operation setup
        pushButton.setOnAction(e -> {
            inputLabel.setVisible(true);
            inputLabel.setText("Enter number to push: ");
            inputField.setVisible(true);
            inputField.requestFocus();
            popButton.setVisible(false);
        });

        inputField.setOnAction(e -> {
            try {
                int value = Integer.parseInt(inputField.getText());
                animationController.push(value);
                operationStage.close();
            } catch (NumberFormatException ex) {
                inputField.setText("Invalid number");
            }
        });

        // Pop operation setup
        popButton.setOnAction(e -> {
            animationController.pop();
            operationStage.close();
        });

        VBox operationLayout = new VBox(10, operationLabel, pushButton, popButton, inputLabel, inputField);
        operationLayout.setPadding(new Insets(10));
        Scene operationScene = new Scene(operationLayout, 300, 200);

        operationStage.setScene(operationScene);
        operationStage.show();
    }

    private void createDataStructure(String selectedStructure, String createOption, int n) {
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

        switch (createOption) {
            case "Empty":
                outputArea.appendText("Created an empty " + selectedStructure + "\n");
                break;
            case "Random":
                for (int i = 0; i < n; i++) {
                    currentDataStructure.insert((int) (Math.random() * 100));
                }
                outputArea.appendText("Created " + selectedStructure + " with " + n + " random elements\n");
                break;
            case "Random Sorted":
                for (int i = 0; i < n; i++) {
                    currentDataStructure.insert((int) (Math.random() * 100));
                }
                currentDataStructure.sort();
                outputArea.appendText("Created " + selectedStructure + " with " + n + " random sorted elements\n");
                break;
        }
    }

    private void executeOperation() {
        if (currentDataStructure == null) {
            animationController.updateOutputArea("Please create a data structure first.\n");
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

    private void displayDataStructure() {
        if (currentDataStructure != null) {

            // Append text representation of the data structure
            outputArea.appendText("Current Data Structure:\n" + currentDataStructure.toString() + "\n");

            // Display visual representation
            animationController.displayCurrentDataStructure(currentDataStructure);
        }
    }

    private void showHelp() {
        Alert helpAlert = new Alert(Alert.AlertType.INFORMATION);
        helpAlert.setTitle("Help");
        helpAlert.setHeaderText("Basic Usage");
        helpAlert.setContentText("1. Select a data structure from the dropdown.\n" +
                                 "2. Choose a creation option and provide the number of elements.\n" +
                                 "3. Click 'Create' to create the selected data structure.\n" +
                                 "4. Choose an operation from the dropdown.\n" +
                                 "5. Click 'Execute' to perform the operation.\n" +
                                 "6. Follow the prompts to provide necessary inputs.");
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
