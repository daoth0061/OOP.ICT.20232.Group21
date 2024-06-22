package screen;

import model.Edge;
import model.Graph;
import model.Node;
import search.Dijkstra;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class GraphUI extends JFrame {
    private final JButton StartButton;
    private JTextField nodesField;
    private JTextField maxWeightField;
    private JButton nextButton;
    private DefaultListModel<String> queueModel;
    private DefaultListModel<String> visitedModel;
    private JList<String> queueList;
    private JList<String> visitedList;
    private DrawPanel drawPanel;
    private int nodeCount;
    private int maxNodes;
    private int maxWeight;
    private ArrayList<Point> points;
    private Map<String, Point> pointMap;
    private Graph graph;
    private Node currentNode;
    private Set<Node> neighborNodes;

    private JTable processTable;

    private Dijkstra dijkstra; // Dijkstra object for search
    private List<Map<String, String>> steps;
    private List<String> keys; // List of keys in order
    int i = 0;

    public GraphUI() {
        // Set up the frame
        setTitle("Graph UI");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null); // Use null layout for custom positioning

        // Initialize points list and map
        points = new ArrayList<>();
        pointMap = new HashMap<>(); // Initialize pointMap

        // Create number of nodes input
        JLabel nodesLabel = new JLabel("Number of Nodes:");
        nodesLabel.setBounds(10, 10, 120, 25);
        add(nodesLabel);

        nodesField = new JTextField();
        nodesField.setBounds(140, 10, 100, 25);
        add(nodesField);

        // Create max weight input
        JLabel maxWeightLabel = new JLabel("Max Weight:");
        maxWeightLabel.setBounds(10, 45, 120, 25);
        add(maxWeightLabel);

        maxWeightField = new JTextField();
        maxWeightField.setBounds(140, 45, 100, 25);
        add(maxWeightField);

        // Create the Start button
        StartButton = new JButton("Start");
        StartButton.setBounds(10, 80, 230, 25);
        StartButton.addActionListener(new StartButtonListener());
        add(StartButton);

        // Create the Next button
        nextButton = new JButton("Next");
        nextButton.setBounds(10, 110, 230, 25);
        nextButton.addActionListener(new NextButtonListener());
        add(nextButton);

        // Create table for process tracking
        JLabel tableLabel = new JLabel("Process Table:");
        tableLabel.setBounds(10, 150, 120, 25);
        add(tableLabel);

        processTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(processTable);
        scrollPane.setBounds(10, 180, 230, 400);
        add(scrollPane);

        // Create and add draw panel
        drawPanel = new DrawPanel();
        drawPanel.setBounds(250, 10, 520, 520);
        add(drawPanel);
    }

    // Listener for StartButton
    private class StartButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                maxNodes = Integer.parseInt(nodesField.getText());
                maxWeight = Integer.parseInt(maxWeightField.getText());
                graph = new Graph(maxNodes, maxWeight);
                dijkstra = new Dijkstra(graph, graph.getNodes().get(0), graph.getNodes().get(maxNodes-1));
                dijkstra.search();
                steps = dijkstra.getSteps();
                keys = dijkstra.getListCurrentKey();
                System.out.println(keys);
                points.clear();
                pointMap.clear(); // Clear the point map as well
                nodeCount = 0; // Reset node count when the graph is initialized
                i = 0; // Reset step index
                drawPanel.repaint();
                updateProcessTable(new  ArrayList<>()); // Clear the table
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(GraphUI.this, "Please enter valid numbers.");
            }
        }
    }

    // Method to update process table
    private void updateProcessTable(List<Map<String, String>> steps) {
        if (steps == null || steps.isEmpty()) {
            return;
        }
        Map<String, String> latestStep = steps.get(steps.size() - 1);
        String[] columns = {"Node", "Distance", "Previous", "Visited"};
        String[][] data = new String[latestStep.size()][4];

        int index = 0;
        for (Map.Entry<String, String> entry : latestStep.entrySet()) {
            String[] values = entry.getValue().split(", ");
            data[index][0] = entry.getKey();
            data[index][1] = values[0].split(": ")[1];
            data[index][2] = values[1].split(": ")[1];
            data[index][3] = values[2].split(": ")[1];
            index++;
        }

        processTable.setModel(new javax.swing.table.DefaultTableModel(data, columns));
    }

    // Panel to draw the graph
    private class DrawPanel extends JPanel {
        public DrawPanel() {
            setBackground(Color.WHITE);
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (nodeCount < maxNodes) {
                        char pointName = (char) ('a' + nodeCount); // Generate point name
                        Point point = e.getPoint();
                        points.add(point);
                        pointMap.put(String.valueOf(pointName), point); // Add point to map
                        nodeCount++;
                        repaint();
                    } else {
                        JOptionPane.showMessageDialog(GraphUI.this, "Max nodes reached.");
                    }
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            // Draw points
            for (Map.Entry<String, Point> entry : pointMap.entrySet()) {
                Point point = entry.getValue();
                String pointName = entry.getKey();
                Node node = graph.getNodeByName(pointName);

                // Set color based on current and neighbor nodes
                if (currentNode != null && node.equals(currentNode)) {
                    g.setColor(Color.RED);
                } else if (neighborNodes != null && neighborNodes.contains(node)) {
                    g.setColor(Color.GREEN);
                } else {
                    g.setColor(Color.BLACK);
                }

                g.fillOval(point.x - 5, point.y - 5, 10, 10);
                g.drawString(pointName, point.x + 10, point.y);
            }

            // Draw edges if graph and edges are initialized
            if (nodeCount == maxNodes && graph != null && graph.getEdges() != null) {
                g.setColor(Color.BLUE);
                for (Edge edge : graph.getEdges()) {
                    List<Node> nodesList = new ArrayList<>(edge.getNodes());
                    Point p1 = pointMap.get(String.valueOf(nodesList.get(0).getValue()));
                    Point p2 = pointMap.get(String.valueOf(nodesList.get(1).getValue()));
                    if (p1 != null && p2 != null) { // Check if points are not null
                        g.drawLine(p1.x, p1.y, p2.x, p2.y);
                        int weightX = (p1.x + p2.x) / 2;
                        int weightY = (p1.y + p2.y) / 2;
                        g.drawString(String.valueOf(edge.getWeight()), weightX, weightY);
                    }
                }
            } else {
                System.out.println();
            }
        }
    }


    // Listener for the Next button
    private class NextButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if (i < steps.size()) {
                    System.out.println("Step " + (i + 1) + ":");
                    Map<String, String> step = steps.get(i);
                    for (Map.Entry<String, String> entry : step.entrySet()) {
                        System.out.println(entry.getKey() + " -> " + entry.getValue());
                    }
                    updateProcessTable(steps.subList(0, i + 1)); // Update table with the current steps

                    // Update current node and neighbor nodes for drawing
                    String currentKey = keys.get(i);
                    currentNode = graph.getNodeByName(currentKey);
                    neighborNodes = dijkstra.getNeighborsPerStep().get(currentNode);
                    System.out.println(currentNode);
                    System.out.println(neighborNodes);
                    if (neighborNodes == null) {
                        neighborNodes = new HashSet<>();
                    }
                    drawPanel.repaint(); // Request the panel to redraw with updated nodes

                    i++;
                } else {
                    ArrayList path = dijkstra.getPath();
                    String pathString = path.toString(); // Chuyển đổi ArrayList thành chuỗi
                    JOptionPane.showMessageDialog(GraphUI.this, "Path: " + pathString);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(GraphUI.this, "Please enter valid numbers.");
            }
        }
    }


    public static void main(String[] args) {
        // Create and display the UI
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GraphUI().setVisible(true);
            }
        });
    }
}
