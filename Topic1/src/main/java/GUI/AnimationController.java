package GUI;

import DataStructure.AbstractList;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.LinkedList;
import java.util.ArrayList;
import java.util.List;

public class AnimationController {
    private TextArea outputArea;
    private Pane animationPane;
    private LinkedList<LinkedListNode> linkedListElements;
    private List<Rectangle> dynamicArrayElements;
    private List<Text> dynamicArrayTextElements;
    private int dynamicArraySize;
    private int dynamicArrayCapacity;
    private double baseX = 100;
    private double baseY = 100;
    private double rectWidth = 50;
    private double rectHeight = 20;
    private double spacing = 10;

    public AnimationController(TextArea outputArea, Pane animationPane) {
        this.outputArea = outputArea;
        this.animationPane = animationPane;
        this.dynamicArrayElements = new ArrayList<>();
        this.dynamicArrayTextElements = new ArrayList<>();
        this.linkedListElements = new LinkedList<>();
        this.dynamicArrayCapacity = 2;  // Initial capacity
        this.dynamicArraySize = 0;  // Initially empty
    }
    private static class LinkedListNode {
        Rectangle rect;
        Text text;
        Polygon arrow;

        LinkedListNode(Rectangle rect, Text text) {
            this.rect = rect;
            this.text = text;
        }
    }
    private static Text createStyledText(String content) {
        // Create the text
        Text animatingText = new Text(content);

        // Set font and size
        animatingText.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));

        // Set color
        animatingText.setFill(Color.DARKBLUE);

        // Add shadow effect
        DropShadow shadow = new DropShadow();
        shadow.setOffsetX(3);
        shadow.setOffsetY(3);
        shadow.setColor(Color.GRAY);
        animatingText.setEffect(shadow);

        // Add glow effect
        Glow glow = new Glow();
        glow.setLevel(0.5);
        animatingText.setEffect(glow);

        return animatingText;
    }

    public void animateLinkedList() {
        animationPane.getChildren().clear();
        Text animatingText = createStyledText("Animating Linked List...");
        animatingText.setX(50);
        animatingText.setY(50);
        animationPane.getChildren().add(animatingText);

        double currentX = baseX;
        LinkedListNode previousElement = null;
        for (LinkedListNode element : linkedListElements) {
            element.rect.setX(currentX);
            element.rect.setY(baseY);
            element.text.setX(currentX + 15); // Center the text horizontally
            element.text.setY(baseY + 15); // Center the text vertically

            animationPane.getChildren().addAll(element.rect, element.text);

            if (previousElement != null) {
                Polygon arrow = createArrow(previousElement.rect.getX() + rectWidth, baseY + rectHeight / 2, currentX, baseY + rectHeight / 2);
                animationPane.getChildren().add(arrow);
                previousElement.arrow = arrow;
            }

            currentX += rectWidth + spacing;
            previousElement = element;
        }

        if (previousElement != null) {
            Text nullText = new Text("null");
            nullText.setX(currentX);
            nullText.setY(baseY + 15);
            animationPane.getChildren().add(nullText);
        }
    }

    private Polygon createArrow(double startX, double startY, double endX, double endY) {
        double arrowHeadSize = 10;
        Polygon arrow = new Polygon();
        arrow.getPoints().addAll(
                endX, endY,
                endX - arrowHeadSize, endY - arrowHeadSize,
                endX - arrowHeadSize, endY + arrowHeadSize
        );
        arrow.setFill(Color.BLACK);
        return arrow;
    }

    public void enqueue(int value) {
        Rectangle rect = new Rectangle(rectWidth, rectHeight, Color.BLUE);
        Text text = new Text(String.valueOf(value));
        text.setFill(Color.WHITE);
        LinkedListNode node = new LinkedListNode(rect, text);
        linkedListElements.add(node);
        animateLinkedList();
    }

    public void dequeue() {
        if (!linkedListElements.isEmpty()) {
            linkedListElements.poll();
            animateLinkedList();
        } else {
            Text animatingText = createStyledText("Queue is empty. Cannot dequeue.\n");
            animatingText.setX(50);
            animatingText.setY(80);
            animationPane.getChildren().add(animatingText);
        }
    }

    public void push(int value) {
        Rectangle rect = new Rectangle(rectWidth, rectHeight, Color.GREEN);
        Text text = new Text(String.valueOf(value));
        text.setFill(Color.WHITE);
        LinkedListNode node = new LinkedListNode(rect, text);
        linkedListElements.addFirst(node);
        animateLinkedList();
    }

    public void pop() {
        if (!linkedListElements.isEmpty()) {
            linkedListElements.pollFirst();
            animateLinkedList();
        } else {
            Text animatingText = createStyledText("Stack is empty. Cannot pop.\n");
            animatingText.setX(50);
            animatingText.setY(145);
            animationPane.getChildren().add(animatingText);
        }
    }
    public void initializeDynamicArray() {
        dynamicArrayCapacity = 2;
        dynamicArraySize = 0;

        dynamicArrayElements.clear();
        dynamicArrayTextElements.clear();
        for (int i = 0; i < dynamicArrayCapacity; i++) {
            Rectangle rect = new Rectangle(rectWidth, rectHeight, Color.LIGHTGRAY);
            dynamicArrayElements.add(rect);
            dynamicArrayTextElements.add(null);
        }
        animateDynamicArray();
    }

    private void animateDynamicArray() {
        animationPane.getChildren().clear();
        Text animatingText = createStyledText("Animating List...");
        animatingText.setX(50);
        animatingText.setY(50);
        animationPane.getChildren().add(animatingText);

        double currentX = baseX;
        for (int i = 0; i < dynamicArrayElements.size(); i++) {
            Rectangle rect = dynamicArrayElements.get(i);
            rect.setX(currentX);
            rect.setY(baseY);
            animationPane.getChildren().add(rect);

            Text text = dynamicArrayTextElements.get(i);
            if (text != null) {
                text.setX(currentX + 15);  // Center the text horizontally
                text.setY(baseY + 15);  // Center the text vertically
                animationPane.getChildren().add(text);
            }

            currentX += rectWidth + spacing;
        }
    }

    private void doubleCapacity() {
        dynamicArrayCapacity *= 2;

        for (int i = dynamicArrayElements.size(); i < dynamicArrayCapacity; i++) {
            Rectangle rect = new Rectangle(rectWidth, rectHeight, Color.LIGHTGRAY);
            dynamicArrayElements.add(rect);
            dynamicArrayTextElements.add(null);
        }
        animateDynamicArray();
    }

    public void addElementToDynamicArray(int value) {

        Rectangle rect = dynamicArrayElements.get(dynamicArraySize);
        rect.setFill(Color.PURPLE);
        Text text = new Text(String.valueOf(value));
        text.setFill(Color.WHITE);

        dynamicArrayTextElements.set(dynamicArraySize, text);
        dynamicArraySize++;
        animateDynamicArray();
        if (dynamicArraySize == dynamicArrayCapacity) {
            doubleCapacity();
        }
    }

    public void deleteElementFromDynamicArray(int index) {
        if (index < 0 || index >= dynamicArraySize) {
            Text animatingText = createStyledText("Invalid index. Cannot delete.\n");
            animatingText.setX(50);
            animatingText.setY(170);
            animationPane.getChildren().add(animatingText);
            return;
        }

        // Shift elements to the left
        for (int i = index; i < dynamicArraySize - 1; i++) {
            dynamicArrayTextElements.set(i, dynamicArrayTextElements.get(i + 1));
        }

        // Remove the last element
        dynamicArrayTextElements.set(dynamicArraySize - 1, null);
        dynamicArrayElements.get(dynamicArraySize - 1).setFill(Color.LIGHTGRAY);
        dynamicArraySize--;
        animateDynamicArray();
    }

    public void sortDynamicArray() {
        int[] i = {1};  // Use an array to allow modification inside lambda
        int[] j = new int[1];
        boolean[] sortingDone = {false};

        Timeline timeline = new Timeline();
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.75), e -> {
            if (i[0] < dynamicArraySize) {
                j[0] = i[0];
                if (j[0] > 0 && Integer.parseInt(dynamicArrayTextElements.get(j[0] - 1).getText()) > Integer.parseInt(dynamicArrayTextElements.get(j[0]).getText())) {
                    swapAndAnimate(j[0] - 1, j[0]);
                    j[0]--;
                    i[0]--;  // Decrement i to retry the current value
                } else {
                    i[0]++;  // Increment i to move to the next element
                }
            } else if (!sortingDone[0]) {
                sortingDone[0] = true;
                Text animatingText = createStyledText("Sorting completed.\n");
                animatingText.setX(50);
                animatingText.setY(175);
                animationPane.getChildren().add(animatingText);
                timeline.stop();
            }
        });

        timeline.getKeyFrames().add(keyFrame);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
    public void updateOutputArea(String text) {
        Text animatingText = createStyledText(text);
        animatingText.setX(50);
        animatingText.setY(60);
        animationPane.getChildren().add(animatingText);
    }

    private void swapAndAnimate(int index1, int index2) {
        // Swap text elements
        Text tempText = dynamicArrayTextElements.get(index1);
        dynamicArrayTextElements.set(index1, dynamicArrayTextElements.get(index2));
        dynamicArrayTextElements.set(index2, tempText);

        // Swap rectangles
        Rectangle tempRect = dynamicArrayElements.get(index1);
        dynamicArrayElements.set(index1, dynamicArrayElements.get(index2));
        dynamicArrayElements.set(index2, tempRect);

        // Animate the swap
        animateDynamicArray();
    }
    public void displayCurrentDataStructure(AbstractList<Integer> dataStructure) {
        animationPane.getChildren().clear();
        int x = 10;
        int y = 10;
        int width = 50;
        int height = 30;
        int spacing = 10;

        for (int i = 0; i < dataStructure.size(); i++) {
            int value = dataStructure.get(i);
            Rectangle rect = new Rectangle(x, y, width, height);
            rect.setFill(Color.LIGHTBLUE);
            rect.setStroke(Color.BLACK);

            Text text = new Text(x + 15, y + 20, String.valueOf(value));

            animationPane.getChildren().addAll(rect, text);
            x += width + spacing;
        }
    }

}
