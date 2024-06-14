package us17_18;

import us13.Edge;

import java.util.*;
import java.io.*;

import static us17_18.ReverseList.reverse;

public class Dijkstra_2 {
    // Method to run Dijkstra's algorithm and return the shortest distances from the start vertex
    public static Map<String, Double> dijkstraAlgorithm(Map<String, List<Edge>> graph, String startVertice){
        // Initialize the distances map with infinity for all vertices except the start vertex
        Map<String, Double> distances = new HashMap<>();
        for (String vertex : graph.keySet()) {
            distances.put(vertex, Double.MAX_VALUE);
        }
        distances.put(startVertice, 0.0);

        // Priority queue to efficiently get the vertex with the smallest distance
        CustomPriorityQueue pq = new CustomPriorityQueue();
        pq.add(new AbstractMap.SimpleEntry<>(startVertice, 0.0));

        // Main loop of the Dijkstra's algorithm
        while (!pq.isEmpty()) {
            // Get the vertex with the smallest known distance
            Map.Entry<String, Double> current = pq.poll();
            String currentVertex = current.getKey();
            double currentDistance = current.getValue();

            // Skip processing if we have already found a shorter path before
            if (currentDistance > distances.get(currentVertex)) {
                continue;
            }

            // Explore the neighbors of the current vertex
            for (Edge edge : graph.get(currentVertex)) {
                String neighbor = edge.vertex2;
                double newDist = currentDistance + edge.cost;

                // If a shorter path to the neighbor is found, update the distance and add to the queue
                if (newDist < distances.get(neighbor)) {
                    distances.put(neighbor, newDist);
                    pq.add(new AbstractMap.SimpleEntry<>(neighbor, newDist));
                }
            }
        }

        writeResultsToCSV_17(distances, startVertice);

        return distances;
    }

    // Method to get the shortest path from start to end using Dijkstra's algorithm
    public static List<String> getShortestPath(Map<String, List<Edge>> graph, String start, String end) {
        // Run Dijkstra's algorithm to get distances
        Map<String, Double> distances = dijkstraAlgorithm(graph, start);
        // Map to store the previous vertex on the shortest path
        Map<String, String> previous = new HashMap<>();
        // Set to keep track of visited vertices
        Set<String> visited = new HashSet<>();
        // Priority queue to process vertices based on shortest distance
        CustomPriorityQueue pq = new CustomPriorityQueue();
        pq.add(new AbstractMap.SimpleEntry<>(start, 0.0));


        // Main loop to process each vertex
        while (!pq.isEmpty()) {
            Map.Entry<String, Double> current = pq.poll();
            String currentVertex = current.getKey();
            if (visited.contains(currentVertex)) continue;
            visited.add(currentVertex);

            if (currentVertex.equals(end)) break;

            for (Edge edge : graph.get(currentVertex)) {
                String neighbor = edge.vertex2;
                double newDist = distances.get(currentVertex) + edge.cost;

                if (newDist < distances.get(neighbor)) {
                    distances.put(neighbor, newDist);
                    previous.put(neighbor, currentVertex);
                    pq.add(new AbstractMap.SimpleEntry<>(neighbor, newDist));
                }
            }
        }

        // Backtrack to find the shortest path
        List<String> path = new LinkedList<>();
        for (String at = end; at != null; at = previous.get(at)) {
            path.add(at);
        }
        reverse(path);

        return path;
    }

    // Method to find the shortest path to one of the assembly points with minimal cost
    public static void findShortestPathToAssemblyPoints(Map<String, List<Edge>> graph, String start, String[] assemblyPoints) {
        double minCost = Double.MAX_VALUE;
        String minCostAssemblyPoint = null;
        List<String> minCostPath = null;

        // Iterate over each assembly point to find the one with the minimal cost
        for (String assemblyPoint : assemblyPoints) {
            List<String> path = getShortestPath(graph, start, assemblyPoint);
            double cost = dijkstraAlgorithm(graph, start).get(assemblyPoint);

            if (cost < minCost) {
                minCost = cost;
                minCostAssemblyPoint = assemblyPoint;
                minCostPath = path;
            }
        }

        // Write the shortest path to the assembly point with minimal cost to a CSV file
        if (minCostPath != null) {
            writeResultsToCSV_18(minCostAssemblyPoint, minCostPath, minCost);
        }
    }

    private static void writeResultsToCSV_17(Map<String, Double> distances, String start) {
        String fileName = "shortest_paths_from_" + start + ".csv";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("Vertex,Cost\n");
            for (Map.Entry<String, Double> entry : distances.entrySet()) {
                writer.write(entry.getKey() + "," + entry.getValue() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to write the shortest path results to a CSV file
    public static void writeResultsToCSV_18(String vertex, List<String> path, double cost) {
        String fileName = "shortest_path_to_" + vertex + ".csv";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("Vertex,Distance\n");
            for (String step : path) {
                writer.write(step + "," + cost + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
