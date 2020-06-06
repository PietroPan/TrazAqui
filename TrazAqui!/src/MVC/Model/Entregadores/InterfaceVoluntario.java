package MVC.Model.Entregadores;

import java.util.List;
import Common.*;

public interface InterfaceVoluntario extends InterfaceBasicInfo, InterfaceEntregador {
    InterfaceEncomenda getEncomenda();

    List<String> getPedidos();

    String toString();

    InterfaceEntregador clone();

    boolean hasRoomAndMed(boolean med);

    void addEncomenda(InterfaceEncomenda e);

    void addPedido(String enc);

    void denyAllRequests();

    void atualizaAtual(InterfaceEncomenda enc);
}
// u898 e288 v244