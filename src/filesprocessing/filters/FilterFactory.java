package filesprocessing.filters;

import java.io.File;
import java.util.function.Predicate;

/**
 *
 */
public class FilterFactory {

    /** NOT suffix */
    private static final String NOT_SUFFIX = "NOT";

    /** kilo bytes */
    private static final int K_BYTES = 1024;

    /** greater than filter name */
    private static final String GREATER = "greater_than";

    /** smaller than filter name */
    private static final String SMALLER = "smaller_than";

    /** between filter name */
    private static final String BETWEEN = "between";

    /** file filter name */
    private static final String FILE = "file";

    /** contains filter name */
    private static final String CONTAINS = "contains";

    /** prefix filter name */
    private static final String PREFIX = "prefix";

    /** suffix filter name */
    private static final String SUFFIX = "suffix";

    /** writable filter name */
    private static final String WRITABLE = "writable";

    /** executable filter name */
    private static final String EXECUTABLE = "executable";

    /** hidden filter name */
    private static final String HIDDEN = "HIDDEN";

    /** all files filter name */
    private static final String ALL = "all";

    /** YES const*/
    private static final String YES = "YES";

    /** NO const*/
    private static final String NO = "NO";

    private enum filterName {
        GREATER_THAN, BETWEEN, SMALLER_THAN, FILE, CONTAINS, PREFIX, SUFFIX, WRITABLE, EXECUTABLE, HIDDEN, ALL
    }

    /**
     * filterFactory instance
     */
    private static final FilterFactory filterFactoryInstance = new FilterFactory();

    /** delimiter between filter components */
    private static final String DELIMITER = "#";

    /**
     * private constructor
     */
    private FilterFactory(){}

    /**
     * @return filterFactory instance.
     */
    public static FilterFactory getFilterFactoryInstance() {
        return filterFactoryInstance;
    }

    public static Predicate<File> createFilter(String filterName) throws Exception{
        Predicate<File> filter;
        String[] filterComponents = filterName.split(DELIMITER);
        switch (filterComponents[0]){
            case GREATER:
                if (isNonNegativeNumber(filterComponents)){
                    filter = getGreaterThan(Double.parseDouble(filterComponents[1]));
                    if (filterComponents.length == 3){
                        filter = filter.negate();
                    }
                    break;
                }
            case SMALLER:
                if (isNonNegativeNumber(filterComponents)){
                    filter = getSmallerThan(Double.parseDouble(filterComponents[1]));
                    if (filterComponents.length == 3){
                        filter = filter.negate();
                    }
                    break;
                }
            case BETWEEN:
                if (isLegalRange(filterComponents)){
                    filter = getBetween(Double.parseDouble(filterComponents[1]),
                            Double.parseDouble(filterComponents[2]));
                    if (filterComponents.length == 4){
                        filter = filter.negate();
                    }
                    break;
                }
            case FILE:
                if (islegalStringValue(filterComponents)){
                    filter = getFileFilter(filterComponents[1]);
                    if (filterComponents.length == 3){
                        filter = filter.negate();
                    }
                    break;
                }
            case CONTAINS:
                if (islegalStringValue(filterComponents)){
                    filter = getContains(filterComponents[1]);
                    if (filterComponents.length == 3){
                        filter = filter.negate();
                    }
                    break;
                }
            case PREFIX:
                if (islegalStringValue(filterComponents)){
                    filter = getPrefix(filterComponents[1]);
                    if (filterComponents.length == 3){
                        filter = filter.negate();
                    }
                    break;
                }
            case SUFFIX:
                if (islegalStringValue(filterComponents)){
                    filter = getSuffix(filterComponents[1]);
                    if (filterComponents.length == 3){
                        filter = filter.negate();
                    }
                    break;
                }
            case WRITABLE:
                if (islegalBooleanValue(filterComponents)){
                    filter = getWritable();
                    if (filterComponents[1].equals(NO)){
                        filter = filter.negate();
                    }
                    if (filterComponents.length == 3){
                        filter = filter.negate();
                    }
                    break;
                }
            case EXECUTABLE:
                if (islegalBooleanValue(filterComponents)){
                    filter = getExecutable();
                    if (filterComponents[1].equals(NO)){
                        filter = filter.negate();
                    }
                    if (filterComponents.length == 3){
                        filter = filter.negate();
                    }
                    break;
                }
            case HIDDEN:
                if (islegalBooleanValue(filterComponents)){
                    filter = getHidden();
                    if (filterComponents[1].equals(NO)){
                        filter = filter.negate();
                    }
                    if (filterComponents.length == 3){
                        filter = filter.negate();
                    }
                    break;
                }
            case ALL:
                if (isLegalAllFilter(filterComponents)){
                    filter = getAll();
                    break;
                }
            default:
                throw new Exception();
        }
        return filter;
    }

    private static Predicate<File> getAll() {
        return x -> true;
    }

    private static boolean isLegalAllFilter(String[] filterComponents) {
        return filterComponents.length == 1;
    }

    private static Predicate<File> getHidden() {
        return File::isHidden;
    }

    private static Predicate<File> getExecutable() {
        return File::canExecute;
    }

    private static boolean islegalBooleanValue(String[] filterComponents) {
        if (filterComponents.length == 2){
            return filterComponents[1].equals(NO) || filterComponents[1].equals(YES);
        }
        if (filterComponents.length == 3){
            return (filterComponents[1].equals(NO) || filterComponents[1].equals(YES)) &&
                    filterComponents[2].equals(NOT_SUFFIX);
        }
        return false;
    }

    private static Filter getWritable() {
        return File::canWrite;
    }

    private static Filter getSuffix(String filterComponent) {
        return x -> x.getName().endsWith(filterComponent);
    }

    private static Filter getPrefix(String filterComponent) {
        return x -> x.getName().startsWith(filterComponent);
    }

    private static Filter getContains(String filterComponent) {
        return x -> x.getName().contains(filterComponent);
    }

    private static Filter getFileFilter(String filterComponent) {
        return x -> x.getName().equals(filterComponent);
    }

    private static boolean islegalStringValue(String[] filterComponents) {
        if (filterComponents.length == 2){
            return true;
        }
        if (filterComponents.length == 3){
            return filterComponents[2].equals(NOT_SUFFIX);
        }
        return false;
    }

    private static Filter getBetween(double lower, double upper) {
        return x -> lower * K_BYTES <= x.length() && x.length() <= upper * K_BYTES;
    }

    private static boolean isLegalRange(String[] filterComponents) {
        if (filterComponents.length < 3){
            return false;
        }
        double lower = Double.parseDouble(filterComponents[1]);
        double upper = Double.parseDouble(filterComponents[2]);
        if (filterComponents.length == 3 ){
            return lower >=0 && upper >= 0 && lower <= upper;
        }
        if (filterComponents.length == 4){
            return lower >=0 && upper >= 0 && lower <= upper && filterComponents[3].equals(NOT_SUFFIX);
        }
        return false;
    }

    private static Filter getSmallerThan(double upper) {
        return x -> x.length() < upper * K_BYTES;
    }

    private static Filter getGreaterThan(double lower) {
        return x -> x.length() > lower * K_BYTES;
    }


    private static boolean isNonNegativeNumber(String[] filterComponents) {
        if (filterComponents.length == 2){
            return Double.parseDouble(filterComponents[1]) >= 0;
        }
        if (filterComponents.length == 3){
            return Double.parseDouble(filterComponents[1]) >= 0 && filterComponents[2].equals(NOT_SUFFIX);
        }
        return false;
    }


}
