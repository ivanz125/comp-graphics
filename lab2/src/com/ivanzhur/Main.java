package com.ivanzhur;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Graph graph = readGraph("input1.txt");
        if (graph != null) {
            graph.prepare();

            // Test few points
            graph.findPointPosition(0, 2);
            graph.findPointPosition(0, 3);
            graph.findPointPosition(2, 2);
            graph.findPointPosition(5, 2);
            graph.findPointPosition(2, 6);
        }
    }

    private static Graph readGraph(String filename) {
        try {
            Scanner input = new Scanner(new FileReader(filename));

            int n = input.nextInt(); // Number of vertices
            // Next read coordinates of all vertices
            Graph graph = new Graph(n);
            for (int i = 0; i < n; i++) {
                double x = input.nextDouble();
                double y = input.nextDouble();
                Point point = new Point(x, y);
                graph.points.add(point);
            }

            // Read adjacency list
            int m = input.nextInt(); // Number of edges
            for (int i = 0; i < m; i++) {
                // Vertex indices
                int v1 = input.nextInt();
                int v2 = input.nextInt();
                // Vertices themselves
                Point p1 = graph.points.get(v1);
                Point p2 = graph.points.get(v2);
                // Create edge
                Edge edge = new Edge(p1, p2);
                // Link edge to it's vertices
                p1.insertEdge(edge);
                p2.insertEdge(edge);
            }

            // Sort points by y-coordinate, ascending
            graph.points.sort((o1, o2) -> {
                if (o1.y < o2.y) return -1;
                if (o1.y > o2.y) return 1;
                return 0;
            });

            return graph;
        }
        catch (FileNotFoundException ex) {
            return null;
        }
    }
}
