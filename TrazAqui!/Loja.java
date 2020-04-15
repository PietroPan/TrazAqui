
/**
 * Escreva a descrição da classe Loja aqui.
 * 
 * @author (seu nome) 
 * @version (número de versão ou data)
 */

import java.util.List;
import java.util.ArrayList;
import java.awt.geom.Point2D;
import java.util.stream.Collectors;

public class Loja
{
   private String codLoja;
   private String nome;
   private Point2D pos;
   private int tamanhoFila;
   private float tempoAtendimento;
   private List<Encomenda> pedidosProntos;
   
   public Loja() {
       this.codLoja="";
       this.nome="Loja Standard";
       this.pos= (Point2D)new Point2D.Double(0,0);
       this.tamanhoFila = 0;
       this.tempoAtendimento=0;
       this.pedidosProntos=new ArrayList<Encomenda>();
   }
   
   public Loja(String codLoja,String nome,Point2D pos,int tF,float tA,List<Encomenda> lE) {
       this.codLoja=codLoja;
       this.nome=nome;
       this.pos= (Point2D)pos.clone();
       this.tamanhoFila = tF;
       this.tempoAtendimento=tA;
       this.pedidosProntos=lE.stream().map(l -> l.clone()).collect(Collectors.toList());
   }
   
   public Loja(Loja l) {
       this.codLoja=l.getCodLoja();
       this.nome=l.getNome();
       this.pos= (Point2D)l.getPos().clone();
       this.tamanhoFila = l.getTamFila();
       this.tempoAtendimento=l.getTempoAtendimento();
       this.pedidosProntos=l.getPedidos().stream().map(e -> e.clone()).collect(Collectors.toList());
   }
   
   public void setCodLoja(String codLoja) {
       this.codLoja=codLoja;
   }
   
   public void setNome(String nome) {
       this.nome=nome;
   }
   
   public void setPos(Point2D pos) {
       this.pos=(Point2D)pos.clone();
   }
   
   public void setTamFila(int tF) {
       this.tamanhoFila=tF;
   }
   
   public void setTempoAtendimento(float t) {
       this.tempoAtendimento=t;
   }
   
   public void setPedidos(List<Encomenda> lE) {
       this.pedidosProntos=lE.stream().map(e -> e.clone()).collect(Collectors.toList());
   }
   
   public String getCodLoja() {
       return this.codLoja;
   }
   
   public String getNome() {
       return this.nome;
   }
   
   public Point2D getPos() {
       return (Point2D)this.pos.clone();
   }
   
   public int getTamFila() {
       return this.tamanhoFila;
   }
   
   public float getTempoAtendimento() {
       return this.tempoAtendimento;
   }
   
   public List<Encomenda> getPedidos() {
       return this.pedidosProntos.stream().map(e -> e.clone()).collect(Collectors.toList());
   }
   
   public String toString() {
       StringBuilder s = new StringBuilder();
       s.append("Codigo de Loja: ").append(this.codLoja)
       .append("\nNome da Loja: ").append(this.nome)
       .append("\nPosiçao: ").append(this.pos.getX()).append(",").append(this.pos.getY()).append(")")
       .append("\nTamanho da Fila: ").append(this.tamanhoFila)
       .append("\nTempo de Atendimento: ").append(this.tempoAtendimento)
       .append("\nEncomendas Prontas a sair: ").append(this.pedidosProntos);
       return s.toString();
   }
   
   public boolean equals(Object loja) {
       Loja l;
       if (loja==null || loja.getClass()==this.getClass()) return false;
       l=(Loja)loja;
       return l.codLoja.equals(this.codLoja);
   }
   
   public Loja clone() {
       return new Loja(this);
   }
   
}
