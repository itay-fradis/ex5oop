package filesprocessing;

import filesprocessing.filters.BadFilterException;
import filesprocessing.filters.FilterFactory;

import java.io.File;
import java.util.function.Predicate;


/**
 *
 */
public class Section {

    /***/
    private final String filterName;

    /***/
    private final String orderName;

    /***/
    private final int filterL;

    /***/
    private final int orderL;

    /** error message - warning in line X */
    private static final String WARNING_MSG = "Warning in line ";


    /**
     *
     * @param filter
     * @param order
     * @param filterLine
     * @param orderLine
     */
    public Section(String filter, String order, int filterLine, int orderLine){
        this.filterName = filter;
        this.orderName = order;
        this.filterL = filterLine;
        this.orderL = orderLine;
    }

    /**
     * @return new filter object.
     */
    private Predicate<File> createFilter()  {
        Predicate<File> filter = FilterFactory.getDefaultFilter();
        try{
            filter = FilterFactory.createFilter(filterName);
        }
        catch (BadFilterException e){
            System.err.println(WARNING_MSG + filterL);
        }
        return filter;
    }

    private void createOrder() {

    }


}
