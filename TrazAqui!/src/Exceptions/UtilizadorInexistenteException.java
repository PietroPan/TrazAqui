package Exceptions;

public class UtilizadorInexistenteException extends Exception{
    /**
     * Construtor parametrizado
     * @param s String a chamar
     */
    public UtilizadorInexistenteException(String s){
        super(s);
    }
}
