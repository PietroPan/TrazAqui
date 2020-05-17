import java.util.HashMap;
import java.util.Map;

public class Lojas {
    private Map<String,Loja> lojas;

    public Lojas() {
        lojas=new HashMap<>();
    }

    public Lojas(Lojas lojas) {
        this.lojas = lojas.getLojas();
    }
    public Map<String, Loja> getLojas() {
        Map<String,Loja> res = new HashMap<>();
        for (Map.Entry<String, Loja> e : lojas.entrySet()) {
            res.put(e.getKey(),e.getValue().clone());
        }
        return res;
    }

    public void setLojas(Map<String,Loja> lojas) {
        this.lojas = new HashMap<>();
        for (Map.Entry<String,Loja> e : lojas.entrySet()) {
            this.lojas.put(e.getKey(),e.getValue().clone());
        }
    }

    public Loja getLoja(String e) throws LojaInexistenteException {
        if (this.lojas.containsKey(e))
            return this.lojas.get(e).clone();
        else throw new LojaInexistenteException("Loja inexistente");
    }

    public void setLoja(String s,Loja l) {
        this.lojas.put(s,l.clone());
    }

    @Override
    public String toString() {
        return "Lojas=" + lojas.toString();
    }

    public void addEncomenda(String s,Encomenda e) {
        lojas.get(s).addPronta(e);
    }

    public void removeReady(String s,String e) {
        lojas.get(s).removeReady(e);
    }

    public void addPronta(Encomenda e) {
        lojas.get(e.getOrigem()).addPronta(e);
    }

}
