package Algoritmos.FloydWarshall;

import java.util.Random;

public class FloydWarshall {
    public static void main(String[] args) {
        int numNodes = 8000;
        int numEdges = 16000;
        int[][] graph = generateRandomGraph(numNodes, numEdges);

        // Imprimir el número de nodos y aristas antes de ejecutar el algoritmo
        System.out.println("Número de nodos: " + numNodes);
        System.out.println("Número de aristas: " + countEdges(graph));

//        System.out.println("\nOriginal Graph:");
//        printGraph(graph);

        long startTime = System.nanoTime();
        floydWarshall(graph);
        long endTime = System.nanoTime();

        double executionTime = (endTime - startTime) / 1e6; // Tiempo de ejecución en milisegundos

//        System.out.println("\nShortest Path Matrix:");
//        printGraph(graph);

        System.out.println("\nExecution Time: " + executionTime + " ms");
    }

    // Método para generar un grafo ponderado aleatorio con un número específico de nodos y aristas
    public static int[][] generateRandomGraph(int numNodes, int numEdges) {
        int[][] graph = new int[numNodes][numNodes];
        int maxWeight = 10; // Peso máximo para las aristas

        for (int i = 0; i < numNodes; i++) {
            for (int j = 0; j < numNodes; j++) {
                if (i == j) {
                    graph[i][j] = 0; // No hay arista entre un nodo y sí mismo
                } else {
                    graph[i][j] = Integer.MAX_VALUE; // Inicializar todas las aristas como infinito
                }
            }
        }

        Random random = new Random();
        int edgesCreated = 0;

        // Agregar aristas aleatorias al grafo
        while (edgesCreated < numEdges) {
            int i = random.nextInt(numNodes);
            int j = random.nextInt(numNodes);
            if (i != j && graph[i][j] == Integer.MAX_VALUE) {
                int weight = random.nextInt(maxWeight) + 1; // Peso aleatorio entre 1 y maxWeight
                graph[i][j] = weight;
                edgesCreated++;
            }
        }

        return graph;
    }

    // Algoritmo de Floyd-Warshall para encontrar los caminos más cortos entre todos los pares de nodos
    public static void floydWarshall(int[][] graph) {
        int numNodes = graph.length;

        for (int k = 0; k < numNodes; k++) {
            for (int i = 0; i < numNodes; i++) {
                for (int j = 0; j < numNodes; j++) {
                    if (graph[i][k] != Integer.MAX_VALUE && graph[k][j] != Integer.MAX_VALUE) {
                        int throughK = graph[i][k] + graph[k][j];
                        if (throughK < graph[i][j]) {
                            graph[i][j] = throughK;
                        }
                    }
                }
            }
        }
    }

    // Método para imprimir la matriz de distancias más cortas o el grafo original
    public static void printGraph(int[][] graph) {
        int numNodes = graph.length;
        for (int i = 0; i < numNodes; i++) {
            for (int j = 0; j < numNodes; j++) {
                if (graph[i][j] == Integer.MAX_VALUE) {
                    System.out.print("INF\t");
                } else {
                    System.out.print(graph[i][j] + "\t");
                }
            }
            System.out.println();
        }
    }

    // Método para contar el número de aristas en el grafo
    public static int countEdges(int[][] graph) {
        int numNodes = graph.length;
        int count = 0;

        for (int i = 0; i < numNodes; i++) {
            for (int j = 0; j < numNodes; j++) {
                if (graph[i][j] != 0 && graph[i][j] != Integer.MAX_VALUE) {
                    count++;
                }
            }
        }

        return count;
    }
}