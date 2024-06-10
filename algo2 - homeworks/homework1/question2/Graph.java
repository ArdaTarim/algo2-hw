//-----------------------------------------------------
// Title: Graph
// Author: Arda Tarım
// ID: 12859191938
// Section: 1
// Assignment: 1
// Description: Impleamentation of graph with custom IterableList class and Input class.
//-----------------------------------------------------

public class Graph {
    private int V;
    private int E;
    private IterableList<Integer>[] adj;
    private boolean fromInput;

    public Graph(int V) {
        // --------------------------------------------------------
        // Summary: Creates a graph with V vertices and no edges.
        // Precondition: V is the number of vertices in the graph.
        // Postcondition: The graph has V vertices and no edges.
        // --------------------------------------------------------
        fromInput = false;

        this.V = V;
        this.E = 0;
        adj = new IterableList[V+1];
        for (int i = 0; i < V; i++) {
            adj[i] = new IterableList<Integer>();
        }
    }

    public Graph(Input in) {
        // --------------------------------------------------------
        // Summary: Creates a graph with vertices and edges from the data.
        // Precondition: in is an input stream ( .txt file).
        // Postcondition: The graph has V vertices and E edges with connections between
        // vertices.
        // --------------------------------------------------------
        fromInput = true;

        this.V = in.readInt();
        this.E = in.readInt();
        adj = new IterableList[V+1];
        for (int i = 0; i < E; i++) {
            int v = in.readInt();
            int w = in.readInt();
            addEdge(v, w);
        }
    }

    public int numberOfVertices() {
        // --------------------------------------------------------
        // Summary: Returns the number of vertices in the graph.
        // --------------------------------------------------------
        return V;
    }

    public int numberOfEdges() {
        // --------------------------------------------------------
        // Summary: Returns the number of edges in the graph.
        // --------------------------------------------------------
        return E;
    }

    public void addEdge(int v, int w) {
        // --------------------------------------------------------
        // Summary: Adds en edge between the vertices v and w.
        // Precondition: v and w are vertices in the graph.
        // Postcondition: An edge between v and w is added to graph and number of edges
        // increased by 1.
        // --------------------------------------------------------
        if (v < 0 || v > V) {
            return;
        }
        if (adj[v] == null) {
            adj[v] = new IterableList<Integer>();
        }
        if (adj[w] == null) {
            adj[w] = new IterableList<Integer>();
        }

        adj[v].add(w);
        adj[w].add(v);

        if (!fromInput) {
            E++;
        }

        //System.out.println("Edge (" + v + ", " + w + ") added"); // for testing purposes
        //System.out.println("Number of edges: " + E);
    }

    public Iterable<Integer> adj(int v) {
        // --------------------------------------------------------
        // Summary: Returns the list of vertices that are adjacent to v as an Iterable.
        // Precondition: v is a vertex in the graph.
        // Postcondition: List of vertices that are adjacent to the vertex.
        // --------------------------------------------------------
        return adj[v];
    }

    public String toString() {
        // --------------------------------------------------------
        // Summary: Returns the string representation of the graph.
        // --------------------------------------------------------
        String s = V + " vertices, " + E + " edges\n";
        for (int v = 1; v <= V; v++) {
            s += v + ": ";
            for (int w : this.adj(v))
                s += w + " ";
            s += "\n";
        }
        return s;
    }

    public String hash() {
        // --------------------------------------------------------
        // Summary: Returns the hash value of the graph.
        // --------------------------------------------------------
        int prime = 17;
        double hash = V * Math.pow(prime, 1);
        hash = hash + E * Math.pow(prime, 3);
        return String.valueOf(hash);

    }

}