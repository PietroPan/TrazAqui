
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

public abstract class Entregador
{
    private String nome;
    private String codEntregador;
    private Point2D pos;
    private float raio;
    private boolean levaMedical;
    private float velocidadeDeEntrega;
    private float classificacao;
    private List<Encomenda> historicoEncomendas;

    public void setNome(String nome) {
       this.nome=nome;
   }
   
   public void setCodEntregador(String codEntregador) {
       this.codEntregador=codEntregador;
   }
    
   public void setPos(Point2D pos) {
       this.pos=(Point2D.Double)pos.clone();
   }
   
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
   
   public String getNome() {
       return this.nome;
   }
   
   public String getCodEntregador() {
       return this.codEntregador;
   }
   
   public Point2D getPos() {
       return (Point2D)this.pos.clone();
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

    public double getCustoKg(){return 1;};

    public double getCustoKm(){return 1;};

    public abstract boolean hasRoomAndMed(boolean med);

    public abstract void addEncomenda(Encomenda enc);
   
    public abstract String toString();
   
    public boolean equals(Entregador v) {
	    if (v==null || !v.getClass().equals(this.getClass()))
	 	   return false;
	    return v.codEntregador.equals(v.getCodEntregador());
    }
   
    public abstract Entregador clone();
   
}