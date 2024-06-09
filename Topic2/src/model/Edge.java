package model;

import java.util.HashSet;
import java.util.Set;

public class Edge {
    private int weight;
    private Set<Node> nodes;

    private Node n1;

    private Node n2;

    public Edge(int weight, Node n1, Node n2) {
        this.weight = weight;
        this.nodes = new HashSet<>();
        this.n1 = n1;
        this.n2 = n2;
        this.nodes.add(n1);
        this.nodes.add(n2);
    }

    public int getWeight() {
        return weight;
    }

    public Set<Node> getNodes() {
        return nodes;
    }
    public Node getLinkedNode(Node node) {
        // Get the other node that linked to given node
        if (node.equals(n1)) {
            return n2;
        } else if (node.equals(n2)) {
            return n1;
        }
        return null;
    }

    public boolean contains(Node node) {
        return nodes.contains(node);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge edge = (Edge) o;
        return nodes.equals(edge.nodes);
    }

    @Override
    public String toString() {
        return this.nodes + " - " + this.weight;
    }
}
