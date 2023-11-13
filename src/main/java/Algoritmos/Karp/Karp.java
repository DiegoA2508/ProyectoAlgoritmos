package Algoritmos.Karp;

import java.util.List;
import java.util.Random;
import java.util.Vector;

public class Karp {

	static int V = 15000; // Número de nodos, cambiado a 15000

	// Una estructura para representar aristas
	static class Edge {
		int from, weight;

		Edge(int from, int weight) {
			this.from = from;
			this.weight = weight;
		}
	}

	// Vector para almacenar aristas
	@SuppressWarnings("unchecked")
	static Vector<Edge>[] edges = new Vector[V];

	static {
		for (int i = 0; i < V; i++)
			edges[i] = new Vector<>();
	}

	// Método para agregar aristas aleatorias al grafo
	static void addRandomEdges(int numEdges) {
		Random random = new Random();
		for (int i = 0; i < numEdges; i++) {
			int u = random.nextInt(V);
			int v = random.nextInt(V);
			int w = random.nextInt(10) + 1; // Peso aleatorio entre 1 y 10
			addedge(u, v, w);
		}
	}

	// Método para agregar una arista al grafo
	static void addedge(int u, int v, int w) {
		edges[v].add(new Edge(u, w));
	}

	// Calcula el camino más corto
	static void shortestpath(int[][] dp) {
		// Inicializando todas las distancias como -1
		for (int i = 0; i <= V; i++)
			for (int j = 0; j < V; j++)
				dp[i][j] = -1;

		// Distancia más corta desde el primer nodo
		// a sí mismo que consiste en 0 aristas
		dp[0][0] = 0;

		// Rellenando la tabla dp
		for (int i = 1; i <= V; i++) {
			for (int j = 0; j < V; j++) {
				for (int k = 0; k < edges[j].size(); k++) {
					if (dp[i - 1][edges[j].elementAt(k).from] != -1) {
						int curr_wt = dp[i - 1][edges[j].elementAt(k).from] +
								edges[j].elementAt(k).weight;
						if (dp[i][j] == -1)
							dp[i][j] = curr_wt;
						else
							dp[i][j] = Math.min(dp[i][j], curr_wt);
					}
				}
			}
		}
	}

	// Devuelve el valor mínimo del peso promedio
	// de un ciclo en el grafo.
	static double minAvgWeight() {
		int[][] dp = new int[V + 1][V];
		shortestpath(dp);

		// Array para almacenar los valores promedio
		double[] avg = new double[V];
		for (int i = 0; i < V; i++)
			avg[i] = -1;

		// Calcular los valores promedio para todos los nodos utilizando
		// los pesos de los caminos más cortos almacenados en dp.
		for (int i = 0; i < V; i++) {
			if (dp[V][i] != -1) {
				for (int j = 0; j < V; j++)
					if (dp[j][i] != -1)
						avg[i] = Math.max(avg[i],
								((double) dp[V][i] -
										dp[j][i]) / (V - j));
			}
		}

		// Encontrar el valor mínimo en avg[]
		double result = avg[0];
		for (int i = 0; i < V; i++)
			if (avg[i] != -1 && avg[i] < result)
				result = avg[i];

		return result;
	}

	public static void main(String[] args) {
		int numNodes = V; // Cambiar el número de nodos según sea necesario
		int numEdges = 30000; // Cambiar el número de aristas según sea necesario

		addRandomEdges(numEdges);

		long startTime = System.nanoTime();

		minAvgWeight();
		long endTime = System.nanoTime();

		double tiempoEnMilisegundos = (endTime - startTime) / 1_000_000.0;

		System.out.println("Número de nodos: " + numNodes);
		System.out.println("Número de aristas: " + numEdges);

		System.out.println("Execution Time: " + tiempoEnMilisegundos + " milliseconds");
	}
}
