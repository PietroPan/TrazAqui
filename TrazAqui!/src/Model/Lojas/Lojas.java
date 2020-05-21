import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
        lojas.get(s).addNotReady(e);
    }

    @Override
    public void removeNotReady(InterfaceEncomenda e) {
        lojas.get(e.getOrigem()).removeNotReady(e.getCodEncomenda());
    }

    @Override
    public void removeReady(String s, String e) {
        lojas.get(s).removeReady(e);
    }

    @Override
    public void addPronta(InterfaceEncomenda e) {
        lojas.get(e.getOrigem()).addPronta(e);
    }

    @Override
    public void addToStock(String idLoja,List<InterfaceLinhaEncomenda> l) {
        this.lojas.get(idLoja).addToStock(l);
    }

    @Override
    public boolean encomendaACaminho(String id, String loja) {
        return this.lojas.get(loja).isReady(id);
    }

    @Override
    public boolean encomendaNotReady(String id,String loja) {
        return this.lojas.get(loja).isNotReady(id);
    }

    @Override
    public List<InterfaceLinhaEncomenda> formaListadeLinhasEncomenda(String loja, List<Map.Entry<String, Double>> l) throws ProductNotAvailableException {
        return lojas.get(loja).formaListaLinhasEncomenda(l);
    }

    @Override
    public void atualizaEstado(LocalDateTime t) {
        for (InterfaceLoja l : this.lojas.values()) {
            l.atualizaLoja(t);
        }
    }

    @Override
    public List<InterfaceLinhaEncomenda> getStock(String l) throws NullPointerException {
        return new ArrayList<>(this.lojas.get(l).getStock().values());
    }
}
