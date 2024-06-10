import java.util.Iterator;

//-----------------------------------------------------
// Title: Directed Graph
// Author: Arda Tarım
// ID: 12859191938
// Section: 1
// Assignment: 2
// Description: Impleamentation of a directed graph with custom IterableList class and Input class.
//-----------------------------------------------------
public class DirectedGraph {
    private int V;
    private int E;
    private IterableList<Integer>[] adj;

    public DirectedGraph(Valuefinder value) {
        // --------------------------------------------------------
        // Summary: Creates a directed graph with vertices and edges from the data.
        // Precondition: take the Valuefinder class as parameter.
        // Postcondition: The graph has V vertices and E edges with connections between
        // vertices.
        // --------------------------------------------------------

        this.V = value.getV();
        this.E = value.getE();
        adj = new IterableList[V+1];

        for (int i = 0; i < E; i++) {
            int v = value.getNext();
            int w = value.getNext();

            String temp = v + " " + w;
            System.out.println(temp);
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
        // Summary: Adds en edge from v to w is added.
        // Precondition: v and w are vertices from the graph.
        // Postcondition: An edge from v to w is added to graph and number of edges
        // increased by 1.
        // --------------------------------------------------------
        if (v < 0 || v > V) {
            return;
        }

        if (adj[v] == null) {
            adj[v] = new IterableList<Integer>();
        }
  
        adj[v].add(w);

        //System.out.println("Edge (" + v + " -> " + w + ") added"); // for testing purposes
        //System.out.println("Number of edges: " + E);
    }

    public Iterable<Integer> adj(int v) {
        // --------------------------------------------------------
        // Summary: Returns the list of vertices directed from v as an Iterable.
        // Precondition: v is a vertex in the graph.
        // Postcondition: List of vertices that are directed from vertex v.
        // --------------------------------------------------------
        return adj[v];
    }

    public String toString() {
        // --------------------------------------------------------
        // Summary: Returns the string representation of the graph.
        // --------------------------------------------------------
        String s = V + " vertices, " + E + " edges\n";

        for (int v = 0; v <= V; v++) {
            s += v + ": ";
            for (int w : this.adj(v))
                s += w + "->";
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




//-----------------------------------------------------
// Title: IterableList
// Author: Arda Tarım
// ID: 12859191938
// Section: 1
// Assignment: 2
// Description: My IterableList class for graph implementation. Implements the Iterable interface. 
// Works like a linkedlist/queue and it is iterable.
//-----------------------------------------------------
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





//-----------------------------------------------------
// Title: Node
// Author: Arda Tarım
// ID: 12859191938
// Section: 1
// Assignment: 2
// Description: Node class for my IterableList class.
//-----------------------------------------------------
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
