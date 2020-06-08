package MVC.Model;

import Common.InterfaceEncomenda;
import Common.TriploHist;

import java.util.*;
import java.util.stream.Collectors;

public class Historico implements InterfaceHistorico {
    private List<TriploHist> historico;

    public Historico(){
        this.historico=new ArrayList<>();
    }

    @Override
    public List<TriploHist> getHistorico(){
        return this.historico.stream().map(TriploHist::clone).collect(Collectors.toList());
    }

    @Override
    public void add(String cod, InterfaceEncomenda encomenda){
        this.historico.add(new TriploHist(cod,false,encomenda.clone()));
    }

    @Override
    public List<TriploHist> getEnt(String ent){
       return this.historico.stream().filter(i->i.getEnt().equals(ent)).map(TriploHist::clone).collect(Collectors.toList());
    }

    @Override
    public List<TriploHist> getLoja(String loja){
        return this.historico.stream().filter(i->i.getEnc().getOrigem().equals(loja)).map(TriploHist::clone).collect(Collectors.toList());
    }

    @Override
    public List<TriploHist> getUser(String user){
        return this.historico.stream().filter(i->i.getEnc().getDestino().equals(user)).map(TriploHist::clone).collect(Collectors.toList());
    }

    @Override
    public void changeStat(String cod,String user){
        for (TriploHist i : this.historico){
            if (i.getEnt().equals(cod)||i.getEnc().getDestino().equals(user)) i.setStat(true);
        }
    }

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
