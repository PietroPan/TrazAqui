
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
    private float chanceAceitar;
    
    public Voluntario () {
        this.setNome("Voluntario Standard");
        this.setCodEntregador("n/a");
        this.setPos((Point2D)new Point2D.Double(0,0));
        this.setRaio(0);
        this.setMedical(false);
        this.setVelocidade(0);
        this.setClassificacao(0);
        this.chanceAceitar=0.5f;
        this.encomendaAtual=new Encomenda();
        this.setHistorico(new ArrayList<Encomenda>());
    }
    
    public Voluntario (String nome,String codEntregador,Point2D pos,float raio,boolean levaMedical,float velocidadeDeEntrega,float c,float chance,Encomenda e,List<Encomenda> lE) {
        this.setNome(nome);
        this.setCodEntregador(codEntregador);
        this.setPos((Point2D)pos.clone());
        this.setRaio(raio);
        this.setMedical(levaMedical);
        this.setVelocidade(velocidadeDeEntrega);
        this.setClassificacao(c);
        this.chanceAceitar=chance;
        this.encomendaAtual=e.clone();
        this.setHistorico(lE.stream().map(Encomenda::clone).collect(Collectors.toList()));
   }
    
   public Voluntario (Voluntario v) {
       this.setNome(v.getNome());
       this.setCodEntregador(v.getCodEntregador());
       this.setPos((Point2D)v.getPos().clone());
       this.setRaio(v.getRaio());
       this.setMedical(v.getMedical());
       this.setVelocidade(v.getVelocidade());
       this.setClassificacao(v.getClassificacao());
       this.chanceAceitar=v.getChanceAceitar();
       this.encomendaAtual=v.encomendaAtual.clone();
       this.setHistorico(v.getHistorico().stream().map(l -> l.clone()).collect(Collectors.toList()));
   }
   
   public Encomenda getEncomenda() {
       return this.encomendaAtual.clone();
   }

    public float getChanceAceitar() {
        return chanceAceitar;
    }

    public void setChanceAceitar(float chanceAceitar) {
        this.chanceAceitar = chanceAceitar;
    }
   
   public String toString() {
       StringBuilder s = new StringBuilder();
       s.append("Nome da Empresa: ").append(this.getNome())
       .append("\nCodigo do Voluntario: ").append(this.getCodEntregador())
       .append("\nPosiçao: (").append(this.getPos().getY()).append(",").append(this.getPos().getX()).append(")")
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
}
