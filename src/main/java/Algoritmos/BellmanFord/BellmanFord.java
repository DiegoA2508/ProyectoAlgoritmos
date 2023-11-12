package Algoritmos.BellmanFord;
import java.util.Arrays;

public class BellmanFord {

    static class Graph {
        int numNodes;
        int[][] graph;
        int numEdges;

        Graph(int numNodes) {
            this.numNodes = numNodes;
            this.graph = new int[numNodes][numNodes];
        }

        void addEdge(int u, int v, int weight) {
            graph[u][v] = weight;
            graph[v][u] = weight;
            numEdges++;
        }

        void generateGraph(int numNodes, int numEdges, int maxEdgeWeight) {
            this.numNodes = numNodes;

            // Agregar nodos
            for (int i = 0; i < numNodes; i++) {
                for (int j = i + 1; j < numNodes; j++) {
                    if (i != j && numEdges > 0) {
                        int weight = (int) (Math.random() * maxEdgeWeight) + 1;
                        addEdge(i, j, weight);
                        numEdges--;
                    }
                }
            }

            System.out.println("Número de nodos: " + numNodes);
            System.out.println("Número de aristas: " + this.numEdges);
        }
    }

    static void bellmanFord(Graph graph) {
        int numNodes = graph.numNodes;

        // Inicializar distancias y visitados
        boolean[] visited = new boolean[numNodes];
        int[] distance = new int[numNodes];
        Arrays.fill(distance, Integer.MAX_VALUE);
        distance[0] = 0;

        // Medir tiempo de ejecución
        long startTime = System.currentTimeMillis();

        // Ejecutar algoritmo de Bellman-Ford
        for (int i = 0; i < numNodes - 1; i++) {
            for (int u = 0; u < numNodes; u++) {
                for (int v = 0; v < numNodes; v++) {
                    if (distance[u] != Integer.MAX_VALUE && distance[u] + graph.graph[u][v] < distance[v]) {
                        distance[v] = distance[u] + graph.graph[u][v];
                    }
                }
            }
        }

        // Imprimir el tiempo de ejecución
        long endTime = System.currentTimeMillis();
        System.out.println("Tiempo de ejecución: " + (endTime - startTime) + " ms");
    }

    public static void main(String[] args) {
        // Parámetros del problema
        int numNodes = 2500; // Número de nodos
        int numEdges = 5000; // Número de aristas
        int maxEdgeWeight = 10; // Peso máximo de los enlaces

        // Crear grafo con 100 nodos y 200 aristas
        Graph graph = new Graph(numNodes);
        graph.generateGraph(numNodes, numEdges, maxEdgeWeight);

        // Ejecutar algoritmo de Bellman-Ford
        bellmanFord(graph);
    }
}
