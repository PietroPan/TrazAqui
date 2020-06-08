package MVC.Model;

import Common.InterfaceEncomenda;
import Common.TriploHist;

import java.util.List;

public interface InterfaceHistorico {
    void add(String cod, InterfaceEncomenda encomenda);

    List<TriploHist> getEnt(String ent);

    List<TriploHist> getLoja(String loja);

    List<TriploHist> getUser(String user);

    void changeStat(String cod,String user);

    int checkClass (String ent,String user);

    public List<TriploHist> getHistorico();
}
