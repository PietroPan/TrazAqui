
/**
 * Write a description of class Voluntário here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Entregador extends BasicInfo implements InterfaceEntregador {
    private float raio;
    private boolean levaMedical;
    private float velocidadeDeEntrega;
    private float classificacao;
    private int vezesClassificado;
    private List<InterfaceEncomenda> historicoEncomendas;
   
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
   public void setHistorico(List<InterfaceEncomenda> lE) {
       this.historicoEncomendas=lE.stream().map(InterfaceEncomenda::clone).collect(Collectors.toList());
   }
   
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
   public List<InterfaceEncomenda> getHistorico() {
       return this.historicoEncomendas.stream().map(InterfaceEncomenda::clone).collect(Collectors.toList());
   }
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
   public InterfaceEncomenda getEncomendaPassado(String id) {
       for (InterfaceEncomenda e : this.historicoEncomendas) {
           if (e.getCodEncomenda().equals(id))
               return e;
       }
       return null;
   }

    @Override
    public void classifica(float c) {
        this.vezesClassificado++;
        this.classificacao=((this.classificacao*this.vezesClassificado+c)/(this.vezesClassificado));
    }
}