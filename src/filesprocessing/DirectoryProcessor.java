package filesprocessing;

import filesprocessing.orders.MyMergeSort;
import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

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
        Section[] sectionArray;
        try{
            sectionArray = Parser.parseCommandFile(args[1]);
        }
        catch (IOException error){
            System.err.println(IO_ERROR_MSG);
            return;
        }
        catch (BadFormatCommandFileException error){
            System.err.println(error.getMessage());
            return;
        }
        File[] directoryFiles = Parser.sourceDir2files(args[0]);
        printFilteredAndOrderedFiles(sectionArray, directoryFiles);
    }

    /**
     * print all the filtered files by the given order
     * @param sectionArray array of sections
     * @param directoryFiles all files to be filtered and printed.
     */
    private static void printFilteredAndOrderedFiles(Section[] sectionArray, File[] directoryFiles) {
        for (Section section: sectionArray){
            Predicate<File> filter = section.createFilter();
            Comparator<File> order = section.createOrder();
            File[] filteredFiles = createFilteredArray(directoryFiles, filter);
            MyMergeSort.getInstance().sort(filteredFiles, order);
            for (File file: filteredFiles){
                System.out.println(file.getName());
            }
        }
    }

    /**
     * @param directoryFiles all files
     * @param filter given filter
     * @return array of filtered files.
     */
    private static File[] createFilteredArray(File[] directoryFiles, Predicate<File> filter) {
        List<File> filteredList = new ArrayList<>();
        for (File file: directoryFiles){
            if (filter.test(file)){
                filteredList.add(file);
            }
        }
        return filteredList.toArray(new File[0]);
    }
}
