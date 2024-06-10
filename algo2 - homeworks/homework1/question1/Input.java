//-----------------------------------------------------
// Title: Input
// Author: Arda Tarım
// ID: 12859191938
// Section: 1
// Assignment: 1
// Description: Input class to create graphs from Input.txt files or input from the console.
//-----------------------------------------------------

import java.io.File;
import java.util.Scanner;

public class Input {

    IterableList<Integer> q = new IterableList<Integer>();

    public Input(String filePath) throws Exception {
        // --------------------------------------------------------
        // Summary: Reads the integers from the txt file and adds them a queue.
        // Precondition: filePath is the path of the file.
        // Postcondition: The integers are added to the queue.
        // --------------------------------------------------------
        try {
            Scanner scanner = new Scanner(new File(filePath));
            while (scanner.hasNextInt()) {
                int number = scanner.nextInt();
                q.add(number);
                //System.out.print(number + " "); // this line is for testing purposes
            }
            scanner.close();
        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
        }
    }

    public Input() throws Exception {
        // --------------------------------------------------------
        // Summary: Reads the integers from the console and adds them a queue.
        // Postcondition: The integers are added to the queue.
        // --------------------------------------------------------
        try {
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNextInt()) {
                int number = scanner.nextInt();
                q.add(number);
                //System.out.print(number + " "); // this line is for testing purposes
            }
            System.out.println();
            scanner.close();
        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
        }
    }

    public int readInt() {
        // --------------------------------------------------------
        // Summary: Returns the integer from the queue.
        // Postcondition: The integer is returned.
        // --------------------------------------------------------
        if (q.peek() == null) {
            return -1;
        } else {
            return q.poll();
        }
    }

    public void printInput() {
        // --------------------------------------------------------
        // Summary: Prints the queue.
        // Postcondition: The queue is printed to the console.
        // --------------------------------------------------------
        while (!q.isEmpty()) {
            System.out.print(q.poll() + " ");
        }
    }

}
