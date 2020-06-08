package MVC.Model.Entregadores;
/**
 * Write a description of class Voluntário here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

import java.awt.geom.Point2D;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import Common.*;

public class Voluntario extends Entregador implements InterfaceVoluntario, Serializable {
    private InterfaceEncomenda encomendaAtual;
    private List<String> pedidos;
    
    public Voluntario() {
        this.setNome("InterfaceVoluntario Standard");
        this.setCodigo("n/a");
        this.setPosicao(new Point2D.Double(0,0));
        this.setPassword("n/a");
        this.setRaio(0);
        this.setMedical(false);
        this.setVelocidade(0);
        this.setClassificacao(0);
        this.setVezesClassificado(1);
        this.setMessages(new ArrayList<>());
        this.pedidos=new ArrayList<>();
        this.encomendaAtual=new Encomenda();
        this.setAEntregar(false);
    }
    
    public Voluntario(String nome, String codEntregador, Point2D pos, String password, float raio, boolean levaMedical, float velocidadeDeEntrega, float c, int vC, ArrayList<String> pedidos, InterfaceEncomenda e, List<InterfaceEncomenda> lE) {
        this.setNome(nome);
        this.setCodigo(codEntregador);
        this.setPosicao((Point2D)pos.clone());
        this.setPassword(password);
        this.setRaio(raio);
        this.setMedical(levaMedical);
        this.setVelocidade(velocidadeDeEntrega);
        this.setClassificacao(c);
        this.setVezesClassificado(vC);
        this.setMessages(new ArrayList<>());
        this.pedidos= new ArrayList<>(pedidos);
        this.encomendaAtual=e.clone();
        this.setAEntregar(false);
   }
    
   public Voluntario(Voluntario v) {
       this.setNome(v.getNome());
       this.setCodigo(v.getCodigo());
       this.setPosicao((Point2D)v.getPosicao().clone());
       this.setPassword(v.getPassword());
       this.setRaio(v.getRaio());
       this.setMedical(v.getMedical());
       this.setVelocidade(v.getVelocidade());
       this.setClassificacao(v.getClassificacao());
       this.setVezesClassificado(v.getVezesClassificado());
       this.setMessages(v.getMessages());
       this.pedidos=v.getPedidos();
       this.encomendaAtual=v.getEncomenda();
       this.setAEntregar(false);
   }
   
   @Override
   public InterfaceEncomenda getEncomenda() {
       return this.encomendaAtual.clone();
   }

   @Override
   public InterfaceEncomenda getEncomenda(String id) {
        InterfaceEncomenda e = this.encomendaAtual;
        if (e.getCodEncomenda().equals(id))
            return e.clone();
        else
            return null;
   }

   @Override
   public List<String> getPedidos() {
        return new ArrayList<>(this.pedidos);
   }
   
   @Override
   public String toString() {
       StringBuilder s = new StringBuilder();
       s.append("Nome da Empresa: ").append(this.getNome())
       .append("\nCodigo do InterfaceVoluntario: ").append(this.getCodigo())
       .append("\nPosiçao: (").append(this.getPosicao().getY()).append(",").append(this.getPosicao().getX()).append(")")
       .append("\nRaio de açao: ").append(this.getRaio())
       .append("\nTransporta encomendas Medicas: ").append(this.getMedical())
       .append("\nVelocidade media(Km/h): ").append(this.getVelocidade())
       .append("\nCommon.Encomenda Atual: ").append(this.encomendaAtual);
       return s.toString();
   }
   
   @Override
   public InterfaceEntregador clone() {
       return new Voluntario(this);
   }

    @Override
    public boolean encomendaACaminho(String id, String s) {
        InterfaceEncomenda e=this.encomendaAtual;
        return e.getCodEncomenda().equals(s) && e.getDestino().equals(s);
    }

    @Override
   public boolean hasRoomAndMed(boolean med) {
        return this.encomendaAtual.getCodEncomenda().equals("Common.Encomenda Standard") && (!med || this.getMedical());
   }

   @Override
   public void addEncomenda(InterfaceEncomenda e) {
        this.encomendaAtual=e.clone();
    }

   @Override
   public void addPedido(String enc) {
        this.pedidos.add(enc);
   }

   @Override
   public void denyAllRequests() {
        this.pedidos=new ArrayList<>();
   }

    @Override
    public List<InterfaceEncomenda> atualizaEstado(LocalDateTime t) {
        List<InterfaceEncomenda> r = new ArrayList<>();
        boolean a = this.encomendaAtual.getCodEncomenda().contains("v");
        if (this.encomendaAtual.getDataEntrega().isBefore(t) && !this.encomendaAtual.getDestino().equals("User Standard")) {
            r.add(this.encomendaAtual.clone());
            this.encomendaAtual=new Encomenda();
        }
        boolean b = this.encomendaAtual.getCodEncomenda().contains("v");
        if (a&&!b) this.setAEntregar(false);
        return r;
    }

    @Override
    public void atualizaAtual(InterfaceEncomenda enc){
        this.encomendaAtual=enc.clone();
    }
}
