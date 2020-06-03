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

    void atualizaEstado(List<InterfaceEncomenda> e, Map<String, List<String>> m);
}
