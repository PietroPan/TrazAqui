import java.util.HashMap;
import java.util.Map;

public class Utilizadores {
    private Map<String,Utilizador> users;

    public Utilizadores() {
        users=new HashMap<>();
    }

    public Utilizadores(Utilizadores users) {
        this.users = users.getUsers();
    }

    public Map<String, Utilizador> getUsers() {
        Map<String,Utilizador> res = new HashMap<>();
        for (Map.Entry<String, Utilizador> e : users.entrySet()) {
            res.put(e.getKey(),e.getValue().clone());
        }
        return res;
    }

    public void setUsers(Map<String, Utilizador> users) {
        this.users = new HashMap<>();
        for (Map.Entry<String,Utilizador> e : users.entrySet()) {
            this.users.put(e.getKey(),e.getValue().clone());
        }
    }

    public Utilizador getUser(String cod) {
        return users.get(cod).clone();
    }

    public void addUser(Utilizador u) {
        users.put(u.getCodigo(),u.clone());
    }

}
