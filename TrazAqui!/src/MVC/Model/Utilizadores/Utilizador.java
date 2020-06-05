package MVC.Model.Utilizadores;
/**
 * Escreva a descrição da classe InterfaceUtilizador aqui.
 * 
 * @author (seu nome) 
 * @version (número de versão ou data)
 */

import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import Common.*;

public class Utilizador extends BasicInfo implements InterfaceUtilizador, Serializable {
    private double balance;
    private Set<Map.Entry<Boolean,String>> pedidosEntregues;
    private List<String> messages;
    private List<TriploPedido> pedidos;

    public Utilizador() {
        this.setNome("NoName");
        this.setCodigo("n/a");
        this.setPassword("n/a");
        this.setPosicao(new Point2D.Double(0, 0));
        this.setPedidosEntregues(new HashSet<>());
        this.balance = 0.00;
        this.pedidosEntregues=new HashSet<>();
        this.messages=new ArrayList<>();
        this.pedidos= new ArrayList<>();
    }

    public Utilizador(String codUtilizador, String password, String nome, double balance, Point2D pos, Set<Map.Entry<Boolean,String>> pedidosEntregues, List<String> messages) {
        this.setNome(nome);
        this.setCodigo(codUtilizador);
        this.setPassword(password);
        this.setPosicao(pos);
        this.setPedidosEntregues(pedidosEntregues);
        this.balance = balance;
        this.messages = new ArrayList<>(messages);
        this.pedidos = new ArrayList<>();
    }

    public Utilizador(InterfaceUtilizador u) {
        this.setNome(u.getNome());
        this.setCodigo(u.getCodigo());
        this.setPassword(u.getPassword());
        this.setPosicao((Point2D)u.getPosicao().clone());
        this.balance = u.getBalance();
        this.pedidosEntregues = u.getPedidosEntregues();
        this.messages=u.getMessages();
        this.pedidos=u.getPedidos();
    }

    @Override
    public List<String> getMessages() {
        return new ArrayList<>(messages);
    }

    @Override
    public void setMessages(List<String> messages) {
        this.messages = new ArrayList<>(messages);
    }

    @Override
    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public double getBalance() {
        return this.balance;
    }

    @Override
    public void setPedidosEntregues(Set<Map.Entry<Boolean, String>> s) {
        this.pedidosEntregues = new HashSet<>(s);
    }

    @Override
    public Set<Map.Entry<Boolean,String>> getPedidosEntregues() {
        return new HashSet<>(pedidosEntregues);
    }

    @Override
    public List<TriploPedido> getPedidos(){
        return new ArrayList<>(pedidos);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("Nome: ").append(this.getNome()).append("\nCodigo do InterfaceUtilizador: ").append(this.getCodigo()).append("\nBalance:").append(this.balance).append("\nPosição: (").append(this.getPosicao().getX()).append(",").append(this.getPosicao().getY()).append(")").append("\nID's de Encomendas Entregues: ").append(this.pedidosEntregues);
        return s.toString();
    }

    @Override
    public boolean equals(Object user) {
        InterfaceUtilizador u;
        if (user == null || user.getClass() != this.getClass()) return false;
        u = (InterfaceUtilizador) user;
        return this.getCodigo().equals(u.getCodigo()) && this.getPassword().equals(u.getPassword());
    }

    @Override
    public InterfaceUtilizador clone() {
        return new Utilizador(this);
    }

    @Override
    public void addEntregue(String cod) {
        this.pedidosEntregues.add(new AbstractMap.SimpleEntry<>(false, cod));
    }

    @Override
    public void addMessage(String message) {
        this.messages.add(message);
    }

    @Override
    public void atualizaEstado(InterfaceEncomenda e) {
        this.messages.add("A sua Encomenda de id "+e.getCodEncomenda()+" foi entregue");
        this.pedidosEntregues.add(new AbstractMap.SimpleEntry<>(false,e.getCodEncomenda()));
    }

    @Override
    public void addPedido(InterfaceEncomenda enc, String trans) {
        this.pedidos.add(new TriploPedido(enc,trans,"p"));
    }

    @Override
    public void addPedido(InterfaceEncomenda enc, String trans, String stat) {
        this.pedidos.add(new TriploPedido(enc,trans,stat));
    }

    @Override
    public void alteraPedido(InterfaceEncomenda enc, String trans, String stat) {
        this.pedidos=this.getPedidos().stream().filter(i->!(i.getEnc().getCodEncomenda().equals(enc.getCodEncomenda())&&i.getTrans().equals(trans))).collect(Collectors.toList());
        this.addPedido(enc,trans,stat);
    }


}
