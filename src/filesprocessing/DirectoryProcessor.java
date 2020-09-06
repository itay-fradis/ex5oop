package filesprocessing;



import java.io.*;

/**
 * process the sourceDir and commandFile. responsible to execute the program.
 */
public class DirectoryProcessor {

    /** error message - wrong num of args */
    private static final String WRONG_NUM_OF_ARGS = "ERROR: Wrong usage. Should receive 2 arguments ";

    /** error message - IO error */
    private static final String IO_ERROR_MSG = "ERROR: An IO error occurred.";

    /**
     * responsible for the main flow of the program.
     * @param args given arguments - sourceDir and Command file directories.
     */
    public static void main(String[] args){

        if (args.length != 2) {
            System.err.println(WRONG_NUM_OF_ARGS);
            return;
        }
        try{
            Section[] sectionArray = Parser.parseCommandFile(args[1]);
        }
        catch (IOException error){
            System.err.println(IO_ERROR_MSG);
        }
        catch (BadFormatCommandFileException error){
            System.err.println(error.getMessage());
        }
        File[] directoryFiles = Parser.sourceDir2files(args[0]);


    }
}
