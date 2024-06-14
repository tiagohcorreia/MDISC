package us13;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;


public class ShowGraph {

    public static String generateDotRepresentation(Map<String, List<Edge>> graphData) {
        StringBuilder dotBuilder = new StringBuilder();
        dotBuilder.append("graph G {\n");

        for (Map.Entry<String, List<Edge>> entry : graphData.entrySet()) {
            String sourceVertex = entry.getKey();
            List<Edge> edges = entry.getValue();
            for (Edge edge : edges) {
                String targetVertex = edge.vertex2;

                double weight = edge.cost;

                if (weight == 99999999) {
                    continue;
                }
                dotBuilder.append("\t").append(sourceVertex).append(" -- ").append(targetVertex);
                dotBuilder.append(" [label=\"").append(weight).append("\"];\n");
            }
        }

        dotBuilder.append("}\n");
        return dotBuilder.toString();
    }

    public static void writeDotToFile(String dotString, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write(dotString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
