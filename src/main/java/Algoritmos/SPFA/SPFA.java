package Algoritmos.SPFA;
import java.util.*;

class SPFA {
    public static void main(String[] args) {
        int numNodes = 50000000;
        int numEdges = 100000000;

        // Generar un grafo aleatorio
        Graph graph = generateRandomGraph(numNodes, numEdges);

        System.out.println("Número de nodos: " + numNodes);
        System.out.println("Número de aristas: " + Graph.getTotalEdges());

        long startTime = System.nanoTime();

        // Ejecutar el algoritmo SPFA desde el nodo 0
        List<Integer> shortestPaths = graph.spfa(0);

        long endTime = System.nanoTime();

        System.out.println("Tiempo de ejecución: " + (endTime - startTime) / 1e6 + " milisegundos");

        // Imprimir los caminos más cortos (opcional)
        // System.out.println("Caminos más cortos:");
        // for (int i = 0; i < shortestPaths.size(); ++i) {
        //     System.out.println("Nodo " + i + ": " + shortestPaths.get(i));
        // }
    }

    // Método para generar un grafo aleatorio
    private static Graph generateRandomGraph(int numNodes, int numEdges) {
        Graph graph = new Graph(numNodes);
        Random random = new Random();

        int generatedEdges = 0;

        while (generatedEdges < numEdges) {
            int u = random.nextInt(numNodes);
            int v = random.nextInt(numNodes);
            int weight = random.nextInt(10) + 1; // Peso aleatorio entre 1 y 10

            if (u != v && !graph.edgeContains(u, v)) {
                graph.addEdge(u, v, weight);
                generatedEdges++;
            }
        }

        return graph;
    }
}

class Graph {
    private int V;
    private static List<List<Edge>> adj;

    public Graph(int V) {
        this.V = V;
        this.adj = new ArrayList<>(V);
        for (int i = 0; i < V; ++i) {
            this.adj.add(new ArrayList<>());
        }
    }

    // Método para agregar una arista al grafo
    public void addEdge(int u, int v, int weight) {
        this.adj.get(u).add(new Edge(v, weight));
    }

    // Método SPFA para encontrar caminos más cortos desde el origen
    public List<Integer> spfa(int source) {
        int[] distance = new int[V];
        Arrays.fill(distance, Integer.MAX_VALUE);
        distance[source] = 0;

        Queue<Integer> queue = new LinkedList<>();
        boolean[] inQueue = new boolean[V];
        queue.add(source);
        inQueue[source] = true;

        while (!queue.isEmpty()) {
            int u = queue.poll();
            inQueue[u] = false;

            for (Edge edge : adj.get(u)) {
                int v = edge.destination;
                int weight = edge.weight;

                if (distance[u] + weight < distance[v] || distance[v] == Integer.MAX_VALUE) {
                    distance[v] = distance[u] + weight;

                    if (!inQueue[v]) {
                        queue.add(v);
                        inQueue[v] = true;
                    }
                }
            }
        }

        List<Integer> shortestPaths = new ArrayList<>();
        for (int dist : distance) {
            shortestPaths.add(dist);
        }
        return shortestPaths;
    }

    // Método para obtener el total de aristas en el grafo
    public static int getTotalEdges() {
        int edges = 0;
        for (List<Edge> edgesList : adj) {
            edges += edgesList.size();
        }
        return edges;
    }

    // Clase interna para representar una arista en el grafo
    private static class Edge {
        int destination;
        int weight;

        public Edge(int destination, int weight) {
            this.destination = destination;
            this.weight = weight;
        }
    }

    // Método para verificar si el grafo ya contiene una arista entre dos nodos
    public boolean edgeContains(int u, int v) {
        for (Edge edge : adj.get(u)) {
            if (edge.destination == v) {
                return true;
            }
        }
        return false;
    }
}
