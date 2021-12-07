package graph;

import java.util.ArrayList;
import java.util.List;

public class Node implements Comparable<Node> {
    public int x, y;

    public int f;
    public int g;
    public int h;

    public Node parent = null;
    public List<Edge> adj;

    public Node(int x, int y) {
        this.x = x;
        this.y = y;

        this.f = Integer.MAX_VALUE;
        this.g = Integer.MAX_VALUE;
        this.h = Integer.MAX_VALUE;

        this.adj = new ArrayList<>();
    }

    public static int distance(Node u, Node v) {
        return Math.abs(u.x - v.x) + Math.abs(u.y - v.y);
    }

    public void addEdge(Node u, int weight) {
        Edge v = new Edge(u, weight);
        this.adj.add(v);
    }

    @Override
    public int compareTo(Node o) {
        return Integer.compare(this.f, o.f);
    }
}
