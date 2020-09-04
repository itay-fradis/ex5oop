package filesprocessing;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class Parser {

    /**
     *
     * @param fileName
     * @return
     */
    public static void parseCommandFile(String fileName){
        BufferedReader reader = null;
        try{
            reader = new BufferedReader(new FileReader(fileName));
            String line = reader.readLine();
            while (line != null){

                line = reader.readLine();
            }

        } catch (FileNotFoundException e) {
            System.err.println("ERROR: The file " + fileName + "is not found. ");
        } catch (IOException e){
            System.err.println("ERROR: An IO error occurred.");
        } finally {
            try{  //closing file
                if (reader != null)
                    reader.close();
            } catch (IOException e) {
                System.err.println("ERROR: Could not close the file " + fileName + ".");
            }
        }
    }

    /**
     *
     * @param fileName
     * @return
     */
    public static File[] sourceDir2files(String fileName){
        File sourceDir = new File(fileName);
        File[] result = sourceDir.listFiles();
        if (result == null || result.length == 0) {
            System.err.println("ERROR: No files in sourcedir");
            return null;
        }
        return result;
    }
}
