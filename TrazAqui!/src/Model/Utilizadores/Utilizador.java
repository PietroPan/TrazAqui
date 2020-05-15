
/**
 * Escreva a descrição da classe Utilizador aqui.
 * 
 * @author (seu nome) 
 * @version (número de versão ou data)
 */

import java.awt.geom.Point2D;
import java.util.*;

public class Utilizador extends BasicInfo {
    private double balance;
    private Set<Map.Entry<Boolean,String>> pedidosEntregues;
    private List<String> messages;

    public Utilizador() {
        this.setNome("NoName");
        this.setCodigo("n/a");
        this.setPassword("n/a");
        this.setPosicao(new Point2D.Double(0, 0));
        this.setPedidosEntregues(new HashSet<>());
        this.balance = 0.00;
        this.pedidosEntregues=new HashSet<>();
        this.messages=new ArrayList<>();
    }

    public Utilizador(String codUtilizador, String password, String nome, double balance, Point2D pos,Set<Map.Entry<Boolean,String>> pedidosEntregues,List<String> messages) {
        this.setNome(nome);
        this.setCodigo(codUtilizador);
        this.setPassword(password);
        this.setPosicao(pos);
        this.setPedidosEntregues(pedidosEntregues);
        this.balance = balance;
        this.messages = new ArrayList<>(messages);
    }

    public Utilizador(Utilizador u) {
        this.setNome(u.getNome());
        this.setCodigo(u.getCodigo());
        this.setPassword(u.getPassword());
        this.setPosicao((Point2D)u.getPosicao().clone());
        this.balance = u.getBalance();
        this.pedidosEntregues = u.getPedidosEntregues();
        this.messages=u.getMessages();
    }

    public List<String> getMessages() {
        return new ArrayList<>(messages);
    }

    public void setMessages(List<String> messages) {
        this.messages = new ArrayList<>(messages);
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getBalance() {
        return this.balance;
    }

    public void setPedidosEntregues(Set<Map.Entry<Boolean,String>> s) {
        this.pedidosEntregues = new HashSet<>(s);
    }

    public Set<Map.Entry<Boolean,String>> getPedidosEntregues() {
        return new HashSet<>(pedidosEntregues);
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("Nome: ").append(this.getNome()).append("\nCodigo do Utilizador: ").append(this.getCodigo()).append("\nBalance:").append(this.balance).append("\nPosição: (").append(this.getPosicao().getX()).append(",").append(this.getPosicao().getY()).append(")").append("\nID's de Encomendas Entregues: ").append(this.pedidosEntregues);
        return s.toString();
    }

    public boolean equals(Object user) {
        Utilizador u;
        if (user == null || user.getClass() != this.getClass()) return false;
        u = (Utilizador) user;
        return this.getCodigo().equals(u.getCodigo()) && this.getPassword().equals(u.getPassword());
    }

    public Utilizador clone() {
        return new Utilizador(this);
    }

    public void addEntregue (String cod) {
        this.pedidosEntregues.add(new AbstractMap.SimpleEntry<>(false, cod));
    }

    public void addMessage (String message) {
        this.messages.add(message);
    }

}
