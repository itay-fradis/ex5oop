package filesprocessing.filters;

/**
 * specific exception of type 1 error. inherits from BadFilterException.
 */
public class BooleanParameterException extends BadFilterException {

    /** prevents annoying warning */
    private static final long serialVersionUID = 1L;

    /** default error message */
    private static final String DEFAULT_ERR_MSG = "ERROR: bad boolean parameter ";

    /**
     * default constructor
     */
    public BooleanParameterException(){
        super(DEFAULT_ERR_MSG);
    }

    /**
     * constructor
     * @param errorMessage error ctor.
     */
    public BooleanParameterException(String errorMessage){
        super(errorMessage);
    }

}
