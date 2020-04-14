
/**
 * Write a description of class Empresa_De_Entregas here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

import java.awt.geom.Point2D;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Empresa
{
   private String nome;
   private Point2D pos;
   private float raio;
   private double custoKm;
   private double custoKg;
   private boolean levaMedical;
   private float velocidadeDeEntrega;
   private int numeroDeEncomendas;
   private List<Encomenda> encomendaAtual;
   private List<Encomenda> historicoEncomendas;
   
   public Empresa () {
       this.nome="Empresa Standard";
       this.pos=(Point2D)new Point2D.Double(0,0);
       this.raio=0;
       this.custoKm=0;
       this.custoKg=0;
       this.levaMedical=false;
       this.velocidadeDeEntrega=0;
       this.numeroDeEncomendas=0;
       this.encomendaAtual=new ArrayList<Encomenda>();
       this.historicoEncomendas=new ArrayList<Encomenda>();
   }
   
   public Empresa (String nome,Point2D pos,float raio,double custoKm,double custoKg,boolean levaMedical,float velocidadeDeEntrega,int numeroDeEncomendas,List<Encomenda> encomendaAtual,List<Encomenda> historicoEncomendas) {
       this.nome=nome;
       this.pos=(Point2D)pos.clone();
       this.raio=raio;
       this.custoKm=custoKm;
       this.custoKg=custoKg;
       this.levaMedical=levaMedical;
       this.velocidadeDeEntrega=velocidadeDeEntrega;
       this.numeroDeEncomendas=numeroDeEncomendas;
       this.encomendaAtual=encomendaAtual.stream().map(l -> l.clone()).collect(Collectors.toList());
       this.historicoEncomendas=historicoEncomendas.stream().map(l -> l.clone()).collect(Collectors.toList());;
   }
   
   public Empresa (Empresa e) {
       this.nome=e.nome;
       this.pos=(Point2D)e.pos.clone();
       this.raio=e.raio;
       this.custoKm=e.custoKm;
       this.custoKg=e.custoKg;
       this.levaMedical=e.levaMedical;
       this.velocidadeDeEntrega=e.velocidadeDeEntrega;
       this.numeroDeEncomendas=e.numeroDeEncomendas;
       this.encomendaAtual=e.encomendaAtual.stream().map(l -> l.clone()).collect(Collectors.toList());
       this.historicoEncomendas=e.historicoEncomendas.stream().map(l -> l.clone()).collect(Collectors.toList());;
   }
   
   public void setNome(String nome) {
       this.nome=nome;
   }
    
   public void setPos(Point2D pos) {
       this.pos=(Point2D.Double)pos.clone();
   }
   
   public void setRaio(float raio) {
       this.raio=raio;
   }
   
   public void setCustoKm(double custoKm) {
       this.custoKm=custoKm;
   }
   
   public void setCustoKg(double custoKg) {
       this.custoKg=custoKg;
   }
   
   public void setMedical(boolean medical) {
        this.levaMedical=medical;
   }
   
   public void setVelocidade(float vel){
       this.velocidadeDeEntrega=vel;
   }
   
   public void setNumeroEnc(int n) {
       this.numeroDeEncomendas=n;
   }
    
   public void setEncomendas( List<Encomenda> lE) {
       this.encomendaAtual=lE.stream().map(e -> e.clone()).collect(Collectors.toList());
   }
   
   public void setHistorico( List<Encomenda> lE) {
       this.historicoEncomendas=lE.stream().map(e -> e.clone()).collect(Collectors.toList());
   }
   
   public String getNome() {
       return this.nome;
   }
   
   public Point2D getPos() {
       return (Point2D)this.pos.clone();
   }
   
   public float getRaio() {
       return this.raio;
   }
   
   public double getCustoKg() {
       return this.custoKg;
   }
   
   public double getCustoKm() {
       return this.custoKm;
   }
   
   public boolean getMedical() {
       return this.levaMedical;
   }
   
   public float getVelocidadeEntrega() {
       return this.velocidadeDeEntrega;
   }
   
   public int getNumEnc() {
       return this.numeroDeEncomendas;
   }
   
   public List<Encomenda> getEncomendaAtual() {
       return this.encomendaAtual.stream().map(l -> l.clone()).collect(Collectors.toList());
   }
   
   public List<Encomenda> getHistorico() {
       return this.historicoEncomendas.stream().map(l -> l.clone()).collect(Collectors.toList());
   }
   
   public String toString() {
       StringBuilder s= new StringBuilder();
       s.append("Nome de Empresa de Entregas: ").append(this.nome)
       .append("\nPosiçao: (").append(this.pos.getX()).append(",").append(this.pos.getY()).append(")")
       .append("\nRaio: ").append(this.raio)
       .append("\nCusto/Kg: ").append(this.custoKg)
       .append("\nCusto/Km: ").append(this.custoKm)
       .append("\nTransporta encomendas medicas: ").append(this.levaMedical)
       .append("\nVelocidade Normal(Km/h): ").append(this.velocidadeDeEntrega)
       .append("\nTransporta ate ").append(this.numeroDeEncomendas).append("encomendas")
       .append("\nEncomenda Atual: ").append(this.encomendaAtual.toString())
       .append("\nHistorico de Encomendas: ").append(this.historicoEncomendas.toString());
       return s.toString();
   }
   
   public boolean equals(Empresa e) {
       return e.nome.equals(this.nome);
   }
   
   public Empresa clone() {
       return new Empresa(this);
   }
   
}
