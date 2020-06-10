package MVC.Model.Lojas;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import Common.*;
import Exceptions.*;

public class Lojas implements InterfaceLojas, Serializable {
    private Map<String, InterfaceLoja> lojas;

    /**
     * Construtor vazio
     */
    public Lojas() {
        lojas=new HashMap<>();
    }

    /**
     * Construtor cópia
     * @param lojas lojas a copiar
     */
    public Lojas(InterfaceLojas lojas) {
        this.lojas = lojas.getLojas();
    }

    /**
     * Getter de map de lojas
     * @return map de lojas copia da classe de lojas
     */
    @Override
    public Map<String, InterfaceLoja> getLojas() {
        Map<String, InterfaceLoja> res = new HashMap<>();
        for (Map.Entry<String, InterfaceLoja> e : lojas.entrySet()) {
            res.put(e.getKey(),e.getValue().clone());
        }
        return res;
    }

    /**
     * Setter de lojas
     * @param lojas map de lojas a copiar
     */
    @Override
    public void setLojas(Map<String, InterfaceLoja> lojas) {
        this.lojas = new HashMap<>();
        for (Map.Entry<String, InterfaceLoja> e : lojas.entrySet()) {
            this.lojas.put(e.getKey(),e.getValue().clone());
        }
    }

    /**
     * Getter de uma loja
     * @param e loja a procurar
     * @return loja de código e
     * @throws LojaInexistenteException caso a loja não exista
     */
    @Override
    public InterfaceLoja getLoja(String e) throws LojaInexistenteException {
        if (this.lojas.containsKey(e))
            return this.lojas.get(e).clone();
        else throw new LojaInexistenteException("InterfaceLoja inexistente");
    }

    /**
     * Setter de uma loja
     * @param s código da loja
     * @param l loja a adicionar ao map
     */
    @Override
    public void setLoja(String s, InterfaceLoja l) {
        this.lojas.put(s,l.clone());
    }

    /**
     * Método toString
     * @return String com a informação de lojas
     */
    @Override
    public String toString() {
        return "Lojas=" + lojas.toString();
    }

    /**
     * Método que adiciona encomenda
     * @param s código de encomenda a lojas
     * @param e encomenda a adicionar a loja
     */
    @Override
    public void addEncomenda(String s, InterfaceEncomenda e) {
        lojas.get(s).addNotReady(e);
    }

    /**
     * Método que remove uma encomenda da lista de espera
     * @param e encomenda a remover da lista de espera
     */
    @Override
    public void removeNotReady(InterfaceEncomenda e) {
        lojas.get(e.getOrigem()).removeNotReady(e.getCodEncomenda());
    }

    /**
     * Método que remove das encomednas prontas
     * @param s código de loja onde remover
     * @param e código da encomenda a remover
     */
    @Override
    public void removeReady(String s, String e) {
        lojas.get(s).removeReady(e);
    }

    /**
     * Método que adiciona uma encomenda uma encomenda as encomendas prontas pra entrega
     * @param e encomenda
     */
    @Override
    public void addPronta(InterfaceEncomenda e) {
        lojas.get(e.getOrigem()).addPronta(e);
    }

    /**
     * Método que adiciona ao stock
     * @param idLoja código da loja
     * @param l lista de linhas de encomendas a adicionar ao stock
     */
    @Override
    public void addToStock(String idLoja,List<InterfaceLinhaEncomenda> l) {
        this.lojas.get(idLoja).addToStock(l);
    }

    /**
     * Método que verifica se uma encomenda está em espera
     * @param id código de encomenda
     * @param loja loja onde procurar
     * @return true se estiver em espera
     */
    @Override
    public boolean encomendaNotReady(String id,String loja) {
        return this.lojas.get(loja).isNotReady(id);
    }

    /**
     * Método que forma uma lista de linha de encomendas atravês de várias linhas de encomenda
     * @param loja loja onde fazer
     * @param l lista de várias linhas de encomenda
     * @return lisat de linhas em encomenda
     * @throws ProductNotAvailableException produto não exsitente
     */
    @Override
    public List<InterfaceLinhaEncomenda> formaListadeLinhasEncomenda(String loja, List<Map.Entry<String, Double>> l) throws ProductNotAvailableException {
        return lojas.get(loja).formaListaLinhasEncomenda(l);
    }

    /**
     * Método que atualiza o estado de lojas
     * @param t data a comparar
     * @return map de códigos de utilizador para lista de mensagens que este vai receber
     */
    @Override
    public Map<String, List<String>> atualizaEstado(LocalDateTime t) {
        Map<String,List<String>> r =new HashMap<>();
        Map<String,List<String>> m;
        for (InterfaceLoja l : this.lojas.values()) {
            m=l.atualizaLoja(t);
            for (Map.Entry<String,List<String>> entry : m.entrySet()) {
                if (r.containsKey(entry.getKey())) {
                    r.get(entry.getKey()).addAll(entry.getValue());
                }
                else r.put(entry.getKey(),entry.getValue());
            }
        }
        return r;
    }

    /**
     * Getter do stock de uma loja
     * @param l loja
     * @return stock de uma encomenda
     * @throws NullPointerException quando uma loja não existe
     */
    @Override
    public List<InterfaceLinhaEncomenda> getStock(String l) throws NullPointerException {
        return new ArrayList<>(this.lojas.get(l).getStock().values());
    }

    /**
     * Mudar preço a um artigo na loja
     * @param loja loja
     * @param cod código do artigo
     * @param preco preço a colocar
     */
    @Override
    public void mudarPreco(String loja, String cod, double preco){
        this.lojas.get(loja).mudarPreco(cod,preco);
    }

    /**
     * Mudar quantidade de um artigo
     * @param loja loja
     * @param cod código de artigo
     * @param quant nova quantidade
     */
    @Override
    public void mudarQuantidade(String loja, String cod, double quant){
        this.lojas.get(loja).mudarQuantidade(cod,quant);
    }

    /**
     * Método que adiciona um novo artigo ao stock
     * @param loja loja
     * @param l novo artigo a adicionar ao stock
     */
    @Override
    public void addSToStock(String loja, InterfaceLinhaEncomenda l){
        this.lojas.get(loja).addSToStock(l);
    }

    /**
     * Remover um artigo de stock
     * @param loja loja
     * @param cod código do artigo a remover de stock
     */
    @Override
    public void removeFromStock(String loja, String cod){
        this.lojas.get(loja).removeFromStock(cod);
    }
}
