package us13;

import java.util.*;

public class Kruskal {

    /*
       This method implements the "find" operation of the disjoint-set data structure, used in Kruskal's algorithm for finding the parent/root of a vertex with path compression.
       It takes a Map called parent (representing the parent relationship of vertices) and a vertex as input.
       It finds the root parent of the vertex by traversing the parent relationship until it reaches the root.
       It performs path compression by updating the parent of each vertex in the path to the root to directly point to the root.
       Finally, it returns the root parent of the vertex.
    */
    // Find the root parent of a vertex with path compression
    private static String find(Map<String, String> parent, String vertex) {
        String root = vertex;
        // Find the root parent of the vertex
        while (!parent.get(root).equals(root)) {
            root = parent.get(root);
        }
        // Path compression: update parent of each vertex in the path to root
        while (!vertex.equals(root)) {
            String next = parent.get(vertex);
            parent.put(vertex, root);
            vertex = next;
        }
        return root;
    }

    /*
       This method implements the sort algorithm to sort a list of edges based on their weights in non-decreasing order.
       It takes a List of edges as input.
       It iterates over the list and compares adjacent edges based on their weights, swapping them if they are out of order.
       This process is repeated until the list is sorted in non-decreasing order of weights.
    */
    // Method for sorting edges based on cost
    private static void sortEdges(List<Edge> edges) {
        int n = edges.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (edges.get(j).cost > edges.get(j + 1).cost) {
                    Edge temp = edges.get(j);
                    edges.set(j, edges.get(j + 1));
                    edges.set(j + 1, temp);
                }
            }
        }
    }

    /*
       This method implements Kruskal's algorithm to find the minimum spanning tree of a graph.
       It takes a Map called graph representing the input graph.
       It initializes a Map called minimumGraph to store the minimum spanning tree, and a Map called parent to track the parent/root of each vertex in disjoint sets.
       It initializes the parent map with each vertex as its own parent and creates an empty list of edges for each vertex in the minimumGraph map.
       It constructs a list of all edges in the graph.
       It sorts the list of edges by weight using the sortEdges method.
       It iterates over the sorted edges and adds them to the minimum spanning tree if they don't create a cycle (i.e., if the source and destination vertices have different parents).
       It updates the parent map to merge disjoint sets when adding edges to the minimum spanning tree.
       Finally, it returns the minimum spanning tree.
    */
    // Kruskal's algorithm to find the graph with minimum cost
    public static Map<String, List<Edge>> kruskalAlgorithm(Map<String, List<Edge>> graph) {

        // start counting time
        long start = System.nanoTime();

        Map<String, List<Edge>> minimumGraph = new HashMap<>();
        Map<String, String> parent = new HashMap<>();

        // Initialize parent map and minimumGraph map
        for (String vertex : graph.keySet()) {
            parent.put(vertex, vertex); // Each vertex is initially its own parent
            minimumGraph.put(vertex, new ArrayList<>()); // Each vertex has an empty list of edges
        }

        // Create a list of all edges in the graph
        List<Edge> edges = new ArrayList<>();
        for (List<Edge> edgeList : graph.values()) {
            edges.addAll(edgeList);
        }

        // Sort edges by cost
        sortEdges(edges);

        // Iterate over sorted edges and add to minimum graph if they don't create a cycle
        for (Edge edge : edges) {
            String sourceParent = find(parent, edge.vertex1);
            String destParent = find(parent, edge.vertex2);

            if (!sourceParent.equals(destParent)) {
                // Add edge to the minimum graph
                minimumGraph.get(edge.vertex1).add(edge);
                // Update parent map to merge disjoint sets
                parent.put(sourceParent, destParent);
            }
        }

        // stop counting time
        long end = System.nanoTime();
        // calculate time elapsed from start to finish
        long elapsedTime = end - start;

        return minimumGraph;
    }
}


