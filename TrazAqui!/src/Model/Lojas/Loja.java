
/**
 * Escreva a descrição da classe InterfaceLoja aqui.
 * 
 * @author (seu nome) 
 * @version (número de versão ou data)
 */

import java.util.*;
import java.awt.geom.Point2D;

public class Loja extends BasicInfo implements InterfaceLoja {
   private int tamanhoFila;
   private float tempoAtendimento;
   private Map<String, InterfaceEncomenda> pedidosProntos;
   
   public Loja() {
       this.setCodigo("n/a");
       this.setNome("InterfaceLoja Standard");
       this.setPosicao(new Point2D.Double(0,0));
       this.setPassword("n/a");
       this.tamanhoFila = 0;
       this.tempoAtendimento=0;
       this.pedidosProntos=new HashMap<>();
   }
   
   public Loja(String codLoja, String nome, Point2D pos, String password, int tF, float tA, Map<String, InterfaceEncomenda> lE) {
       this.setCodigo(codLoja);
       this.setNome(nome);
       this.setPosicao((Point2D)pos.clone());
       this.setPassword(password);
       this.tamanhoFila = tF;
       this.tempoAtendimento=tA;
       this.pedidosProntos=new HashMap<>();
       for (Map.Entry<String, InterfaceEncomenda> entry : lE.entrySet()) {
           this.pedidosProntos.put(entry.getKey(),entry.getValue().clone());
       }
   }
   
   public Loja(InterfaceLoja l) {
       this.setCodigo(l.getCodigo());
       this.setNome(l.getNome());
       this.setPosicao((Point2D)l.getPosicao().clone());
       this.setPassword(l.getPassword());
       this.tamanhoFila = l.getTamFila();
       this.tempoAtendimento=l.getTempoAtendimento();
       this.pedidosProntos=new HashMap<>();
       for (Map.Entry<String, InterfaceEncomenda> entry : l.getPedidos().entrySet()) {
           this.pedidosProntos.put(entry.getKey(),entry.getValue().clone());
       }
   }
   
   @Override
   public void setTamFila(int tF) {
       this.tamanhoFila=tF;
   }
   
   @Override
   public void setTempoAtendimento(float t) {
       this.tempoAtendimento=t;
   }
   
   @Override
   public void setPedidos(Map<String, InterfaceEncomenda> lE) {
       for (Map.Entry<String, InterfaceEncomenda> entry : lE.entrySet()) {
           this.pedidosProntos.put(entry.getKey(),entry.getValue().clone());
       }
   }
   
   @Override
   public int getTamFila() {
       Random r =new Random();
       if (this.tamanhoFila==-1)
           return r.nextInt()%10;
       return this.tamanhoFila;
   }
   
   @Override
   public float getTempoAtendimento() {
       return this.tempoAtendimento;
   }
   
   @Override
   public Map<String, InterfaceEncomenda> getPedidos() {
       Map<String, InterfaceEncomenda> m=new HashMap<>();
       for (Map.Entry<String, InterfaceEncomenda> entry : this.pedidosProntos.entrySet()) {
           m.put(entry.getKey(),entry.getValue().clone());
       }
       return m;
   }
   
   @Override
   public String toString() {
       StringBuilder s = new StringBuilder();
       s.append("Codigo de InterfaceLoja: ").append(this.getCodigo())
       .append("\nNome da InterfaceLoja: ").append(this.getNome())
       .append("\nPosiçao: ").append(this.getPosicao().getX()).append(",").append(this.getPosicao().getY()).append(")")
       .append("\nTamanho da Fila: ").append(this.tamanhoFila)
       .append("\nTempo de Atendimento: ").append(this.tempoAtendimento)
       .append("\nEncomendas Prontas a sair: ").append(this.pedidosProntos);
       return s.toString();
   }
   
   @Override
   public boolean equals(Object loja) {
       InterfaceLoja l;
       if (loja==null || loja.getClass()==this.getClass()) return false;
       l=(InterfaceLoja)loja;
       return l.getCodigo().equals(this.getCodigo());
   }
   
   @Override
   public InterfaceLoja clone() {
       return new Loja(this);
   }
   
   @Override
   public void addPronta(InterfaceEncomenda e) {
	   this.pedidosProntos.put(e.getCodEncomenda(),e.clone());
   }

   @Override
   public InterfaceEncomenda getEncomenda(String id) {return this.pedidosProntos.get(id);}

   @Override
   public void removeReady(String cod) {
       this.pedidosProntos.remove(cod);
   }
   
}
