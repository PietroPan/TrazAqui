package MVC.Model;

import Common.InterfaceEncomenda;
import Common.TriploHist;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

public class Historico implements InterfaceHistorico, Serializable {
    private List<TriploHist> historico;

    /**
     * Construtor vazio
     */
    public Historico(){
        this.historico=new ArrayList<>();
    }

    /**
     * Getter para o parametro historico
     * @return
     */
    @Override
    public List<TriploHist> getHistorico(){
        return this.historico.stream().map(TriploHist::clone).collect(Collectors.toList());
    }

    /**
     * Adicionar encomenda ao historico
     * @param cod codigo de entregador
     * @param encomenda encomenda
     */
    @Override
    public void add(String cod, InterfaceEncomenda encomenda){
        this.historico.add(new TriploHist(cod,false,encomenda.clone()));
    }

    /**
     * Get historico de um entregador
     * @param ent codigo de entregador
     * @return historico de entregador
     */
    @Override
    public List<TriploHist> getEnt(String ent){
       return this.historico.stream().filter(i->i.getEnt().equals(ent)).map(TriploHist::clone).collect(Collectors.toList());
    }

    /**
     * Get historico de uma loja
     * @param loja loja
     * @return historico desta
     */
    @Override
    public List<TriploHist> getLoja(String loja){
        return this.historico.stream().filter(i->i.getEnc().getOrigem().equals(loja)).map(TriploHist::clone).collect(Collectors.toList());
    }

    /**
     * Get historico de um utilizador
     * @param user utilizador
     * @return historico de um utilizador
     */
    @Override
    public List<TriploHist> getUser(String user){
        return this.historico.stream().filter(i->i.getEnc().getDestino().equals(user)).map(TriploHist::clone).collect(Collectors.toList());
    }

    /**
     * Mudar o estado de uma encomend no historico
     * @param cod codigo de entregador
     * @param user codigo de utilizador
     */
    @Override
    public void changeStat(String cod,String user){
        for (TriploHist i : this.historico){
            if (i.getEnt().equals(cod)||i.getEnc().getDestino().equals(user)) i.setStat(true);
        }
    }

    /**
     * Verificar o estado de uma encomenda (classificada ou não classificada)
     * @param ent entregador que a realizou
     * @param user utilizador que recebeu
     * @return 1 ou 0 caso esteja ou não classificada
     */
    @Override
    public int checkClass (String ent,String user){
        int i=1;
        for (TriploHist j : this.historico){
            if (j.getEnt().equals(ent)&&j.getEnc().getDestino().equals(user)){
                i=2;
                if (!j.isStat()) return 0;
            }
        }
        return i;
    }
}
