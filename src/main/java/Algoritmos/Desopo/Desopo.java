package Algoritmos.Desopo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Desopo {

	// Método principal para el algoritmo de Desopo
	public static int[] desopo(int[][] graph) {
		int v = graph.length; // Número de nodos en el grafo
		List<Integer> q = new ArrayList<>(); // Lista para la cola de nodos
		int[] distance = new int[v]; // Arreglo para almacenar las distancias más cortas
		Arrays.fill(distance, Integer.MAX_VALUE); // Inicializa todas las distancias como infinito
		boolean[] isInQueue = new boolean[v]; // Bandera para rastrear si un nodo está en la cola
		int source = 0; // Nodo fuente
		distance[source] = 0;
		q.add(source);
		isInQueue[source] = true;

		while (!q.isEmpty()) {
			int u = q.remove(0); // Obtiene el primer nodo de la cola
			isInQueue[u] = false;

			if (graph[u] != null) {
				for (int i = 0; i < graph[u].length; i += 2) {
					int e = graph[u][i + 1]; // Nodo adyacente
					if (distance[e] > distance[u] + graph[u][i]) {
						distance[e] = distance[u] + graph[u][i];
						if (!isInQueue[e]) {
							if (distance[e] == Integer.MAX_VALUE)
								q.add(e);
							else
								q.add(0, e);
							isInQueue[e] = true;
						}
					}
				}
			}
		}
		return distance;
	}

	// Método para imprimir información sobre el grafo
	public static void printGraphInfo(int[][] graph) {
		int numNodes = graph.length;
		int numEdges = 0;

		for (int i = 0; i < numNodes; i++) {
			for (int j = 0; j < graph[i].length; j++) {
				if (graph[i][j] == 1) {
					numEdges++;
				}
			}
		}

		System.out.println("Número de nodos: " + numNodes);
		System.out.println("Número de aristas: " + numEdges);
	}

	// Método principal del programa
	public static void main(String[] args) {
		int numNodes = 150000; // Número de nodos
		int numEdges = 300000; // Número de aristas

		// Crear un grafo aleatorio
		int[][] graph = createRandomGraph(numNodes, numEdges);

		// Imprimir información sobre el grafo
		printGraphInfo(graph);

		// Medir el tiempo de ejecución del algoritmo de Desopo
		long startTime = System.nanoTime();
		int[] distances = desopo(graph);
		long endTime = System.nanoTime();

		// Imprimir el tiempo de ejecución
		System.out.println("Tiempo de ejecución: " + (endTime - startTime) / 1e6 + " milisegundos");
	}

	// Método para crear un grafo aleatorio
	public static int[][] createRandomGraph(int numNodes, int numEdges) {
		int[][] graph = new int[numNodes][];
		for (int i = 0; i < numNodes; i++) {
			List<Integer> edges = new ArrayList<>();
			for (int j = i + 1; j < numNodes; j++) {
				if (Math.random() < 0.5 && numEdges > 0) {
					edges.add(1);
					edges.add(j);
					numEdges--;
				}
			}
			graph[i] = new int[edges.size()];
			for (int j = 0; j < edges.size(); j++) {
				graph[i][j] = edges.get(j);
			}
		}
		return graph;
	}
}
