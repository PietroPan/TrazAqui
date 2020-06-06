package MVC.Model;

import java.awt.geom.Point2D;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
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

    boolean encomendaACaminho(String id, String user);

    boolean encomendaNotReady(String id, String user);

    String voluntarioAvailable(String enc) throws UtilizadorInexistenteException, LojaInexistenteException;

    void askVoluntario(String idVoluntario, String idEnc);

    List<InterfaceLinhaEncomenda> formaListaDeLinhasEncomenda(String loja, List<Map.Entry<String, Double>> l) throws ProductNotAvailableException;

    void encomenda(InterfaceEncomenda e, double preco) throws NotEnoughMoneyException;

    void aceitar(String entrega, String enc, double time);

    InterfaceEncomenda getEncomendaPassado(String id);

    InterfaceEncomenda getEncomenda(String id);

    double calculaDistTotal(Point2D p1, Point2D p2, Point2D p3);

    Set<String[]> getEntregadoresDisp(String id) throws UtilizadorInexistenteException, LojaInexistenteException;

    void classifica(Set<Map.Entry<Boolean, String>> encomendasID, String eID, String codUser, float c) throws UtilizadorInexistenteException;

    List<String> getVoluntarioRequests(String cod) throws EntregadorInexistenteException, UtilizadorInexistenteException, LojaInexistenteException;

    double getTempoEsperado(String idEntregador, String idEnc) throws EntregadorInexistenteException, LojaInexistenteException, UtilizadorInexistenteException;

    void denyAll(String cod) throws EntregadorInexistenteException;

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
}
