
/**
 * Write a description of class EncomendasAceites here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class EncomendasAceites
{
    private List<String> codEncomendas;

    public EncomendasAceites()
    {
        this.codEncomendas=new ArrayList<String>();
    }
    
    public EncomendasAceites(List<String> codEncomendas){
        this.codEncomendas=codEncomendas.stream().collect(Collectors.toList());
    }
    
    public EncomendasAceites(EncomendasAceites c){
        this.codEncomendas=c.codEncomendas.stream().collect(Collectors.toList());
    }
    
    public void SetCodEncomendas(List<String> codEncomendas){
        this.codEncomendas=codEncomendas.stream().collect(Collectors.toList());
    }
    
    public List<String> GetCodEncomendas(){
        return this.codEncomendas.stream().collect(Collectors.toList());
    }
    
    public boolean equals(Object obj) {
        if(obj==this) return true;
        if(obj==null || obj.getClass() != this.getClass()) return false;
        EncomendasAceites encsA = (EncomendasAceites)obj;
        return encsA.GetCodEncomendas().equals(this.codEncomendas);
    }
    
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Codigos de Encomendas: ").append(this.codEncomendas);
        return sb.toString();
    }
    
    public EncomendasAceites clone(){
        return new EncomendasAceites(this);
    }
}
