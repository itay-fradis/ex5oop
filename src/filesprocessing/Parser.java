package filesprocessing;

import javax.imageio.IIOException;
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
    public static Section[] parseCommandFile(String fileName) throws IOException{

        List<Section> sectionList = new ArrayList<Section>();
        int lineIndex = 0;
        int filterLine;
        int orderLine;
        String filter;
        String order;
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line = reader.readLine();
        lineIndex++;
        while (line != null){
            if (!line.equals("FILTER")){
                throw new IIOException("");
            }
            line = reader.readLine();
            filter = line;
            lineIndex++;
            filterLine = lineIndex;
            line = reader.readLine();
            if (!line.equals("ORDER")){
                throw new IIOException("");
            }
            line = reader.readLine();
            lineIndex += 2;
            orderLine = lineIndex;
            if (line != null && !line.equals("FILTER")){
                order = line;
                sectionList.add(new Section(filter, order, filterLine, orderLine));

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
