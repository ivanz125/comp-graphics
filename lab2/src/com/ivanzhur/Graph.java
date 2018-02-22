package com.ivanzhur;

import java.util.*;

public class Graph {

    public int size;
    public List<Point> points;
    public List<TreeSet<Edge>> lines;

    public Graph(int n) {
        this.size = n;
        points = new ArrayList<>(n);
        lines = new ArrayList<>(n);
    }

    public void prepare() {
        for (int i = 0; i < size; i++) {
            double lineY = points.get(i).y;
            Comparator<Edge> comparator = (e1, e2) -> {
                if (e1 == e2) return 0;
                if (e1.getCrossingX(lineY) < e2.getCrossingX(lineY)) return -1;
                if (e1.getCrossingX(lineY) > e2.getCrossingX(lineY)) return 1;
                if (e1.upAngle > e2.upAngle) return -1;
                if (e1.upAngle < e2.upAngle) return 1;
                return 1;
            };
            // Create list (tree) of edges with proper comparator for ordering
            TreeSet<Edge> set = new TreeSet<>(comparator);
            // Copy previous list
            if (i > 0) set.addAll(lines.get(i - 1));
            // Delete edges that are below the line
            for (Edge e : points.get(i).downstreamEdges) set.remove(e);
            // Add edges that are above line (started in current point)
            for (Edge e : points.get(i).upstreamEdges) set.add(e);
            // Add set to set list
            lines.add(set);
        }
    }

    public void findPointPosition(double x, double y) {
        System.out.println(String.format("Point (%f; %f) is:", x, y));

        // Find line
        int position = size / 2;
        while (true) {
            // Lower than minimum
            if (position == 0 && y < points.get(0).y) {
                position = -1;
                break;
            }
            // Higher than maximum
            if (position >= size - 1 && y >= points.get(size - 1).y) break;
            if (points.get(position).y <= y && y <= points.get(position + 1).y) break; // Found
            if (y < points.get(position ).y) position /= 2; // Search down
            else position = (size + position) / 2; // Search up
        }

        if (position == -1) System.out.println("Below entire graph");
        else if (position == size - 1) System.out.println("Above entire graph");
        else System.out.println(String.format("In line %d between vertices %s and %s", position + 1, points.get(position), points.get(position + 1)));

        // Find edges
        if (position == -1 || position >= size - 1) return;
        Iterator<Edge> iterator = lines.get(position).iterator();
        boolean edgesFound = false;
        Edge previousEdge = null;
        while (iterator.hasNext()) {
            Edge edge = iterator.next();
            // Found edge to the right
            if (x < edge.getCrossingX(y)) {
                if (previousEdge == null) System.out.println("To the left of edge " + edge);
                else System.out.println(String.format("Between edges: %s and %s", previousEdge, edge));
                edgesFound = true;
                break;
            }
            previousEdge = edge;
        }
        if (!edgesFound) System.out.println("To the right of edge " + previousEdge);

        System.out.println();
    }
}
