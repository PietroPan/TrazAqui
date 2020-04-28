
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

public class Voluntario extends Entregador
{
    private Encomenda encomendaAtual;
    private List<String> pedidos;
    
    public Voluntario () {
        this.setNome("Voluntario Standard");
        this.setCodigo("n/a");
        this.setPosicao((Point2D)new Point2D.Double(0,0));
        this.setPassword("n/a");
        this.setRaio(0);
        this.setMedical(false);
        this.setVelocidade(0);
        this.setClassificacao(0);
        this.pedidos=new ArrayList<>();
        this.encomendaAtual=new Encomenda();
        this.setHistorico(new ArrayList<Encomenda>());
    }
    
    public Voluntario (String nome,String codEntregador,Point2D pos,String password,float raio,boolean levaMedical,float velocidadeDeEntrega,float c,ArrayList<String> pedidos,Encomenda e,List<Encomenda> lE) {
        this.setNome(nome);
        this.setCodigo(codEntregador);
        this.setPosicao((Point2D)pos.clone());
        this.setPassword(password);
        this.setRaio(raio);
        this.setMedical(levaMedical);
        this.setVelocidade(velocidadeDeEntrega);
        this.setClassificacao(c);
        this.pedidos= new ArrayList<>(pedidos);
        this.encomendaAtual=e.clone();
        this.setHistorico(lE.stream().map(Encomenda::clone).collect(Collectors.toList()));
   }
    
   public Voluntario (Voluntario v) {
       this.setNome(v.getNome());
       this.setCodigo(v.getCodigo());
       this.setPosicao((Point2D)v.getPosicao().clone());
       this.setPassword(v.getPassword());
       this.setRaio(v.getRaio());
       this.setMedical(v.getMedical());
       this.setVelocidade(v.getVelocidade());
       this.setClassificacao(v.getClassificacao());
       this.pedidos=v.getPedidos();
       this.encomendaAtual=v.getEncomenda();
       this.setHistorico(v.getHistorico());
   }
   
   public Encomenda getEncomenda() {
       return this.encomendaAtual.clone();
   }

   public List<String> getPedidos() {
        return new ArrayList<>(this.pedidos);
   }
   
   public String toString() {
       StringBuilder s = new StringBuilder();
       s.append("Nome da Empresa: ").append(this.getNome())
       .append("\nCodigo do Voluntario: ").append(this.getCodigo())
       .append("\nPosiçao: (").append(this.getPosicao().getY()).append(",").append(this.getPosicao().getX()).append(")")
       .append("\nRaio de açao: ").append(this.getRaio())
       .append("\nTransporta encomendas Medicas: ").append(this.getMedical())
       .append("\nVelocidade media(Km/h): ").append(this.getVelocidade())
       .append("\nEncomenda Atual: ").append(this.encomendaAtual)
       .append("\nHistorico de Encomendas: ").append(this.getHistorico().toString());
       return s.toString();
   }
   
   public Voluntario clone() {
       return new Voluntario(this);
   }

   public boolean hasRoomAndMed(boolean med) {
        return this.encomendaAtual.getCodEncomenda().equals("Encomenda Standard") && this.getMedical()==med;
   }

   public void addEncomenda(Encomenda e) {this.encomendaAtual=e.clone();}

   public void addPedido(String enc) {
        this.pedidos.add(enc);
   }
}
