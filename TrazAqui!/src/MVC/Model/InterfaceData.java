package MVC.Model;

import java.awt.geom.Point2D;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import Common.*;
import Exceptions.*;
import MVC.Model.Utilizadores.*;
import MVC.Model.Entregadores.*;
import MVC.Model.Lojas.*;


public interface InterfaceData {

    LocalDateTime getHoras();

    void setHoras(LocalDateTime d);

    InterfaceUtilizador getUser(String cod) throws UtilizadorInexistenteException;

    void addUser(InterfaceUtilizador u);

    InterfaceEntregador getEntregador(String cod) throws EntregadorInexistenteException;

    void addEntregador(InterfaceEntregador e);

    InterfaceLoja getLoja(String cod) throws LojaInexistenteException;

    void addLoja(InterfaceLoja l);

    void readFile() throws java.io.IOException;

    boolean encomendaNotReady(String id, String user);

    List<InterfaceLinhaEncomenda> formaListaDeLinhasEncomenda(String loja, List<Map.Entry<String, Double>> l) throws ProductNotAvailableException;

    void encomenda(InterfaceEncomenda e, double preco) throws NotEnoughMoneyException, LojaInexistenteException;

    InterfaceEncomenda getEncomenda(String id);

    double calculaDistTotal(Point2D p1, Point2D p2, Point2D p3);

    void resetMessages(String cod);

    void maquinaTempo(int horas, int minutos);

    void atualizaEstado();

    List<InterfaceLinhaEncomenda> getStock(String l);

    String gerarCodUser();

    String gerarCodLoja();

    String gerarCodVol();

    String gerarCodTrans();

    double getDistTotal(String idEntregador,String idEnc) throws EntregadorInexistenteException, LojaInexistenteException, UtilizadorInexistenteException;

    List<InterfaceEncomenda> getEncomendasDisp(String trans) throws EntregadorInexistenteException, UtilizadorInexistenteException;

    void fazerEncomenda(String cod) throws EntregadorInexistenteException , LojaInexistenteException, UtilizadorInexistenteException;

    void aceitarPedido(InterfaceEncomenda enc,String trans);

    List<Boolean> fazerPedido(InterfaceEncomenda enc,String trans) throws EntregadorInexistenteException, LojaInexistenteException, UtilizadorInexistenteException;

    boolean isNear (InterfaceEntregador cod,InterfaceLoja loja,InterfaceUtilizador uti);

    void addEncomendaVol (InterfaceEncomenda enc,String vol);

    boolean isAEntregar(String cod);

    String checkStatPedido(String enc,String trans,String user);

    void atualizaPedidos(List<String> trans);

    boolean existePedido(String trans,String enc);

    List<TriploHist> getHistorico(String cod);

    int classificaEnt(String ent,String user,float clas);

    List<TriploHist> getHistoricoByEnt(String ent,List<TriploHist> l);

    List<TriploHist> getHistoricoByDate(LocalDateTime after,LocalDateTime before,List<TriploHist> l);

    void atualizaHistorico(Map<String,List<InterfaceEncomenda>> m);

    List<Map.Entry<Double,TriploPedido>> getByPreco (List<TriploPedido> l) throws EntregadorInexistenteException, LojaInexistenteException, UtilizadorInexistenteException;

    double totalFaturado(String trans,LocalDateTime after, LocalDateTime before) throws EntregadorInexistenteException, LojaInexistenteException, UtilizadorInexistenteException;

    List<Map.Entry<String,Integer>> top10Users();

    List<Map.Entry<String,Double>> top10Trans() throws EntregadorInexistenteException, LojaInexistenteException, UtilizadorInexistenteException;

    void mudarPreco(String loja, String cod, double preco);

    void mudarQuantidade(String loja, String cod, double qnt);

    void addToStock(String loja,InterfaceLinhaEncomenda l);

    void removeFromStock(String loja, String cod);

    String timeLeft(String ent,String enc);

    boolean isFree(String enc,String user);

    String gerarCodEnc();
}
