package ui;

import us12.ImportGraph;
import us13.*;
import us14.Grafico;

import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class RunUI implements Runnable {

    private final String FILE_1 = "files/segunda_feira.csv";
    private final String FILE_2 = "files/US13_JardimDosSentimentos.csv";
    private final String FILE_3 = "files/US13_JardimEspeciesNucleoRural.csv";

    @Override
    public void run() {

        System.out.println("====== Select option ======");
        System.out.println();

        System.out.println("1.Import and execute Kruskal on segunda_feira");
        System.out.println("2.Import and execute Kruskal on Jardim Dos Sentimentos");
        System.out.println("3.Import and execute Kruskal on Jardim Especies Nucleo Rural");
        System.out.println("4.Create Graphic and csv");
        System.out.println("5.Exit");

        Scanner sc = new Scanner(System.in);

        System.out.println();


        int opt = sc.nextInt();

        switch (opt) {

            case 1:

                Map<String, List<Edge>> graph1 = ImportGraph.createGraph(FILE_1);
                String dotInput1 = ShowGraph.generateDotRepresentation(graph1);
                ShowGraph.writeDotToFile(dotInput1, "results/inputGraph1.dot");

                if (graph1 != null) {

                    Map<String, List<Edge>> graphMin1 = Kruskal.kruskalAlgorithm(graph1);

                    double total1 = 0;
                    for (String vertex : graphMin1.keySet()) {
                        for (Edge edge : graphMin1.get(vertex)) {
                            total1 += edge.cost;
                        }
                    }

                    ExportGraph.exportToCSV(graphMin1, total1, "results/graph1.csv");
                    String dotOutput1 = ShowGraph.generateDotRepresentation(graphMin1);
                    ShowGraph.writeDotToFile(dotOutput1, "results/outputSubgraph1.dot");

                }
                break;

            case 2:

                Map<String, List<Edge>> graph2 = ImportGraph.createGraph(FILE_2);
                String dotInput2 = ShowGraph.generateDotRepresentation(graph2);
                ShowGraph.writeDotToFile(dotInput2, "results/inputGraph_JardimDosSentimentos.dot");

                if (graph2 != null) {

                    Map<String, List<Edge>> graphMin2 = Kruskal.kruskalAlgorithm(graph2);

                    double total2 = 0;
                    for (String vertex : graphMin2.keySet()) {
                        for (Edge edge : graphMin2.get(vertex)) {
                            total2 += edge.cost;
                        }
                    }

                    ExportGraph.exportToCSV(graphMin2, total2, "results/subGraph_JardimDosSentimentos.csv");
                    String dotOutput2 = ShowGraph.generateDotRepresentation(graphMin2);
                    ShowGraph.writeDotToFile(dotOutput2, "results/outputSubgraph_JardimDosSentimentos.dot");
                }

                break;

            case 3:

                Map<String, List<Edge>> graph3 = ImportGraph.createGraph(FILE_3);
                String dotInput3 = ShowGraph.generateDotRepresentation(graph3);
                ShowGraph.writeDotToFile(dotInput3, "results/inputGraph_JardimEspeciesNucleoRural.dot");

                if (graph3 != null) {

                    Map<String, List<Edge>> graphMin3 = Kruskal.kruskalAlgorithm(graph3);

                    double total3 = 0;
                    for (String vertex : graphMin3.keySet()) {
                        for (Edge edge : graphMin3.get(vertex)) {
                            total3 += edge.cost;
                        }
                    }

                    ExportGraph.exportToCSV(graphMin3, total3, "results/subGraph_JardimEspeciesNucleoRural.csv");
                    String dotOutput3 = ShowGraph.generateDotRepresentation(graphMin3);
                    ShowGraph.writeDotToFile(dotOutput3, "results/outputSubgraph_JardimEspeciesNucleoRural.dot");
                }
                break;
            case 4:

                Grafico.launch(Grafico.class);

                break;
            case 5:
                break;
            default:
                System.out.println("Invalid option");

        }
    }
}
