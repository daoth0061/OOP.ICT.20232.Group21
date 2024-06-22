package main;

import model.Edge;
import model.Graph;
import gui.MainFrame;
import model.Node;
import search.Dijkstra;

import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Graph graph = new Graph(5,5);
        System.out.println(graph.getAdjMatrix());
        System.out.println(graph.getEdges());

        Dijkstra dijkstra = new Dijkstra(graph, graph.getNodes().get(0), graph.getNodes().get(4));
        dijkstra.search();

        // Lấy các bước đã ghi lại
        List<Map<String, String>> steps = dijkstra.getSteps();

        // In các bước
        for (int i = 0; i < steps.size(); i++) {
            System.out.println("Step " + (i + 1) + ":");
            Map<String, String> step = steps.get(i);
            for (Map.Entry<String, String> entry : step.entrySet()) {
                System.out.println(entry.getKey() + " -> " + entry.getValue());
            }
            System.out.println();
        }
        System.out.println(dijkstra.getNeighborsPerStep());
    }
}
//adjmatrix: dict
//"A" = [F, T, F]
//"B" = [T, F, T]
//"C" = [T, T, F]
//
//nodes = [A, B, C]
//
//edges = [(A, B), (B,C)]