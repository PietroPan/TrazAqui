package MVC.Model.Entregadores;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import Common.*;
import Exceptions.*;

public class Entregadores implements InterfaceEntregadores, Serializable {
    private Map<String, InterfaceEntregador> entregadores;

    public Entregadores() {
        entregadores=new HashMap<>();
    }

    public Entregadores(InterfaceEntregadores e) {
        entregadores=e.getEntregadores();
    }

    @Override
    public Map<String, InterfaceEntregador> getEntregadores() {
       Map<String, InterfaceEntregador> res = new HashMap<>();
       for (Map.Entry<String, InterfaceEntregador> e : entregadores.entrySet()) {
           res.put(e.getKey(),e.getValue().clone());
        }
       return res;
    }

    @Override
    public void setEntregadores(Map<String, InterfaceEntregador> entregadores) {
        this.entregadores = new HashMap<>();
        for (Map.Entry<String, InterfaceEntregador> e : entregadores.entrySet()) {
            this.entregadores.put(e.getKey(),e.getValue().clone());
        }
    }

    @Override
    public InterfaceEntregador getEntregador(String e) throws EntregadorInexistenteException {
        if (this.entregadores.containsKey(e))
            return this.entregadores.get(e).clone();
        else
            throw new EntregadorInexistenteException("InterfaceEntregador inexistente");
    }

    @Override
    public void setEntregador(String s, InterfaceEntregador e) {
        this.entregadores.put(s,e.clone());
    }

    @Override
    public String toString() {
        return "Entregadores: " + entregadores.toString();
    }

    @Override
    public void addPedidoVoluntario(String idV, String enc) {
        ((InterfaceVoluntario)this.entregadores.get(idV)).addPedido(enc);
    }

    @Override
    public void addEncomenda(String s, InterfaceEncomenda e) {
        entregadores.get(s).addEncomenda(e);
    }

    @Override
    public boolean encomendaACaminho(String id, String s) {
        for (InterfaceEntregador e : this.entregadores.values()) {
            if (e.encomendaACaminho(id,s))
                return true;
        }
        return false;
    }

    @Override
    public void classificaUser(String cod,float clas){
        this.entregadores.get(cod).classifica(clas);
    }

    @Override
    public void denyAll(String cod) {
        ((InterfaceVoluntario)this.entregadores.get(cod)).denyAllRequests();
    }

    @Override
    public Map<String,List<InterfaceEncomenda>> atualizaEstado(LocalDateTime t) {
        Map<String,List<InterfaceEncomenda>> r = new HashMap<>();
        for (InterfaceEntregador e : this.entregadores.values()) {
            r.put(e.getCodigo(),e.atualizaEstado(t));
        }
        return r;
    }

    @Override
    public void addPedido(InterfaceEncomenda enc,String trans){
        InterfaceTransportadora aux = ((InterfaceTransportadora)this.entregadores.get(trans));
        aux.addPedido(enc);
    }

    @Override
    public void alteraPedido(InterfaceEncomenda enc,String trans,String stat){
        InterfaceTransportadora aux = ((InterfaceTransportadora)this.entregadores.get(trans));
        aux.alteraPedido(enc,stat);
    }

    @Override
    public void clearAtual(String trans){
        InterfaceTransportadora aux = (InterfaceTransportadora)this.entregadores.get(trans);
        aux.clearAtual();
    }

    @Override
    public void addMessage(String cod, String message) {
        this.entregadores.get(cod).addMessage(message);
    }

    @Override
    public void atualizaAtual(String cod,InterfaceEncomenda enc){
        if (cod.contains("t")){
            InterfaceTransportadora trans = (InterfaceTransportadora)this.entregadores.get(cod);
            trans.atualizaAtual(enc);
        }
        else {
            InterfaceVoluntario vol = (InterfaceVoluntario)this.entregadores.get(cod);
            vol.atualizaAtual(enc);
        }
    }

    @Override
    public void resetMessages(String cod) {
        this.entregadores.get(cod).setMessages(new ArrayList<>());
    }

    @Override
    public void setAEntregar(String cod,boolean b){
        this.entregadores.get(cod).setAEntregar(b);
    }

    @Override
    public boolean isAEntregar(String cod){
        return this.entregadores.get(cod).isAEntregar();
    }

    @Override
    public boolean hasRoom(String trans) {
        InterfaceTransportadora t = (InterfaceTransportadora) this.entregadores.get(trans);
        return t.hasRoom();
    }

    @Override
    public void alteraTodosPedidosIf(String trans,String stat,String statIf){
        InterfaceTransportadora t = (InterfaceTransportadora) this.entregadores.get(trans);
        t.alteraTodosPedidosIf(stat,statIf);
    }

    @Override
    public List<String> getAllFree(){
        return this.entregadores.entrySet().stream().filter(i->i.getKey().contains("t")&&!(i.getValue().isAEntregar())).map(Map.Entry::getKey).collect(Collectors.toList());
    }

    @Override
    public boolean existePedido(String trans,String enc){
        InterfaceTransportadora t = (InterfaceTransportadora)this.entregadores.get(trans);
        return t.existePedido(enc);
    }
}
