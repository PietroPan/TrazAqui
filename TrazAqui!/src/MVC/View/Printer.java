package MVC.View;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
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

    /*Paginacao*/

    public void apresentaMenuTabela()
    {
        System.out.println("[s] Adicionar Produto | [n] Proxima Pagina | [p] Pagina Anterior | [(numero)] Ir para pagina | [q] Finalizar encomenda");
    }

    public void askLinhasTabela()
    {
        System.out.print("Quantas linhas na tabela(max:5)? ");
    }

    public void askColunasTabela()
    {
        System.out.print("Quantas colunas na tabela(max:5)? ");
    }

    public void askPagina(int nPaginas)
    {
        System.out.print("Que pagina deseja visualizar("+nPaginas+" paginas)? ");
    }

    public void apresentaTotalProdutosStock(List<InterfaceLinhaEncomenda> l)
    {
        System.out.println("Total " + l.size() + " produtos a listar");
    }

    public int getNumeroPaginas(int dataSize, int linhasPagina, int nProdutosLinha)
    {
        int npaginas;
        int numProdsPagina = linhasPagina*nProdutosLinha;

        if(dataSize<=numProdsPagina) npaginas=1;
        else if(dataSize%numProdsPagina == 0) npaginas = dataSize / numProdsPagina;
        else npaginas = dataSize/numProdsPagina + 1;

        return npaginas;
    }

    public Map.Entry<Integer,Integer> getPagina(int dataSize, int pagina, int linhasPagina, int nProdutosLinha)
    {
        int npaginas;
        int i,f;

        i=(pagina-1)*linhasPagina*nProdutosLinha;
        f = (i+linhasPagina*nProdutosLinha)>dataSize ? dataSize-1 : i+(linhasPagina*nProdutosLinha)-1;

        return new AbstractMap.SimpleEntry<>(i,f);
    }

    public void printSeparadorTabela(int nProdutosLinha)
    {
        int sizeInsideBox = 30;

        System.out.print('-');
        for(int i = (sizeInsideBox+1)*nProdutosLinha ; i>0 ; i--)
            System.out.print('-');
        System.out.println("");
    }

    public void printCelulaDadosTabela(String data)
    {
        int sizeInsideBox = 30;

        System.out.print(data);

        for(int size=data.length()+1 ; size<sizeInsideBox ; size++)
            System.out.print(' ');
        System.out.print("| ");
    }

    public void printLinhaTabela(List<InterfaceLinhaEncomenda> l,Map.Entry<Integer,Integer> rangeLinha)
    {
        System.out.print("| ");
        for(int i=rangeLinha.getKey() ; i<=rangeLinha.getValue() ; i++)
            printCelulaDadosTabela(l.get(i).getDescricao());
        System.out.println("");

        System.out.print("| ");
        for(int i=rangeLinha.getKey() ; i<=rangeLinha.getValue() ; i++)
            printCelulaDadosTabela("Codigo: " + l.get(i).getcodProduto());
        System.out.println("");

        System.out.print("| ");
        for(int i=rangeLinha.getKey() ; i<=rangeLinha.getValue() ; i++)
            printCelulaDadosTabela("Preço: " + String.format("%.2f",l.get(i).getPreco()));
        System.out.println("");

        System.out.print("| ");
        for(int i=rangeLinha.getKey() ; i<=rangeLinha.getValue() ; i++)
            printCelulaDadosTabela("Quantidade: " + String.format("%.2f",l.get(i).getQuantidade()));
        System.out.println("");

        printSeparadorTabela(rangeLinha.getValue()-rangeLinha.getKey()+1);
    }

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
        System.out.println("Insira o código do entregador que pretende classificar: ");
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
        System.out.println("Insira Data no formato Year:Month(1 a 12):Day(1 a 31):Hour(0 a 23):Minute(0 a 59)");
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

    public void askByData(){
        System.out.println("Deseja procurar num certo espaço de tempo? (s/S) ou (n/N)");
    }

    public void askByEnt(){
        System.out.println("Deseja procurar por um certo entregador? (s/S) ou (n/N)");
    }

    public void askEnt(){
        System.out.println("Digite o código do entregador a procurar:");
    }

    public void askDataInicio(){
        System.out.println("Digite a data inicial (no formato Year:Month(1 a 12):Day(1 a 31):Hour(0 a 23)):" );
    }

    public void askDataFim(){
        System.out.println("Digite a data final((no formato Year:Month(1 a 12):Day(1 a 31):Hour(0 a 23))):" );
    }

    public void showTotalFat(double f){
        System.out.println("Faturou um total de "+f+" euros");
    }

    /*Menu prints*/

    public void showMainMenu() {
        System.out.println("1.Login" +
                            "\n2.Criar conta" +
                            "\n3.Avançar tempo" +
                            "\n4.Queries do Sistema" +
                            "\n5.Sair");
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
                            "\n2.Verificar ofertas de transporte" +
                            "\n3.Ver Entregas efetuadas por tempo e por um Entregador" +
                            "\n4.Classificar entregadores" +
                            "\n5.Mudar de Conta" +
                            "\n0.Sair" );
    }

    public void showVoluntarioOptions() {
        System.out.println("1.Ver pedidos de entrega" +
                            "\n2.Pedir Entrega"+
                            "\n3.Fazer Entrega"+
                            "\n4.Histórico de entregas efetuadas"+
                            "\n5.Mudar de Conta"+
                            "\n0.Sair");
    }

    public void showTransportadoraOptions() {
        System.out.println("1.Ver pedidos de entrega" +
                "\n2.Calcular preco de transporte"+
                "\n3.Propor Entrega"+
                "\n4.Verificar Pedidos Propostos"+
                "\n5.Fazer Entrega"+
                "\n6.Histórico de entregas efetuadas" +
                "\n7.Total Faturado"+
                "\n8.Mudar de Conta" +
                "\n0.Sair");
    }

    public void showQueriesMenu(){
        System.out.println("1.Top 10 Utilizadores" +
                "\n2.Top 10 Transportadoras"+
                "\n3.Voltar Para Menu" +
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
        System.out.println("EXCEPTION OCCURRED: " + s);
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
        System.out.println("A sua Encomenda já está a caminho\nData estimada de entrega: " + t.toString());
    }

    public void encomendaNotReady() {
        System.out.println("A sua Encomenda está a ser preparada");
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
        System.out.println("Preco Total: " + preco+ "\n");
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

    public void apresentaStock(List<InterfaceLinhaEncomenda> l,int pagina,int linhasPagina,int nProdutosLinha) {
        int i=0;
        Map.Entry<Integer,Integer> rangePag = null;
        int npaginas = 0;

        if(l.size()==0)
        {
            System.out.println("Não existem produtos a exibir");
            return;
        }

        /* tamanho default tabela se exceder limites maximos 5*5 */
        if(nProdutosLinha<1 || nProdutosLinha>5) nProdutosLinha = 3;
        if(linhasPagina<1 || linhasPagina>5) linhasPagina = 4;

        //if(nProdutosLinha>l.size()) nProdutosLinha = l.size();

        npaginas = getNumeroPaginas(l.size(),linhasPagina,nProdutosLinha);

        if(pagina>npaginas) pagina = npaginas;
        else if(pagina<1) pagina = 1;

        rangePag = getPagina(l.size(),pagina,linhasPagina,nProdutosLinha);

        printSeparadorTabela(nProdutosLinha);

        for(i=rangePag.getKey();;)
        {
            int j = i+nProdutosLinha-1>rangePag.getValue()? rangePag.getValue() : i+nProdutosLinha-1;
            printLinhaTabela(l,new AbstractMap.SimpleEntry<>(i,j));

            if(j==rangePag.getValue()) break;

            i = i+nProdutosLinha>rangePag.getValue()? rangePag.getValue() : i+nProdutosLinha;
        }

        System.out.println("Pagina "+pagina+"/"+npaginas);
    }

    public void apresentaStockAll(List<InterfaceEncomenda> l){
        int i=0;
        for (InterfaceEncomenda p : l) {
            i++;
            System.out.println("#######Encomenda "+i +"#######");
            System.out.println(p.toString());

        }
    }

    public void apresentaPedidos1(List<Map.Entry<Double,TriploPedido>> l) {
        int i=0;
        for (Map.Entry<Double,TriploPedido> p : l) {
            i++;
            System.out.println("\n#######Pedido "+i +"#######");
            System.out.println(p.getValue().toString());
            System.out.println("Preço de Entrega : "+p.getKey()+"\n");
        }
    }

    public void apresentaPedidos2(List<Map.Entry<InterfaceEncomenda,String>> l) {
        int i=0;
        for (Map.Entry<InterfaceEncomenda,String> p : l) {
            i++;
            System.out.println("\n#######Pedido "+i +"#######");
            System.out.println(p.getKey().toString());
            System.out.println("Status: "+printStatus(p.getValue()));
        }
    }

    public void printHist(List<TriploHist> l){
        int i=0;
        for (TriploHist h : l){
            i++;
            System.out.println("\n#######Entrega "+i +"#######");
            System.out.println(h.toString());
        }
    }

    public void showTop10Users(List<Map.Entry<String,Integer>> top){
        int i=0;
        for (Map.Entry<String,Integer> l : top){
            i++;
            System.out.println("\n####### "+i +"º Utilizador #######");
            System.out.println(l.getKey());
            System.out.println("Número de Encomendas: "+l.getValue());
        }
    }

    public void showTop10Trans(List<Map.Entry<String,Double>> top){
        int i=0;
        for (Map.Entry<String,Double> l : top){
            i++;
            System.out.println("\n####### "+i +"ª Transportadora #######");
            System.out.println(l.getKey());
            System.out.println("Número de Kms Percorridos: "+l.getValue());
        }
    }

    public String printStatus(String stat){
        switch (stat) {
            case "a": return "Aceite";
            case "p": return "Pendente";
            case "c": return "Cancelado";
            case "s": return "Congelado";
            default: return "Error";
        }
    }
    public void askOferta(){
        System.out.println("Deseja aceitar alguma oferta? (s/S) ou (n/N)");
    }

    public void askOfertaMais(){
        System.out.println("Deseja aceitar mais alguma oferta? (s/S) ou (n/N)");
    }

    public void askEfetuar(){
        System.out.println("Deseja efetuar já a encomenda? (s/S) ou (n/N)");
    }

    public void askCodEnc(){
        System.out.println("Digite o código da encomenda:");
    }

    public void askCodTrans(){
        System.out.println("Digite o código da Empresa Transportadora:");
    }

    public void showValTransporte(double val){
        System.out.println("O valor de transporte será: "+val);
    }

    public void pedidoSucesso(){
        System.out.println("Pedido solicitado com sucesso!");
    }

    public void encomendaSucesso(){
        System.out.println("Encomenda efetuada com sucesso!");
    }

    public void fazerEncomenda(){
        System.out.println("Deseja fazer a encomenda atual?");
    }

    public void pedidoAceite() {
        System.out.println("Pedido de entrega aceite!");
    }

    public void naoRaio(){
        System.out.println("Encomenda não se encontra no sei raio de ação");
    }

    public void naoMedical(){
        System.out.println("Não tem permissao para levantar encomendas do tipo medical");
    }

    public void naoPronto(){
        System.out.println("Essa encomenda não se encontra pronta para levantamento");
    }

    public void encomendaEntregue(){
        System.out.println("Encomenda a ser entregue");
    }

    public void acaoIndesponivel(){
        System.out.println("Acão indesponível, está a ser efetuada uma entrega");
    }

    public void pedido(String stat){
        switch (stat){
            case ("x"):
                System.out.println("Esse pedido não existe");
                break;
            case ("s"):
                System.out.println("Esse pedido foi congelado");
                break;
            case ("a"):
                System.out.println("Esse pedido já foi aceite");
                break;
            case ("p"):
                System.out.println("Pedido aceite com sucesso");
                break;
            case ("c"):
                System.out.println("Esse pedido foi cancelado");
                break;
            default:
                System.out.println("Erro no pedido");
        }
    }

    public void existePedido(){
        System.out.println("Esse pedido já foi feito");
    }

    public void classificacao(int res){
        if (res==0) System.out.println("Entregador classificado com sucesso!");
        if (res==2) System.out.println("Entregador já foi classificado recentemente");
        if (res==1) System.out.println("Entregador não encontrado");
    }

    public void LOL(){
        System.out.println("Nunca vai dar erro LOL");
    }
}
