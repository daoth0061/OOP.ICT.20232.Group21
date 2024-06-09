package search;

import model.Edge;
import model.Graph;
import model.Node;
import screen.TraversalController;

import java.util.*;

public class Dijkstra extends Search{
    private Set<Node> visitedNodes;
    private PriorityQueue<Node> traversalNodes;
    private Map<Node, Node> predecessors;
    private Map<Node, Integer> distances;

    public Dijkstra(Graph graph, Node start, Node end) {
        super(graph, start, end);
        this.visitedNodes = new HashSet<>();
        this.traversalNodes = new PriorityQueue<>(Comparator.comparingInt(distances::get));
        this.predecessors = new HashMap<>();
        this.distances = new HashMap<>();
    }

    @Override
    public void search() {
        // Initialize distances
        for (Node node : graph.getNodes()) {
            distances.put(node, Integer.MAX_VALUE);
        }
        distances.put(start, 0);

        traversalNodes.add(start);
        TraversalController reference = new TraversalController();
        while (!traversalNodes.isEmpty()) {
            Node current = traversalNodes.poll();
            reference.setCurnode(current);

            // Stop if we reach the end node
            if (current.equals(end)) {
                updatePath();
                return;
            }

            if (!visitedNodes.add(current)) {
                continue;
            }

            // Traverse all adjacent nodes
            HashSet<Edge> adjEdges = graph.getAdjEdges(current);
            for (Edge edge : adjEdges) {
                Node neighbor = edge.getLinkedNode(current);
                int newDist = distances.get(current) + edge.getWeight();

                if (newDist < distances.get(neighbor)) {
                    distances.put(neighbor, newDist);
                    predecessors.put(neighbor, current);
                    traversalNodes.add(neighbor);
                }
            }
        }

        System.out.println("End node not reachable from start node");
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
