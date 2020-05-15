
/**
 * Write a description of class EncomendasAceites here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;

public class EncomendasAceites
{
    private Set<String> codEncomendas;

    public EncomendasAceites()
    {
        this.codEncomendas=new HashSet<String>();
    }
    
    public EncomendasAceites(Set<String> codEncomendas){
        this.codEncomendas= new HashSet<>(codEncomendas);
    }
    
    public EncomendasAceites(EncomendasAceites c){
        this.codEncomendas= new HashSet<>(c.getCodEncomendas());
    }
    
    public void setCodEncomendas(Set<String> codEncomendas){
        this.codEncomendas= new HashSet<>(codEncomendas);
    }
    
    public Set<String> getCodEncomendas(){
        return new HashSet<>(this.codEncomendas);
    }
    
    public boolean equals(Object obj) {
        if(obj==this) return true;
        if(obj==null || obj.getClass() != this.getClass()) return false;
        EncomendasAceites encsA = (EncomendasAceites)obj;
        return encsA.getCodEncomendas().equals(this.codEncomendas);
    }
    
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Codigos de Encomendas: ").append(this.codEncomendas);
        return sb.toString();
    }
    
    public EncomendasAceites clone(){
        return new EncomendasAceites(this);
    }

    public boolean existe (String id) {
        return this.codEncomendas.contains(id);
    }

    public void add(String e) {
        this.codEncomendas.add(e);
    }
}
