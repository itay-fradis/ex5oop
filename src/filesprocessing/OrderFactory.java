package filesprocessing;

import java.util.Comparator;
import java.io.*;
import java.util.function.Predicate;

import org.apache.*;


/**
 *
 */
public class OrderFactory {

    /** delimiter between filter components */
    private static final String DELIMITER = "#";

    /** sort files by absolute name - symbol */
    private static final String ABS_ORDER = "abs";

    /** sort files by their type - symbol */
    private static final String TYPE_ORDER = "type";

    /** sort files by their size - symbol */
    private static final String SIZE_ORDER = "size";

    /** reverse suffix */
    private static final String REVERSE_SUFFIX = "REVERSE";


    /** order factory instance */
    private static final OrderFactory orderFactoryInstance = new OrderFactory();

    /**
     * private constructor
     */
    private OrderFactory(){}

    /**
     * @return OrderFactory instance
     */
    public static OrderFactory getOrderFactoryInstance() {return orderFactoryInstance; }

    public static Comparator<File> createFilter(String orderName) throws Exception {
        Comparator<File> order = null;
        Predicate<File> filter;
        String[] orderComponents = orderName.split(DELIMITER);
        switch (orderComponents[0]){
            case ABS_ORDER:
                if (isLegalNameOrder(orderComponents)){
                    order = getAbsOrder();
                    break;
                }
                throw new Exception();
            case TYPE_ORDER:
                if (isLegalNameOrder(orderComponents)){
                    order = getTypeOrder();
                    break;
                }
                throw new Exception();
            case SIZE_ORDER:
                if (isLegalNameOrder(orderComponents)){
                    order = getSizeOrder();
                    break;
                }
                throw new Exception();
            default:
                throw new Exception();
        }
        if (orderComponents[orderComponents.length - 1].equals(REVERSE_SUFFIX)){
            order = order.reversed();
        }
        return order;
    }

    private static Comparator<File> getSizeOrder() {
        return (x, y) -> {
            if (x.length() == y.length()){
                return getAbsOrder().compare(x, y);
            }
            return (int) (x.length() - y.length());
        };
    }

    private static Comparator<File> getTypeOrder() {
        return (x, y) -> {
            if (getFileType(x).compareTo(getFileType(y)) == 0){
                return getAbsOrder().compare(x, y);
            }
            return getFileType(x).compareTo(getFileType(y));
        };
    }

    private static Comparator<File> getAbsOrder() {
        return (x, y) -> x.getAbsolutePath().compareTo(y.getAbsolutePath());
    }

    private static boolean isLegalNameOrder(String[] orderComponents) {
        if (orderComponents.length == 1){
            return true;
        }
        if (orderComponents.length == 2){
            return orderComponents[1].equals(REVERSE_SUFFIX);
        }
        return false;
    }

    private static String getFileType(File file){
        String[] components = file.getName().split("\\.");
        if (components.length == 1){
            return "";
        }
        return components[components.length - 1];
    }


}
