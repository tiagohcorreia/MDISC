package us15;

import us13.Edge;
import us13.ExportGraph;
import us13.ShowGraph;

import java.util.*;

public class Dijkstra {

    public static Map<String, List<Edge>> dijkstra(Map<String, List<Edge>> graph, String start) {

        Map<String, List<Edge>> shortestPath = new HashMap<>();

        Map<String, Double> distances = new HashMap<>();

        Map<String, String> previousNodes = new HashMap<>();

        Set<String> unvisited = new HashSet<>(graph.keySet());

        // Initialize distances and previous nodes
        for (String node : graph.keySet()) {

            distances.put(node, Double.POSITIVE_INFINITY);
            previousNodes.put(node, null);
        }
        distances.put(start, 0.0);

        while (!unvisited.isEmpty()) {

            // Find the node with the smallest distance
            String currentNode = null;
            double smallestDistance = Double.POSITIVE_INFINITY;

            for (String node : unvisited) {
                double currentDistance = distances.get(node);

                if (currentDistance < smallestDistance) {
                    smallestDistance = currentDistance;
                    currentNode = node;
                }
            }

            if (currentNode == null) {
                break;
            }

            unvisited.remove(currentNode);

            // Update distances
            for (Edge edge : graph.get(currentNode)) {
                String neighbor = edge.getTarget();

                if (!unvisited.contains(neighbor)) {
                    continue;
                }

                double newDistance = distances.get(currentNode) + edge.getWeight();

                if (newDistance < distances.get(neighbor)) {
                    distances.put(neighbor, newDistance);
                    previousNodes.put(neighbor, currentNode);
                }
            }
        }

        // Construct shortest path graph
        for (String node : graph.keySet()) {
            shortestPath.put(node, new ArrayList<>());
        }

        for (String node : previousNodes.keySet()) {
            String prevNode = previousNodes.get(node);

            if (prevNode != null) {
                double weight = distances.get(node) - distances.get(prevNode);
                shortestPath.get(prevNode).add(new Edge(prevNode, node, weight));
            }
        }
        return shortestPath;
    }

    // Method to find the shortest path to one of the assembly points with minimal cost
    public static Map<String, List<Edge>> findShortestPathToAssemblyPoints(Map<String, List<Edge>> graph, String[] assemblyPoints) {
        double minCost = Double.MAX_VALUE;
        String minCostAssemblyPoint = null;
        Map<String, List<Edge>> minCostPath = null;

        // Iterate over each assembly point to find the one with the minimal cost
        Map<String, List<Edge>> path = null;
        for (String assemblyPoint : assemblyPoints) {
            path = dijkstra(graph, assemblyPoint);
/*            double cost = path.get(assemblyPoint).get(0).getWeight();

            if (cost < minCost) {
                minCost = cost;
                minCostAssemblyPoint = assemblyPoint;
                minCostPath = path;
            }*/

            double cost = calculateTotalCost(path);

            ExportGraph.exportToCSV(path, cost, "results/graph"+ assemblyPoint + ".csv");
            String dotOutput4 = ShowGraph.generateDotRepresentation(path);
            ShowGraph.writeDotToFile(dotOutput4, "results/outputSubgraph" + assemblyPoint + ".dot");
        }
        return path;
    }

    public static double calculateTotalCost(Map<String, List<Edge>> graph) {

        double totalCost = 0;

        for (String node : graph.keySet()) {

            for (Edge edge : graph.get(node)) {
                totalCost += edge.getWeight();
            }
        }
        return totalCost;
    }
}
