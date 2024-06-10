//-----------------------------------------------------
// Title: Question 1
// Author: Arda Tarım
// ID: 12859191938
// Section: 1
// Assignment: 1
//-----------------------------------------------------
public class Question1 {

    public static void main(String[] args) throws Exception {
        // ---------------------------------------------------------------------
        // These two lines are for setting up a graph from a .txt file.
        // filePath can be used as a parameter for the Input class.
        //
        //String workingDirectory = System.getProperty("user.dir");
        //String filePath = workingDirectory + "/test.txt";
        // ---------------------------------------------------------------------

        // Initialize the Input class to read data
        Input data = new Input("tests/test-q12.txt");

        // Get the important variables from the Input
        int noVertices = data.readInt();
        int noEdges = data.readInt();
        int stateChangeTime = data.readInt();
        int travelTime = data.readInt();

        // Initalize the graph and add the edges
        if(noVertices == 1) {
            System.out.println("1\n1\n0");
            System.exit(0);
        }
        Graph G = new Graph(noVertices);

        for (int i = 0; i < noEdges; i++) {
            G.addEdge(data.readInt(), data.readInt());
        }

        // Get the start and end vertices from the Input
        int startVertex = data.readInt();
        int endVertex = data.readInt();

        // Initialize the BreadthFirstSearch class
        BreadthFirstSearch bfs = new BreadthFirstSearch(G);

        // Find the shortest path from the start to the end vertex
        IterableList<Integer> path = bfs.findPath(startVertex, endVertex);

        // Printing the path
        String travelPath = "";
        for (Integer integer : path) {
            travelPath += integer + " ";
        }

        // Calculating the total time
        int numberOfTravels = path.getSize() - 1;
        int length = numberOfTravels * travelTime + 100;

        // Creating 2 arrays to calculate time
        int[] travels = new int[length];
        int[] changes = new int[length];

        // filling the array with 1's and 0's to represent state changes
        // 0 means ports are running and 1 means otherwise
        // we can only tavel when the ports are running
        // if the ports change every 3 minutes the array is:
        // 000111000111000111000111
        int count = 0;
        boolean isRunning = true;
        for (int i = 0; i < length; i++) {
            if (isRunning) {
                changes[i] = 0;
                count++;
                if (count == stateChangeTime) {
                    isRunning = false;
                    count = 0;
                }
            } else {
                changes[i] = 1;
                count++;
                if (count == stateChangeTime) {
                    isRunning = true;
                    count = 0;
                }
            }
        }  

        // using another array to find when we can travel
        // for example if the travel time is 5 minutes
        // 000111000111000111000111 -> state change array
        // 00000-00000 
        // value of the position variable is the total time
        int position = 0;
        while (numberOfTravels > 0) {
            if (changes[position] == 0) {
                for (int i = 0; i < travelTime; i++) {
                    travels[position + i] = 1;
                }
                position += travelTime;
                numberOfTravels--;
            } else {
                position++;
            }
        }

        // Print the results
        System.out.println(path.getSize());
        System.out.println(travelPath);
        System.out.println(position);
    }
}
