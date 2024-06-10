//-----------------------------------------------------
// Title: HW2_solution
// Author: Arda Tarım
// ID: 12859191938
// Section: 1
// Assignment: 2
//-----------------------------------------------------

import java.util.Scanner;

public class HW2_solution {

    public static void main(String[] args) throws Exception {
        // Scanner for the starting point
        Scanner scanner = new Scanner(System.in);
        
        // Read the .txt file 
        FileRead file = new FileRead("q1test1.txt");

        // Get the values from the .txt file
        Valuefinder finder = new Valuefinder(file);

        // Create the directed graph with the values
        DirectedGraph graph = new DirectedGraph(finder);

        //System.out.println(graph.toString());

        // Get the starting point
        System.out.println();
        System.out.println("Start point:");
        int startVertex = scanner.nextInt();
        System.out.println(startVertex);
        String startVertexStr = Integer.toString(startVertex);
        scanner.close();

        // Printing the result
        String result;
        // For each adjacent vertex from the starting point
        for (int adjacent : graph.adj(startVertex)) {

            // Each line begins with startVertex
            result = startVertexStr;

            // Each line has the current adjacent
            result += " " + Integer.toString(adjacent);

            // For each adjacent there is second step
            for (int adjacent2 : graph.adj(adjacent)) {
                
                String print = result + " " + Integer.toString(adjacent2);
                // Printing the result
                System.out.println(print);
            }
        }
    }   
}