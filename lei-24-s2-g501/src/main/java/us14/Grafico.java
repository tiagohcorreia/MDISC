package us14;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import us12.ImportGraph;
import us13.Edge;
import us13.Kruskal;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Grafico extends Application {
    public void start(Stage stage) {
        // Create x and y axes
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();

        // Set labels for the axes
        xAxis.setLabel("Sample size");
        yAxis.setLabel("Time Elapsed (ms)");

        // Create a line chart
        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);

        // Create a series
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName("Asymptotic behavior of execution running times");

        // Number of graphs to process
        int numGraphs = 30;

        // FileWriter to write data into a single CSV file
        FileWriter writer = null;
        try {
            writer = new FileWriter("execution_times/execution_times_2feira.csv");
            writer.append("Input Size;Execution Time (ms)\n");

            // Loop to process each graph
            for (int i = 1; i <= numGraphs; i++) {
                // Read graph data from CSV file
                String filename = "files/fx_2feira_" + i + ".csv";
                Map<String, List<Edge>> graph = ImportGraph.createGraph(filename);

                // Measure the execution time of the Kruskal algorithm
                long startTime = System.nanoTime();
                Map<String, List<Edge>> graphMin1 = Kruskal.kruskalAlgorithm(graph);
                long endTime = System.nanoTime();
                long executionTime = (endTime - startTime) / 1000000; // Convert to milliseconds

                int size = countEdges(graph);

                series.getData().add(new XYChart.Data<>(size, executionTime));
                // Write execution time to the CSV file
                writer.append(size + ";" + executionTime + "\n");
            }

            System.out.println("All tests completed.");

        } catch (IOException e) {
            System.out.println("Error writing CSV file: " + e.getMessage());
        } finally {
            try {
                if (writer != null) {
                    writer.flush();
                    writer.close();
                    System.out.println("CSV file written.");
                }
            } catch (IOException e) {
                System.out.println("Error closing FileWriter: " + e.getMessage());
            }
        }

        // Add series to the line chart
        lineChart.getData().add(series);

        // Create the scene and set it to the stage
        Scene scene = new Scene(lineChart, 600, 400);
        stage.setScene(scene);
        stage.setTitle("Asymptotic behavior of execution running times");
        stage.show();
    }

    // Method to count the total number of edges in the graph
    private static int countEdges(Map<String, List<Edge>> graph) {
        int totalEdges = 0;
        for (List<Edge> edges : graph.values()) {
            totalEdges += edges.size();
        }
        return totalEdges;
    }
}
