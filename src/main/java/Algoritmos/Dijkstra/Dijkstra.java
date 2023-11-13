package Algoritmos.Dijkstra;
import java.util.*;

public class Dijkstra {

    private static final int INFINITY = Integer.MAX_VALUE;

    // Método para aplicar el algoritmo de Dijkstra en un grafo representado por una matriz de adyacencia
    public static void dijkstra(int[][] adjMatrix, int source) {
        int n = adjMatrix.length; // Número de nodos en el grafo
        int[] dist = new int[n]; // Arreglo para almacenar las distancias más cortas desde el nodo fuente
        Arrays.fill(dist, INFINITY); // Inicializa todas las distancias como infinito
        dist[source] = 0; // La distancia desde el nodo fuente a sí mismo es cero

        boolean[] visited = new boolean[n]; // Arreglo para rastrear nodos visitados
        Arrays.fill(visited, false);

        // Iterar sobre todos los nodos
        for (int i = 0; i < n - 1; i++) {
            int u = -1;
            // Encuentra el nodo no visitado con la distancia más corta
            for (int j = 0; j < n; j++) {
                if (!visited[j] && (u == -1 || dist[j] < dist[u])) {
                    u = j;
                }
            }

            visited[u] = true;

            // Actualiza las distancias de los nodos adyacentes a u
            for (int v = 0; v < n; v++) {
                if (!visited[v] && adjMatrix[u][v] != INFINITY && dist[u] + adjMatrix[u][v] < dist[v]) {
                    dist[v] = dist[u] + adjMatrix[u][v];
                }
            }
        }

        // Imprimir las distancias desde el nodo fuente
//        System.out.println("Node \t Distance from Source");
//        for (int i = 0; i < n; i++) {
//            System.out.println(i + " \t\t " + dist[i]);
//        }
    }

    // Método principal del programa
    public static void main(String[] args) {
        int n = 15000; // Número de nodos
        int numEdges = 30000; // Número de aristas
        int[][] adjMatrix = new int[n][n]; // Matriz de adyacencia para representar el grafo

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
