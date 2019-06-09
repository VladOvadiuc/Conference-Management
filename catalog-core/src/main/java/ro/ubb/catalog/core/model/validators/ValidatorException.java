package ro.ubb.catalog.core.model.validators;

/**
 * Created by Patricia on 03/02/2019
 **/
public class ValidatorException extends RuntimeException {
    /**
     * Constructor with a message as an input.
     *
     * @param message-the meesage of the exception
     *
     */
    public ValidatorException(String message) {
        super(message);
    }


    /**
     * Constructor with a message and a throwable cause as an input.
     *
     * @param message-the message of the exception
     * @param cause-the cause of the exception
     *
     */
    public ValidatorException(String message, Throwable cause) {
        super(message, cause);
    }


    /**
     * Constructor with throwable cause as an input.
     *
     * @param cause-the cause of the exception
     *
     */
    public ValidatorException(Throwable cause) {
        super(cause);
    }
}
