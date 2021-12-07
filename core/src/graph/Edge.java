package graph;

public class Edge {
    public int weight;
    public Node node;

    public Edge(Node node, int weight) {
        this.node = node;
        this.weight = weight;
    }
}
