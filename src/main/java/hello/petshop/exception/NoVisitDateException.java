package hello.petshop.exception;

public class NoVisitDateException extends RuntimeException{
    public NoVisitDateException() {
        super();
    }

    public NoVisitDateException(String message) {
        super(message);
    }

    public NoVisitDateException(String message, Throwable cause) {
        super(message, cause);
    }
}
