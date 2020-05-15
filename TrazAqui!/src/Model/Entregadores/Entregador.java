
/**
 * Write a description of class Voluntário here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

import java.awt.geom.Point2D;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

public abstract class Entregador extends BasicInfo
{
    private float raio;
    private boolean levaMedical;
    private float velocidadeDeEntrega;
    private float classificacao;
    private int vezesClassificado;
    private List<Encomenda> historicoEncomendas;
   
   public void setRaio(float raio) {
       this.raio=raio;
   }
   
   public void setMedical(boolean medical) {
        this.levaMedical=medical;
   }
   
   public void setVelocidade(float vel){
       this.velocidadeDeEntrega=vel;
   }

   public void setClassificacao(float c) {this.classificacao=c;}

   public void setHistorico( List<Encomenda> lE) {
       this.historicoEncomendas=lE.stream().map(Encomenda::clone).collect(Collectors.toList());
   }
   
   public float getRaio() {
       return this.raio;
   }
   
   public boolean getMedical() {
       return this.levaMedical;
   }
   
   public float getVelocidade() {
       return this.velocidadeDeEntrega;
   }

   public float getClassificacao() { return this.classificacao;}

   public List<Encomenda> getHistorico() {
       return this.historicoEncomendas.stream().map(Encomenda::clone).collect(Collectors.toList());
   }
    public int getVezesClassificado() {
        return vezesClassificado;
    }

    public void setVezesClassificado(int vezesClassificado) {
        this.vezesClassificado = vezesClassificado;
    }

    public abstract boolean hasRoomAndMed(boolean med);

    public abstract void addEncomenda(Encomenda enc);
   
    public abstract String toString();
   
    public boolean equals(Entregador v) {
	    if (v==null || !v.getClass().equals(this.getClass()))
	 	   return false;
	    return this.getCodigo().equals(v.getCodigo());
    }
   
    public abstract Entregador clone();

    public void classifica(float c) {
        this.vezesClassificado++;
        this.classificacao=((this.classificacao*this.vezesClassificado+c)/(this.vezesClassificado));
    }
   
}