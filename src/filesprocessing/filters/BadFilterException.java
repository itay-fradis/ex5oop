package filesprocessing.filters;

/**
 * type 1 error, of a bad filter name/parameters.
 */
public class BadFilterException extends Exception{

    /** prevents annoying warning */
    private static final long serialVersionUID = 1L;

    /** default error message */
    private static final String DEFAULT_ERR_MSG = "ERROR: illegal filter name ";

    /**
     * default constructor
     */
    public BadFilterException(){
        super(DEFAULT_ERR_MSG);
    }

    /**
     * constructor
     * @param errorMessage error ctor.
     */
    public BadFilterException(String errorMessage){
        super(errorMessage);
    }
}
