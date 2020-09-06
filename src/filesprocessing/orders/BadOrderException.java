package filesprocessing.orders;

/**
 * type 1 error, of a bad filter name/parameter.
 */
public class BadOrderException extends Exception{

    /** prevents annoying warning */
    private static final long serialVersionUID = 1L;

    /** default error message */
    private static final String DEFAULT_ERR_MSG = "ERROR: illegal order name ";

    /**
     * default constructor
     */
    public BadOrderException(){
        super(DEFAULT_ERR_MSG);
    }

    /**
     * constructor
     * @param errorMessage error ctor.
     */
    public BadOrderException(String errorMessage){
        super(errorMessage);
    }
}
