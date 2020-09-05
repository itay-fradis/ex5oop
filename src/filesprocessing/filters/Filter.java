package filesprocessing.filters;
import java.io.File;
import java.util.function.Predicate;

/**
 *
 */
@FunctionalInterface
public interface Filter extends Predicate<File>{

    @Override
    boolean test(File file);

}
