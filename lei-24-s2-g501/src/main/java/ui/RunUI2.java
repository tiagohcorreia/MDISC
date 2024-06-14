package ui;

import us13.Edge;
import us13.ExportGraph;
import us13.ShowGraph;
import us15.Dijkstra;
import us15.ImportGraph2;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class RunUI2 implements Runnable {

    private final String FILE1 = "us17/us17_matrix.csv";

    private final String FILE2 = "us17/us17_points_names.csv";

    private final String FILE3 = "us18/us18_matrix.csv";

    private final String FILE4 = "us18/us18_points_names.csv";

    // Para defesa
    private final String FILE5 = "";
    private final String FILE6 = "";

    private final String FILE7 = "";
    private final String FILE8 = "";


    @Override
    public void run() {

        System.out.println("====== Select option ======");
        System.out.println();
        System.out.println("0.Exit");
        System.out.println("1.Import and execute Dijkstra on us17_matrix and us17_points_names");
        System.out.println("2.Import and calculate shortest routes to the closest Assembly Point.");
        System.out.println("3.Import and execute Dijkstra on us17_matrix and us17_points_names (presentation).");
        System.out.println("4.Import and calculate shortest routes to the closest Assembly Point. (presentation)");
        System.out.println();

        Scanner sc = new Scanner(System.in);

        int opt = sc.nextInt();

        switch (opt) {

            case 1:

                Map<String, List<Edge>> graph3 = ImportGraph2.createGraphFromMatrix(FILE1, FILE2);

                String dotInput3 = ShowGraph.generateDotRepresentation(graph3);
                ShowGraph.writeDotToFile(dotInput3, "results/inputGraphUS17.dot");

                System.out.println("Enter the starting point: ");
                String start1 = sc.next();

                Map<String, List<Edge>> shortestPath = Dijkstra.dijkstra(graph3, start1);

                double totalCost = Dijkstra.calculateTotalCost(shortestPath);

                ExportGraph.exportToCSV(shortestPath, totalCost, "results/US17.csv");
                String dotOutput3 = ShowGraph.generateDotRepresentation(shortestPath);
                ShowGraph.writeDotToFile(dotOutput3, "results/shortestPath.dot");
                break;

            case 2:

                Map<String, List<Edge>> graph_us18 = ImportGraph2.createGraphFromMatrix(FILE3, FILE4);

                String dotInput4 = ShowGraph.generateDotRepresentation(graph_us18);
                ShowGraph.writeDotToFile(dotInput4, "results/inputGraphUS18.dot");

                List<String> apValues = new ArrayList<>();

                try (BufferedReader br = new BufferedReader(new FileReader(FILE4))) {
                    String line = br.readLine();
                    if (line != null) {
                        // Split the line by the semicolon delimiter
                        String[] values = line.split(";");
                        // Iterate over the values and collect those that start with "AP"
                        for (String value : values) {
                            if (value.startsWith("AP")) {
                                apValues.add(value);
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // Convert the List to a String array
                String[] apArray = apValues.toArray(new String[0]);

                Map<String, List<Edge>> shortestToAssembly = Dijkstra.findShortestPathToAssemblyPoints(graph_us18, apArray);

                break;

            case 3:

                Map<String, List<Edge>> graph4 = ImportGraph2.createGraphFromMatrix(FILE5, FILE6);

                String dotInput5 = ShowGraph.generateDotRepresentation(graph4);
                ShowGraph.writeDotToFile(dotInput5, "results/inputGraphUS17.dot");

                System.out.println("Enter the starting point: ");
                String start2 = sc.next();

                Map<String, List<Edge>> shortestPath2 = Dijkstra.dijkstra(graph4, start2);

                double totalCost2 = Dijkstra.calculateTotalCost(shortestPath2);

                ExportGraph.exportToCSV(shortestPath2, totalCost2, "results/defesa.csv");
                String dotOutput6 = ShowGraph.generateDotRepresentation(shortestPath2);
                ShowGraph.writeDotToFile(dotOutput6, "results/shortestPath.dot");
                break;

            case 4:

                Map<String, List<Edge>> graph_us18_2 = ImportGraph2.createGraphFromMatrix(FILE7, FILE8);

                String dotInput7 = ShowGraph.generateDotRepresentation(graph_us18_2);
                ShowGraph.writeDotToFile(dotInput7, "results/inputGraphUS18.dot");

                List<String> apValues2 = new ArrayList<>();

                try (BufferedReader br = new BufferedReader(new FileReader(FILE8))) {
                    String line = br.readLine();
                    if (line != null) {
                        // Split the line by the semicolon delimiter
                        String[] values = line.split(";");
                        // Iterate over the values and collect those that start with "AP"
                        for (String value : values) {
                            if (value.startsWith("AP")) {
                                apValues2.add(value);
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // Convert the List to a String array
                String[] apArray2 = apValues2.toArray(new String[0]);

                Map<String, List<Edge>> shortestToAssembly2 = Dijkstra.findShortestPathToAssemblyPoints(graph_us18_2, apArray2);

                break;

            case 0:
                System.exit(0);
                break;

            default:
                System.out.println("Invalid option");
                break;
        }
    }
}
