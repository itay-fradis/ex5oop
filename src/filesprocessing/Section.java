package filesprocessing;

import filesprocessing.filters.Filter;
import filesprocessing.orders.Order;

import java.io.File;


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

    /***/
    private Filter filter;

    /***/
    private Order order;

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
        this.filter = null;
        this.order = null;
    }

    private void createFilter(){

    }

    private void createOrder() {
    }


}
