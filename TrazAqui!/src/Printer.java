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

    public void voluntarioLivre() {
        System.out.println("Foi escolhido o voluntario livre que deve demorar menos tempo a entregar a sua encomenda");
    }

    /*Questions*/
    public void askUserCod() {
        System.out.println("Insira o seu Codigo de Utilizador:");
    }
    
    public void askNewUser() {
        System.out.println("Utilizador nao faz parte da nossa Database deseja adiciona-lo?(S/s) ou(N/n)");
    }

    public void askPassword() {
        System.out.println("Insira a Password: ");
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

    public void askLojaID() {
        System.out.println("Insira o código da Loja: ");
    }

    public void askMedical() {
        System.out.println("A sua encomenda irá conter produtos médicos?(s/S) ou (n/N) ");
    }

    public void askLinhaEnc() {
        System.out.println("Deseja adicionar algum produto ? (s/S) ou (n/N)");
    }

    public void askCodProduto() {
        System.out.println("Insira o código do produto a encomendar: ");
    }

    public void askDescricao() {
        System.out.println("Insira a descrição do produto");
    }

    public void askQuantidade() {
        System.out.println("Insira a quantidade que deseja encomendar: ");
    }

    public void askConfirmacao() {
        System.out.println("Tem a certeza? (s/S) ou (n/N)");
    }

    /*Menu prints*/

    public void showLoginOptions() {
        System.out.println("1.Sou um Utilizador" +
                            "\n2.Sou um Voluntario" +
                            "\n3.Sou uma Transportadora"+
                            "\n4.Sou uma Loja" +
                            "\n0.Sair");
    }

    public void showUserOptions(){
        System.out.println("1.Fazer encomenda" +
                            "\n2.Solicitar entrega de encomenda" +
                            "\n3.Ver Entregas efetuadas por tempo e por um Entregador" +
                            "\n4.Classificar encomendas já entregues" +
                            "\n5.Mudar de conta" +
                            "\n0.Fechar Programa");
    }

    public void showBye() {
        System.out.println("Obrigado por escolher TrazAqui!" +
                            "\nEsperamos que tenha gostado");
    }
    /*Exceptions*/
    public void fileNotFound() {
        System.out.println("Ficheiro não encontrado");
    }

    public void invalid(String s) {
        System.out.println(s+" invalid(o)/(a)");
    }

    public void nadaEncomendado() {
        System.out.println("Não possui linhas de encomenda");
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

    public void apresentaEncomenda(String enc) {
        System.out.println("Encomenda:\n" + enc + "\n");
    }

    public void apresentaPrecoEnc(double preco) {
        System.out.println("Preco Total: " + preco);
    }
}
