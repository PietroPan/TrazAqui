import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Utilizadores implements InterfaceUtilizadores {
    private Map<String, InterfaceUtilizador> users;

    public Utilizadores() {
        users=new HashMap<>();
    }

    public Utilizadores(InterfaceUtilizadores users) {
        this.users = users.getUsers();
    }

    @Override
    public Map<String, InterfaceUtilizador> getUsers() {
        Map<String, InterfaceUtilizador> res = new HashMap<>();
        for (Map.Entry<String, InterfaceUtilizador> e : users.entrySet()) {
            res.put(e.getKey(),e.getValue().clone());
        }
        return res;
    }

    @Override
    public void setUsers(Map<String, InterfaceUtilizador> users) {
        this.users = new HashMap<>();
        for (Map.Entry<String, InterfaceUtilizador> e : users.entrySet()) {
            this.users.put(e.getKey(),e.getValue().clone());
        }
    }

    @Override
    public InterfaceUtilizador getUser(String cod) throws UtilizadorInexistenteException {
        if (users.containsKey(cod))
            return users.get(cod).clone();
        else
            throw new UtilizadorInexistenteException("InterfaceUtilizador não registado");
    }

    @Override
    public void addUser(InterfaceUtilizador u) {
        users.put(u.getCodigo(),u.clone());
    }

    @Override
    public void addMessageToUser(String cod, String message) {
        this.users.get(cod).addMessage(message);
    }

    @Override
    public void resetMessages(String cod) {
        this.users.get(cod).setMessages(new ArrayList<>());
    }
}
