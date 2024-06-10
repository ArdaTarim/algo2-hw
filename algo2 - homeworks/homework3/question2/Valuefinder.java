package question2;
//-----------------------------------------------------
// Title: Valuefinder
// Author: Arda Tarım
// ID: 12859191938
// Section: 1
// Assignment: 3
// Description: Valuefinder class for reading integers from FileRead class.
// Basic wrapper around the FileRead class.
//-----------------------------------------------------
public class Valuefinder {

    FileRead file;

    Valuefinder(FileRead file){
        this.file = file;
        file.readFile();
    }

    public int getNext() {
        //-----------------------------------------------------
        // Summary: Returns the next integer from the file.
        //-----------------------------------------------------
        return file.readInt();
    }
    
}