package gui;

import javax.swing.*;
import java.awt.*;
import model.Graph;

public class ControlPanel extends JPanel {
    public ControlPanel() {
        setLayout(new FlowLayout());

        JComboBox<String> graphTypeBox = new JComboBox<>(new String[]{"Directed", "Undirected"});
        JTextField nodeCountField = new JTextField(5);
        JButton generateButton = new JButton("Generate Graph");
        JButton resetButton = new JButton("Reset");

        generateButton.addActionListener(e -> {
            int numsNode = Integer.parseInt(nodeCountField.getText());
            Graph graph = new Graph(numsNode);
            // Now you can use this graph object in your application
        });

        add(new JLabel("Graph Type:"));
        add(graphTypeBox);
        add(new JLabel("Nodes:"));
        add(nodeCountField);
        add(generateButton);
        add(resetButton);
    }
}
