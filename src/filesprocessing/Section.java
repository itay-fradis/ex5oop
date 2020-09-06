package filesprocessing;

import filesprocessing.filters.BadFilterException;
import filesprocessing.filters.FilterFactory;
import filesprocessing.orders.BadOrderException;

import java.io.File;
import java.util.Comparator;
import java.util.function.Predicate;


/**
 * this class is a unit of instructions how to filter and order the files.
 * composed of filter and order objects.
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
     * constructor
     * @param filter filter name
     * @param order order name
     * @param filterLine number of line the filter was shown in file.
     * @param orderLine number of line the order was shown in file.
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
    public Predicate<File> createFilter()  {
        Predicate<File> filter = FilterFactory.getDefaultFilter();
        try{
            filter = FilterFactory.createFilter(filterName);
        }
        catch (BadFilterException e){
            System.err.println(WARNING_MSG + filterL);
        }
        return filter;
    }

    /**
     * @return new order object (comparator)
     */
    public Comparator<File> createOrder() {
        Comparator<File> order = OrderFactory.getDefaultOrder();
        try{
            order = OrderFactory.createOrder(orderName);
        } catch (BadOrderException e) {
            System.err.println(WARNING_MSG + orderL);
        }
        return order;

    }


}
