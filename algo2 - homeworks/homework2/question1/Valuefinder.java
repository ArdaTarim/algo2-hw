//-----------------------------------------------------
// Title: Valuefinder
// Author: Arda Tarım
// ID: 12859191938
// Section: 1
// Assignment: 2
// Description: Valuefinder class for reading integers from FileRead class.
// Basic wrapper around the FileRead class.
//-----------------------------------------------------
public class Valuefinder {

    FileRead file;

    Valuefinder(FileRead file){
        this.file = file;
        file.readFile();
    }

    public int getV() {
        //-----------------------------------------------------
        // Summary: Returns the number of vertices from the file.
        //-----------------------------------------------------
        int v = file.readInt();
        System.out.println("V="+v);
        return v;
    }

    public int getE() {
        //-----------------------------------------------------
        // Summary: Returns the number of edges from the file.
        //-----------------------------------------------------
        int e = file.readInt();
        System.out.println("E="+e);
        return e;
    }

    public int getNext() {
        //-----------------------------------------------------
        // Summary: Returns the next integer from the file.
        //-----------------------------------------------------
        return file.readInt();
    }
    
}