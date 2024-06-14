package us13;

// Class representing an edge in the graph
public class Edge {

    // Vertex1 and vertex2 represent the vertices connected by the edge, and cost represents the weight of the edge
    public String vertex1;
    public String vertex2;
    public double cost;

    // Constructor to initialize an edge with vertex1, vertex2, and cost
    public Edge(String vertex1, String vertex2, double cost) {
        this.vertex1 = vertex1;
        this.vertex2 = vertex2;
        this.cost = cost;
    }

    public String getVertex1() {
        return vertex1;
    }

    public String getTarget() {
        return vertex2;
    }

    public double getWeight() {
        return cost;
    }
}
