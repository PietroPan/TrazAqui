package MVC.Model.Utilizadores;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import Exceptions.*;
import Common.*;
import MVC.Model.Entregadores.InterfaceTransportadora;

public class Utilizadores implements InterfaceUtilizadores, Serializable {
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
    public void pay(String e, double money) throws NotEnoughMoneyException {
        InterfaceUtilizador u =this.users.get(e);
        double after =u.getBalance()-money;
        if (after<0) {
            throw new NotEnoughMoneyException("O balanço da sua conta é insuficiente, a encomenda não pode ser feita");
        }
        u.setBalance(after);
    }

    @Override
    public void addMessageToUser(String cod, String message) {
        this.users.get(cod).addMessage(message);
    }

    @Override
    public void resetMessages(String cod) {
        this.users.get(cod).setMessages(new ArrayList<>());
    }

    @Override
    public void atualizaEstado(List<InterfaceEncomenda> l, Map<String, List<String>> m) {

        for(Map.Entry<String,List<String>> mEntry : m.entrySet()) {
            InterfaceUtilizador u =this.users.get(mEntry.getKey());
            for (String mens : mEntry.getValue()) {
                u.addMessage(mens);
            }
        }
        for (InterfaceEncomenda e : l) {
            this.users.get(e.getDestino()).atualizaEstado(e);
        }
    }

    @Override
    public void addPedido(InterfaceEncomenda enc,String trans){
        InterfaceUtilizador aux = this.users.get(enc.getDestino());
        aux.addPedido(enc,trans);
    }

    @Override
    public void alteraPedido(InterfaceEncomenda enc,String trans,String stat){
        InterfaceUtilizador aux = this.users.get(enc.getDestino());
        aux.alteraPedido(enc,trans,stat);
    }

    @Override
    public void addEntregue(String uti,String enc){
        this.users.get(uti).addEntregue(enc);
    }
}
