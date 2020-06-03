package MVC.Model.Entregadores;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    public InterfaceEncomenda getEncomendaPassado(String id) {
        InterfaceEncomenda r=null;
        for (InterfaceEntregador e : this.entregadores.values()) {
            if ((r=e.getEncomendaPassado(id))!=null)
                return r;
        }
        return r;
    }

    @Override
    public void classifica(InterfaceEncomenda e, float c) {
        for (InterfaceEntregador en : this.entregadores.values()) {
            if (en.getHistorico().stream().anyMatch(l -> l.equals(e))) {
                en.classifica(c);
                break;
            }
        }
    }

    @Override
    public void denyAll(String cod) {
        ((InterfaceVoluntario)this.entregadores.get(cod)).denyAllRequests();
    }

    @Override
    public List<InterfaceEncomenda> atualizaEstado(LocalDateTime t) {
        List<InterfaceEncomenda> r = new ArrayList<>();
        for (InterfaceEntregador e : this.entregadores.values()) {
            r.addAll(e.atualizaEstado(t));
        }
        return r;
    }

}
