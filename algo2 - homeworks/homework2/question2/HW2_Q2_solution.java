//-----------------------------------------------------
// Title: HW2_Q2_solution
// Author: Arda Tarım
// ID: 12859191938
// Section: 1
// Assignment: 2
//-----------------------------------------------------

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;

public class HW2_Q2_solution {

    static NeuralNetwork nn;

    // this array will hold the missing paths
    static String[] paths = new String[16];
    static int pathsIdx = 0;

    public static void main(String[] args) throws Exception {

        // Read the .txt file
        FileRead file = new FileRead("q2test3.txt");

        // Get the values from the .txt file
        Valuefinder value = new Valuefinder(file);

        // Create the directed graph with the values
        nn = new NeuralNetwork(value);

        System.out.println("\nResult is");

        // Caling the recursive function from the first vertex
        findConnections(0, "");

        // sorting the paths array using comparator
        // shorter paths are prioritized
        // then they are sorted lexicographically
        Arrays.sort(paths, Comparator.nullsLast(Comparator.comparing(String::length).thenComparing(String::compareTo)));

        // printing the paths inside the path array
        for (String path : paths) {
            if (path != null) {
                System.out.println(path);
            }
        }

    }

    static void findConnections(int vertex, String path) {
        // --------------------------------------------------------
        // Summary: Finds the missing paths in the neural network recursively
        // Precondition: vertex is a vertex in the neural network
        //               path is a string containing the path so far
        // Postcondition: if the final vertex is reached return nothing
        //                if not recursive function is called on adjacent vertices
        // --------------------------------------------------------
        if (vertex == 30) {
            return;
        }

        path += vertex + " ";

        boolean isEmpty = true;
        for (int adjacent : nn.adj(vertex)) {
            isEmpty = false;
            findConnections(adjacent, path);
        }

        if (isEmpty) {
            paths[pathsIdx] = path;
            pathsIdx++;
        }
    }
}

// -----------------------------------------------------
// Title: Neural Network
// Author: Arda Tarım
// ID: 12859191938
// Section: 1
// Assignment: 2
// Description: Basically a directed graph implementation using hashmap to store adjacency lists
// -----------------------------------------------------
class NeuralNetwork {
    private int numberOfVertices;
    private int numberOfEdges;
    private HashMap<Integer, IterableList<Integer>> map = new HashMap<>();

    public NeuralNetwork(Valuefinder value) {
        // --------------------------------------------------------
        // Summary: Creates a neural network (directed graph)
        // Precondition: value is the vertex-edge data from .txt file
        // Postcondition: A directed graph representing a neural network is created
        // --------------------------------------------------------

        this.numberOfVertices = value.getV();
        this.numberOfEdges = value.getE();

        for (int i = 0; i < numberOfEdges; i++) {
            int v = value.getNext();
            int w = value.getNext();

            String temp = v + " " + w;
            System.out.println(temp);
            addEdge(v, w);
        }
    }

    public int numberOfVertices() {
        // --------------------------------------------------------
        // Summary: Returns the number of vertices in the neural network.
        // --------------------------------------------------------
        return numberOfVertices;
    }

    public int numberOfEdges() {
        // --------------------------------------------------------
        // Summary: Returns the number of edges in the neural network.
        // --------------------------------------------------------
        return numberOfEdges;
    }

    public void addEdge(int v, int w) {
        // --------------------------------------------------------
        // Summary: Adds en edge from v directed to w.
        // Precondition: v and w are vertices in the graph.
        // Postcondition: An edge from v to w is added to neural network.
        // --------------------------------------------------------

        if (map.containsKey(v)) {
            map.get(v).add(w);
        } else {
            IterableList<Integer> listV = new IterableList<>();
            listV.add(w);
            map.put(v, listV);
        }

        if (!map.containsKey(w)) {
            IterableList<Integer> listW = new IterableList<>();
            map.put(w, listW);
        }
        // System.out.println("Edge (" + v + " -> " + w + ") added"); // for testing
        // purposes
        // System.out.println("Number of edges: " + E);
    }

    public Iterable<Integer> adj(int v) {
        // --------------------------------------------------------
        // Summary: Returns the list of vertices directed from v as an Iterable.
        // Precondition: v is a vertex in the graph.
        // Postcondition: List of vertices that are directed from vertex v.
        // --------------------------------------------------------
        return map.get(v);
    }

    public void printNN() {
        // --------------------------------------------------------
        // Summary: Returns the string representation of the graph.
        // --------------------------------------------------------
        System.out.println(numberOfVertices + " vertices, " + numberOfEdges + " edges\n");

        for (int vertex : map.keySet()) {
            for (int connection : map.get(vertex)) {
                System.out.println(vertex + "->" + connection);
            }
        }

    }
}

// -----------------------------------------------------
// Title: IterableList
// Author: Arda Tarım
// ID: 12859191938
// Section: 1
// Assignment: 2
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

    public void reverse() {
        // --------------------------------------------------------
        // Summary: Returns the reverse of the current list.
        // --------------------------------------------------------
        if (head == null || head.next == null) {
            return;
        }

        Node<T> prev = null;
        Node<T> current = head;

        while (current != null) {
            Node<T> nextNode = current.next;
            current.next = prev;
            prev = current;
            current = nextNode;
        }

        head = prev;
    }

    public int getSize() {
        // --------------------------------------------------------
        // Summary: Returns the size of the current list.
        // Postcondition: Size of the list is returned.
        // --------------------------------------------------------
        int size = 0;
        Node<T> current = head;
        while (current != null) {
            size++;
            current = current.next;
        }
        return size;
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
// Assignment: 2
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
