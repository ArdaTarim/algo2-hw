import java.util.Arrays;

public class Question2 {

    public static void main(String args[]) throws Exception {
        Input data = new Input("tests/test-q2-2.txt");

        int noVertices = data.readInt();
        int noEdges = data.readInt();

        if(noVertices == 1) {
            System.out.println("1");
            System.exit(0);
        }
        Graph G = new Graph(noVertices);
        for (int i = 0; i < noEdges; i++) {
            G.addEdge(data.readInt(), data.readInt());
        }

        int startVertex = data.readInt();
        int includeVertex = data.readInt();

        BreadthFirstSearch bfs = new BreadthFirstSearch(G);

        IterableList<Integer> firstPath = bfs.findPath(startVertex, includeVertex);
        //for (Integer integer : firstPath) {System.out.println(integer);} // debug line

        IterableList<Integer> secondPath = bfs.findPathExclude(firstPath, includeVertex, startVertex);
        //for (Integer integer : secondPath) {System.out.println(integer);} // debug line
        
        IterableList<Integer> sortedPath = new IterableList<>();

        for (Integer integer : firstPath) {
            if (!sortedPath.contains(integer)){
                sortedPath.add(integer);
            }
        }

        for (Integer integer : secondPath) {
            if (!sortedPath.contains(integer)){
                sortedPath.add(integer);
            }
        }

        int j = 0;
        int[] arr = new int[sortedPath.getSize()];
        for (int vertex : sortedPath) {
            arr[j] = vertex;  
            j++; 
        }

        Arrays.sort(arr);

        for (int i : arr) {
            System.out.print(i + " ");
        }
    }
}

