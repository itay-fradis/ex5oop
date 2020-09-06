package filesprocessing.filters;

import java.io.File;
import java.util.function.Predicate;

/**
 * A factory singleton class that creates a filter object accordingly to the input string.
 * if encounters a problem, throws an appropriate exception, and returns default filter - "all".
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
    private static final String HIDDEN = "hidden";

    /** all files filter name */
    private static final String ALL = "all";

    /** YES const*/
    private static final String YES = "YES";

    /** NO const*/
    private static final String NO = "NO";

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

    /**
     * the factory itself.
     * @param filterName given filter name.
     * @return filter object.
     * @throws BadFilterException if encounters bad filter format/name.
     */
    public static Predicate<File> createFilter(String filterName) throws BadFilterException{
        Predicate<File> filter;
        String[] filterComponents = filterName.split(DELIMITER, -1);
        switch (filterComponents[0]){
            case GREATER:
                if (isNonNegativeNumber(filterComponents)){
                    filter = getGreaterThan(Double.parseDouble(filterComponents[1]));
                    break;
                }
                throw new SizeParametersException();
            case SMALLER:
                if (isNonNegativeNumber(filterComponents)){
                    filter = getSmallerThan(Double.parseDouble(filterComponents[1]));
                    break;
                }
                throw new SizeParametersException();
            case BETWEEN:
                if (isLegalRange(filterComponents)){
                    filter = getBetween(Double.parseDouble(filterComponents[1]),
                            Double.parseDouble(filterComponents[2]));
                    break;
                }
                throw new BetweenParametersException();
            case FILE:
                if (islegalStringValue(filterComponents)){
                    filter = getFileFilter(filterComponents[1]);
                    break;
                }
                throw new BadFilterException();
            case CONTAINS:
                if (islegalStringValue(filterComponents)){
                    filter = getContains(filterComponents[1]);
                    break;
                }
                throw new BadFilterException();
            case PREFIX:
                if (islegalStringValue(filterComponents)){
                    filter = getPrefix(filterComponents[1]);
                    break;
                }
                throw new BadFilterException();
            case SUFFIX:
                if (islegalStringValue(filterComponents)){
                    filter = getSuffix(filterComponents[1]);
                    break;
                }
                throw new BadFilterException();
            case WRITABLE:
                if (isLegalBooleanValue(filterComponents)){
                    filter = getWritable();
                    if (filterComponents[1].equals(NO)){
                        filter = filter.negate();
                    }
                    break;
                }
                throw new BooleanParameterException();
            case EXECUTABLE:
                if (isLegalBooleanValue(filterComponents)){
                    filter = getExecutable();
                    if (filterComponents[1].equals(NO)){
                        filter = filter.negate();
                    }
                    break;
                }
                throw new BooleanParameterException();
            case HIDDEN:
                if (isLegalBooleanValue(filterComponents)){
                    filter = getHidden();
                    if (filterComponents[1].equals(NO)){
                        filter = filter.negate();
                    }
                    break;
                }
                throw new BooleanParameterException();
            case ALL:
                if (isLegalAllFilter(filterComponents)){
                    filter = getAll();
                    break;
                }
                throw new BadFilterException();
            default:
                throw new BadFilterException();
        }
        if (filterComponents[filterComponents.length - 1].equals(NOT_SUFFIX)){
            filter = filter.negate();
        }
        return filter;
    }

    /**
     * @return filter always true.
     */
    private static Predicate<File> getAll() {
        return x -> true;
    }

    /***
     * @param filterComponents given filter components in string array.
     * @return whether the format is legal or not.
     */
    private static boolean isLegalAllFilter(String[] filterComponents) {
        if (filterComponents.length == 1){
            return true;
        }
        if (filterComponents.length == 2){
            return filterComponents[1].equals(NOT_SUFFIX);
        }
        return false;
    }

    /**
     * @return filter check if hiiden.
     */
    private static Predicate<File> getHidden() {
        return File::isHidden;
    }

    /**
     * @return filter check if can execute.
     */
    private static Predicate<File> getExecutable() {
        return File::canExecute;
    }

    /***
     * @param filterComponents given filter components in string array.
     * @return whether the format is legal or not.
     */
    private static boolean isLegalBooleanValue(String[] filterComponents) {
        if (filterComponents.length == 2){
            return filterComponents[1].equals(NO) || filterComponents[1].equals(YES);
        }
        if (filterComponents.length == 3){
            return (filterComponents[1].equals(NO) || filterComponents[1].equals(YES)) &&
                    filterComponents[2].equals(NOT_SUFFIX);
        }
        return false;
    }

    /**
     * @return filter that checks if can write to the file.
     */
    private static Predicate<File> getWritable() {
        return File::canWrite;
    }

    /**
     * @param filterComponent given suffix to check
     * @return filter that checks if includes that suffix.
     */
    private static Predicate<File> getSuffix(String filterComponent) {
        return x -> x.getName().endsWith(filterComponent);
    }

    /**
     * @param filterComponent given prefix to check
     * @return filter that checks if includes that prefix.
     */
    private static Predicate<File> getPrefix(String filterComponent) {
        return x -> x.getName().startsWith(filterComponent);
    }

    /**
     * @param filterComponent given string to check
     * @return filter that checks if includes that string.
     */
    private static Predicate<File> getContains(String filterComponent) {
        return x -> x.getName().contains(filterComponent);
    }

    /**
     * @param filterComponent given file name.
     * @return filter that checks is it the exact file name.
     */
    private static Predicate<File> getFileFilter(String filterComponent) {
        return x -> x.getName().equals(filterComponent);
    }

    /***
     * @param filterComponents given filter components in string array.
     * @return whether the format is legal or not.
     */
    private static boolean islegalStringValue(String[] filterComponents) {
        if (filterComponents.length == 2){
            return true;
        }
        if (filterComponents.length == 3){
            return filterComponents[2].equals(NOT_SUFFIX);
        }
        return false;
    }

    private static Predicate<File> getBetween(double lower, double upper) {
        return x -> lower * K_BYTES <= x.length() && x.length() <= upper * K_BYTES;
    }

    /***
     * @param filterComponents given filter components in string array.
     * @return whether the format is legal or not.
     */
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

    /**
     * @param upper upper bound.
     * @return filter that checks is it strictly smaller than the upper bound.
     */
    private static Predicate<File> getSmallerThan(double upper) {
        return x -> x.length() < upper * K_BYTES;
    }

    /**
     * @param lower lower bound.
     * @return filter that checks is it strictly greater than the lower bound.
     */
    private static Predicate<File> getGreaterThan(double lower) {
        return x -> x.length() > lower * K_BYTES;
    }

    /***
     * @param filterComponents given filter components in string array.
     * @return whether the format is legal or not.
     */
    private static boolean isNonNegativeNumber(String[] filterComponents) {
        if (filterComponents.length == 2){
            return Double.parseDouble(filterComponents[1]) >= 0;
        }
        if (filterComponents.length == 3){
            return Double.parseDouble(filterComponents[1]) >= 0 && filterComponents[2].equals(NOT_SUFFIX);
        }
        return false;
    }

    /**
     * @return default filter (aka "all").
     */
    public static Predicate<File> getDefaultFilter(){
        return getAll();
    }


}
