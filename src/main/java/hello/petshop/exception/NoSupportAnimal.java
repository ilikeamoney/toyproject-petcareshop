package hello.petshop.exception;

public class NoSupportAnimal extends RuntimeException{
    public NoSupportAnimal() {
    }

    public NoSupportAnimal(String message) {
        super(message);
    }

    public NoSupportAnimal(String message, Throwable cause) {
        super(message, cause);
    }
}
