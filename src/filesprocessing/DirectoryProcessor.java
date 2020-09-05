package filesprocessing;


import com.sun.tools.javac.code.Scope;

import java.io.*;

/**
 * process the sourceDir and commandFile. responsible to execute the program.
 */
public class DirectoryProcessor {

    /**
     *
     * @param args
     */
    public static void main(String[] args){

        if (args.length != 2) {
            System.err.println("Error: Wrong usage. Should receive 2 arguments");
            return;
        }
        try{
            Section[] sectionArray = Parser.parseCommandFile(args[1]);
        }
        catch (IOException e){
            System.err.println("ERROR: An IO error occured.");
            return;
        }




    }
}
