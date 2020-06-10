package Exceptions;

public class EntregadorInexistenteException extends Exception {
    /**
     * Construtor parametrizado
     * @param s String a chamar
     */
    public EntregadorInexistenteException(String s){
        super(s);
    }
}
