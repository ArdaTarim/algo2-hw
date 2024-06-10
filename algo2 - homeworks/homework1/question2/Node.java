//-----------------------------------------------------
// Title: Node
// Author: Arda Tarım
// ID: 12859191938
// Section: 1
// Assignment: 1
// Description: Node class for my IterableList class.
//-----------------------------------------------------

public class Node<T> {

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
