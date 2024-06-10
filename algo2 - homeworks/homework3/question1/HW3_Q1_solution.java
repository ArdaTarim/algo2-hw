package question1;

import java.util.Iterator;
import java.util.PriorityQueue;

//----------------------------------------------------- 
// Title: HW3_Q1_solution
// Author: Arda Tarım
// ID: 12859191938
// Section: 1 
// Assignment: 3
// Description: Solution class for the Assigment 3, question 1
//----------------------------------------------------- 
public class HW3_Q1_solution {

    public static void main(String[] args) {

        // Initializing the FileRead class
        FileRead file = new FileRead("question1/HW3_Q1.txt");
        
        // Initializing the valuefinder class with the FileRead object
        Valuefinder valuefinder = new Valuefinder(file);

        // Initializing the Edge Weighted Graph class with valuefinder object
        EdgeWeightedGraph Graph = new EdgeWeightedGraph(valuefinder);

        for (int i = 0; i < Graph.getV(); i++) {
            for (Edge e : Graph.adj(i)) {
                System.out.println(e.toString());
            }
        }

        // Creating a minimum spanning tress with Edge Weighted Graph
        MinimumSpanningTree MST = new MinimumSpanningTree(Graph);

        System.out.println("\nThe Minimum Spanning Tree Path");

        // Printing the paths of minimum spanning tree
        for (Edge e : MST.edges()) {
            System.out.println(e.toString());
        }

        System.out.println("\nThe Minimum Spanning Tree value= " + MST.getWeight());

    }
}

// -----------------------------------------------------
// Title: MinimumSpanningTree
// Author: Arda Tarım
// ID: 12859191938
// Section: 1
// Assignment: 3
// Description: Creates a minimum spanning tree from 
// an edge weighted graph using Lazy Prim's algorithm.
// -----------------------------------------------------
class MinimumSpanningTree {

    private double weight = 0;
    private boolean marked[];
    private IterableList<Edge> mst;
    private PriorityQueue<Edge> pq;

    // --------------------------------------------------------
    // Summary: Creates a minimum spanning tree from EdgeWeightedGraph.
    // Uses Lazy implementation of Prim's algorithm.
    // --------------------------------------------------------
    MinimumSpanningTree(EdgeWeightedGraph G){
        // Initializing minimum priority queue,
        // boolean marked array,
        // and IterableList
        pq = new PriorityQueue<Edge>();
        marked = new boolean[G.getV()+1];
        mst = new IterableList<Edge>();

        // start from the vertex 0
        // visit all the adjacent vertices
        visit(G, 0);

        while (!pq.isEmpty()) {
            Edge e = pq.poll();
            int v = e.either();
            int w = e.other(v);

            // continue if both are visited before
            if (marked[v] && marked[w]) {
                continue;
            }

            // add edge to mst
            mst.add(e);

            // visit and add to pq all the
            // adjacent vertices of v and w
            if (!marked[v]) {
                visit(G, v);
            }

            if(!marked[w]) {
                visit(G, w);
            }
        }
    } 

    // --------------------------------------------------------
    // Summary: Firstlt it marks the verice as marked. 
    // Visits all the unmarked adjacent nodes of the edge V.
    // Adds them to a minimum priority queue.
    // --------------------------------------------------------
    void visit(EdgeWeightedGraph G, int v) {
        marked[v] = true;
        for (Edge e : G.adj[v]) {
            if (!marked[e.other(v)]) {
                pq.add(e);
            }
        }
    }   

    // --------------------------------------------------------
    // Summary: Returns all of the edges creating the minimum spanning tree.
    // --------------------------------------------------------
    Iterable<Edge> edges() {
        return mst;
    }

    // --------------------------------------------------------
    // Summary: Returns the total weight value of the graph.
    // --------------------------------------------------------
    double getWeight() {
        for (Edge e : edges()) {
            weight = weight + e.getWeight();
        }
        return weight;
    }
    
}

// -----------------------------------------------------
// Title: EdgeWeightedGraph
// Author: Arda Tarım
// ID: 12859191938
// Section: 1
// Assignment: 3
// Description: Edge Weighted Graph data structure.
// Uses IterableList and Edge classes to create Graphs.
// -----------------------------------------------------
class EdgeWeightedGraph {

    private int V;
    private int E;
    IterableList<Edge>[] adj;

    // --------------------------------------------------------
    // Summary: Initializes a graph with V vertices and 0 edges.
    // --------------------------------------------------------
    EdgeWeightedGraph(int v) {
        this.V = v;
        this.E = 0;
        adj = new IterableList[V+1];
        for (int i = 0; i <= v; i++) {
            adj[v] = new IterableList<Edge>();
        }
    }

    // --------------------------------------------------------
    // Summary: Initializes a graph from Valuefinder class.
    // Adds all the edges with their weight values to the graph.
    // --------------------------------------------------------
    EdgeWeightedGraph(Valuefinder value) {
        this.V = value.getNext();
        this.E = value.getNext();

        System.out.println("V=" + V);
        System.out.println("E=" + E);

        adj = new IterableList[V+1];
        
        int n = this.E;
        for (int i = 0; i < n; i++) {
            int v = value.getNext();
            int w = value.getNext();
            int weight = value.getNext();
            Edge e = new Edge(v, w, weight);
            addEdge(e);
        }
    }

    // --------------------------------------------------------
    // Summary: Returns the number of vertices.
    // --------------------------------------------------------
    int getV() {   
        return V;
    }

    // --------------------------------------------------------
    // Summary: Returns the number of edges.
    // --------------------------------------------------------
    int getE() {
        return E;
    }

    // --------------------------------------------------------
    // Summary: Adds an edge to the graph.
    // Adds the edge "e" to vertice "v" and vertice "w" adjaceny list.
    // --------------------------------------------------------
    void addEdge(Edge e) {
        int v = e.either();
        int w = e.other(v);

        if (adj[v] == null) {
            adj[v] = new IterableList<Edge>();
        }
        adj[v].add(e);

        if (adj[w] == null) {
            adj[w] = new IterableList<Edge>();
        }
        Edge otherEdge = new Edge(w, v, e.getWeight());
        adj[w].add(otherEdge);

        E++;
    }

    // --------------------------------------------------------
    // Summary: Returns the adjacent edges of vertice v.
    // --------------------------------------------------------
    Iterable<Edge> adj(int v) {
        return adj[v];
    }    
}

// -----------------------------------------------------
// Title: Edge
// Author: Arda Tarım
// ID: 12859191938
// Section: 1
// Assignment: 3
// Description: Edge class for implementing Edge Weighted Graphs.
// Includes basic methods and implements the Comparable interface.
// -----------------------------------------------------
class Edge implements Comparable<Edge> {

    private int V;
    private int W;
    private double weight;

    // --------------------------------------------------------
    // Summary: Creates an edge object between 2 vertices v and w.
    // Edge object has a weight value.
    // --------------------------------------------------------
    Edge(int v, int w, double weight) {
        this.V = v;
        this.W = w;
        this.weight = weight;
    }

    // --------------------------------------------------------
    // Summary: Returns the weight value of the edge.
    // --------------------------------------------------------
    double getWeight() {
        return weight;
    }

    // --------------------------------------------------------
    // Summary: Returns one endpoint of the edge.
    // --------------------------------------------------------
    int either() {
        return V;
    }

    // --------------------------------------------------------
    // Summary: Returns the other endpoint of the edge.
    // Throws runtime exception if there is a problem.
    // -------------------------------------------------------- 
    int other(int v) {
        if (v == V ) {
            return W;
        } else if (v == W) {
            return V;
        } else {
            throw new RuntimeException("Edge error");
        }
    }

    // --------------------------------------------------------
    // Summary: Compares this edge to that edge.
    // Returns -1 if this edge's weight is smaller.
    // Returns 1 if this edge's weight is greater.
    // Returns 0 if they are equal.
    // --------------------------------------------------------
    @Override
    public int compareTo(Edge that) {
        if (this.weight < that.weight) {
            return -1;
        } else if (this.weight > that.weight) {
            return 1;
        } else {
            return 0;
        }
    }
    
    // --------------------------------------------------------
    // Summary: Returns the string representation of the edge.
    // --------------------------------------------------------
    public String toString() {
        String s = "";
        s = V + " " + W + " " + (int) weight;
        return s;
    }

}

// -----------------------------------------------------
// Title: IterableList
// Author: Arda Tarım
// ID: 12859191938
// Section: 1
// Assignment: 3
// Description: My IterableList class for graph implementation. Implements the
// Iterable interface.
// Works like a linkedlist/queue and it is iterable.
// -----------------------------------------------------
class IterableList<T> implements Iterable<T> {

    Node<T> head;
    Node<T> tail;

    public IterableList() {
        // --------------------------------------------------------
        // Summary: Creates an empty IterableList object.
        // --------------------------------------------------------
        this.head = null;
    }

    public IterableList(T a) {
        // --------------------------------------------------------
        // Summary: Creates an IterableList object with a Node.
        // --------------------------------------------------------
        this.head = new Node<T>(a);
    }

    public boolean isEmpty() {
        // --------------------------------------------------------
        // Summary: Returns true if the list is empty.
        // --------------------------------------------------------
        return head == null;
    }

    public void add(T a) {
        // --------------------------------------------------------
        // Summary: Adds a Node to the list.
        // --------------------------------------------------------
        Node<T> newNode = new Node<T>(a);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
    }

    public T poll() {
        // --------------------------------------------------------
        // Summary: Removes and returns the first Node of the list.
        // Postcondition: The first Node of the list is removed and returned.
        // --------------------------------------------------------
        if (head == null) {
            return null;
        }

        T data = head.data;
        head = head.next;
        return data;
    }

    @Override
    public Iterator<T> iterator() {
        // --------------------------------------------------------
        // Summary: Returns an Iterator for the IterableList.
        // Postcondition: An iterator for IterableList is returned.
        // --------------------------------------------------------
        Iterator<T> it = new Iterator<T>() {
            Node<T> current = head;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public T next() {
                if (current == null) {
                    return null;
                }
                Node<T> temp = current;
                current = current.next;
                return temp.data;
            }
        };
        return it;
    }

    public void printList() {
        // --------------------------------------------------------
        // Summary: Prints the IterableList to console.
        // Postcondition: The IterableList is printed.
        // --------------------------------------------------------
        Iterator<T> it = iterator();
        while (it.hasNext()) {
            System.out.print(it.next() + " ");
        }
    }

    public T peek() {
        // --------------------------------------------------------
        // Summary: Returns the head of the list.
        // --------------------------------------------------------
        if (head == null) {
            return null;
        }
        return head.data;

    }
}

// -----------------------------------------------------
// Title: Node
// Author: Arda Tarım
// ID: 12859191938
// Section: 1
// Assignment: 3
// Description: Node class for my IterableList class.
// -----------------------------------------------------
class Node<T> {
    T data;
    Node<T> next;

    public Node(T data) {
        // --------------------------------------------------------
        // Summary: Creates a Node object.
        // Postcondition: A Node object is created.
        // --------------------------------------------------------
        this.data = data;
        this.next = null;
    }
}
