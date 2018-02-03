package com.ivanzhur;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Data {

    public static List<Polygon> polygons;
    public static List<Double> pointsX;
    public static List<Double> pointsY;

    /**
     * Fill polygons, pointsX and pointsY.
     */
    public static void generateTestCases() {
        polygons = new ArrayList<>(7);
        pointsX = new ArrayList<>(7);
        pointsY = new ArrayList<>(7);

        Polygon polygon = new Polygon(3);
        polygon.addVertex(0, 0);
        polygon.addVertex(3, 0);
        polygon.addVertex(1, 5);
        polygons.add(polygon);
        polygons.add(polygon);
        pointsX.add(1.0);
        pointsY.add(2.0);
        pointsX.add(2.0);
        pointsY.add(4.0);

        polygon = new Polygon(7);
        polygon.addVertex(0, 0);
        polygon.addVertex(2, 0);
        polygon.addVertex(4, 2);
        polygon.addVertex(3, 4);
        polygon.addVertex(1, 4);
        polygon.addVertex(-3, 2);
        polygon.addVertex(-2, 0);
        polygons.add(polygon);
        polygons.add(polygon);
        polygons.add(polygon);
        pointsX.add(5.0);
        pointsY.add(5.0);
        pointsX.add(0.0);
        pointsY.add(3.0);
        pointsX.add(-3.0);
        pointsY.add(0.0);

        polygon = new Polygon(4);
        polygon.addVertex(0, 0);
        polygon.addVertex(1, -1);
        polygon.addVertex(2, -1);
        polygon.addVertex(4, 1);
        polygons.add(polygon);
        polygons.add(polygon);
        pointsX.add(1.0);
        pointsY.add(0.0);
        pointsX.add(1.0);
        pointsY.add(-2.0);
    }

    public static Polygon getPolygonFromConsoleInput() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter number of vertices in polygon:");
        int nVertices = scanner.nextInt();
        Polygon polygon = new Polygon(nVertices);
        System.out.println("Now enter coordinates (x, y) for each vertex consequently in counter-clockwise order:");

        // Vertices coordinates
        int currentVertex = 1;
        while (currentVertex <= nVertices) {
            System.out.println("Vertex " + currentVertex + ":");
            double x = scanner.nextDouble();
            double y = scanner.nextDouble();
            polygon.addVertex(x, y);
            currentVertex++;
        }

        return polygon;
    }

    public static Pair<Double, Double> getPointFromConsoleInput() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter point to check (x, y):");
        double x = scanner.nextDouble();
        double y = scanner.nextDouble();

        return new Pair<>(x, y);
    }

    public static Polygon getPolygon(int testCase) {
        return polygons.get(testCase);
    }

    public static Pair<Double, Double> getPoint(int testCase) {
        return new Pair<>(pointsX.get(testCase), pointsY.get(testCase));
    }
}
