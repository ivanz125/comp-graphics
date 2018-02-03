package com.ivanzhur;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Data.generateTestCases();
        for (int i = 0; i < 7; i++) {
            Polygon polygon = Data.getPolygon(i);
            Pair<Double, Double> point = Data.getPoint(i);
            boolean isInside = checkPointInPolygon(point.getKey(), point.getValue(), polygon);
            System.out.println("TEST " + i);
            System.out.println(polygon);
            System.out.println("Point: (" + point.getKey() + "; " + point.getValue() + ")");
            System.out.println(isInside ? "Point is INSIDE polygon" : "Point is OUTSIDE of polygon");
            System.out.println();
        }
    }

    private static boolean checkPointInPolygon(double pX, double pY, Polygon polygon) {
        // Number of vertices in polygon
        int n = polygon.getSize();

        // Point inside polygon, center of one of it's triangles
        Pair<Double, Double> center = polygon.getCenter();
        double cX = center.getKey();
        double cY = center.getValue();

        // Angle of vector center -> given point in polar coordinates
        double pAngle = Math.atan2(pY - cY, pX - cX);
        if (pAngle < 0) pAngle += 2 * Math.PI;

        // Angles of all vertices in polar coordinates. (0, 0) is center from few rows above.
        // Array is sorted counter-clockwise (user should input vertices in same order)
        // and the first element has lowest angle, all angles are positive
        List<Double> angles = getVertexAngles(polygon);

        // Find in which sector is given point lying
        // Do the binary search in angles array and find place for vector center -> given point
        int iLeft = n/2;
        while (true) {
            if (angles.get(iLeft) <= pAngle && (iLeft >= n || pAngle <= angles.get(iLeft + 1))) break;
            // Lower then first element
            if (iLeft == 0) {
                iLeft = n - 1;
                break;
            }
            if (pAngle < angles.get(iLeft % n)) iLeft /= 2;
            else iLeft *= 2;
        }

        // Shift index to match original polygon vertex order
        iLeft += polygon.getFirstVertexIndexInAngles();

        // Edges of sector where given point lying
        double p1X = polygon.getX(iLeft % n);
        double p1Y = polygon.getY(iLeft % n);
        double p2X = polygon.getX((iLeft + 1) % n);
        double p2Y = polygon.getY((iLeft + 1) % n);

        // Basically sign is 1 if p1->p2->p traversed CCW and it means point is inside polygon
        int sign = getAngleSign(p1X, p1Y, p2X, p2Y, pX, pY);

        return sign == 1;
    }

    /**
     * Returns list of angles of vectors (center->p1), (center->p2),... in polar coordinates,
     * where center is the center of triangle p1-p2-p3 (just a point inside polygon),
     * p1, p2,... - vertices of polygon.<br>
     * List is ordered counter-clockwise (user input should be appropriate) and in ascending order.
     * Range: 0 - 2 * PI
     * @param polygon Given polygon
     * @return List of angles
     */
    private static List<Double> getVertexAngles(Polygon polygon) {
        Pair<Double, Double> center = polygon.getCenter();
        double cX = center.getKey();
        double cY = center.getValue();

        int n = polygon.getSize();
        List<Double> angles = new ArrayList<>(n);
        int minAngleIndex = 0;
        for (int i = 0; i < n; i++) {
            double pX = polygon.getX(i);
            double pY = polygon.getY(i);
            // Transform to polar coordinates
            double angle = Math.atan2(pY - cY, pX - cX);
            // Make value non-negative
            if (angle < 0) angle += 2 * Math.PI;
            angles.add(angle);
            // Find smallest angle index to shift array start
            if (angles.get(i) < angles.get(minAngleIndex)) minAngleIndex = i;
        }

        // List should be from smallest (-2*PI) to biggest (2*PI)
        polygon.setFirstVertexIndexInAngles(minAngleIndex);
        List<Double> anglesSorted = new ArrayList<>(n);
        for (int i = minAngleIndex; i < minAngleIndex + n; i++) {
            anglesSorted.add(angles.get(i % n));
        }

        return anglesSorted;
    }

    /**
     * Angle sign. Returns 1 if given vertices 1->2->3 traversed CCW and -1 otherwise.
     */
    private static int getAngleSign(double x1, double y1, double x2, double y2, double x3, double y3) {
        double s1 = x2 * y3 - x3 * y2;
        double s2 = x1 * y3 - x3 * y1;
        double s3 = x1 * y2 - x2 * y1;
        return s1 - s2 + s3 >= 0 ? 1 : -1;
    }
}
