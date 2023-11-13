package Algoritmos.Dial;
import java.util.*;

public class Dial {
	static final int INF = Integer.MAX_VALUE;
	private int V; // Número de nodos
	private ArrayList<ArrayList<Tuple>> adj; // Lista de adyacencia para representar el grafo

	public Dial(int v) {
		this.V = v;
		this.adj = new ArrayList<ArrayList<Tuple>>();
		for (int i = 0; i < v; i++) {
			this.adj.add(new ArrayList<Tuple>());
		}
	}

	// Método para agregar una arista al grafo (bidireccional)
	public void addEdge(int u, int v, int w) {
		adj.get(u).add(new Tuple(v, w));
		adj.get(v).add(new Tuple(u, w));
	}

	// Método para generar un grafo aleatorio con un número específico de nodos, aristas y peso máximo
	public void generateRandomGraph(int numNodes, int numEdges, int maxWeight) {
		if (numNodes <= 0 || numEdges < 0 || numEdges > numNodes * (numNodes - 1) / 2) {
			System.out.println("Parámetros de entrada no válidos.");
			return;
		}

		Random random = new Random();
		this.V = numNodes;  // Actualizar el número de nodos
		for (int i = 0; i < numEdges; i++) {
			int u = random.nextInt(numNodes);
			int v;
			do {
				v = random.nextInt(numNodes);
			} while (u == v);
			int weight = random.nextInt(maxWeight) + 1;
			addEdge(u, v, weight);
		}
	}

	// Método principal para encontrar caminos más cortos desde el nodo fuente con un límite en el peso de las aristas
	public void shortestPath(int src, int W) {
		int[] dist = new int[V];
		Arrays.fill(dist, INF);

		ArrayList<Integer>[] B = new ArrayList[W * V + 1];
		for (int i = 0; i < W * V + 1; i++)
			B[i] = new ArrayList<Integer>();

		B[0].add(src);
		dist[src] = 0;

		int idx = 0;
		while (true) {
			while (B[idx].size() == 0 && idx < W * V)
				idx++;

			if (idx == W * V)
				break;

			int u = B[idx].get(0);
			B[idx].remove(0);

			for (Tuple i : adj.get(u)) {
				int v = i.v;
				int weight = i.w;

				int du = dist[u];
				int dv = dist[v];

				if (dv > du + weight) {
					dist[v] = du + weight;
					dv = dist[v];

					B[dv].add(0, v);
				}
			}
		}

		// Imprimir las distancias desde el nodo fuente
//		System.out.println("Vertex Distance from Source");
//		for (int i = 0; i < V; ++i)
//			System.out.println(i + "\t\t" + dist[i]);
	}

	// Método principal del programa
	public static void main(String[] args) {
		int numNodes = 500000;
		long startTime = System.nanoTime();
		Dial g = new Dial(numNodes);
		long endTime = System.nanoTime();

		// Parámetros para la generación del grafo
		int numEdges = 1000000; // Puedes ajustar este número según tus necesidades
		int maxWeight = 20;

		// Generar un grafo aleatorio
		g.generateRandomGraph(numNodes, numEdges, maxWeight);

		// Imprimir información sobre el grafo generado aleatoriamente
		System.out.println("Grafo generado aleatoriamente:");
		System.out.println("Número de nodos: " + numNodes);
		System.out.println("Número de aristas: " + numEdges);

		// Especifica el peso máximo como parámetro para shortestPath
		int maxEdgeWeight = 14;
		g.shortestPath(0, maxEdgeWeight);

		// Imprimir el tiempo de ejecución
		System.out.println("Tiempo de ejecución: " + (endTime - startTime) / 1e6 + " milisegundos");
	}

	// Clase Tuple para representar una pareja de nodos y peso de arista
	static class Tuple {
		int v, w;
		Tuple(int v, int w) {
			this.v = v;
			this.w = w;
		}
	}
}
