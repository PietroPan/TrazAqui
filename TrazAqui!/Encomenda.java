
/**
 * Escreva a descrição da classe Encomenda aqui.
 * 
 * @author (seu nome) 
 * @version (número de versão ou data)
 */

import java.awt.geom.Point2D;

public class Encomenda
{
    private boolean medical;
    private float peso;
    private String lojaOrigem;
    private String userDestino;
    
    public Encomenda() {
        this.medical=false;
        this.peso=0;
        this.lojaOrigem="Loja Standard";
        this.userDestino="User Standard";
    }
    
    public Encomenda(boolean medical,float peso,String loja,String user) {
        this.medical=medical;
        this.peso=peso;
        this.lojaOrigem=loja;
        this.userDestino=user;
    }
    
    public Encomenda(Encomenda e) {
        this.medical=e.medical;
        this.peso=e.peso;
        this.lojaOrigem=e.lojaOrigem;
        this.userDestino=e.userDestino;
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
    
    public boolean equals(Object enc) {
        Encomenda e;
        if (enc==null || enc.getClass()!=enc.getClass()) return false;
        e=(Encomenda)enc;
        return e.peso==this.peso && e.medical==this.medical && e.lojaOrigem.equals(this.lojaOrigem) && e.userDestino.equals(this.userDestino);
    }
    
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("Loja de Origem: ").append(this.lojaOrigem).append("\nUtilizador de Destino: ").append(this.userDestino).append("\nEncomenda Médica: ").append(this.medical).append("\nPeso(Kgs): ").append(this.peso);
        return s.toString();
    }
    
    public Encomenda clone() {
        return new Encomenda(this);
    }
}
