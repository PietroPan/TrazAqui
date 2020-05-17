
/**
 * Write a description of class InterfaceEncomendasAceites here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

import java.util.HashSet;
import java.util.Set;

public class EncomendasAceites implements InterfaceEncomendasAceites {
    private Set<String> codEncomendas;

    public EncomendasAceites()
    {
        this.codEncomendas=new HashSet<String>();
    }
    
    public EncomendasAceites(Set<String> codEncomendas){
        this.codEncomendas= new HashSet<>(codEncomendas);
    }
    
    public EncomendasAceites(InterfaceEncomendasAceites c){
        this.codEncomendas= new HashSet<>(c.getCodEncomendas());
    }
    
    @Override
    public void setCodEncomendas(Set<String> codEncomendas){
        this.codEncomendas= new HashSet<>(codEncomendas);
    }
    
    @Override
    public Set<String> getCodEncomendas(){
        return new HashSet<>(this.codEncomendas);
    }
    
    @Override
    public boolean equals(Object obj) {
        if(obj==this) return true;
        if(obj==null || obj.getClass() != this.getClass()) return false;
        InterfaceEncomendasAceites encsA = (InterfaceEncomendasAceites)obj;
        return encsA.getCodEncomendas().equals(this.codEncomendas);
    }
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Codigos de Encomendas: ").append(this.codEncomendas);
        return sb.toString();
    }
    
    @Override
    public InterfaceEncomendasAceites clone(){
        return new EncomendasAceites(this);
    }

    @Override
    public boolean existe(String id) {
        return this.codEncomendas.contains(id);
    }

    @Override
    public void add(String e) {
        this.codEncomendas.add(e);
    }
}
