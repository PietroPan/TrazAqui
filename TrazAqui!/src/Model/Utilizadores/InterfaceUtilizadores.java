import java.util.Map;

public interface InterfaceUtilizadores {
    Map<String, InterfaceUtilizador> getUsers();

    void setUsers(Map<String, InterfaceUtilizador> users);

    InterfaceUtilizador getUser(String cod) throws UtilizadorInexistenteException;

    void addUser(InterfaceUtilizador u);

    void addMessageToUser(String cod, String message);

    void resetMessages(String cod);
}
