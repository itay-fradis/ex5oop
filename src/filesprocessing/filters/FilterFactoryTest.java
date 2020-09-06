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

    /***/
    private static final File file3 = new File("file3.txt");

    @Test
    public void testGreater() throws Exception{
        assertTrue(FilterFactory.createFilter("greater_than#2").test(file1));
        assertFalse(FilterFactory.createFilter("greater_than#2#NOT").test(file1));

        assertFalse(FilterFactory.createFilter("greater_than#2").test(file2));
        assertTrue(FilterFactory.createFilter("greater_than#2#NOT").test(file2));

        assertTrue(FilterFactory.createFilter("greater_than#0.25#NOT").test(file3));

    }

    @Test
    public void testSmaller() throws Exception {

        assertTrue(FilterFactory.createFilter("smaller_than#10.1").test(file1));
        assertFalse(FilterFactory.createFilter("smaller_than#10.1#NOT").test(file1));

    }

    @Test
    public void testBetween() throws Exception {

        assertTrue(FilterFactory.createFilter("between#1#5").test(file1));
        assertFalse(FilterFactory.createFilter("between#1#5#NOT").test(file1));
        assertTrue(FilterFactory.createFilter("between#0.25#5").test(file3));

        //illegal range
    }

    @Test
    public void testFile() throws Exception {

        assertTrue(FilterFactory.createFilter("file#file1.txt").test(file1));
        assertFalse(FilterFactory.createFilter("file#file1.txt#NOT").test(file1));
        assertFalse(FilterFactory.createFilter("file#File1.txt").test(file1));

    }

    @Test
    public void testContains() throws Exception {

        assertTrue(FilterFactory.createFilter("contains#file1.txt").test(file1));
        assertTrue(FilterFactory.createFilter("contains#ile").test(file1));
        assertFalse(FilterFactory.createFilter("contains#ile#NOT").test(file1));
        assertFalse(FilterFactory.createFilter("contains#i le").test(file1));
    }

    @Test
    public void testPrefix() throws Exception {
        assertTrue(FilterFactory.createFilter("prefix#fi").test(file1));
        assertFalse(FilterFactory.createFilter("prefix#fi#NOT").test(file1));
        assertFalse(FilterFactory.createFilter("prefix#i").test(file1));
    }

    @Test
    public void testSuffix() throws Exception {
        assertTrue(FilterFactory.createFilter("suffix#.txt").test(file1));
        assertFalse(FilterFactory.createFilter("suffix#.txt#NOT").test(file1));
    }

    @Test
    public void testWritable() throws Exception{
        assertTrue(FilterFactory.createFilter("writable#YES").test(file1));
        assertFalse(FilterFactory.createFilter("writable#NO").test(file1));
        //file3
        assertTrue(FilterFactory.createFilter("writable#NO").test(file3));
        assertFalse(FilterFactory.createFilter("writable#NO#NOT").test(file3));
    }

    @Test
    public void testExecutable() throws Exception {

    }
}
