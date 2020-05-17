import java.util.HashMap;
import java.util.Map;

public class Lojas implements InterfaceLojas {
    private Map<String, InterfaceLoja> lojas;

    public Lojas() {
        lojas=new HashMap<>();
    }

    public Lojas(InterfaceLojas lojas) {
        this.lojas = lojas.getLojas();
    }
    @Override
    public Map<String, InterfaceLoja> getLojas() {
        Map<String, InterfaceLoja> res = new HashMap<>();
        for (Map.Entry<String, InterfaceLoja> e : lojas.entrySet()) {
            res.put(e.getKey(),e.getValue().clone());
        }
        return res;
    }

    @Override
    public void setLojas(Map<String, InterfaceLoja> lojas) {
        this.lojas = new HashMap<>();
        for (Map.Entry<String, InterfaceLoja> e : lojas.entrySet()) {
            this.lojas.put(e.getKey(),e.getValue().clone());
        }
    }

    @Override
    public InterfaceLoja getLoja(String e) throws LojaInexistenteException {
        if (this.lojas.containsKey(e))
            return this.lojas.get(e).clone();
        else throw new LojaInexistenteException("InterfaceLoja inexistente");
    }

    @Override
    public void setLoja(String s, InterfaceLoja l) {
        this.lojas.put(s,l.clone());
    }

    @Override
    public String toString() {
        return "Lojas=" + lojas.toString();
    }

    @Override
    public void addEncomenda(String s, InterfaceEncomenda e) {
        lojas.get(s).addPronta(e);
    }

    @Override
    public void removeReady(String s, String e) {
        lojas.get(s).removeReady(e);
    }

    @Override
    public void addPronta(InterfaceEncomenda e) {
        lojas.get(e.getOrigem()).addPronta(e);
    }

}
