package Algoritmos.Dijkstra;
import java.util.*;

public class Dijkstra {

    private static final int INFINITY = Integer.MAX_VALUE;

    public static void dijkstra(int[][] adjMatrix, int source) {
        int n = adjMatrix.length;
        int[] dist = new int[n];
        Arrays.fill(dist, INFINITY);
        dist[source] = 0;

        boolean[] visited = new boolean[n];
        Arrays.fill(visited, false);

        for (int i = 0; i < n - 1; i++) {
            int u = -1;
            for (int j = 0; j < n; j++) {
                if (!visited[j] && (u == -1 || dist[j] < dist[u])) {
                    u = j;
                }
            }

            visited[u] = true;

            for (int v = 0; v < n; v++) {
                if (!visited[v] && adjMatrix[u][v] != INFINITY && dist[u] + adjMatrix[u][v] < dist[v]) {
                    dist[v] = dist[u] + adjMatrix[u][v];
                }
            }
        }

//        System.out.println("Node \t Distance from Source");
//        for (int i = 0; i < n; i++) {
//            System.out.println(i + " \t\t " + dist[i]);
//        }
    }

    public static void main(String[] args) {
        int n = 10000; // number of nodes
        int numEdges = 20000; // number of edges
        int[][] adjMatrix = new int[n][n];

        long startTime = System.currentTimeMillis();

        // Lógica del programa aquí
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    adjMatrix[i][j] = 0;
                } else {
                    adjMatrix[i][j] = INFINITY;
                }
            }
        }

        // Crear aristas aleatorias
        Random random = new Random();
        int edgesCreated = 0;

        while (edgesCreated < numEdges) {
            int i = random.nextInt(n);
            int j = random.nextInt(n);
            if (i != j && adjMatrix[i][j] == INFINITY) {
                int weight = random.nextInt(10) + 1; // Peso aleatorio entre 1 y 10
                adjMatrix[i][j] = weight;
                adjMatrix[j][i] = weight;
                edgesCreated++;
            }
        }

        // Imprimir el número de nodos y aristas
        System.out.println("Número de nodos: " + n);
        System.out.println("Número de aristas: " + numEdges);

        int source = 0; // Nodo inicial
        dijkstra(adjMatrix, source);

        // Medición del tiempo de finalización
        long endTime = System.currentTimeMillis();

        // Cálculo del tiempo de ejecución
        long executionTime = endTime - startTime;

        // Imprimir el tiempo de ejecución
        System.out.println("Tiempo de ejecución: " + executionTime + " ms");
    }
}
