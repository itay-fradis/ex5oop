package filesprocessing;

/**
 * type two error. bad format of sub-section.
 */
public class BadFormatCommandFile extends Exception{

    /** prevents annoying warning */
    private static final long serialVersionUID = 1L;

    /** bad format fo the command file - error msg */
    private static final String DEFAULT_ERR_MSG_BAD_FORMAT = "There is a problem in the command file format ";

    /**
     * default constructor
     */
    public BadFormatCommandFile(){
        super(DEFAULT_ERR_MSG_BAD_FORMAT);
    }

    /**
     * constructor
     * @param errorMessage message ctor
     */
    public BadFormatCommandFile(String errorMessage){
        super(errorMessage);
    }
}
