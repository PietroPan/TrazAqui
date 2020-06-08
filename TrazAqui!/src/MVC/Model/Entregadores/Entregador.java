package MVC.Model.Entregadores;
/**
 * Write a description of class Voluntário here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import Common.*;

public abstract class Entregador extends BasicInfo implements InterfaceEntregador, Serializable {
    private float raio;
    private boolean levaMedical;
    private float velocidadeDeEntrega;
    private float classificacao;
    private int vezesClassificado;
    private boolean aEntregar;

    @Override
    public void setAEntregar(boolean aEntregar){
        this.aEntregar=aEntregar;
    }

    @Override
    public boolean isAEntregar(){
        return this.aEntregar;
    }

   @Override
   public void setRaio(float raio) {
       this.raio=raio;
   }
   
   @Override
   public void setMedical(boolean medical) {
        this.levaMedical=medical;
   }
   
   @Override
   public void setVelocidade(float vel){
       this.velocidadeDeEntrega=vel;
   }

   @Override
   public void setClassificacao(float c) {this.classificacao=c;}
   
   @Override
   public float getRaio() {
       return this.raio;
   }
   
   @Override
   public boolean getMedical() {
       return this.levaMedical;
   }
   
   @Override
   public float getVelocidade() {
       return this.velocidadeDeEntrega;
   }

   @Override
   public float getClassificacao() { return this.classificacao;}

    @Override
    public int getVezesClassificado() {
        return vezesClassificado;
    }

    @Override
    public void setVezesClassificado(int vezesClassificado) {
        this.vezesClassificado = vezesClassificado;
    }

    @Override
    public boolean equals(InterfaceEntregador v) {
	    if (v==null || !v.getClass().equals(this.getClass()))
	 	   return false;
	    return this.getCodigo().equals(v.getCodigo());
    }

    @Override
    abstract public InterfaceEntregador clone();

    @Override
    public void classifica(float c) { this.classificacao=((this.classificacao*(this.vezesClassificado++)+c)/(this.vezesClassificado)); }
}