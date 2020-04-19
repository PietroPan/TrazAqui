
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

public class Voluntario
{
    private String nome;
    private String codVoluntario;
    private Point2D pos;
    private float raio;
    private boolean levaMedical;
    private float velocidadeDeEntrega;
    private Encomenda encomendaAtual;
    private List<Encomenda> historicoEncomendas;
    
    public Voluntario () {
        this.nome="Voluntario Standard";
        this.codVoluntario="n/a";
        this.pos=(Point2D)new Point2D.Double(0,0);
        this.raio=0;
        this.levaMedical=false;
        this.velocidadeDeEntrega=0;
        this.encomendaAtual=new Encomenda();
        this.historicoEncomendas=new ArrayList<Encomenda>();
    }
    
    public Voluntario (String nome,String codVoluntario,Point2D pos,float raio,boolean levaMedical,float velocidadeDeEntrega,Encomenda e,List<Encomenda> lE) {
        this.nome=nome;
        this.codVoluntario=codVoluntario;
        this.pos=(Point2D)pos.clone();
        this.raio=raio;
        this.levaMedical=levaMedical;
        this.velocidadeDeEntrega=velocidadeDeEntrega;
        this.encomendaAtual=e.clone();
        this.historicoEncomendas=lE.stream().map(l -> l.clone()).collect(Collectors.toList());
   }
    
   public Voluntario (Voluntario v) {
       this.nome=v.nome;
       this.codVoluntario=v.codVoluntario;
       this.pos=(Point2D)v.pos.clone();
       this.raio=v.raio;
       this.levaMedical=v.levaMedical;
       this.velocidadeDeEntrega=v.velocidadeDeEntrega;
       this.encomendaAtual=v.encomendaAtual.clone();
       this.historicoEncomendas=v.historicoEncomendas.stream().map(l -> l.clone()).collect(Collectors.toList());
   }
   
   public void setNome(String nome) {
       this.nome=nome;
   }
   
   public void setCodVoluntario(String codVoluntario) {
       this.codVoluntario=codVoluntario;
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
   
   public void setEncomenda(Encomenda e) {
       this.encomendaAtual=e.clone();
   }
   
   public void setHistorico( List<Encomenda> lE) {
       this.historicoEncomendas=lE.stream().map(e -> e.clone()).collect(Collectors.toList());
   }
   
   public String getNome() {
       return this.nome;
   }
   
   public String getCodVoluntario() {
       return this.codVoluntario;
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
   
   public float getVelocidadeEntrega() {
       return this.velocidadeDeEntrega;
   }
   
   public Encomenda getEncomenda() {
       return this.encomendaAtual.clone();
   }
   
   public List<Encomenda> getHistorico() {
       return this.historicoEncomendas.stream().map(l -> l.clone()).collect(Collectors.toList());
   }
   
   public String toString() {
       StringBuilder s = new StringBuilder();
       s.append("Nome da Empresa: ").append(this.nome)
       .append("\nCodigo do Voluntario: ").append(this.codVoluntario)
       .append("\nPosiçao: (").append(this.pos.getY()).append(",").append(this.pos.getX()).append(")")
       .append("\nRaio de açao: ").append(this.raio)
       .append("\nTransporta encomendas Medicas: ").append(this.levaMedical)
       .append("\nVelocidade media(Km/h): ").append(this.velocidadeDeEntrega)
       .append("\nEncomenda Atual: ").append(this.encomendaAtual)
       .append("\nHistorico de Encomendas: ").append(this.historicoEncomendas.toString());
       return s.toString();
   }
   
   public boolean equals(Voluntario v) {
       return v.codVoluntario.equals(this.codVoluntario);
   }
   
   public Voluntario clone() {
       return new Voluntario(this);
   }
   
}
