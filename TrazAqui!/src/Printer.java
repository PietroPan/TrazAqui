import java.util.Set;

/**
 * Write a description of class Printer here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Printer
{
    public Printer() {}

    /*Informa de randomEvents*/

    public void unlucky() {
        System.out.println("Tiveste pouca sorte o Voluntário não aceitou a tua encomenda");
    }

    /*Questions*/
    public void askUserCod() {
        System.out.println("Insira o seu Codigo de Utilizador:");
    }
    
    public void askNewUser() {
        System.out.println("Utilizador nao faz parte da nossa Database deseja adiciona-lo?(S/s) ou(N/n)");
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

    public void askEncomendaId() {
        System.out.println("Insira o código da sua encomenda:");
    }

    public void askEntregadorId() {
        System.out.println("Insira o código do entregador que pretende: ");
    }

    /*Menu prints*/

    public void showUserOptions(){
        System.out.println("1.Solicitar entrega de encomenda" +
                           "\n2.Ver Entregas efetuadas por tempo e por um Entregador" +
                           "\n3.Classificar encomendas já entregues" );
    }

    /*Exceptions*/
    public void fileNotFound() {
        System.out.println("Ficheiro não encontrado");
    }

    public void invalid(String s) {
        System.out.println(s+" invalid(o)/(a)");
    }

    /*Apresenta Resultados*/

    public void encomendaACaminho(Encomenda e) {
        //WORK IN PROGRESS
        System.out.println("WIP");
    }

    public void apresentaEntregador(String[] s) {
        System.out.println("Nome do entregador: " + s[1] + "\nCódigo do entregador: " +s[0] + " Classificação: " + s[2] + " Custo do entregador: " + s[3] + " Tempo Estimado para entrega: " + s[4]);
    }

    public void apresentaEntregadores(Set<String[]> s) {
        for (String[] st : s) {
            apresentaEntregador(st);
        }
    }
}
