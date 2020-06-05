package MVC.Model.Entregadores;
/**
 * Write a description of class Empresa_De_Entregas here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

import java.awt.geom.Point2D;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import Common.*;

public class Transportadora extends Entregador implements InterfaceTransportadora, Serializable {
   private String NIF;
   private double custoKm;
   private double custoKg;
   private int numeroDeEncomendas;
   private List<InterfaceEncomenda> encomendaAtual;
   private List<Map.Entry<InterfaceEncomenda,String>> pedidos;
   
   public Transportadora() {
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
       this.encomendaAtual=new ArrayList<>();
       this.setHistorico(new ArrayList<>());
       this.pedidos=new ArrayList<>();
   }
   
   public Transportadora(String nome, String codEmpresa, Point2D pos, String password, float raio, String NIF, double custoKm, double custoKg, boolean levaMedical, float velocidadeDeEntrega, float c, int vC, int numeroDeEncomendas, List<InterfaceEncomenda> encomendaAtual, List<InterfaceEncomenda> historicoEncomendas) {
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
       this.encomendaAtual=encomendaAtual.stream().map(InterfaceEncomenda::clone).collect(Collectors.toList());
       this.setHistorico(historicoEncomendas.stream().map(InterfaceEncomenda::clone).collect(Collectors.toList()));
       this.pedidos=new ArrayList<>();
   }
   
   public Transportadora(Transportadora e) {
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
       this.pedidos=e.getPedidos();
   }
   
   @Override
   public void setNIF(String NIF) {
       this.NIF=NIF;
   }
   
   @Override
   public void setCustoKm(double custoKm) {
       this.custoKm=custoKm;
   }
   
   @Override
   public void setCustoKg(double custoKg) {
       this.custoKg=custoKg;
   }
   
   @Override
   public void setNumeroEnc(int n) {
       this.numeroDeEncomendas=n;
   }
    
   @Override
   public void setEncomendas(List<InterfaceEncomenda> lE) {
       this.encomendaAtual=lE.stream().map(InterfaceEncomenda::clone).collect(Collectors.toList());
   }

   @Override
   public void addEncomenda(InterfaceEncomenda e) {
       this.numeroDeEncomendas++;
       this.encomendaAtual.add(e.clone());
   }

   @Override
   public String getNIF() {
       return this.NIF;
   }
   
   @Override
   public double getCustoKg() {
       return this.custoKg;
   }
   
   @Override
   public double getCustoKm() {
       return this.custoKm;
   }
   
   @Override
   public int getNumEnc() {
       return this.numeroDeEncomendas;
   }
   
   @Override
   public List<InterfaceEncomenda> getEncomendaAtual() {
       return this.encomendaAtual.stream().map(InterfaceEncomenda::clone).collect(Collectors.toList());
   }

    @Override
    public InterfaceEncomenda getEncomenda(String id) {
       for (InterfaceEncomenda e : this.encomendaAtual) {
           if (e.getCodEncomenda().equals(id))
               return e.clone();
       }
       return null;
    }

    @Override
    public List<Map.Entry<InterfaceEncomenda,String>> getPedidos(){
       return new ArrayList<>(this.pedidos);
    }

    @Override
    public List<InterfaceEncomenda> atualizaEstado(LocalDateTime t) {
        List<InterfaceEncomenda> r = new ArrayList<>();
        List<InterfaceEncomenda> h;
        Iterator<InterfaceEncomenda> i =this.encomendaAtual.iterator();
        while (i.hasNext()) {
            InterfaceEncomenda e = i.next();
            if (e.getDataEntrega().isBefore(t)) {
                r.add(e.clone());
                i.remove();
            }
        }
        h = this.getHistorico();
        h.addAll(r.stream().map(InterfaceEncomenda::clone).collect(Collectors.toSet()));
        this.setHistorico(h);
        return r;
    }

    @Override
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
               "\nCommon.Encomenda Atual: " + this.encomendaAtual.toString() +
               "\nHistorico de Encomendas: " + this.getHistorico().toString();
       return s;
   }
   
   @Override
   public InterfaceEntregador clone() {
       return new Transportadora(this);
   }

    @Override
    public boolean encomendaACaminho(String id, String s) {
        for (InterfaceEncomenda e : this.encomendaAtual) {
            if (e.getCodEncomenda().equals(s))
                return e.getDestino().equals(s);
        }
        return false;
    }

    @Override
   public boolean hasRoomAndMed(boolean med) {
       return (this.numeroDeEncomendas-this.encomendaAtual.size())>0 && (!med || this.getMedical());
   }

   @Override
    public double calculaCusto(double dist,double peso){
       return (dist*custoKm)+(peso*custoKg);
   }

   @Override
    public void addPedido(InterfaceEncomenda enc){
       this.pedidos.add(new AbstractMap.SimpleEntry<>(enc,"p"));
   }

    @Override
    public void addPedido(InterfaceEncomenda enc,String stat){ this.pedidos.add(new AbstractMap.SimpleEntry<>(enc,stat));    }

   @Override
    public void alteraPedido(InterfaceEncomenda enc,String stat){
       this.pedidos=this.getPedidos().stream().filter(i->!(i.getKey().getCodEncomenda().equals(enc.getCodEncomenda()))).collect(Collectors.toList());
       this.addPedido(enc,stat);
   }

   @Override
    public void clearAtual(){
       this.encomendaAtual= new ArrayList<>();
   }
}
