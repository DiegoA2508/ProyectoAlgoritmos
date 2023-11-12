package Algoritmos.Karp;

import java.util.List;
import java.util.Random;
import java.util.Vector;

public class Karp {

	static int V = 10000; // Cambiado el número de nodos a 100

	// a struct to represent edges
	static class Edge {
		int from, weight;

		Edge(int from, int weight) {
			this.from = from;
			this.weight = weight;
		}
	}

	// vector to store edges
	@SuppressWarnings("unchecked")
	static Vector<Edge>[] edges = new Vector[V];

	static {
		for (int i = 0; i < V; i++)
			edges[i] = new Vector<>();
	}

	static void addRandomEdges(int numEdges) {
		Random random = new Random();
		for (int i = 0; i < numEdges; i++) {
			int u = random.nextInt(V);
			int v = random.nextInt(V);
			int w = random.nextInt(10) + 1; // Peso aleatorio entre 1 y 10
			addedge(u, v, w);
		}
	}

	static void addedge(int u, int v, int w) {
		edges[v].add(new Edge(u, w));
	}

	// calculates the shortest path
	static void shortestpath(int[][] dp) {
		// initializing all distances as -1
		for (int i = 0; i <= V; i++)
			for (int j = 0; j < V; j++)
				dp[i][j] = -1;

		// shortest distance from the first vertex
		// to itself consisting of 0 edges
		dp[0][0] = 0;

		// filling up the dp table
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

	// Returns the minimum value of the average weight
	// of a cycle in the graph.
	static double minAvgWeight() {
		int[][] dp = new int[V + 1][V];
		shortestpath(dp);

		// array to store the avg values
		double[] avg = new double[V];
		for (int i = 0; i < V; i++)
			avg[i] = -1;

		// Compute average values for all vertices using
		// weights of shortest paths stored in dp.
		for (int i = 0; i < V; i++) {
			if (dp[V][i] != -1) {
				for (int j = 0; j < V; j++)
					if (dp[j][i] != -1)
						avg[i] = Math.max(avg[i],
								((double) dp[V][i] -
										dp[j][i]) / (V - j));
			}
		}

		// Find the minimum value in avg[]
		double result = avg[0];
		for (int i = 0; i < V; i++)
			if (avg[i] != -1 && avg[i] < result)
				result = avg[i];

		return result;
	}

	public static void main(String[] args) {
		int numNodes = V; // Cambia el número de nodos según tus necesidades
		int numEdges = 20000; // Cambia el número de aristas según tus necesidades

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
