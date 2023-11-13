package Algoritmos.Multietapa;

import java.util.Random;

class MultistageGraph {

	static int N = 15000; // Número de nodos, cambiado a 15000
	static int INF = Integer.MAX_VALUE;

	public static int shortestDist(int[][] graph) {
		int[] dist = new int[N];
		dist[N - 1] = 0;

		// Calcular las distancias más cortas
		for (int i = N - 2; i >= 0; i--) {
			dist[i] = INF;
			for (int j = i; j < N; j++) {
				if (graph[i][j] == INF) {
					continue;
				}
				dist[i] = Math.min(dist[i], graph[i][j] + dist[j]);
			}
		}

		return dist[0];
	}

	public static void main(String[] args) {
		int numNodes = N; // Cambiar el número de nodos según sea necesario
		int numEdges = 200000; // Cambiar el número de aristas según sea necesario
		int[][] graph = generateRandomGraph(numNodes, numEdges);

		System.out.println("Número de nodos: " + numNodes);
		System.out.println("Número de aristas: " + countEdges(graph));

		long startTime = System.nanoTime();
		shortestDist(graph);
		long endTime = System.nanoTime();

		double executionTime = (endTime - startTime) / 1e6; // Tiempo de ejecución en milisegundos
		System.out.println("Tiempo de ejecución: " + executionTime + " ms");
	}

	public static int[][] generateRandomGraph(int numNodes, int numEdges) {
		Random random = new Random();
		int[][] graph = new int[numNodes][numNodes];

		// Generar aristas aleatorias
		for (int i = 0; i < numEdges; i++) {
			int u = random.nextInt(numNodes);
			int v;
			do {
				v = random.nextInt(numNodes);
			} while (u == v);

			int weight = random.nextInt(20); // Peso aleatorio entre 0 y 19
			graph[u][v] = weight;
			graph[v][u] = weight;
		}

		return graph;
	}

	public static int countEdges(int[][] graph) {
		int count = 0;
		// Contar el número de aristas en el grafo
		for (int i = 0; i < graph.length; i++) {
			for (int j = i + 1; j < graph[i].length; j++) {
				if (graph[i][j] != INF) {
					count++;
				}
			}
		}
		return count;
	}
}
