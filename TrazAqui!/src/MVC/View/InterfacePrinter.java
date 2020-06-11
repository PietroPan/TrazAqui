package MVC.View;

import Common.InterfaceEncomenda;
import Common.InterfaceLinhaEncomenda;
import Common.TriploHist;
import Common.TriploPedido;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface InterfacePrinter {
    void apresentaMenuTabela();

    void apresentaMenuTabelaLoja();

    void askLinhasTabela();

    void askColunasTabela();

    void askPagina(int nPaginas);

    void apresentaTotalProdutosStock(List<InterfaceLinhaEncomenda> l);

    int getNumeroPaginas(int dataSize, int linhasPagina, int nProdutosLinha);

    Map.Entry<Integer,Integer> getPagina(int dataSize, int pagina, int linhasPagina, int nProdutosLinha);

    void printSeparadorTabela(int nProdutosLinha);

    void printCelulaDadosTabela(String data);

    void printLinhaTabela(List<InterfaceLinhaEncomenda> l, Map.Entry<Integer, Integer> rangeLinha);

    void voluntarioLivre();

    void askCod();

    void askNew();

    void askPassword();

    void askUserName();

    void askBalance();

    void askLocalizacao(String eixo);

    void askEncomendaId();

    void askEntregadorId();

    void askLojaID();

    void askMedical();

    void askLinhaEnc();

    void askCodProduto();

    void askCodProdutoAlt();

    void askCodProdutoRm();

    void askDescricao();

    void askQuantidade();

    void askQuantidadeProd();

    void askPrecoProd();

    void askConfirmacao();

    void askClassificacao();

    void askData();

    void askVelocidadeNormal();

    void askRaio();

    void askNIF();

    void askCusto(String t);

    void askNEncomendas();

    void askTamFila();

    void askTempoAtendimento();

    void askQuantoTempo();

    void askByData();

    void askByEnt();

    void askEnt();

    void askDataInicio();

    void askDataFim();

    void showTotalFat(double f);

    void showMainMenu();

    void showLoginOptions();

    void showUserOptions();

    void showVoluntarioOptions();

    void showTransportadoraOptions();

    void showLojaOptions();

    void showSystemMenu();

    void showBye();

    void showFeed();

    void showObrigado();

    void exception(String s);

    void fileNotFound();

    void invalid(String s);

    void nadaAApresentar();

    void naoRegistado(String v);

    void encomendaACaminho(LocalDateTime t);

    void encomendaNotReady();

    void apresentaEntregador(String[] s);

    void apresentaEntregadores(Set<String[]> s);

    void apresentaEncomenda(String enc);

    void apresentaPrecoEnc(double preco);

    void apresentaUserEncomendas(Set<String> classifica);

    void apresentaListRequest(List<String> ls);

    void apresentaUnreadMessages(List<String> ls);

    void apresentaStock(List<InterfaceLinhaEncomenda> l, int pagina, int linhasPagina, int nProdutosLinha);

    void apresentaStockAll(List<InterfaceEncomenda> l);

    void apresentaPedidos1(List<Map.Entry<Double, TriploPedido>> l);

    void apresentaPedidos2(List<Map.Entry<InterfaceEncomenda, String>> l);

    void printHist(List<TriploHist> l);

    void printHist2(List<TriploHist> l);

    void showTop10Users(List<Map.Entry<String, Integer>> top);

    void showTop10Trans(List<Map.Entry<String, Double>> top);

    String printStatus(String stat);

    void askFileName();

    void askOferta();

    void askOfertaMais();

    void askEfetuar();

    void askCodEnc();

    void askCodTrans();

    void showValTransporte(double val);

    void pedidoSucesso();

    void encomendaSucesso();

    void fazerEncomenda();

    void pedidoAceite();

    void naoRaio();

    void naoMedical();

    void naoPronto();

    void encomendaEntregue();

    void acaoIndisponivel();

    void pedido(String stat);

    void existePedido();

    void classificacao(int res);

    void LOL();

    void askEnt2();

    void classInv();

    void encInv();

    void foiAceite();
}
