package com.ivanzhur;

import java.util.ArrayList;
import java.util.List;

public class Point {

    public double x;
    public double y;

    public List<Edge> upstreamEdges;
    public List<Edge> downstreamEdges;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
        upstreamEdges = new ArrayList<>();
        downstreamEdges = new ArrayList<>();
    }

    public void insertEdge(Edge edge) {
        // Keep array in CW order (angle descending)
        if (edge.isUpstream(this)) {
            int i;
            for (i = 0; i < upstreamEdges.size(); i++) {
                if (edge.upAngle < upstreamEdges.get(i).upAngle) continue;
                break;
            }
            upstreamEdges.add(i, edge);
        }
        // Keep array in CCW order (angle ascending)
        else {
            int i;
            for (i = 0; i < downstreamEdges.size(); i++) {
                if (edge.downAngle > downstreamEdges.get(i).downAngle) continue;
                break;
            }
            downstreamEdges.add(i, edge);
        }
    }

    @Override
    public String toString() {
        return String.format("(%.2f; %.2f)", x, y);
    }
}
