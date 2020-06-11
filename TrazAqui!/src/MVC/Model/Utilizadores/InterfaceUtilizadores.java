package MVC.Model.Utilizadores;

import java.util.List;
import java.util.Map;
import Common.*;
import Exceptions.*;

public interface InterfaceUtilizadores {
    Map<String, InterfaceUtilizador> getUsers();

    void setUsers(Map<String, InterfaceUtilizador> users);

    InterfaceUtilizador getUser(String cod) throws UtilizadorInexistenteException;

    void addUser(InterfaceUtilizador u);

    void pay(String e, double money) throws NotEnoughMoneyException;

    void addMessageToUser(String cod, String message);

    void resetMessages(String cod);

    void atualizaEstado(Map<String, List<String>> m);

    void addPedido(InterfaceEncomenda enc,String trans);

    void alteraPedido(InterfaceEncomenda enc,String trans,String stat);

    void addEntregue(String uti,String enc);

    void alteraTodosPedidosIf(String trans,String stat,String statif);

    String checkStatPedido(String enc,String trans,String user);

    void atualizaPedidos(List<String> trans);

    void rejeitaPedidos(String enc);

    boolean isFree (String usr,String enc);
}
