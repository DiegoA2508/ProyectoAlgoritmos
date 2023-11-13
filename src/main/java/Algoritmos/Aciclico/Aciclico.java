package Algoritmos.Aciclico;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Stack;

class ShortestPath {
	static final int INF = Integer.MAX_VALUE;

	class AdjListNode {
		private int v;
		private int weight;

		AdjListNode(int _v, int _w) {
			v = _v;
			weight = _w;
		}

		int getV() {
			return v;
		}

		int getWeight() {
			return weight;
		}
	}

	class Graph {
		private int V;
		private LinkedList<AdjListNode> adj[];
		private int numEdges;

		Graph(int v) {
			V = v;
			adj = new LinkedList[V];
			for (int i = 0; i < v; ++i)
				adj[i] = new LinkedList<AdjListNode>();
		}

		// Método para agregar una arista al grafo
		void addEdge(int u, int v, int weight) {
			AdjListNode node = new AdjListNode(v, weight);
			adj[u].add(node); // Agrega v a la lista de u
			numEdges++;
		}

		// Método para realizar el orden topológico del grafo
		void topologicalSortUtil(int v, Boolean visited[], Stack stack) {
			visited[v] = true;
			Iterator<AdjListNode> it = adj[v].iterator();
			while (it.hasNext()) {
				AdjListNode node = it.next();
				if (!visited[node.getV()])
					topologicalSortUtil(node.getV(), visited, stack);
			}
			stack.push(v);
		}

		// Método principal para encontrar el camino más corto desde un nodo de origen
		void shortestPath(int s) {
			Stack stack = new Stack();
			int dist[] = new int[V];
			Boolean visited[] = new Boolean[V];
			for (int i = 0; i < V; i++)
				visited[i] = false;

			// Realiza el orden topológico para cada nodo no visitado
			for (int i = 0; i < V; i++)
				if (visited[i] == false)
					topologicalSortUtil(i, visited, stack);

			// Inicializa las distancias desde el nodo de origen como infinito, excepto el propio nodo
			for (int i = 0; i < V; i++)
				dist[i] = INF;
			dist[s] = 0;

			// Procesa los nodos en orden topológico y actualiza las distancias
			while (stack.empty() == false) {
				int u = (Integer) stack.pop();
				Iterator<AdjListNode> it;
				if (dist[u] != INF) {
					it = adj[u].iterator();
					while (it.hasNext()) {
						AdjListNode i = it.next();
						if (dist[i.getV()] > dist[u] + i.getWeight())
							dist[i.getV()] = dist[u] + i.getWeight();
					}
				}
			}

			// Imprimir distancias o realizar otras operaciones según sea necesario
			// System.out.println("Número de aristas: " + numEdges);
		}

		// Método para generar un grafo con valores predeterminados
		void generateGraphWithDefaultValues() {
			V = 50000;
			adj = new LinkedList[V];
			for (int i = 0; i < V; ++i)
				adj[i] = new LinkedList<AdjListNode>();

			System.out.println("Número de nodos: " + V);
			System.out.println("Número de aristas: 20000");

			// Añadir 200 aristas de manera predeterminada
			for (int i = 0; i < V; i++) {
				for (int j = i + 1; j < V && numEdges < 100000; j++) {
					int weight = (int) (Math.random() * 10) + 1; // Peso aleatorio entre 1 y 10
					addEdge(i, j, weight);
				}
			}
		}
	}

	// Método para crear un objeto de la clase Graph y generar un grafo con valores predeterminados
	Graph createGraph() {
		Graph graph = new Graph(0);
		graph.generateGraphWithDefaultValues();
		return graph;
	}

	public static void main(String args[]) {
		ShortestPath t = new ShortestPath();

		// Crear un grafo y generar valores predeterminados
		Graph g = t.createGraph();

		int s = 1;
		long startTime = System.nanoTime();
		g.shortestPath(s);
		long endTime = System.nanoTime();

		double tiempoEnMilisegundos = (endTime - startTime) / 1_000_000.0;

		System.out.println("Execution Time: " + tiempoEnMilisegundos + " milliseconds");
	}
}

