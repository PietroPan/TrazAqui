
/**
 * Write a description of class Printer here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Printer
{
    public Printer() {}

    public void askUserCod() {
        System.out.println("Insira o seu Codigo de Utilizador:");
    }
    
    public void newUser() {
        System.out.println("Utilizador nao faz parte da nossa Database deseja adiciona-lo?");
    }
    
    public void askUserName() {
        System.out.println("Insira o seu Nome de Utilizador:");
    }
    
    public void askBalance() {
        System.out.println("Insira o balanço atual da sua conta:");
    }
    
    public void askLocalizacao(String eixo) {
        System.out.println("Insira a sua localizaçao atual("+eixo+"):");
    }

    public void showUserOptions(){
        System.out.println("1.Solicitar entrega de encomenda" +
                           "2.Ver Entregas efetuadas por tempo e por um Entregador" +
                           "3.Classificar encomendas já entregues" );
    }

    /*Exceptions*/
    public void fileNotFound() {
        System.out.println("Ficheiro não encontrado");
    }

    public void wrongFileTextFormat() {
        System.out.println("Formato de linhas do ficheiro inválido");
    }
}
