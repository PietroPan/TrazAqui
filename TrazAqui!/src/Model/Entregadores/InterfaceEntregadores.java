import java.util.Map;

public interface InterfaceEntregadores {
    Map<String, InterfaceEntregador> getEntregadores();

    void setEntregadores(Map<String, InterfaceEntregador> entregadores);

    InterfaceEntregador getEntregador(String e) throws EntregadorInexistenteException;

    void setEntregador(String s, InterfaceEntregador e);

    @Override
    String toString();

    void addPedidoVoluntario(String idV, String enc);

    void addEncomenda(String s, InterfaceEncomenda e);

    void classifica(InterfaceEncomenda e, float c);

    void denyAll(String cod);
}
