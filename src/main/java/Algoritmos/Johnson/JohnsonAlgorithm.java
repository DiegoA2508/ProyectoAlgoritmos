package Algoritmos.Johnson;

import java.util.*;

public class JohnsonAlgorithm {
    private static final int INFINITY = Integer.MAX_VALUE;

    static class Graph {
        private final int V;
        private final List<List<Edge>> adj;

        public Graph(int V) {
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

        public List<List<Integer>> johnson() {
            List<List<Integer>> allPairsShortestPaths = new ArrayList<>();
            for (int i = 0; i < V; i++) {
                List<Integer> shortestPaths = dijkstraWithReWeighting(this, i);
                allPairsShortestPaths.add(shortestPaths);
            }
            return allPairsShortestPaths;
        }

        private List<Integer> dijkstraWithReWeighting(Graph graph, int start) {
            List<Integer> reWeightedDistances = dijkstra(graph, start);

            Graph reWeightedGraph = createReWeightedGraph(start, reWeightedDistances);

            List<Integer> shortestPaths = dijkstra(reWeightedGraph, start);

            for (int i = 0; i < V; i++) {
                if (i != start) {
                    shortestPaths.set(i, shortestPaths.get(i) - reWeightedDistances.get(start));
                }
            }

            return shortestPaths;
        }

        private Graph createReWeightedGraph(int start, List<Integer> reWeightedDistances) {
            Graph reWeightedGraph = new Graph(V + 1);
            for (int i = 0; i < V; i++) {
                for (Edge edge : adj.get(i)) {
                    int newWeight = edge.weight + reWeightedDistances.get(i) - reWeightedDistances.get(edge.destination);
                    reWeightedGraph.addEdge(i, edge.destination, newWeight);
                }
            }
            return reWeightedGraph;
        }

        private List<Integer> dijkstra(Graph graph, int start) {
            PriorityQueue<Node> minHeap = new PriorityQueue<>(Comparator.comparingInt(a -> a.distance));
            List<Integer> distances = new ArrayList<>(Collections.nCopies(graph.V, INFINITY));

            minHeap.add(new Node(start, 0));

            while (!minHeap.isEmpty()) {
                Node current = minHeap.poll();

                if (current.distance == INFINITY) {
                    break;
                }

                for (Edge neighbor : graph.adj.get(current.vertex)) {
                    int newDistance = current.distance + neighbor.weight;
                    if (newDistance < distances.get(neighbor.destination)) {
                        distances.set(neighbor.destination, newDistance);
                        minHeap.add(new Node(neighbor.destination, newDistance));
                    }
                }
            }

            return distances;
        }

        private static class Node {
            private final int vertex;
            private final int distance;

            public Node(int vertex, int distance) {
                this.vertex = vertex;
                this.distance = distance;
            }
        }

        private static class Edge {
            private final int destination;
            private final int weight;

            public Edge(int destination, int weight) {
                this.destination = destination;
                this.weight = weight;
            }
        }
    }

    public static void main(String[] args) {
        int numNodes = 1000;
        int numEdges = 2000;
        Graph graph = generateRandomGraph(numNodes, numEdges);

        printGraphInfo(graph);

        long startTime = System.nanoTime();
        List<List<Integer>> allPairsShortestPaths = graph.johnson();
        long endTime = System.nanoTime();

        double tiempoEnMilisegundos = (endTime - startTime) / 1_000_000.0;

        System.out.println("Execution Time: " + tiempoEnMilisegundos + " milliseconds");
    }

    private static Graph generateRandomGraph(int numNodes, int numEdges) {
        Graph graph = new Graph(numNodes);
        Random random = new Random();

        while (numEdges > 0) {
            int source = random.nextInt(numNodes);
            int destination;
            do {
                destination = random.nextInt(numNodes);
            } while (source == destination);

            int weight = random.nextInt(10) + 1;
            graph.addEdge(source, destination, weight);
            numEdges--;
        }

        return graph;
    }

    private static void printGraphInfo(Graph graph) {
        System.out.println("Número de nodos: " + graph.V);
        System.out.println("Número de aristas: " + countEdges(graph));
    }

    private static int countEdges(Graph graph) {
        int count = 0;
        for (List<Graph.Edge> edges : graph.adj) {
            count += edges.size();
        }
        return count;
    }
}
