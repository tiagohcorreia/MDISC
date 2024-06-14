package us12;

import us13.Edge;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImportGraph {

    // Method to read graph data from a CSV file and create a graph representation
    public static Map<String, List<Edge>> createGraph(String file) {

        // Create data structures to store vertices and edges
        Map<String, List<Edge>> graph = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Split the CSV line by semicolon
                String[] parts = line.split(";");
                if (parts.length == 3) {
                    String source = parts[0];
                    String target = parts[1];
                    double weight = Double.parseDouble(parts[2]);

                    // Add source and target vertices to the graph
                    graph.putIfAbsent(source, new ArrayList<>());
                    graph.putIfAbsent(target, new ArrayList<>());

                    // Add edge to the graph
                    graph.get(source).add(new Edge(source, target, weight));
                }
            }

            return graph;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
