
/**
 * Escreva a descrição da classe Loja aqui.
 * 
 * @author (seu nome) 
 * @version (número de versão ou data)
 */

import java.util.*;
import java.awt.geom.Point2D;
import java.util.stream.Collectors;

public class Loja extends BasicInfo
{
   private int tamanhoFila;
   private float tempoAtendimento;
   private Map<String,Encomenda> pedidosProntos;
   
   public Loja() {
       this.setCodigo("n/a");
       this.setNome("Loja Standard");
       this.setPosicao((Point2D)new Point2D.Double(0,0));
       this.setPassword("n/a");
       this.tamanhoFila = 0;
       this.tempoAtendimento=0;
       this.pedidosProntos=new HashMap<>();
   }
   
   public Loja(String codLoja,String nome,Point2D pos,String password,int tF,float tA,Map<String,Encomenda> lE) {
       this.setCodigo(codLoja);
       this.setNome(nome);
       this.setPosicao((Point2D)pos.clone());
       this.setPassword(password);
       this.tamanhoFila = tF;
       this.tempoAtendimento=tA;
       this.pedidosProntos=new HashMap<>();
       for (Map.Entry<String, Encomenda> entry : lE.entrySet()) {
           this.pedidosProntos.put(entry.getKey(),entry.getValue().clone());
       }
   }
   
   public Loja(Loja l) {
       this.setCodigo(l.getCodigo());
       this.setNome(l.getNome());
       this.setPosicao((Point2D)l.getPosicao().clone());
       this.setPassword(l.getPassword());
       this.tamanhoFila = l.getTamFila();
       this.tempoAtendimento=l.getTempoAtendimento();
       this.pedidosProntos=new HashMap<>();
       for (Map.Entry<String, Encomenda> entry : l.getPedidos().entrySet()) {
           this.pedidosProntos.put(entry.getKey(),entry.getValue().clone());
       }
   }
   
   public void setTamFila(int tF) {
       this.tamanhoFila=tF;
   }
   
   public void setTempoAtendimento(float t) {
       this.tempoAtendimento=t;
   }
   
   public void setPedidos(Map<String,Encomenda> lE) {
       for (Map.Entry<String, Encomenda> entry : lE.entrySet()) {
           this.pedidosProntos.put(entry.getKey(),entry.getValue().clone());
       }
   }
   
   public int getTamFila() {
       Random r =new Random();
       if (this.tamanhoFila==-1)
           return r.nextInt()%10;
       return this.tamanhoFila;
   }
   
   public float getTempoAtendimento() {
       return this.tempoAtendimento;
   }
   
   public Map<String,Encomenda> getPedidos() {
       Map<String,Encomenda> m=new HashMap<>();
       for (Map.Entry<String, Encomenda> entry : this.pedidosProntos.entrySet()) {
           m.put(entry.getKey(),entry.getValue().clone());
       }
       return m;
   }
   
   public String toString() {
       StringBuilder s = new StringBuilder();
       s.append("Codigo de Loja: ").append(this.getCodigo())
       .append("\nNome da Loja: ").append(this.getNome())
       .append("\nPosiçao: ").append(this.getPosicao().getX()).append(",").append(this.getPosicao().getY()).append(")")
       .append("\nTamanho da Fila: ").append(this.tamanhoFila)
       .append("\nTempo de Atendimento: ").append(this.tempoAtendimento)
       .append("\nEncomendas Prontas a sair: ").append(this.pedidosProntos);
       return s.toString();
   }
   
   public boolean equals(Object loja) {
       Loja l;
       if (loja==null || loja.getClass()==this.getClass()) return false;
       l=(Loja)loja;
       return l.getCodigo().equals(this.getCodigo());
   }
   
   public Loja clone() {
       return new Loja(this);
   }
   
   public void addPronta(Encomenda e) {
	   this.pedidosProntos.put(e.getCodEncomenda(),e.clone());
   }

   public Encomenda getEncomenda(String id) {return this.pedidosProntos.get(id);}

   public void removeReady(String cod) {
       this.pedidosProntos.remove(cod);
   }
   
}
