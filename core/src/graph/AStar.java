package graph;

import helper.Vars;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class AStar {

    public int[][] grid;
    public Node[][] nodes;
    int width;
    int height;

    public AStar(int width, int height, int[][] gridMap) {
        this.grid = gridMap;
        this.width = width;
        this.height = height;

        /*
        for (int i = height - 1; i >= 0; --i) {
            for (int j = 0; j < width; ++j) {
                System.out.print(grid[j][i]);
            }
            System.out.println();
        }
         */

        nodes = new Node[width][height];
        for (int i = 0; i < width; ++i) {
            for (int j = 0; j < height; ++j) {
                nodes[i][j] = new Node(i, j);
            }
        }

        for (int i = 0; i < width; ++i) {
            for (int j = 0; j < height; ++j) {
                if (isMoveale(i, j)) {
                    for (int k = 0; k < 4; ++k) {
                        int x = i + Vars.dx[k];
                        int y = j + Vars.dy[k];

                        if (!isMoveale(x, y)) {
                            continue;
                        }
                        nodes[i][j].addEdge(nodes[x][y], 1);
                        // System.out.println(i + " " + j + " " + x + " " + y);
                    }
                }
            }
        }
        // System.out.println("Init graph");
    }

    public static List<Node> tracePath(Node dst) {
        List<Node> path = new ArrayList<Node>();

        for (Node node = dst; node != null; node = node.parent) {
            path.add(node);
        }

        Collections.reverse(path);

        return path;
    }

    public boolean isValid(int x, int y) {
        return 0 <= x && x < width && 0 <= y && y < height;
    }

    public boolean isMoveale(int x, int y) {
        if (isValid(x, y)) {
            if (grid[x][y] == 3) {
                return true;
            }
        }
        return false;
    }

    public int calcHeuristics(Node src, Node dst) {
        return 0;
    }

    public List<Node> search(Node src, Node dst) {

        PriorityQueue<Node> set = new PriorityQueue<>();
        PriorityQueue<Node> queue = new PriorityQueue<>();

        src.g = 0;
        src.f = src.g + calcHeuristics(src, dst);

        queue.add(src);

        boolean foundDst = false;

        while (!queue.isEmpty() && !foundDst) {
            Node u = queue.poll();

            if (u.equals(dst)) {
                foundDst = true;
            }

            for (Edge edge : u.adj) {
                Node v = edge.node;
                int temp_g = u.g + edge.weight;

                if (!queue.contains(v) && !set.contains(v)) {
                    v.parent = u;
                    v.g = temp_g;
                    v.f = v.g + calcHeuristics(v, dst);
                    queue.add(v);
                } else {
                    if (temp_g < v.g) {
                        v.parent = u;
                        v.g = temp_g;
                        v.f = v.g + calcHeuristics(v, dst);
                        if (set.contains(v)) {
                            set.remove(v);
                            queue.add(v);
                        }
                    }
                }
            }
            set.add(u);
        }

        List<Node> e = tracePath(dst);

        System.out.println("path");
        for (Node u : e) {
            System.out.println(u.x + " " + u.y);
        }
        return e;
    }
}
