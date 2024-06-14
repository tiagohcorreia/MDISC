package us13;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ExportGraph {

    public static void exportToCSV(Map<String, List<Edge>> graph, double cost, String filename) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {

            // Write data
            for (Map.Entry<String, List<Edge>> entry : graph.entrySet()) {
                String source = entry.getKey();
                List<Edge> edges = entry.getValue();
                for (Edge edge : edges) {
                    writer.write(source + "," + edge.vertex2 + "," + edge.cost + "\n");
                }
            }

            // Write total cost
            writer.write("Total Cost: " + cost);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
