package exeptions;

public class InvalidFactureExeption extends RuntimeException{

    public InvalidFactureExeption(String message) {
        super(message);
    }

}