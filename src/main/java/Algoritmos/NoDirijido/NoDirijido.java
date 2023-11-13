package Algoritmos.NoDirijido;

import java.util.*;

class NoDirigido {

    public static void main(String[] args) {
        int numNodes = 15000;
        int numEdges = 30000;

        // Generar un grafo no dirigido aleatorio
        Graph g = generateRandomGraph(numNodes, numEdges);

        System.out.println("Número de nodos: " + numNodes);
        System.out.println("Número de aristas: " + numEdges);

        long startTime = System.nanoTime();
        g.findMinimumCycle();
        long endTime = System.nanoTime();

        double executionTime = (endTime - startTime) / 1e6; // Tiempo de ejecución en milisegundos
        System.out.println("Tiempo de ejecución: " + executionTime + " ms");
    }

    // Método para generar un grafo no dirigido aleatorio
    public static Graph generateRandomGraph(int numNodes, int numEdges) {
        Random random = new Random();
        Graph graph = new Graph(numNodes);

        int generatedEdges = 0;

        while (generatedEdges < numEdges) {
            int u = random.nextInt(numNodes);
            int v = random.nextInt(numNodes);
            int weight = random.nextInt(20); // Peso aleatorio entre 0 y 19

            // Asegurar que la arista no exista ya
            if (u != v && !graph.edge.contains(new Edge(u, v, weight))) {
                graph.addEdge(u, v, weight);
                generatedEdges++;
            }
        }

        return graph;
    }
}

class Graph {
    int V;
    List<Tuple<Integer, Integer>>[] adj;
    List<Edge> edge;

    public Graph(int V) {
        this.V = V;
        adj = new List[V];
        for (int i = 0; i < V; i++) {
            adj[i] = new ArrayList<>();
        }
        edge = new ArrayList<>();
    }

    // Método para agregar una arista al grafo
    public void addEdge(int u, int v, int w) {
        adj[u].add(new Tuple<>(v, w));
        adj[v].add(new Tuple<>(u, w));
        Edge e = new Edge(u, v, w);
        e.u = u;
        e.v = v;
        e.weight = w;
        edge.add(e);
    }

    // Método para eliminar una arista del grafo
    public void removeEdge(int u, int v, int w) {
        adj[u].remove(new Tuple<>(v, w));
        adj[v].remove(new Tuple<>(u, w));
    }

    // Método para encontrar el camino más corto entre dos nodos en el grafo
    public int shortestPath(int u, int v) {
        SortedSet<Tuple<Integer, Integer>> setds = new TreeSet<>();
        List<Integer> dist = new ArrayList<>();
        for (int i = 0; i < V; i++) {
            dist.add(Integer.MAX_VALUE);
        }
        setds.add(new Tuple<>(0, u));
        dist.set(u, 0);

        while (!setds.isEmpty()) {
            Tuple<Integer, Integer> tmp = setds.first();
            setds.remove(tmp);
            int uu = tmp.second;
            for (Tuple<Integer, Integer> i : adj[uu]) {
                int vv = i.first;
                int weight = i.second;
                if (dist.get(vv) > dist.get(uu) + weight) {
                    if (dist.get(vv) != Integer.MAX_VALUE) {
                        setds.remove(new Tuple<>(dist.get(vv), vv));
                    }
                    dist.set(vv, dist.get(uu) + weight);
                    setds.add(new Tuple<>(dist.get(vv), vv));
                }
            }
        }
        return dist.get(v);
    }

    // Método para encontrar el ciclo ponderado más corto en el grafo
    public int findMinimumCycle() {
        int minCycle = Integer.MAX_VALUE;
        int E = edge.size();
        for (int i = 0; i < E; i++) {
            Edge e = edge.get(i);
            removeEdge(e.u, e.v, e.weight);
            int distance = shortestPath(e.u, e.v);
            minCycle = Math.min(minCycle, distance + e.weight) + 4;
            addEdge(e.u, e.v, e.weight);
        }
        return minCycle;
    }
}

// Clase Tuple para almacenar pares de enteros
class Tuple<T, U> implements Comparable<Tuple<T, U>> {
    public final T first;
    public final U second;

    public Tuple(T first, U second) {
        this.first = first;
        this.second = second;
    }

    // Método para comparar dos tuplas
    public int compareTo(Tuple<T, U> other) {
        if (this.first.equals(other.first)) {
            return this.second.toString().compareTo(other.second.toString());
        } else {
            return this.first.toString().compareTo(other.first.toString());
        }
    }

    // Método para representar la tupla como cadena
    public String toString() {
        return "(" + first.toString() + ", " + second.toString() + ")";
    }
}

// Clase Edge para representar una arista en el grafo
class Edge {
    public int u;
    public int v;
    public int weight;

    public Edge(int u, int v, int weight) {
        this.u = u;
        this.v = v;
        this.weight = weight;
    }

    // Métodos equals y hashCode para comparar y hashear aristas
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Edge edge = (Edge) obj;
        return u == edge.u && v == edge.v && weight == edge.weight;
    }

    @Override
    public int hashCode() {
        return Objects.hash(u, v, weight);
    }
}
