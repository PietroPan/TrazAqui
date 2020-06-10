package Exceptions;

public class NotEnoughMoneyException extends Exception{
    /**
     * Construtor parametrizado
     * @param s String a chamar
     */
    public NotEnoughMoneyException(String s) {
        super(s);
    }
}
