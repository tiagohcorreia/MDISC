package us15;

import us13.Edge;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImportGraph2 {

    public static Map<String, List<Edge>> createGraphFromMatrix(String file, String namesFile) {

        Map<String, List<Edge>> graph = new HashMap<>();

        try (BufferedReader matrix = new BufferedReader(new FileReader(file));
             BufferedReader names = new BufferedReader(new FileReader(namesFile))) {

            // Read vertex names
            List<String> vertices = new ArrayList<>();
            String line;

            System.out.println("Reading vertex names from " + namesFile);

            while ((line = names.readLine()) != null) {

                String[] vertexNames = line.split(";");

                for (String vertex : vertexNames) {
                    vertices.add(vertex.trim());
                }
            }

            System.out.println("Vertices: " + vertices);

            // Read the matrix and create edges
            int rowIndex = 0;

            System.out.println("Reading cost matrix from " + file);

            while ((line = matrix.readLine()) != null) {

                line = line.replaceAll("^\\P{Print}+", "");
                String[] parts = line.split(";");

                System.out.println("Processing row " + rowIndex + ": " + line);

                for (int colIndex = 0; colIndex < parts.length; colIndex++) {

                    double weight = Double.parseDouble(parts[colIndex]);

                    if (weight == 0) {
                        weight = 99999999;
                    }

                    if (rowIndex < vertices.size() && colIndex < vertices.size()) {

                        String source = vertices.get(rowIndex);
                        String target = vertices.get(colIndex);

                        System.out.println("Adding edge from " + source + " to " + target + " with weight " + weight);

                        graph.putIfAbsent(source, new ArrayList<>());
                        graph.get(source).add(new Edge(source, target, weight));
                    }
                }

                rowIndex++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(graph);
        return graph;
    }
}