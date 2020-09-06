package filesprocessing;

import filesprocessing.filters.Filter;
import filesprocessing.filters.FilterFactory;

import java.io.*;
import java.util.*;

/**
 * responsible for first parsing of the command file and the sourceDir.
 */
public class Parser {

    /** error message - bad subsection name \ missing FILTER */
    private static final String BAD_FORMAT_ERROR_FILTER = "ERROR: FILTER is missing or has a bad name.";

    /** error message - bad subsection name \ missing ORDER  */
    private static final String BAD_FORMAT_ERROR_ORDER = "ERROR: ORDER is missing or has a bad name.";

    /** filter name */
    private static final String FILTER_NAME = "FILTER";

    /** order name */
    private static final String ORDER_NAME = "ORDER";

    /**
     * generates an arrays of sections from the command file. throws IO and bad-format errors.
     * @param fileName of the command file.
     * @return array of sections.
     */
    public static Section[] parseCommandFile(String fileName) throws IOException, BadFormatCommandFileException,
                                                                    NullPointerException {

        List<Section> sectionList = new ArrayList<>();
        int lineIndex = 0;
        int filterLine;
        int orderLine;
        String filter;
        String order;
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line = reader.readLine();
        lineIndex++;
        while (line != null){
            if (!line.equals(FILTER_NAME)){
                throw new BadFormatCommandFileException(BAD_FORMAT_ERROR_FILTER);
            }
            line = reader.readLine();
            filter = line;
            lineIndex++;
            filterLine = lineIndex;
            line = reader.readLine();
            if (line == null || !line.equals(ORDER_NAME)){
                throw new BadFormatCommandFileException(BAD_FORMAT_ERROR_ORDER);
            }
            line = reader.readLine();
            lineIndex += 2;
            orderLine = lineIndex;
            if (line != null && !line.equals(FILTER_NAME)){
                order = line;
                sectionList.add(new Section(filter, order, filterLine, orderLine));
                line = reader.readLine();
                lineIndex++;
            }
            else{
                sectionList.add(new Section(filter, "", filterLine, orderLine));
            }

        }
        reader.close();
        Section[] sectionArray = new Section[sectionList.size()];
        return sectionList.toArray(sectionArray);
    }


    /**
     * parse and return files from sourceDir.
     * @param fileName sourceDir directory.
     * @return array of file objects of the sourceDir directory.
     */
    public static File[] sourceDir2files(String fileName){
        File sourceDir = new File(fileName);
        File[] filesForCheck = sourceDir.listFiles();
        ArrayList<File> result = new ArrayList<>();
        for (File file: filesForCheck){
            if (file.isFile()){
                result.add(file);
            }
        }
        return result.toArray(new File[0]);
    }
}
