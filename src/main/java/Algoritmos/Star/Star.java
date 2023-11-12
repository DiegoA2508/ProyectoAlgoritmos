package Algoritmos.Star;

import java.util.*;

public class Star {
    public static void main(String[] args) {
        int numNodes = 10000;
        int numEdges = 20000;

        Grafo grafo = generateGraph(numNodes, numEdges);

        int startNode = 0;
        int goalNode = numNodes - 1;

        System.out.println("Número de nodos: " + numNodes);
        System.out.println("Número de aristas: " + grafo.getTotalEdges());

        long startTime = System.nanoTime();
        List<Integer> path = grafo.aStar(startNode, goalNode);
        long endTime = System.nanoTime();

        double tiempoEnMilisegundos = (endTime - startTime) / 1_000_000.0;

        // System.out.println("Path: " + path);
        System.out.println("Execution Time: " + tiempoEnMilisegundos + " milliseconds");
    }


    private static Grafo generateGraph(int numNodes, int numEdges) {
        Grafo graph = new Grafo(numNodes);
        Random random = new Random();

        for (int i = 0; i < numEdges; i++) {
            int source = random.nextInt(numNodes);
            int destination = random.nextInt(numNodes);
            int weight = random.nextInt(10) + 1; // Peso aleatorio entre 1 y 10
            graph.addEdge(source, destination, weight);
        }

        return graph;
    }

}

class Grafo {
    private final int V;
    private final List<List<Edge>> adj;

    public Grafo(int V) {
        this.V = V;
        adj = new ArrayList<>(V);
        for (int i = 0; i < V; i++) {
            adj.add(new ArrayList<>());
        }
    }

    public void addEdge(int source, int destination, int weight) {
        Edge edge = new Edge(destination, weight);
        adj.get(source).add(edge);
    }

    public List<Integer> aStar(int start, int goal) {
        PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingInt(a -> a.fScore));
        openSet.add(new Node(start, 0, heuristic(start, goal)));

        Map<Integer, Integer> cameFrom = new HashMap<>();
        Map<Integer, Integer> gScore = new HashMap<>();
        gScore.put(start, 0);

        while (!openSet.isEmpty()) {
            int current = openSet.poll().vertex;

            if (current == goal) {
                return reconstructPath(cameFrom, current);
            }

            for (Edge neighbor : adj.get(current)) {
                int tentativeGScore = gScore.get(current) + neighbor.weight;
                if (tentativeGScore < gScore.getOrDefault(neighbor.vertex, Integer.MAX_VALUE)) {
                    cameFrom.put(neighbor.vertex, current);
                    gScore.put(neighbor.vertex, tentativeGScore);

                    int fScore = tentativeGScore + heuristic(neighbor.vertex, goal);
                    openSet.add(new Node(neighbor.vertex, tentativeGScore, fScore));
                }
            }
        }

        return Collections.emptyList();
    }

    public int getTotalEdges() {
        int edges = 0;
        for (List<Edge> edgesList : adj) {
            edges += edgesList.size();
        }
        return edges;
    }private int heuristic(int current, int goal) {
        return Math.abs(current - goal);
    }

    private List<Integer> reconstructPath(Map<Integer, Integer> cameFrom, int current) {
        List<Integer> path = new ArrayList<>();
        path.add(current);
        while (cameFrom.containsKey(current)) {
            current = cameFrom.get(current);
            path.add(current);
        }
        Collections.reverse(path);
        return path;
    }

    private static class Node {
        private final int vertex;
        private final int gScore;
        private final int fScore;

        public Node(int vertex, int gScore, int fScore) {
            this.vertex = vertex;
            this.gScore = gScore;
            this.fScore = fScore;
        }
    }

    private static class Edge {
        private final int vertex;
        private final int weight;

        public Edge(int vertex, int weight) {
            this.vertex = vertex;
            this.weight = weight;
        }
    }
}
