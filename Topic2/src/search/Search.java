package search;

import model.Graph;
import model.Node;
import java.util.ArrayList;

public abstract class Search {
    protected Graph graph;
    protected Node start;
    protected Node end;
    protected ArrayList<Node> path;

    protected int totalCost;

    public Search(Graph graph, Node start, Node end) {
        this.graph = graph;
        this.start = start;
        this.end = end;
        this.path = new ArrayList<>();
        this.totalCost = 0;
    }

    protected abstract void search();
    protected abstract void updatePath();

    public ArrayList<Node> getPath() {
        return path;
    }

    public int getTotalCost() {
        return totalCost;
    }
}
