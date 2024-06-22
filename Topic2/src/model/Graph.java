package model;

import java.util.*;

public class Graph {
    private ArrayList<Node> nodes;
    private Set<Edge> edges;
    private int numsNode;

    private int maxWeight = 10;

    private Map<Node, List<Boolean>> adjMatrix;

    public Graph(int numsNode) {
        this.numsNode = numsNode;
        this.nodes = new ArrayList<>();
        this.edges = new HashSet<>();
        this.adjMatrix = new HashMap<>();
        generateNodes();
        initAdjMatrix();
        generateEdges();
    }

    public Graph(int numsNode, int maxWeight) {
        this.numsNode = numsNode;
        this.maxWeight = maxWeight;
        this.nodes = new ArrayList<>();
        this.edges = new HashSet<>();
        this.adjMatrix = new HashMap<>();
        generateNodes();
        initAdjMatrix();
        generateEdges();
    }

    private void initAdjMatrix() {
        for (Node node : nodes) {
            List<Boolean> adjList = new ArrayList<>(Collections.nCopies(this.numsNode, false));
            adjMatrix.put(node, adjList);
        }
    }
    public Node getNodeByName(String name) {
        for (Node node : nodes) {
            if (String.valueOf(node.getValue()).equals(name)) {
                return node;
            }
        }
        return null;
    }

    private void generateNodes() {
        for (int i = 0; i < this.numsNode; i++) {
            this.nodes.add(new Node((char) ('a' + i)));
        }
    }

    private void generateEdges() {
        Random random = new Random();
        // Generate a Hamiltonian path
        for (int i = 0; i < this.numsNode - 1; i++) {
            updateNewEdge(random.nextInt(this.maxWeight) + 1, this.nodes.get(i), this.nodes.get(i + 1));
        }
        // Add random edges
        int bound = ((this.numsNode*this.numsNode)-3*this.numsNode+2)/2;
        int additionalEdges = random.nextInt(bound);
        for (int i = 0; i < additionalEdges; i++) {
            updateNewEdge(random.nextInt(this.maxWeight), this.nodes.get(random.nextInt(this.numsNode)), this.nodes.get(random.nextInt(this.numsNode)));
        }
    }

    private void updateNewEdge(int weight, Node n1, Node n2) {
        Edge newEdge = new Edge(weight, n1, n2);
        if (n1.equals(n2)) {
            return;
        }
        for (Edge edge : edges) {
            if (edge.equals(newEdge)) {
                return;
            }
        }
        this.edges.add(new Edge(weight, n1, n2));
        this.adjMatrix.get(n1).set(this.nodes.indexOf(n2), true);
        this.adjMatrix.get(n2).set(this.nodes.indexOf(n1), true);
    }

    public HashSet<Edge> getAdjEdges(Node node) {
        if (!this.adjMatrix.containsKey(node)) {
            return null;
        }
        HashSet<Edge> adjEdges = new HashSet<>();
        for (Edge edge : this.edges) {
            if (edge.contains(node)) {
                adjEdges.add(edge);
            }
        }
        return adjEdges;
    }

    public Edge findEdge(Node n1, Node n2) {
        Edge target = new Edge(0, n1, n2);
        for (Edge edge :
                edges) {
            if (edge.equals(target)) {
                return edge;
            }
        }
        return null;
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }

    public Set<Edge> getEdges() {
        return edges;
    }

    public int getNumsNode() {
        return numsNode;
    }

    public int getMaxWeight() {
        return maxWeight;
    }

    public Map<Node, List<Boolean>> getAdjMatrix() {
        return adjMatrix;
    }
}
