
/**
 * Escreva a descrição da classe Encomenda aqui.
 * 
 * @author (seu nome) 
 * @version (número de versão ou data)
 */

import java.util.List;
import java.util.ArrayList;
import java.awt.geom.Point2D;
import java.util.stream.Collectors;

public class Encomenda
{
    private String codEncomenda;
    private boolean medical;
    private float peso;
    private String lojaOrigem;
    private String userDestino;
    private List<LinhaEncomenda> pedido;
    
    public Encomenda() {
        this.codEncomenda="Encomenda Standard";
        this.medical=false;
        this.peso=0;
        this.lojaOrigem="Loja Standard";
        this.userDestino="User Standard";
        this.pedido=new ArrayList<LinhaEncomenda>();
    }
    
    public Encomenda(String enc,boolean medical,float peso,String loja,String user,List<LinhaEncomenda> pedido) {
        this.codEncomenda=enc;
        this.medical=medical;
        this.peso=peso;
        this.lojaOrigem=loja;
        this.userDestino=user;
        this.pedido=pedido.stream().map(l -> l.clone()).collect(Collectors.toList());
    }
    
    public Encomenda(Encomenda e) {
        this.codEncomenda=e.getCodEncomenda();
        this.medical=e.getMedical();
        this.peso=e.getPeso();
        this.lojaOrigem=e.getOrigem();
        this.userDestino=e.getDestino();
        this.pedido=e.getPedido();
    }
    
    public void setCodEncomenda(String enc) {
        this.codEncomenda=enc;
    }
    
    public void setMedical(boolean medical) {
        this.medical=medical;
    }
    
    public void setPeso(float peso) {
        this.peso=peso;
    }
    
    public void setOrigem(String loja) {
        this.lojaOrigem=loja;
    }
    
    public void setDestino(String user) {
        this.userDestino=user;
    }
    
    public void setPedido(List<LinhaEncomenda> lE) {
        this.pedido=lE.stream().map(l -> l.clone()).collect(Collectors.toList());
    }
    
    public String getCodEncomenda() {
        return this.codEncomenda;
    }
    
    public boolean getMedical() {
        return this.medical;
    }
    
    public float getPeso() {
        return this.peso;
    }
    
    public String getOrigem() {
        return this.lojaOrigem;
    }
    
    public String getDestino() {
        return this.userDestino;
    }
    
    public List<LinhaEncomenda> getPedido() {
        return this.pedido.stream().map(l -> l.clone()).collect(Collectors.toList());
    }
    
    public boolean equals(Object enc) {
        Encomenda e;
        if (enc==null || enc.getClass()!=enc.getClass()) 
            return false;
        e=(Encomenda)enc;
        return e.getCodEncomenda().equals(this.codEncomenda);
    }
    
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("Codigo de Encomenda: ").append(this.codEncomenda)
        .append("\nLoja de Origem: ").append(this.lojaOrigem)
        .append("\nUtilizador de Destino: ").append(this.userDestino)
        .append("\nEncomenda Médica: ").append(this.medical)
        .append("\nPeso(Kgs): ").append(this.peso)
        .append("\nPedido: ").append(this.pedido.toString());
        return s.toString();
    }
    
    public Encomenda clone() {
        return new Encomenda(this);
    }
}
