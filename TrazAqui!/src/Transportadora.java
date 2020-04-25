                                
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
       this.setCodEntregador("n/a");
       this.setPos((Point2D)new Point2D.Double(0,0));
       this.setRaio(0);
       this.NIF="n/a";
       this.custoKm=0;
       this.custoKg=0;
       this.setMedical(false);
       this.setVelocidade(0);
       this.setClassificacao(0);
       this.numeroDeEncomendas=0;
       this.encomendaAtual=new ArrayList<Encomenda>();
       this.setHistorico(new ArrayList<Encomenda>());
   }
   
   public Transportadora (String nome,String codEmpresa,Point2D pos,float raio,String NIF,double custoKm,double custoKg,boolean levaMedical,float velocidadeDeEntrega,float c,int numeroDeEncomendas,List<Encomenda> encomendaAtual,List<Encomenda> historicoEncomendas) {
       this.setNome(nome);
       this.setCodEntregador(codEmpresa);
       this.setPos((Point2D)pos.clone());
       this.setRaio(raio);
       this.NIF=NIF;
       this.custoKm=custoKm;
       this.custoKg=custoKg;
       this.setMedical(levaMedical);
       this.setVelocidade(velocidadeDeEntrega);
       this.setClassificacao(c);
       this.numeroDeEncomendas=numeroDeEncomendas;
       this.encomendaAtual=encomendaAtual.stream().map(Encomenda::clone).collect(Collectors.toList());
       this.setHistorico(historicoEncomendas.stream().map(Encomenda::clone).collect(Collectors.toList()));
   }
   
   public Transportadora (Transportadora e) {
       this.setNome(e.getNome());
       this.setCodEntregador(e.getCodEntregador());
       this.setPos((Point2D)e.getPos().clone());
       this.setRaio(e.getRaio());
       this.NIF=e.NIF;
       this.custoKm=e.custoKm;
       this.custoKg=e.custoKg;
       this.setMedical(e.getMedical());
       this.setVelocidade(e.getVelocidade());
       this.setClassificacao(e.getClassificacao());
       this.numeroDeEncomendas=e.numeroDeEncomendas;
       this.encomendaAtual=e.encomendaAtual.stream().map(Encomenda::clone).collect(Collectors.toList());
       this.setHistorico(e.getHistorico().stream().map(Encomenda::clone).collect(Collectors.toList()));
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
       StringBuilder s= new StringBuilder();
       s.append("Nome de Empresa de Entregas: ").append(this.getNome())
       .append("Codigo da Empresa: ").append(this.getCodEntregador())
       .append("\nPosiçao: (").append(this.getPos().getX()).append(",").append(this.getPos().getY()).append(")")
       .append("\nRaio: ").append(this.getRaio())
       .append("\nNIF: ").append(this.NIF)
       .append("\nCusto/Kg: ").append(this.custoKg)
       .append("\nCusto/Km: ").append(this.custoKm)
       .append("\nTransporta encomendas medicas: ").append(this.getMedical())
       .append("\nVelocidade Normal(Km/h): ").append(this.getVelocidade())
       .append("\nTransporta até ").append(this.numeroDeEncomendas).append("encomendas")
       .append("\nEncomenda Atual: ").append(this.encomendaAtual.toString())
       .append("\nHistorico de Encomendas: ").append(this.getHistorico().toString());
       return s.toString();
   }
   
   public Transportadora clone() {
       return new Transportadora(this);
   }

   public boolean hasRoomAndMed(boolean med) {
       return (this.numeroDeEncomendas-this.encomendaAtual.size())>0 && this.getMedical()==med;
   }

}
