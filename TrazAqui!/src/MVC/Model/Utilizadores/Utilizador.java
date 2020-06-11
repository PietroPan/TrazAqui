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
    private List<TriploPedido> pedidos;

    /**
     * Construtor vazio
     */
    public Utilizador() {
        super();
        this.pedidosEntregues=new HashSet<>();
        this.balance = 0.00;
        this.pedidosEntregues=new HashSet<>();
        this.pedidos= new ArrayList<>();
    }

    /**
     * Construtor parametrizado
     * @param codUtilizador código
     * @param password password
     * @param nome nome
     * @param balance balanço da conta
     * @param pos posição
     * @param pedidosEntregues pedidosEntregues
     */
    public Utilizador(String codUtilizador, String password, String nome, double balance, Point2D pos, Set<Map.Entry<Boolean,String>> pedidosEntregues) {
        super(nome,codUtilizador,pos,password);
        this.setPedidosEntregues(pedidosEntregues);
        this.balance = balance;
        this.pedidos = new ArrayList<>();
    }

    /**
     * Construtor cópia
     * @param u Utilizador a copiar
     */
    public Utilizador(InterfaceUtilizador u) {
        super((BasicInfo) u);
        this.balance = u.getBalance();
        this.pedidosEntregues = u.getPedidosEntregues();
        this.pedidos=u.getPedidos();
    }

    /**
     * Setter para o balanço de uma conta
     * @param balance balanço a colocar
     */
    @Override
    public void setBalance(double balance) {
        this.balance = balance;
    }

    /**
     * Getter para o balanço de uma conta
     * @return balanço
     */
    @Override
    public double getBalance() {
        return this.balance;
    }

    /**
     * Setter de pedidos entregues
     * @param s pedidos entregues
     */
    @Override
    public void setPedidosEntregues(Set<Map.Entry<Boolean, String>> s) {
        this.pedidosEntregues = new HashSet<>(s);
    }

    /**
     * Getter de pedidos entregues
     * @return pedidos entregues
     */
    @Override
    public Set<Map.Entry<Boolean,String>> getPedidosEntregues() {
        return new HashSet<>(pedidosEntregues);
    }

    /**
     * Getter de pedidos prontos
     * @return pedidos prontos cópia
     */
    @Override
    public List<TriploPedido> getPedidos(){
        return new ArrayList<>(pedidos);
    }

    /**
     * Método toString
     * @return informação de um utilizador em forma de string
     */
    @Override
    public String toString() {
        return "Nome: " + this.getNome() + "\nCodigo do InterfaceUtilizador: " + this.getCodigo() + "\nBalance:" + this.balance + "\nPosição: (" + this.getPosicao().getX() + "," + this.getPosicao().getY() + ")" + "\nID's de Encomendas Entregues: " + this.pedidosEntregues;
    }

    /**
     * Método equals
     * @param user objeto ao qual comparar
     * @return true se password e código forem iguais
     */
    @Override
    public boolean equals(Object user) {
        InterfaceUtilizador u;
        if (user == null || user.getClass() != this.getClass()) return false;
        u = (InterfaceUtilizador) user;
        return this.getCodigo().equals(u.getCodigo()) && this.getPassword().equals(u.getPassword());
    }

    /**
     * Método clone
     * @return utilizador cópia
     */
    @Override
    public InterfaceUtilizador clone() {
        return new Utilizador(this);
    }

    /**
     * Método que adiciona um código de encomenda as encomendas entregues ao user
     * @param cod código a adicionar
     */
    @Override
    public void addEntregue(String cod) {
        this.pedidosEntregues.add(new AbstractMap.SimpleEntry<>(false, cod));
    }

    /**
     * Método que atualiza o estado de utilizador
     * @param e encomenda entregue
     */
    @Override
    public void atualizaEstado(InterfaceEncomenda e) {
        this.addMessage("A sua Encomenda de id "+e.getCodEncomenda()+" foi entregue");
        this.pedidosEntregues.add(new AbstractMap.SimpleEntry<>(false,e.getCodEncomenda()));
    }

    /**
     * Método que adiciona um pedido
     * @param enc código de encomenda
     * @param trans transportadora
     */
    @Override
    public void addPedido(InterfaceEncomenda enc, String trans) {
        this.pedidos.add(new TriploPedido(enc,trans,"p"));
    }

    /**
     * Adiconar um pedido com estado
     * @param enc código de encomenda
     * @param trans transportadora
     * @param stat estado
     */
    @Override
    public void addPedido(InterfaceEncomenda enc, String trans, String stat) {
        this.pedidos.add(new TriploPedido(enc,trans,stat));
    }

    /**
     * Método que adiciona vários pedidos
     * @param encs lista de encomendas a adicionar
     * @param trans transportadora que as realizou
     * @param stat estado da encomenda
     */
    @Override
    public void addPedidos(List<InterfaceEncomenda> encs,String trans,String stat){
        for (InterfaceEncomenda i : encs){
            this.addPedido(i,trans,stat);
        }
    }

    /**
     * Alterar um pedido
     * @param enc encomenda
     * @param trans código de entregador
     * @param stat estado da encomenda
     */
    @Override
    public void alteraPedido(InterfaceEncomenda enc, String trans, String stat) {
        this.pedidos=this.getPedidos().stream().filter(i->!(i.getEnc().getCodEncomenda().equals(enc.getCodEncomenda())&&i.getTrans().equals(trans))).collect(Collectors.toList());
        this.addPedido(enc,trans,stat);
    }

    /**
     * alterar todos os pedidos que satisfazem uma condição
     * @param trans tranportadora onde alterar
     * @param stat estado a verificar
     * @param statif estado a colocar se se verificar a condição
     * @return utilizador com os pedidos alterados
     */
    @Override
    public InterfaceUtilizador alteraTodosPedidosIf(String trans,String stat,String statif){
        List<InterfaceEncomenda> aux = new ArrayList<>();
        int n=0;
        for (TriploPedido i : this.getPedidos()){
            if (i.getTrans().equals(trans)&&i.getStat().equals(statif)) {
                n++;
                aux.add(i.getEnc());
            }
        }
        this.pedidos.removeIf(i->i.getStat().equals(statif)&&i.getTrans().equals(trans));
        this.addPedidos(aux,trans,stat);
        if (n>0&&statif.equals("p")) addMessage("Pedidos pendentes feitos pela transportadora "+trans+" foram congelados");
        else if (n>0&&statif.equals("s")) addMessage("Pedidos pendentes feitos pela transportadora "+trans+" foram descongelados");
        return this.clone();
    }

    /**
     * Verificar o estado de um pedido
     * @param enc código de encomenda
     * @param trans transportadora
     * @return x caso não exista a encomenda
     */
    @Override
    public String checkStatPedido(String enc,String trans){
        for (TriploPedido i : this.getPedidos()){
            if (i.getTrans().equals(trans)&&i.getEnc().getCodEncomenda().equals(enc)) return i.getStat();
        }
        return "x";
    }


    /**
     * Rejeita pedidos a mais
     * @param enc encomenda com pedido a mais
     */
    @Override
    public void rejeitaPedidos(String enc){
        for (TriploPedido i : this.pedidos){
            if (i.getEnc().getCodEncomenda().equals(enc)&&!i.getStat().equals("a")){
                i.setStat("r");
            }
        }
    }

    /**
     * Verifica se uma encomenda já foi aceite
     * @param enc Encomenda a verificar
     * @return resultado da verificaçao
     */
    @Override
    public boolean isFree(String enc){
        for (TriploPedido i : this.pedidos){
            if (i.getEnc().getCodEncomenda().equals(enc)&&i.getStat().equals("a")) return false;
        }
        return true;
    }

}
