                                
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

public class Transportadora extends Entregador
{
   private String NIF;
   private double custoKm;
   private double custoKg;
   private int numeroDeEncomendas;
   private List<Encomenda> encomendaAtual;
   
   public Transportadora () {
       this.setNome("Empresa Standard");
       this.setCodigo("n/a");
       this.setPosicao(new Point2D.Double(0,0));
       this.setPassword("n/a");
       this.setRaio(0);
       this.NIF="n/a";
       this.custoKm=0;
       this.custoKg=0;
       this.setMedical(false);
       this.setVelocidade(0);
       this.setClassificacao(0);
       this.setVezesClassificado(1);
       this.numeroDeEncomendas=0;
       this.encomendaAtual=new ArrayList<Encomenda>();
       this.setHistorico(new ArrayList<Encomenda>());
   }
   
   public Transportadora (String nome,String codEmpresa,Point2D pos,String password,float raio,String NIF,double custoKm,double custoKg,boolean levaMedical,float velocidadeDeEntrega,float c,int vC,int numeroDeEncomendas,List<Encomenda> encomendaAtual,List<Encomenda> historicoEncomendas) {
       this.setNome(nome);
       this.setCodigo(codEmpresa);
       this.setPosicao((Point2D)pos.clone());
       this.setPassword(password);
       this.setRaio(raio);
       this.NIF=NIF;
       this.custoKm=custoKm;
       this.custoKg=custoKg;
       this.setMedical(levaMedical);
       this.setVelocidade(velocidadeDeEntrega);
       this.setClassificacao(c);
       this.setVezesClassificado(vC);
       this.numeroDeEncomendas=numeroDeEncomendas;
       this.encomendaAtual=encomendaAtual.stream().map(Encomenda::clone).collect(Collectors.toList());
       this.setHistorico(historicoEncomendas.stream().map(Encomenda::clone).collect(Collectors.toList()));
   }
   
   public Transportadora (Transportadora e) {
       this.setNome(e.getNome());
       this.setCodigo(e.getCodigo());
       this.setPosicao((Point2D)e.getPosicao().clone());
       this.setPassword(e.getPassword());
       this.setRaio(e.getRaio());
       this.NIF=e.NIF;
       this.custoKm=e.getCustoKm();
       this.custoKg=e.getCustoKg();
       this.setMedical(e.getMedical());
       this.setVelocidade(e.getVelocidade());
       this.setClassificacao(e.getClassificacao());
       this.setVezesClassificado(e.getVezesClassificado());
       this.numeroDeEncomendas=e.getNumEnc();
       this.encomendaAtual=e.getEncomendaAtual();
       this.setHistorico(e.getHistorico());
   }
   
   public void setNIF(String NIF) {
       this.NIF=NIF;
   }
   
   public void setCustoKm(double custoKm) {
       this.custoKm=custoKm;
   }
   
   public void setCustoKg(double custoKg) {
       this.custoKg=custoKg;
   }
   
   public void setNumeroEnc(int n) {
       this.numeroDeEncomendas=n;
   }
    
   public void setEncomendas( List<Encomenda> lE) {
       this.encomendaAtual=lE.stream().map(Encomenda::clone).collect(Collectors.toList());
   }

   public void addEncomenda(Encomenda e) {
       this.numeroDeEncomendas++;
       this.encomendaAtual.add(e.clone());
   }
   
   public String getNIF() {
       return this.NIF;
   }
   
   public double getCustoKg() {
       return this.custoKg;
   }
   
   public double getCustoKm() {
       return this.custoKm;
   }
   
   public int getNumEnc() {
       return this.numeroDeEncomendas;
   }
   
   public List<Encomenda> getEncomendaAtual() {
       return this.encomendaAtual.stream().map(Encomenda::clone).collect(Collectors.toList());
   }
   
   public String toString() {
       String s = "Nome de Empresa de Entregas: " + this.getNome() +
               "Codigo da Empresa: " + this.getCodigo() +
               "\nPosiçao: (" + this.getPosicao().getX() + "," + this.getPosicao().getY() + ")" +
               "\nRaio: " + this.getRaio() +
               "\nNIF: " + this.NIF +
               "\nCusto/Kg: " + this.custoKg +
               "\nCusto/Km: " + this.custoKm +
               "\nTransporta encomendas medicas: " + this.getMedical() +
               "\nVelocidade Normal(Km/h): " + this.getVelocidade() +
               "\nTransporta até " + this.numeroDeEncomendas + "encomendas" +
               "\nEncomenda Atual: " + this.encomendaAtual.toString() +
               "\nHistorico de Encomendas: " + this.getHistorico().toString();
       return s;
   }
   
   public Transportadora clone() {
       return new Transportadora(this);
   }

   public boolean hasRoomAndMed(boolean med) {
       return (this.numeroDeEncomendas-this.encomendaAtual.size())>0 && this.getMedical()==med;
   }

}
