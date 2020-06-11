package MVC.Model.Utilizadores;

import java.util.List;
import java.util.Map;
import java.util.Set;
import Common.*;

public interface InterfaceUtilizador extends InterfaceBasicInfo {

    void setMessages(List<String> messages);

    void setBalance(double balance);

    double getBalance();

    void setPedidosEntregues(Set<Map.Entry<Boolean, String>> s);

    Set<Map.Entry<Boolean,String>> getPedidosEntregues();

    List<TriploPedido> getPedidos();

    String toString();

    boolean equals(Object user);

    InterfaceUtilizador clone();

    void addEntregue(String cod);

    void atualizaEstado(InterfaceEncomenda e);

    void addPedido(InterfaceEncomenda enc,String trans);

    void addPedido(InterfaceEncomenda enc,String trans,String stat);

    void alteraPedido(InterfaceEncomenda enc,String trans,String stat);

    InterfaceUtilizador alteraTodosPedidosIf(String trans,String stat,String statif);

    void addPedidos(List<InterfaceEncomenda> encs,String trans,String stat);

    String checkStatPedido(String enc,String trans);

    void rejeitaPedidos(String enc);

    boolean isFree(String enc);
}
