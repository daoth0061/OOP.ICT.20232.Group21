package screen;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import model.Graph;
import model.Node;
import model.Edge;

import java.io.IOException;
import java.util.*;

public class GraphGenController {

    @FXML
    private Canvas graphCanvas;

    @FXML
    private Button generateButton;

    @FXML
    private TextField numberOfNodesTextField;

    @FXML
    private TextField maxWeightTextField;

    @FXML
    private Label generatedLabel;

    // Khai báo Map để lưu trữ tọa độ và màu sắc của các node
    private Map<String, double[]> nodeInfoMap = new HashMap<>();


    @FXML
    private void handleGenerate() throws IOException {
        int numberOfNodes = Integer.parseInt(numberOfNodesTextField.getText());
        int maxWeight = Integer.parseInt(maxWeightTextField.getText());

        Graph graph = new Graph(numberOfNodes, maxWeight);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Traversal.fxml"));
        AnchorPane traversalPane = loader.load();
        TraversalController traversalController = loader.getController();
        traversalController.setGraph(graph);


        drawGraph(graph);

    }

    private void drawGraph(Graph graph) {
        GraphicsContext gc = graphCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0, graphCanvas.getWidth(), graphCanvas.getHeight());

        double width = graphCanvas.getWidth();
        double height = graphCanvas.getHeight();

        ArrayList<Node> nodeList = graph.getNodes();
        Set<Node> nodeSet = new HashSet<>(nodeList);
        Map<Node, List<Boolean>> adjMatrix = graph.getAdjMatrix();
        Set<Edge> edgeList = graph.getEdges();

        int n = nodeSet.size();
        double radius = 8; // Radius for nodes
        double centerX = width / 2;
        double centerY = height / 2;
        double angleStep = 137.5; // Golden angle in degrees
        double spiralFactor = 70; // Controls spacing between nodes in the spiral

        double[] x = new double[n];
        double[] y = new double[n];
        Node[] nodeArray = nodeSet.toArray(new Node[0]);

        // Calculate coordinates for the nodes in a spiral
        for (int i = 0; i < n; i++) {
            double angle = Math.toRadians(i * angleStep);
            double distance = spiralFactor * Math.sqrt(i);
            x[i] = centerX + distance * Math.cos(angle);
            y[i] = centerY + distance * Math.sin(angle);
        }

// Draw edges with weights
        gc.setStroke(Color.BLACK);
        gc.setFill(Color.BLACK);
        for (Edge edge : edgeList) {
            Iterator<Node> it = edge.getNodes().iterator();
            Node node1 = it.next();
            Node node2 = it.next();

            int index1 = -1, index2 = -1;
            for (int i = 0; i < n; i++) {
                if (nodeArray[i].equals(node1)) {
                    index1 = i;
                }
                if (nodeArray[i].equals(node2)) {
                    index2 = i;
                }
                if (index1 != -1 && index2 != -1) break;
            }
            if (index1 != -1 && index2 != -1) {
                gc.strokeLine(x[index1], y[index1], x[index2], y[index2]);

                // Calculate the position for weight to avoid overlapping with edges
                double weightX = (x[index1] + x[index2]) / 2;
                double weightY = (y[index1] + y[index2]) / 2;

                // Draw the weight of the edge at the new position
                gc.setFill(Color.BLUE);
                gc.setFont(new Font("Arial", 12));
                gc.fillText(String.valueOf(edge.getWeight()), weightX, weightY);
                gc.setFill(Color.BLACK);
                gc.setFont(Font.getDefault());
            }
        }

// Draw nodes
        gc.setFill(Color.RED);
        for (int i = 0; i < n; i++) {
            gc.fillOval(x[i] - radius, y[i] - radius, radius * 2, radius * 2);
            gc.setFill(Color.BLACK);
            gc.setFont(new Font("Arial", 10));

            // Calculate the position for the label to ensure it is within the node
            double labelX = x[i] - radius + 2; // Offset from left side of the node
            double labelY = y[i] + 4; // Offset from top side of the node
            gc.fillText(Character.toString(nodeArray[i].getValue()), labelX, labelY);
            gc.setFill(Color.RED);
            nodeInfoMap.put(String.valueOf(nodeArray[i].getValue()), new double[]{x[i], y[i], radius});
        }

    }
    public void drawColoredNode( String nodeName, double radius, Color color) {
        GraphicsContext gc = graphCanvas.getGraphicsContext2D();
        double[] coordinates = nodeInfoMap.get(nodeName);
        if (coordinates != null) {
            double x = coordinates[0];
            double y = coordinates[1];
            gc.setFill(color);
            gc.fillOval(x - radius, y - radius, radius * 2, radius * 2);
            gc.setFill(Color.BLACK);
            gc.setFont(new Font("Arial", 10));

            // Calculate the position for the label to ensure it is within the node
            double labelX = x - radius + 2; // Offset from left side of the node
            double labelY = y + 4; // Offset from top side of the node
            gc.fillText(nodeName, labelX, labelY);
        } else {
            System.out.println("Node không tồn tại trong nodeInfoMap");
        }
    }


    public void handleGenerateAction(ActionEvent actionEvent) throws IOException {
        handleGenerate();
    }
}
