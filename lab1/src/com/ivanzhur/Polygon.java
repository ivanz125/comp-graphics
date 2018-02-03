package com.ivanzhur;

import javafx.util.Pair;

import java.util.ArrayList;

public class Polygon {

    private ArrayList<Double> verticesX;
    private ArrayList<Double> verticesY;
    private int firstVertexIndexInAngles;

    public Polygon(int n) {
        if (n < 3) throw new IllegalArgumentException("Polygon should have at least 3 vertices");
        verticesX = new ArrayList<>(n);
        verticesY = new ArrayList<>(n);
    }

    public void addVertex(double x, double y) {
        verticesX.add(x);
        verticesY.add(y);
    }

    public Pair<Double, Double> getCenter() {
        double centerX = (verticesX.get(0) + verticesX.get(1) + verticesX.get(2)) / 3;
        double centerY = (verticesY.get(0) + verticesY.get(1) + verticesY.get(2)) / 3;
        return new Pair<>(centerX, centerY);
    }

    public int getSize() {
        return verticesX.size();
    }

    public double getX(int ordinalNumber) {
        return verticesX.get(ordinalNumber);
    }

    public double getY(int ordinalNumber) {
        return verticesY.get(ordinalNumber);
    }

    public void setFirstVertexIndexInAngles(int firstVertexIndexInAngles) {
        this.firstVertexIndexInAngles = firstVertexIndexInAngles;
    }

    public int getFirstVertexIndexInAngles() {
        return firstVertexIndexInAngles;
    }

    @Override
    public String toString() {
        if (verticesX == null) return "";
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("Polygon (%d vertices):\n", verticesX.size()));
        for (int i = 0; i < verticesX.size(); i++) {
            double x = verticesX.get(i);
            double y = verticesY.get(i);
            stringBuilder.append(String.format("(%.1f; %.1f) ", x, y));
        }
        return stringBuilder.toString();
    }
}
