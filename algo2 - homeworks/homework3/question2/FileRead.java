package question2;

import java.io.File;
import java.util.Scanner;

//-----------------------------------------------------
// Title: FileRead
// Author: Arda Tarım
// ID: 12859191938
// Section: 1
// Assignment: 3
// Description: File read class to create graphs from .txt files.
//-----------------------------------------------------
public class FileRead {
    IterableList<Integer> q = new IterableList<Integer>();
    String filePath;

    public FileRead(String filePath) {
        this.filePath = filePath;
    }

    public void readFile() {
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
                // System.out.print(number + " "); // this line is for testing purposes
            }
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
