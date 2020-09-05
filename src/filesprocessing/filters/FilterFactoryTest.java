package filesprocessing.filters;

import org.junit.Test;
import java.io.*;
import static org.junit.Assert.*;

/**
 * test filter factory.
 */
public class FilterFactoryTest {

    /***/
    private static final File file1 = new File("file1.txt");

    /***/
    private static final File file2 = new File("file2.txt");

    @Test
    public void testGreater() throws Exception{
        assertTrue(FilterFactory.createFilter("greater_than#2").test(file1));
        assertFalse(FilterFactory.createFilter("greater_than#2#NOT").test(file1));

        FilterFactory.createFilter("greater_than#2").test(file2);

    }

    @Test
    public void testSmaller(){

    }
}
