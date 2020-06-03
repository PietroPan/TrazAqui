package MVC.View;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import Common.*;

/**
 * Write a description of class MVC.View.Printer here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Printer implements Serializable
{
    public Printer() {}

    /*Informa de randomEvents*/

    public void voluntarioLivre() {
        System.out.println("Foi escolhido o voluntario livre que deve demorar menos tempo a entregar a sua encomenda");
    }

    /*Questions*/
    public void askUserCod() {
        System.out.println("Insira o seu Codigo de Utilizador:");
    }

    public void askCod() {
        System.out.println("Insira o seu Código:");
    }
    public void askNew() {
        System.out.println("Quer fazer parte da TrazAqui!?(S/s) ou(N/n)");
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
        System.out.println("Insira o código da sua encomenda:(-1 caso não queira nenhuma)");
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

    public void askClassificacao() {
        System.out.println("Insira a Classificação que deseja dar: ");
    }

    public void askData() {
        System.out.println("Insira MVC.Model.Data no formato Year:Month(1 a 12):Day(1 a 31):Hour(0 a 23):Minute(0 a 59)");
    }

    public void askVelocidadeNormal() {
        System.out.println("Insira a sua velocidade normal: (Use . para separa casas decimais)");
    }

    public void askRaio() {
        System.out.println("Insira o seu raio de ação: ");
    }


    public void askNIF() {
        System.out.println("Insira o seu NIF");
    }

    public void askCusto(String t) {
        System.out.println("Insira o custo por "+t);
    }

    public void askNEncomendas() {
        System.out.println("Insira o número de encomendas que pode transportar ao mesmo tempo");
    }

    public void askTamFila() {
        System.out.println("Insira o tamanho da fila(-1 caso não queira dar essa informação)");
    }

    public void askTempoAtendimento() {
        System.out.println("Insira o tempo normal de atendimento(em min)");
    }

    public void askQuantoTempo() {
        System.out.println("Diga quanto tempo deseja ficar em Cryosleep(no formato (Horas:Minutos))");
    }

    /*Menu prints*/

    public void showMainMenu() {
        System.out.println("1.Login" +
                            "\n2.Criar conta" +
                            "\n3.Avançar tempo" +
                            "\n4.Sair");
    }

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
                            "\n4.Classificar encomendas já recebidas" +
                            "\n5.Mudar de Conta" +
                            "\n0.Sair" );
    }

    public void showVoluntarioOptions() {
        System.out.println("1.Ver pedidos de entrega" +
                            "\n2.Mudar de Conta" +
                            "\n0.Sair");
    }

    public void showBye() {
        System.out.println("Obrigado por escolher TrazAqui!" +
                            "\nEsperamos que tenha gostado");
    }

    public void showObrigado() {
        System.out.println("Obrigado por se juntar a TrazAqui!");
    }
    /*Exceptions*/

    public void exception(String s) {
        System.out.println(s);
    }

    public void fileNotFound() {
        System.out.println("Ficheiro não encontrado");
    }

    public void invalid(String s) {
        System.out.println(s+" invalid(o)/(a)");
    }

    public void nadaAApresentar() {
        System.out.println("Nada a Apresentar");
    }

    public void naoRegistado(String v) {
        System.out.println(v + " não registado");
    }

    /*Apresenta Resultados*/

    public void encomendaACaminho(LocalDateTime t) {
        System.out.println("A sua Common.Encomenda já está a caminho\nMVC.Model.Data estimada de entrega: " + t.toString());
    }

    public void encomendaNotReady() {
        System.out.println("A sua Common.Encomenda está a ser preparada");
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
        System.out.println("Common.Encomenda:\n" + enc + "\n");
    }

    public void apresentaPrecoEnc(double preco) {
        System.out.println("Preco Total: " + preco);
    }

    public void apresentaUserEncomendas(Set<String> classifica) {
        for (String s : classifica) {
            apresentaEncomenda(s);
            System.out.println("\n//////////////////////////////\n");
        }
    }

    public void apresentaListRequest(List<String> ls) {
        for (String s : ls) {
            System.out.println(s);
        }
    }

    public void apresentaUnreadMessages(List<String> ls) {
        System.out.println("###########################");
        if (ls.isEmpty()) System.out.println("No unread messages.");
        for (String s : ls) {
            System.out.println(s);
        }
        System.out.println("###########################");
    }

    public void apresentaStock(List<InterfaceLinhaEncomenda> l) {
        int i=0;
        for (InterfaceLinhaEncomenda p : l) {
            i++;
            System.out.println("#######Produto "+i +"#######");
            System.out.println(p.toString());

        }
    }
}