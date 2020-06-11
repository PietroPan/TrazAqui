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
public class Printer implements InterfacePrinter, Serializable {
    public Printer() {}

    /*Paginacao*/

    @Override
    public void apresentaMenuTabela()
    {
        System.out.println("[a] Adicionar Produto | [r] Remover Produto | [n] Proxima Pagina | [p] Pagina Anterior | [(numero)] Ir para pagina | [q] Finalizar encomenda");
    }

    @Override
    public void apresentaMenuTabelaLoja()
    {
        System.out.println("[a] Adicionar Produto | [s] Mudar Quantidade | [c] Mudar Preco | [r] Remover Produto | [n] Proxima Pagina | [p] Pagina Anterior | [(numero)] Ir para pagina | [q] Terminar Alterações");
    }

    @Override
    public void askLinhasTabela()
    {
        System.out.print("Quantas linhas na tabela(max:5)? ");
    }

    @Override
    public void askColunasTabela()
    {
        System.out.print("Quantas colunas na tabela(max:5)? ");
    }

    @Override
    public void askPagina(int nPaginas)
    {
        System.out.print("Que pagina deseja visualizar("+nPaginas+" paginas)? ");
    }

    @Override
    public void apresentaTotalProdutosStock(List<InterfaceLinhaEncomenda> l)
    {
        System.out.println("Total " + l.size() + " produtos a listar");
    }

    @Override
    public int getNumeroPaginas(int dataSize, int linhasPagina, int nProdutosLinha)
    {
        int npaginas;
        int numProdsPagina = linhasPagina*nProdutosLinha;

        if(dataSize<=numProdsPagina) npaginas=1;
        else if(dataSize%numProdsPagina == 0) npaginas = dataSize / numProdsPagina;
        else npaginas = dataSize/numProdsPagina + 1;

        return npaginas;
    }

    @Override
    public Map.Entry<Integer,Integer> getPagina(int dataSize, int pagina, int linhasPagina, int nProdutosLinha)
    {
        int npaginas;
        int i,f;

        i=(pagina-1)*linhasPagina*nProdutosLinha;
        f = (i+linhasPagina*nProdutosLinha)>dataSize ? dataSize-1 : i+(linhasPagina*nProdutosLinha)-1;

        return new AbstractMap.SimpleEntry<>(i,f);
    }

    @Override
    public void printSeparadorTabela(int nProdutosLinha)
    {
        int sizeInsideBox = 30;

        System.out.print('-');
        for(int i = (sizeInsideBox+1)*nProdutosLinha ; i>0 ; i--)
            System.out.print('-');
        System.out.println("");
    }

    @Override
    public void printCelulaDadosTabela(String data)
    {
        int sizeInsideBox = 30;

        System.out.print(data);

        for(int size=data.length()+1 ; size<sizeInsideBox ; size++)
            System.out.print(' ');
        System.out.print("| ");
    }

    @Override
    public void printLinhaTabela(List<InterfaceLinhaEncomenda> l, Map.Entry<Integer, Integer> rangeLinha)
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

    @Override
    public void voluntarioLivre() {
        System.out.println("Foi escolhido o voluntario livre que deve demorar menos tempo a entregar a sua encomenda");
    }

    @Override
    public void askCod() {
        System.out.println("Insira o seu Código:");
    }
    @Override
    public void askNew() {
        System.out.println("Quer fazer parte da TrazAqui!?(S/s) ou(N/n)");
    }

    @Override
    public void askPassword() {
        System.out.println("Insira a Password: ");
    }

    @Override
    public void askUserName() {
        System.out.println("Insira o seu Nome:");
    }
    
    @Override
    public void askBalance() {
        System.out.println("Insira o balanço atual da sua conta:");
    }
    
    @Override
    public void askLocalizacao(String eixo) {
        System.out.println("Insira a sua localizaçao atual("+eixo+"):");
    }

    @Override
    public void askEncomendaId() {
        System.out.println("Insira o código da sua encomenda:(-1 caso não queira nenhuma)");
    }

    @Override
    public void askEntregadorId() {
        System.out.println("Insira o código do entregador que pretende classificar: ");
    }

    @Override
    public void askLojaID() {
        System.out.println("Insira o código da Loja: ");
    }

    @Override
    public void askMedical() {
        System.out.println("A sua encomenda irá conter produtos médicos?(s/S) ou (n/N) ");
    }

    @Override
    public void askLinhaEnc() {
        System.out.println("Deseja adicionar algum produto ? (s/S) ou (n/N)");
    }

    @Override
    public void askCodProduto() {
        System.out.println("Insira o código do produto a encomendar: ");
    }

    @Override
    public void askCodProdutoAlt() {
        System.out.println("Insira o código do produto a alterar: ");
    }

    @Override
    public void askCodProdutoRm() {
        System.out.println("Insira o código do produto a remover: ");
    }

    @Override
    public void askDescricao() {
        System.out.println("Insira a descrição do produto");
    }

    @Override
    public void askQuantidade() {
        System.out.println("Insira a quantidade que deseja encomendar: ");
    }

    @Override
    public void askQuantidadeProd() {
        System.out.println("Insira a quantidade do produto: ");
    }

    @Override
    public void askPrecoProd() {
        System.out.println("Insira o preco do produto: ");
    }

    @Override
    public void askConfirmacao() {
        System.out.println("Tem a certeza? (s/S) ou (n/N)");
    }

    @Override
    public void askClassificacao() {
        System.out.println("Insira a Classificação que deseja dar: ");
    }

    @Override
    public void askData() {
        System.out.println("Insira Data no formato Year:Month(1 a 12):Day(1 a 31):Hour(0 a 23):Minute(0 a 59)");
    }

    @Override
    public void askVelocidadeNormal() {
        System.out.println("Insira a sua velocidade média(m/s): (Use . para separa casas decimais)");
    }

    @Override
    public void askRaio() {
        System.out.println("Insira o seu raio de ação(km): ");
    }


    @Override
    public void askNIF() {
        System.out.println("Insira o seu NIF");
    }

    @Override
    public void askCusto(String t) {
        System.out.println("Insira o custo por "+t);
    }

    @Override
    public void askNEncomendas() {
        System.out.println("Insira o número de encomendas que pode transportar ao mesmo tempo");
    }

    @Override
    public void askTamFila() {
        System.out.println("Insira o tamanho da fila(-1 caso não queira dar essa informação)");
    }

    @Override
    public void askTempoAtendimento() {
        System.out.println("Insira o tempo normal de atendimento(em min)");
    }

    @Override
    public void askQuantoTempo() {
        System.out.println("Diga quanto tempo deseja ficar em Cryosleep(no formato (Horas:Minutos))");
    }

    @Override
    public void askByData(){
        System.out.println("Deseja procurar num certo espaço de tempo? (s/S) ou (n/N)");
    }

    @Override
    public void askByEnt(){
        System.out.println("Deseja procurar por um certo entregador? (s/S) ou (n/N)");
    }

    @Override
    public void askEnt(){
        System.out.println("Digite o código do entregador a procurar:");
    }

    @Override
    public void askDataInicio(){
        System.out.println("Digite a data inicial (no formato Year:Month(1 a 12):Day(1 a 31):Hour(0 a 23)):" );
    }

    @Override
    public void askDataFim(){
        System.out.println("Digite a data final((no formato Year:Month(1 a 12):Day(1 a 31):Hour(0 a 23))):" );
    }

    @Override
    public void showTotalFat(double f){
        System.out.println("Faturou um total de "+f+" euros");
    }

    /*Menu prints*/

    @Override
    public void showMainMenu() {
        System.out.println("1.Login" +
                            "\n2.Criar conta" +
                            "\n3.Avançar tempo" +
                            "\n4.Opções do Sistema" +
                            "\n5.Sair");
    }

    @Override
    public void showLoginOptions() {
        System.out.println("\n1.Sou um Utilizador" +
                            "\n2.Sou um Voluntario" +
                            "\n3.Sou uma Transportadora"+
                            "\n4.Sou uma Loja" +
                            "\n0.Menu Principal");
    }

    @Override
    public void showUserOptions(){
        System.out.println("\n1.Fazer encomenda" +
                            "\n2.Verificar ofertas de transporte" +
                            "\n3.Ver Entregas efetuadas por tempo e por um Entregador" +
                            "\n4.Classificar entregadores" +
                            "\n5.Verificar tempo de entrega de uma encomenda" +
                            "\n6.Logout" +
                            "\n0.Sair" );
    }

    @Override
    public void showVoluntarioOptions() {
        System.out.println("1.Ver pedidos de entrega" +
                            "\n2.Pedir Entrega"+
                            "\n3.Fazer Entrega"+
                            "\n4.Histórico de entregas efetuadas"+
                            "\n5.Logout"+
                            "\n0.Sair");
    }

    @Override
    public void showTransportadoraOptions() {
        System.out.println("1.Ver pedidos de entrega" +
                "\n2.Calcular preco de transporte"+
                "\n3.Propor Entrega"+
                "\n4.Verificar Pedidos Propostos"+
                "\n5.Fazer Entrega"+
                "\n6.Histórico de entregas efetuadas" +
                "\n7.Total Faturado"+
                "\n8.Logout" +
                "\n0.Sair");
    }

    @Override
    public void showLojaOptions(){
        System.out.println("1.Atualizar Stock" +
                "\n2.Logout" +
                "\n3.Sair");
    }

    @Override
    public void showSystemMenu(){
        System.out.println("\n1.Top 10 Utilizadores" +
                "\n2.Top 10 Transportadoras"+
                "\n3.Guardar estado" +
                "\n4.Carregar estado" +
                "\n5.Voltar Para Menu" +
                "\n0.Sair");
    }

    @Override
    public void showBye() {
        System.out.println("Obrigado por escolher TrazAqui!" +
                            "\nEsperamos que tenha gostado" +
                            "\nDeseja fornecer algum feedback? (S/s)");
    }

    @Override
    public void showFeed() {
        System.out.println(
                "(Ctrl+D para terminar review)");
    }

    @Override
    public void showObrigado() {
        System.out.println("Obrigado por se juntar a TrazAqui!");
    }
    /*Exceptions*/

    @Override
    public void exception(String s) {
        System.out.println("EXCEPTION OCCURRED: " + s);
    }

    @Override
    public void fileNotFound() {
        System.out.println("Ficheiro não encontrado");
    }

    @Override
    public void invalid(String s) {
        System.out.println(s+" invalid(o)/(a)");
    }

    @Override
    public void nadaAApresentar() {
        System.out.println("Nada a Apresentar");
    }

    @Override
    public void naoRegistado(String v) {
        System.out.println(v + " não registado");
    }

    /*Apresenta Resultados*/

    @Override
    public void encomendaACaminho(LocalDateTime t) {
        System.out.println("A sua Encomenda já está a caminho\nData estimada de entrega: " + t.toString());
    }

    @Override
    public void encomendaNotReady() {
        System.out.println("A sua Encomenda está a ser preparada");
    }

    @Override
    public void apresentaEntregador(String[] s) {
        System.out.println("Nome do entregador: " + s[1] + "\nCódigo do entregador: " +s[0] + " Classificação: " + s[2] + " Custo do entregador: " + s[3] + " Tempo Estimado para entrega: " + s[4]);
    }

    @Override
    public void apresentaEntregadores(Set<String[]> s) {
        for (String[] st : s) {
            apresentaEntregador(st);
        }
    }

    @Override
    public void apresentaEncomenda(String enc) {
        System.out.println("Encomenda:\n" + enc + "\n");
    }

    @Override
    public void apresentaPrecoEnc(double preco) {
        System.out.println("Preco Total: " + preco+ "\n");
    }

    @Override
    public void apresentaUserEncomendas(Set<String> classifica) {
        for (String s : classifica) {
            apresentaEncomenda(s);
            System.out.println("\n//////////////////////////////\n");
        }
    }

    @Override
    public void apresentaListRequest(List<String> ls) {
        for (String s : ls) {
            System.out.println(s);
        }
    }

    @Override
    public void apresentaUnreadMessages(List<String> ls) {
        System.out.println("###########################");
        if (ls.isEmpty()) System.out.println("No unread messages.");
        for (String s : ls) {
            System.out.println(s);
        }
        System.out.println("###########################");
    }

    @Override
    public void apresentaStock(List<InterfaceLinhaEncomenda> l, int pagina, int linhasPagina, int nProdutosLinha) {
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

    @Override
    public void apresentaStockAll(List<InterfaceEncomenda> l){
        int i=0;
        for (InterfaceEncomenda p : l) {
            i++;
            System.out.println("\n#######Encomenda "+i +"#######");
            System.out.println("Código Encomenda: "+p.getCodEncomenda());
            System.out.println("Código Utilizador: "+p.getDestino());
            System.out.println("Código Loja: "+p.getOrigem());
            System.out.println("Peso: "+p.getPeso());
        }
    }

    @Override
    public void apresentaPedidos1(List<Map.Entry<Double, TriploPedido>> l) {
        int i=0;
        for (Map.Entry<Double,TriploPedido> p : l) {
            i++;
            System.out.println("\n#######Pedido "+i +"#######");
            System.out.println(p.getValue().toString());
            System.out.println("Preço de Entrega : "+p.getKey()+"\n");
        }
    }

    @Override
    public void apresentaPedidos2(List<Map.Entry<InterfaceEncomenda, String>> l) {
        int i=0;
        for (Map.Entry<InterfaceEncomenda,String> p : l) {
            i++;
            System.out.println("\n#######Pedido "+i +"#######");
            System.out.println("Código Encomenda: "+p.getKey().getCodEncomenda());
            System.out.println("Código Utilizador: "+p.getKey().getDestino());
            System.out.println("Código Loja: "+p.getKey().getOrigem());
            System.out.println("Status: "+printStatus(p.getValue()));
        }
    }

    @Override
    public void printHist(List<TriploHist> l){
        int i=0;
        for (TriploHist h : l){
            i++;
            System.out.println("\n#######Entrega "+i +"#######");
            System.out.println(h.toString());
        }
    }

    @Override
    public void printHist2(List<TriploHist> l){
        int i=0;
        for (TriploHist h : l){
            i++;
            System.out.println("\n#######Entrega "+i +"#######");
            System.out.println(h.toString());

        }
    }

    @Override
    public void showTop10Users(List<Map.Entry<String, Integer>> top){
        int i=0;
        for (Map.Entry<String,Integer> l : top){
            i++;
            System.out.println("\n####### "+i +"º Utilizador #######");
            System.out.println(l.getKey());
            System.out.println("Número de Encomendas: "+l.getValue());
        }
    }

    @Override
    public void showTop10Trans(List<Map.Entry<String, Double>> top){
        int i=0;
        for (Map.Entry<String,Double> l : top){
            i++;
            System.out.println("\n####### "+i +"ª Transportadora #######");
            System.out.println(l.getKey());
            System.out.println("Número de Kms Percorridos: "+l.getValue());
        }
    }

    @Override
    public String printStatus(String stat){
        switch (stat) {
            case "a": return "Aceite";
            case "p": return "Pendente";
            case "c": return "Cancelado";
            case "s": return "Congelado";
            default: return "Error";
        }
    }

    @Override
    public void askFileName(){
        System.out.println("Nome do ficheiro onde guardar");
    }

    @Override
    public void askOferta(){
        System.out.println("Deseja aceitar alguma oferta? (s/S) ou (n/N)");
    }

    @Override
    public void askOfertaMais(){
        System.out.println("Deseja aceitar mais alguma oferta? (s/S) ou (n/N)");
    }

    @Override
    public void askEfetuar(){
        System.out.println("Deseja efetuar já a encomenda? (s/S) ou (n/N)");
    }

    @Override
    public void askCodEnc(){
        System.out.println("Digite o código da encomenda:");
    }

    @Override
    public void askCodTrans(){
        System.out.println("Digite o código da Empresa Transportadora:");
    }

    @Override
    public void showValTransporte(double val){
        System.out.println("O valor de transporte será: "+val);
    }

    @Override
    public void pedidoSucesso(){
        System.out.println("Pedido solicitado com sucesso!");
    }

    @Override
    public void encomendaSucesso(){
        System.out.println("Encomenda efetuada com sucesso!");
    }

    @Override
    public void fazerEncomenda(){
        System.out.println("Deseja fazer a encomenda atual? (s/S)");
    }

    @Override
    public void pedidoAceite() {
        System.out.println("Pedido de entrega aceite!");
    }

    @Override
    public void naoRaio(){
        System.out.println("Encomenda não se encontra no sei raio de ação");
    }

    @Override
    public void naoMedical(){
        System.out.println("Não tem permissao para levantar encomendas do tipo medical");
    }

    @Override
    public void naoPronto(){
        System.out.println("Essa encomenda não se encontra pronta para levantamento");
    }

    @Override
    public void encomendaEntregue(){
        System.out.println("Encomenda a ser entregue");
    }

    @Override
    public void acaoIndisponivel(){
        System.out.println("Acão indisponível, está a ser efetuada uma entrega");
    }

    @Override
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
            case ("r"):
                System.out.println("Esse pedido já foi rejeitado");
                break;
            default:
                System.out.println("Erro no pedido");
        }
    }

    @Override
    public void existePedido(){
        System.out.println("Esse pedido já foi feito");
    }

    @Override
    public void classificacao(int res){
        if (res==0) System.out.println("Entregador classificado com sucesso!");
        if (res==2) System.out.println("Entregador já foi classificado recentemente");
        if (res==1) System.out.println("Entregador não encontrado");
    }

    @Override
    public void LOL(){
        System.out.println("Nunca vai dar erro LOL");
    }

    @Override
    public void askEnt2(){
        System.out.println("Digite o código do Entregador");
    }

    @Override
    public void classInv(){
        System.out.println("Classificação inválida");
    }

    @Override
    public void encInv(){
        System.out.println("Encomenda inválida");
    }

    @Override
    public void foiAceite(){
        System.out.println("Encomenda já foi aceite");
    }
}
