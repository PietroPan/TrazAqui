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
import java.time.temporal.ChronoUnit;
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
       this.setMessages(new ArrayList<>());
       this.NIF="n/a";
       this.custoKm=0;
       this.custoKg=0;
       this.setMedical(false);
       this.setVelocidade(0);
       this.setClassificacao(0);
       this.setVezesClassificado(1);
       this.setMessages(new ArrayList<>());
       this.numeroDeEncomendas=0;
       this.encomendaAtual=new ArrayList<>();
       this.pedidos=new ArrayList<>();
       this.setAEntregar(false);
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
       this.setMessages(new ArrayList<>());
       this.numeroDeEncomendas=numeroDeEncomendas;
       this.encomendaAtual=encomendaAtual.stream().map(InterfaceEncomenda::clone).collect(Collectors.toList());
       this.pedidos=new ArrayList<>();
       this.setAEntregar(false);
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
       this.setMessages(e.getMessages());
       this.numeroDeEncomendas=e.getNumEnc();
       this.encomendaAtual=e.getEncomendaAtual();
       this.pedidos=e.getPedidos();
       this.setAEntregar(false);
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
        int a = this.encomendaAtual.size();
        Iterator<InterfaceEncomenda> i =this.encomendaAtual.iterator();
        while (i.hasNext()) {
            InterfaceEncomenda e = i.next();
            if (e.getDataEntrega().isBefore(t)) {
                r.add(e.clone());
                i.remove();
            }
        }
        int b = this.encomendaAtual.size();
        if (a>0&&b==0) {
            this.setAEntregar(false);
            alteraTodosPedidosIf("p","s");
        }
        return r;
    }

    @Override
    public Map<String,String> checkEvent(LocalDateTime t){
       Map<String,String> m = new HashMap<>();
       String[] goodEvents = new String[]{
               " encontrou uma estrela colorida, parece que a sua encomenda já chegou",
               "Uma encomenda foi cancelada,",
               "O tempo melhorou,",

       };
        String[] badEvents = new String[]{
                " protestantes roubaram a sua encomenda, lamentamos a inconveniência",
                "Começou a chover torrencialmente,",
                "Ocorreu uma acidente,",
                "A sua encomenda ficou presa na Alfândega,",
                "Começou uma tempestade de neve,"
        };
       Random rand = new Random();
       String usr,msg;
       int r1=rand.nextInt(100);//u677 e1836 t480
       if (r1<10||r1==100){
           if (r1<this.getClassificacao()){
                for (InterfaceEncomenda i : this.encomendaAtual){
                    usr = i.getDestino();
                    if (r1==100){
                        msg = "O transportador "+this.getCodigo()+goodEvents[0];
                        i.setDataEntrega(t);
                        m.put(usr,msg);
                    }
                    else {
                        msg = goodEvents[(rand.nextInt(2)+1)]+" o transportador "+this.getCodigo()+" vai chegar mais cedo";
                        long time = ChronoUnit.MINUTES.between(t,i.getDataEntrega());
                        i.setDataEntrega(i.getDataEntrega().minusMinutes(time/2));
                        m.put(usr,msg);
                    }
                }
           } else {
               for (InterfaceEncomenda i : this.encomendaAtual){
                   usr = i.getDestino();
                   if (r1==100){
                       msg = "O transportador "+this.getCodigo()+"informa que"+badEvents[0];
                       m.put(usr,msg);
                   }
                   else {
                       msg = badEvents[(rand.nextInt(4)+1)]+" o transportador "+this.getCodigo()+" vai chegar mais tarde";
                       long time = ChronoUnit.MINUTES.between(t,i.getDataEntrega());
                       i.setDataEntrega(i.getDataEntrega().plusMinutes(time/2));
                       m.put(usr,msg);
                   }
               }
               if (r1==100) this.encomendaAtual=new ArrayList<>();
           }
       }
       return m;
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
               "\nCommon.Encomenda Atual: " + this.encomendaAtual.toString();
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
   public boolean hasRoom(){
       return (this.encomendaAtual.size()<this.numeroDeEncomendas);
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
    public void addPedidos(List<InterfaceEncomenda> encs,String stat){
       for (InterfaceEncomenda i : encs){
           this.pedidos.add(new AbstractMap.SimpleEntry<>(i,stat));
       }
   }


    @Override
    public void alteraPedido(InterfaceEncomenda enc,String stat){
       this.pedidos=this.getPedidos().stream().filter(i->!(i.getKey().getCodEncomenda().equals(enc.getCodEncomenda()))).collect(Collectors.toList());
       this.addPedido(enc,stat);
   }

   @Override
   public void alteraTodosPedidosIf(String stat,String statIf){
       List<InterfaceEncomenda> aux = new ArrayList<>();
       for (Map.Entry<InterfaceEncomenda,String> i : this.getPedidos()){
           if (i.getValue().equals(statIf)) aux.add(i.getKey());
       }
       this.pedidos.removeIf(i->i.getValue().equals(statIf));
       addPedidos(aux,stat);
   }

   @Override
    public void clearAtual(){
       this.encomendaAtual= new ArrayList<>();
   }

   @Override
    public void atualizaAtual (InterfaceEncomenda enc){
       this.encomendaAtual.removeIf(i->i.getCodEncomenda().equals(enc.getCodEncomenda()));
       this.encomendaAtual.add(enc);
   }

   @Override
    public boolean existePedido (String enc){
       for (Map.Entry<InterfaceEncomenda,String> i : this.pedidos){
           if (i.getKey().getCodEncomenda().equals(enc)) return true;
       }
       return false;
   }
}
