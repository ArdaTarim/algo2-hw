package question2;

import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Stack;

//----------------------------------------------------- 
// Title: HW3_Q2_solution
// Author: Arda Tarım
// ID: 12859191938
// Section: 1 
// Assignment: 3
// Description: Solution class for the Assigment 3, question 2
//----------------------------------------------------- 
public class HW3_Q2_solution {

    public static void main(String[] args) {

        // Initializing the FileRead class
        FileRead file = new FileRead("question2/HW3_Q2.txt");

        // Initializing the valuefinder class with the FileRead object
        Valuefinder valuefinder = new Valuefinder(file);

        // Initializing the Edge Weighted Graph class with valuefinder object
        EdgeWeightedDigraph Graph = new EdgeWeightedDigraph(valuefinder);

        // Initializing the Dijktra class
        Dijktras dijktras = new Dijktras(Graph, 0);

        // Calling Dijktra's Algorithm for each destination vertex
        System.out.println("\nThe result");
        for (int i = 1; i < 4; i++) {
            dijktras.pathTo(i);
        }
    }
}

// -----------------------------------------------------
// Title: DijktraShortestPath
// Author: Arda Tarım
// ID: 12859191938
// Section: 1
// Assignment: 3
// Description: Implements the Dijkstra's shortest path algorithm
// Shortest path from source to destination can be printed with pathTo method.
// -----------------------------------------------------
class Dijktras {

    private EdgeWeightedDigraph G;
    private DirectedEdge[] edgeTo;
    private double[] distTo;
    private PriorityQueue<Integer> pq;

    // --------------------------------------------------------
    // Summary: Initialized the Dijktra class, edgeTo and 
    // distTo arrays, and priority queue.
    // --------------------------------------------------------
    Dijktras(EdgeWeightedDigraph G, int source) {
        this.G = G;
        edgeTo = new DirectedEdge[G.getV() + 1];
        distTo = new double[G.getV() + 1];
        pq = new PriorityQueue<Integer>();

        for (int v = 0; v < G.getV() + 1; v++) {
            distTo[v] = Double.POSITIVE_INFINITY;
        }
        distTo[source] = 0.0;

        pq.add(source);
        while (!pq.isEmpty()) {
            int v = pq.poll();
            for (DirectedEdge e : G.adj(v)) {
                relax(e);
            }
        }
    }

    // --------------------------------------------------------
    // Summary: Relaxes the edge and updates the distance if there
    // is a shorter path to the edge.
    // --------------------------------------------------------
    private void relax(DirectedEdge e) {
        int v = e.from();
        int w = e.to();
        if (distTo[w] > distTo[v] + e.getWeight()) {
            distTo[w] = distTo[v] + e.getWeight();
            edgeTo[w] = e;
            if (pq.contains(w)) {
                pq.remove(w);
            }
            pq.add(w);
        }
    }

    // --------------------------------------------------------
    // Summary: Prints the path from source to the given sink vertex 
    // then prints the total weight.
    // --------------------------------------------------------
    void pathTo(int sink) {
        if (!hasPathTo(sink)) {
            return;
        }
    
        int sum = 0;
        Stack<Integer> path = new Stack<>();
        path.push(sink); 
    
        for (DirectedEdge e = edgeTo[sink]; e != null; e = edgeTo[e.from()]) {
            path.push(e.from());
            sum += e.getWeight();
        }
    
        
        while (!path.isEmpty()) {
            System.out.print(path.pop() + " ");
        }
        System.out.println(sum);
    }

    // --------------------------------------------------------
    // Summary: Returns the total distance to the vertex.
    // --------------------------------------------------------
    double distTo(int sink) {
        return distTo[sink];
    }

    // --------------------------------------------------------
    // Summary: Returns true if there is a path to the vertex.
    // --------------------------------------------------------
    boolean hasPathTo(int sink) {
        return distTo[sink] < Double.POSITIVE_INFINITY;
    }

}

// -----------------------------------------------------
// Title: EdgeWeightedDigraph
// Author: Arda Tarım
// ID: 12859191938
// Section: 1
// Assignment: 3
// Description: Edge Weighted Directed Graph data structure.
// Uses IterableList and DirectedEdge classes to create Directed Graphs.
// -----------------------------------------------------
class EdgeWeightedDigraph {

    private int V;
    private int E;
    IterableList<DirectedEdge>[] adj;

    // --------------------------------------------------------
    // Summary: Initializes a graph with V vertices and 0 edges.
    // --------------------------------------------------------
    EdgeWeightedDigraph(int v) {
        this.V = v;
        this.E = 0;
        adj = new IterableList[V + 1];
        for (int i = 0; i <= v; i++) {
            adj[v] = new IterableList<DirectedEdge>();
        }
    }

    // --------------------------------------------------------
    // Summary: Initializes a graph from Valuefinder class.
    // Adds all the edges with their weight values to the graph.
    // --------------------------------------------------------
    EdgeWeightedDigraph(Valuefinder value) {
        this.V = value.getNext();
        this.E = value.getNext();

        System.out.println("V=" + V);
        System.out.println("E=" + E);

        adj = new IterableList[V + 1];

        int n = this.E;
        for (int i = 0; i < n; i++) {
            int v = value.getNext();
            int w = value.getNext();
            int weight = value.getNext();
            DirectedEdge e = new DirectedEdge(v, w, weight);
            System.out.println(e.toString());
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
    // Adds the edge "e" from vertice "v" to vertice "w" with a weight.
    // --------------------------------------------------------
    void addEdge(DirectedEdge e) {
        int v = e.from();

        if (adj[v] == null) {
            adj[v] = new IterableList<DirectedEdge>();
        }
        adj[v].add(e);

        E++;
    }

    // --------------------------------------------------------
    // Summary: Returns the adjacent edges of vertice v.
    // --------------------------------------------------------
    Iterable<DirectedEdge> adj(int v) {
        return adj[v];
    }
}

// -----------------------------------------------------
// Title: DirectedEdge
// Author: Arda Tarım
// ID: 12859191938
// Section: 1
// Assignment: 3
// Description: DirectedEdge class for implementing Directed Edge Weighted
// Graphs.
// Includes basic methods and implements the Comparable interface.
// -----------------------------------------------------
class DirectedEdge implements Comparable<DirectedEdge> {

    private int V;
    private int W;
    private double weight;

    // --------------------------------------------------------
    // Summary: Creates an edge object between 2 vertices v and w.
    // Edge object has a weight value.
    // --------------------------------------------------------
    DirectedEdge(int v, int w, double weight) {
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
    // Summary: Returns the starting point of the edge.
    // --------------------------------------------------------
    int from() {
        return V;
    }

    // --------------------------------------------------------
    // Summary: Returns the ending point of the edge.
    // --------------------------------------------------------
    int to() {
        return W;
    }

    // --------------------------------------------------------
    // Summary: Compares this edge to that edge.
    // Returns -1 if this edge's weight is smaller.
    // Returns 1 if this edge's weight is greater.
    // Returns 0 if they are equal.
    // --------------------------------------------------------
    @Override
    public int compareTo(DirectedEdge that) {
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
