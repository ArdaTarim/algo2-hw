//-----------------------------------------------------
// Title: IterableList
// Author: Arda Tarım
// ID: 12859191938
// Section: 1
// Assignment: 1
// Description: My IterableList class for graph implementation. Implements the Iterable interface. 
// Works like a linkedlist/queue but it is iterable.
//-----------------------------------------------------
import java.util.Iterator;

public class IterableList<T> implements Iterable<T> {

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

    public boolean contains(int i) {
        // --------------------------------------------------------
        // Summary: Checks if an integer exists in the list.
        // Postcondition: True is returned if integer exists, false otherwise.
        // --------------------------------------------------------
        Node<Integer> current = (Node<Integer>) head;
        while (current != null) {
            if(current.data == i) {
                return true;
            }
            current = current.next;
        }
        return false;
    }
}
