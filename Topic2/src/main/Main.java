package main;

import model.Edge;
import model.Graph;
import gui.MainFrame;
import model.Node;

public class Main {
    public static void main(String[] args) {
        Node node1 = new Node('A');
        Node node2 = new Node('B');
        Edge edge1 = new Edge(3,node1,node2);
        Edge edge2 = new Edge(3,node1,node2);
        System.out.println(edge1.equals(edge2));
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