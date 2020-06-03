package MVC.Model.Utilizadores;

import java.util.List;
import java.util.Map;
import java.util.Set;
import Common.*;

public interface InterfaceUtilizador extends InterfaceBasicInfo {
    List<String> getMessages();

    void setMessages(List<String> messages);

    void setBalance(double balance);

    double getBalance();

    void setPedidosEntregues(Set<Map.Entry<Boolean, String>> s);

    Set<Map.Entry<Boolean,String>> getPedidosEntregues();

    String toString();

    boolean equals(Object user);

    InterfaceUtilizador clone();

    void addEntregue(String cod);

    void addMessage(String message);

    void atualizaEstado(InterfaceEncomenda e);
}
