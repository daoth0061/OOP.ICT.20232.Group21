package search;

import model.Edge;
import model.Graph;
import model.Node;

import java.util.*;

public class BellmanFord extends Search {
    private Map<Node, Integer> distances;
    private Map<Node, Node> predecessors;

    public BellmanFord(Graph graph, Node start, Node end) {
        super(graph, start, end);
        this.distances = new HashMap<>();
        this.predecessors = new HashMap<>();
    }

    @Override
    public void search() {
        // Initialize distances and predecessors
        for (Node node : graph.getNodes()) {
            distances.put(node, Integer.MAX_VALUE);
            predecessors.put(node, null);
        }
        distances.put(start, 0);

        // Relax edges |V| - 1 times
        for (int i = 1; i < graph.getNodes().size(); i++) {
            for (Edge edge : graph.getEdges()) {
                Node u = edge.getLinkedNode(edge.getNodes().iterator().next());
                Node v = edge.getLinkedNode(u);
                int weight = edge.getWeight();
                if (distances.get(u) != Integer.MAX_VALUE && distances.get(u) + weight < distances.get(v)) {
                    distances.put(v, distances.get(u) + weight);
                    predecessors.put(v, u);
                }
            }
        }

        // Check for negative-weight cycles
        for (Edge edge : graph.getEdges()) {
            Node u = edge.getLinkedNode(edge.getNodes().iterator().next());
            Node v = edge.getLinkedNode(u);
            int weight = edge.getWeight();
            if (distances.get(u) != Integer.MAX_VALUE && distances.get(u) + weight < distances.get(v)) {
                System.out.println("Graph contains a negative-weight cycle");
                return;
            }
        }

        updatePath();
    }

    @Override
    protected void updatePath() {
        Node step = end;

        if (predecessors.get(step) == null) {
            System.out.println("No path found");
            return;
        }

        path.add(step);
        while (predecessors.get(step) != null) {
            step = predecessors.get(step);
            path.add(step);
        }

        Collections.reverse(path);
        totalCost = distances.get(end);
    }
}
