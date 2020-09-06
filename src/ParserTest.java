import filesprocessing.BadFormatCommandFileException;
import filesprocessing.Parser;
import org.junit.Test;
import java.io.*;

import static org.junit.Assert.*;


/**
 * test parser
 */
public class ParserTest {


    @Test
    public void testParserLegalFile() throws IOException, BadFormatCommandFileException {
        Parser.parseCommandFile("Command files/filter002.flt");
        Parser.parseCommandFile("Command files/filter007.flt");
        Parser.parseCommandFile("Command files/filter019.flt");
        assertEquals(1, Parser.parseCommandFile("Command files/filter002.flt").length);


        //blank line after ORDER
        Parser.parseCommandFile("Command files/filtertest1.flt");

        //3 sections
        Parser.parseCommandFile("Command files/filtertest2.flt");
        assertEquals(3, Parser.parseCommandFile("Command files/filtertest2.flt").length);

        //complex length of sections, 6 sections
        Parser.parseCommandFile("Command files/filtertest3sections6.flt");
        assertEquals(6, Parser.parseCommandFile("Command files/filtertest3sections6.flt").length);
        Parser.parseCommandFile("Command files/filtertest6.flt");

    }

    @Test(expected = BadFormatCommandFileException.class)
    public void testParserIllegal() throws IOException, BadFormatCommandFileException {
        //FILTEr
        Parser.parseCommandFile("Command files/filtertest4.flt");

        //ORDEr
        Parser.parseCommandFile("Command files/filtertest5.flt");
        //complex
        Parser.parseCommandFile("Command files/filtertest7.flt");
        //orderBefore
        Parser.parseCommandFile("Command files/filtertest8.flt");
        //empty command file
        Parser.parseCommandFile("Command files/filtertest9.flt");
        //without ORDER
        Parser.parseCommandFile("Command files/filtertest10.flt");

    }





}
