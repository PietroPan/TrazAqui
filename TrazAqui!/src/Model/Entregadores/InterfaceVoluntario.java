import java.util.List;

public interface InterfaceVoluntario extends InterfaceBasicInfo, InterfaceEntregador {
    InterfaceEncomenda getEncomenda();

    List<String> getPedidos();

    String toString();

    InterfaceEntregador clone();

    boolean hasRoomAndMed(boolean med);

    void addEncomenda(InterfaceEncomenda e);

    void addPedido(String enc);

    void denyAllRequests();
}
