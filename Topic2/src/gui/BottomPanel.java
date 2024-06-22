package gui;

import javax.swing.*;
import java.awt.*;

public class BottomPanel extends JPanel {
    private GraphPanel graphPanel;

    public BottomPanel(GraphPanel graphPanel) {
        this.graphPanel = graphPanel;
        setLayout(new FlowLayout());

        JComboBox<String> startNodeBox = new JComboBox<>();
        JComboBox<String> endNodeBox = new JComboBox<>();
        JComboBox<String> searchMethodBox = new JComboBox<>(new String[]{"BFS", "DFS", "IDA*"});
        JButton nextButton = new JButton("Next Step");
        JButton autoButton = new JButton("Auto");

        add(new JLabel("Start Node:"));
        add(startNodeBox);
        add(new JLabel("End Node:"));
        add(endNodeBox);
        add(new JLabel("Search Method:"));
        add(searchMethodBox);
        add(nextButton);
        add(autoButton);
    }
}
