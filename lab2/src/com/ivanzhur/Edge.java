package com.ivanzhur;

public class Edge {

    public Point pointA;
    public Point pointB;

    public double upAngle;
    public double downAngle;

    public Edge(Point a, Point b) {
        this.pointA = a;
        this.pointB = b;
        upAngle = getUpAngle();
        downAngle = getDownAngle();
    }

    public boolean isUpstream(Point start) {
        if (start == pointA && pointB.y > start.y) return true;
        if (start == pointB && pointA.y > start.y) return true;
        return false;
    }

    private double getUpAngle() {
        double dX;
        double dY;
        if (pointA.y > pointB.y) {
            dY = pointA.y - pointB.y;
            dX = pointA.x - pointB.x;
        }
        else {
            dY = pointB.y - pointA.y;
            dX = pointB.y - pointA.y;
        }
        return Math.atan2(dY, dX);
    }

    private double getDownAngle() {
        double dX;
        double dY;
        if (pointA.y < pointB.y) {
            dY = pointA.y - pointB.y;
            dX = pointA.x - pointB.x;
        }
        else {
            dY = pointB.y - pointA.y;
            dX = pointB.y - pointA.y;
        }
        return Math.atan2(dY, dX);
    }

    /**
     * Get x coordinate of crossing point with given line
     * @param y y coordinate of given line
     * @return x coordinate of line crossing point
     */
    public double getCrossingX(double y) {
        return pointA.x + (pointB.x - pointA.x) * (y - pointA.y) / (pointB.y - pointA.y);
    }

    @Override
    public String toString() {
        return String.format("[%s -- %s]", pointA, pointB);
    }
}
