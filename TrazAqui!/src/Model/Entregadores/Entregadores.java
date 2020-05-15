import java.util.HashMap;
import java.util.Map;

public class Entregadores {
    private Map<String,Entregador> entregadores;

    public Entregadores() {
        entregadores=new HashMap<>();
    }

    public Entregadores(Entregadores e) {
        entregadores=e.getEntregadores();
    }

    public Map<String, Entregador> getEntregadores() {
       Map<String,Entregador> res = new HashMap<>();
       for (Map.Entry<String, Entregador> e : entregadores.entrySet()) {
           res.put(e.getKey(),e.getValue().clone());
        }
       return res;
    }

    public void setEntregadores(Map<String, Entregador> entregadores) {
        this.entregadores = new HashMap<>();
        for (Map.Entry<String,Entregador> e : entregadores.entrySet()) {
            this.entregadores.put(e.getKey(),e.getValue().clone());
        }
    }

    public Entregador getEntregador(String e) {
        return this.entregadores.get(e).clone();
    }

    public void setEntregador(String s,Entregador e) {
        this.entregadores.put(s,e.clone());
    }

    @Override
    public String toString() {
        return "Entregadores: " + entregadores.toString();
    }

    public void addPedidoVoluntario(String idV,String enc) {
        ((Voluntario)this.entregadores.get(idV)).addPedido(enc);
    }

    public void addEncomenda(String s,Encomenda e) {
        entregadores.get(s).addEncomenda(e);
    }

    public void classifica(Encomenda e,float c) {
        for (Entregador en : this.entregadores.values()) {
            if (en.getHistorico().stream().anyMatch(l -> l.equals(e))) {
                en.classifica(c);
                break;
            }
        }
    }
}
