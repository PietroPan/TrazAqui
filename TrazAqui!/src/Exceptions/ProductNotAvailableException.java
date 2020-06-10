package Exceptions;

public class ProductNotAvailableException extends Exception{
    /**
     * Construtor parametrizado
     * @param s String a chamar
     */
    public ProductNotAvailableException(String s) {
        super(s);
    }
}
