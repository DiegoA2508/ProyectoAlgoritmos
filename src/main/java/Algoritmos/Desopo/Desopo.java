package Algoritmos.Desopo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Desopo {

	public static int[] desopo(int[][] graph) {
		int v = graph.length;
		List<Integer> q = new ArrayList<>();
		int[] distance = new int[v];
		Arrays.fill(distance, Integer.MAX_VALUE);
		boolean[] isInQueue = new boolean[v];
		int source = 0;
		distance[source] = 0;
		q.add(source);
		isInQueue[source] = true;

		while (!q.isEmpty()) {
			int u = q.remove(0);
			isInQueue[u] = false;

			if (graph[u] != null) {
				for (int i = 0; i < graph[u].length; i += 2) {
					int e = graph[u][i + 1];
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


	public static void main(String[] args) {
		int numNodes = 10000; // Número de nodos
		int numEdges = 20000; // Número de aristas

		int[][] graph = createRandomGraph(numNodes, numEdges);

		printGraphInfo(graph);

		long startTime = System.nanoTime();
		int[] distances = desopo(graph);
		long endTime = System.nanoTime();

		System.out.println("Tiempo de ejecución: " + (endTime - startTime) / 1e6 + " milisegundos");
	}

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
