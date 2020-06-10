package Exceptions;

public class LojaInexistenteException extends Exception {
    /**
     * Construtor parametrizado
     * @param s String a chamar
     */
    public LojaInexistenteException(String s) {
        super(s);
    }
}
