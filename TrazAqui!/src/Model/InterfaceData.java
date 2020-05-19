import java.awt.geom.Point2D;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

    void encomenda(InterfaceEncomenda e, double preco) throws NotEnoughMoneyException;

    void aceitar(String entrega, String enc, double time);

    InterfaceEncomenda getEncomenda(String id);

    double calculaDistTotal(Point2D p1, Point2D p2, Point2D p3);

    Set<String[]> getEntregadoresDisp(String id) throws UtilizadorInexistenteException, LojaInexistenteException;

    void addEncomendaLoja(InterfaceEncomenda e);

    void classifica(Set<Map.Entry<Boolean, String>> encomendasID, String eID, String codUser, float c) throws UtilizadorInexistenteException;

    List<String> getVoluntarioRequests(String cod) throws EntregadorInexistenteException, UtilizadorInexistenteException, LojaInexistenteException;

    double getTempoEsperado(String idEntregador, String idEnc) throws EntregadorInexistenteException, LojaInexistenteException, UtilizadorInexistenteException;

    void denyAll(String cod) throws EntregadorInexistenteException;

    void resetMessages(String cod);

    void maquinaTempo(int horas, int minutos);

    void atualizaEstado();
}
