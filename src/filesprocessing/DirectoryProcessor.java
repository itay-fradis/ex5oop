package filesprocessing;

/**
 * process the sourceDir and commandFile. responsible to execute the program.
 */
public class DirectoryProcessor {

    /**
     *
     * @param args
     */
    public static void main(String[] args){

        if (args.length != 2){
            System.err.println("Error: Wrong usage. Should receive 2 arguments");
            return;
        }
//        File[] files = sourceDir2files(args[0]);
//        String[] filterContent = filter2array(args[1]);
        Parser.parseCommandFile(args[1]);

    }
}
