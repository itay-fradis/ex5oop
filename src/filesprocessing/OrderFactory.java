package filesprocessing;

import java.util.Comparator;
import java.io.*;
import java.util.function.Predicate;

import filesprocessing.orders.BadOrderException;


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

    /**
     * the factory itself.
     * @param orderName given order name.
     * @return order object (comperator).
     * @throws BadOrderException if encounters bad order format/name.
     */
    public static Comparator<File> createOrder(String orderName) throws BadOrderException {
        Comparator<File> order = null;
        Predicate<File> filter;
        String[] orderComponents = orderName.split(DELIMITER);
        switch (orderComponents[0]){
            case ABS_ORDER:
                if (isLegalNameOrder(orderComponents)){
                    order = getAbsOrder();
                    break;
                }
                throw new BadOrderException();
            case TYPE_ORDER:
                if (isLegalNameOrder(orderComponents)){
                    order = getTypeOrder();
                    break;
                }
                throw new BadOrderException();
            case SIZE_ORDER:
                if (isLegalNameOrder(orderComponents)){
                    order = getSizeOrder();
                    break;
                }
                throw new BadOrderException();
            default:
                throw new BadOrderException();
        }
        if (orderComponents[orderComponents.length - 1].equals(REVERSE_SUFFIX)){
            order = order.reversed();
        }
        return order;
    }

    /**
     * @return comparator of file's size. if sizes are equal, compare by abs.
     */
    private static Comparator<File> getSizeOrder() {
        return (x, y) -> {
            if (x.length() == y.length()){
                return getAbsOrder().compare(x, y);
            }
            return (int) (x.length() - y.length());
        };
    }

    /**
     * @return comparator of file's type alphabetic order.
     */
    private static Comparator<File> getTypeOrder() {
        return (x, y) -> {
            if (getFileType(x).compareTo(getFileType(y)) == 0){
                return getAbsOrder().compare(x, y);
            }
            return getFileType(x).compareTo(getFileType(y));
        };
    }

    /**
     * @return comparator of alphabetic order.
     */
    private static Comparator<File> getAbsOrder() {
        return (x, y) -> x.getAbsolutePath().compareTo(y.getAbsolutePath());
    }

    /**
     * checks of order is in the correct format.
     * @param orderComponents given order components in string.
     * @return whether the format is legal or not.
     */
    private static boolean isLegalNameOrder(String[] orderComponents) {
        if (orderComponents.length == 1){
            return true;
        }
        if (orderComponents.length == 2){
            return orderComponents[1].equals(REVERSE_SUFFIX);
        }
        return false;
    }

    /**
     * @param file given file
     * @return file type.
     */
    private static String getFileType(File file){
        String[] components = file.getName().split("\\.");
        if (components.length == 1){
            return "";
        }
        return components[components.length - 1];
    }

    /**
     * @return AbsOrder comparator.
     */
    public static Comparator<File> getDefaultOrder(){
        return getAbsOrder();
    }

}
