package ro.ubb.catalog.core.model.validators;

public class UserException extends ValidatorException {

    /**
     * Constructor with a message as an input.
     *
     * @param message-the message of the Exception
     *
     */

    public UserException(String message) {
        super(message);
    }

    /**
     * Constructor with a message and a throwable cause as an input.
     *
     * @param message-the message of the Exception
     * @param cause-the cause of the Exception
     *
     */
    public UserException(String message, Throwable cause) {
        super(message, cause);
    }


    /**
     * Constructor with a throwable cause as an input.
     *
     * @param cause-the cause of the Eception
     *
     */
    public UserException(Throwable cause) {
        super(cause);
    }
}
