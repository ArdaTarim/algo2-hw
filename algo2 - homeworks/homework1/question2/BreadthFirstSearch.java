//-----------------------------------------------------
// Title: BreadthFirstSearch
// Author: Arda Tarım
// ID: 12859191938
// Section: 1
// Assignment: 1
// Description: Breadth first search for the graph and additional
// method to find the shortest path between two vertices.
//-----------------------------------------------------

public class BreadthFirstSearch {

    Graph Graph;
    int V;

    IterableList<Integer> q = new IterableList<>();

    public BreadthFirstSearch(Graph G) {
        // --------------------------------------------------------
        // Summary: Creates a BreadthFirstSearch object for the graph.
        // --------------------------------------------------------
        this.Graph = G;
        this.V = G.numberOfVertices();
    }

    public IterableList<Integer> findPath(int startVertex, int endVertex) {
        // --------------------------------------------------------
        // Summary: Finds the shortest path between the startVertex and endVertex.
        // Postcondition: Returns an IterableList containing the shortest path.
        // --------------------------------------------------------

        boolean[] marked = new boolean[V+1];
        int[] edgeTo = new int[V+1];
        int[] dist = new int[V+1];

        IterableList<Integer> bfsOrder = new IterableList<>();

        marked[startVertex] = true;
        edgeTo[startVertex] = -1;
        dist[startVertex] = 0;
        q.add(startVertex);

        while (!q.isEmpty()) {
            int currentNode = q.poll();
            bfsOrder.add(currentNode);
            // System.out.println(currentNode + " "); // for testing purposes

            for (int adj : Graph.adj(currentNode)) {
                if (!marked[adj]) {
                    marked[adj] = true;
                    edgeTo[adj] = currentNode;
                    dist[adj] = dist[currentNode] + 1;
                    q.add(adj);
                }
            }
        }

        IterableList<Integer> path = new IterableList<>();

        int current = endVertex;
        path.add(endVertex);
        while (current != startVertex) {
            current = edgeTo[current];
            path.add(current);
        }

        path.reverse();
        return path;
    }

    public <T> IterableList<Integer> findPathExclude(IterableList<T> exclude, int startVertex, int includeVertex) {
        // --------------------------------------------------------
        // Summary: Finds the shortest path between the startVertex and includeVertex
        // but excluded vertices cant be visited;
        // Postcondition: Returns an IterableList containing the shortest path.
        // --------------------------------------------------------

        Graph excludedGraph = new Graph(this.V);

        // adding unvisited vertices to the graph
        for (int i = 1; i <= V; i++) {
            if (!exclude.contains(i)) {
                for (int adj : this.Graph.adj(i)) {
                    excludedGraph.addEdge(i, adj);
                }
            }
        }

        boolean[] marked = new boolean[V+1];
        int[] edgeTo = new int[V+1];
        int[] dist = new int[V+1];

        IterableList<Integer> bfsOrder = new IterableList<>();

        marked[startVertex] = true;
        edgeTo[startVertex] = -1;
        dist[startVertex] = 0;
        q.add(startVertex);

        while (!q.isEmpty()) {
            int currentNode = q.poll();
            bfsOrder.add(currentNode);
            // System.out.println(currentNode + " "); // for testing purposes

            for (int adj : excludedGraph.adj(currentNode)) {
                if (!marked[adj]) {
                    marked[adj] = true;
                    edgeTo[adj] = currentNode;
                    dist[adj] = dist[currentNode] + 1;
                    q.add(adj);
                }
            }
        }

        IterableList<Integer> path = new IterableList<>();

        int current = includeVertex;
        path.add(includeVertex);
        while (current != startVertex) {
            current = edgeTo[current];
            path.add(current);
        }

        path.reverse();
        return path;
    }
}