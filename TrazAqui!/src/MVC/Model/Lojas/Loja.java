package MVC.Model.Lojas;
/**
 * Escreva a descrição da classe InterfaceLoja aqui.
 * 
 * @author (seu nome) 
 * @version (número de versão ou data)
 */

import java.awt.geom.Point2D;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;
import Common.*;
import Exceptions.*;

public class Loja extends BasicInfo implements InterfaceLoja, Serializable {
   private int tamanhoFila;
   private float tempoAtendimento;
   private Map<String, InterfaceLinhaEncomenda> stock;
   private Map<String, InterfaceEncomenda> pedidosProntos;
   private Map<String, InterfaceEncomenda> pedidosEmEspera;

   public Loja() {
       this.setCodigo("n/a");
       this.setNome("InterfaceLoja Standard");
       this.setPosicao(new Point2D.Double(0,0));
       this.setPassword("n/a");
       this.tamanhoFila = 0;
       this.tempoAtendimento=0;
       this.stock=new HashMap<>();
       this.pedidosProntos=new HashMap<>();
       this.pedidosEmEspera=new HashMap<>();
   }

   public Loja(String codLoja, String nome, Point2D pos, String password, int tF, float tA, Map<String, InterfaceEncomenda> lE, Map<String, InterfaceEncomenda> le, Map<String, InterfaceLinhaEncomenda> stock) {
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
       this.pedidosEmEspera=new HashMap<>();
       for (Map.Entry<String, InterfaceEncomenda> entry : le.entrySet()) {
           this.pedidosEmEspera.put(entry.getKey(),entry.getValue().clone());
       }
       this.stock=new HashMap<>();
       for (Map.Entry<String, InterfaceLinhaEncomenda> entry : stock.entrySet()) {
           this.stock.put(entry.getKey(),entry.getValue().clone());
       }
   }

   public Loja(InterfaceLoja l) {
       this.setCodigo(l.getCodigo());
       this.setNome(l.getNome());
       this.setPosicao((Point2D)l.getPosicao().clone());
       this.setPassword(l.getPassword());
       this.tamanhoFila = l.getTamFila();
       this.tempoAtendimento=l.getTempoAtendimento();
       this.stock=l.getStock();
       this.pedidosProntos=l.getPedidos();
       this.pedidosEmEspera=l.getPedidosEspera();
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
       this.pedidosProntos=new HashMap<>();
       for (Map.Entry<String, InterfaceEncomenda> entry : lE.entrySet()) {
           this.pedidosProntos.put(entry.getKey(),entry.getValue().clone());
       }
   }

   @Override
   public void setPedidosEspera(HashMap<String, InterfaceEncomenda> m) {
       this.pedidosEmEspera=new HashMap<>();
       for (Map.Entry<String, InterfaceEncomenda> entry : m.entrySet()) {
           this.pedidosEmEspera.put(entry.getKey(),entry.getValue().clone());
       }
   }

   @Override
   public void setStock(HashMap<String, InterfaceLinhaEncomenda> m) {
       this.stock=new HashMap<>();
       for (Map.Entry<String, InterfaceLinhaEncomenda> entry : m.entrySet()) {
           this.stock.put(entry.getKey(),entry.getValue().clone());
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
   public Map<String, InterfaceEncomenda> getPedidosEspera() {
       Map<String, InterfaceEncomenda> m=new HashMap<>();
       for (Map.Entry<String, InterfaceEncomenda> entry : this.pedidosEmEspera.entrySet()) {
           m.put(entry.getKey(),entry.getValue().clone());
       }
       return m;
   }

   @Override
   public Map<String, InterfaceLinhaEncomenda> getStock() {
       Map<String, InterfaceLinhaEncomenda> m=new HashMap<>();
       for (Map.Entry<String, InterfaceLinhaEncomenda> entry : this.stock.entrySet()) {
           m.put(entry.getKey(),entry.getValue().clone());
       }
       return m;
   }

   @Override
   public void addToStock(List<InterfaceLinhaEncomenda> l) {
       for (InterfaceLinhaEncomenda e : l) {
           InterfaceLinhaEncomenda ant =this.stock.get(e.getcodProduto());
           if (ant!=null) {
               ant.setQuantidade(ant.getQuantidade()+e.getQuantidade());
               ant.setPreco(e.getPreco());
           }
           else {
               this.stock.put(e.getcodProduto(),e.clone());
           }
       }
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
   public InterfaceEncomenda getEncomenda(String id) {
       InterfaceEncomenda e=this.pedidosProntos.get(id);
       if (e!=null) {
           return e;
       }
       else return this.pedidosEmEspera.get(id);
   }

    @Override
    public void addPronta(InterfaceEncomenda e) {
        this.pedidosProntos.put(e.getCodEncomenda(),e.clone());
    }

    @Override
    public void addNotReady(InterfaceEncomenda e) {
       InterfaceLinhaEncomenda p;
       this.pedidosEmEspera.put(e.getCodEncomenda(),e.clone());
       for (InterfaceLinhaEncomenda l :e.getPedido()) {
           if ((p=this.stock.get(l.getcodProduto()))!=null)
            p.removeQuantidade(l.getQuantidade());
       }
    }

    @Override
    public void removeNotReady(String s) {
       this.pedidosEmEspera.remove(s);
    }

   @Override
   public void removeReady(String cod) {
       this.pedidosProntos.remove(cod);
   }

   @Override
   public boolean isReady(String id) {
       return pedidosProntos.get(id) != null;
   }

   @Override
   public boolean isNotReady(String id) {
       return this.pedidosEmEspera.get(id) != null;
   }

   @Override
   public List<InterfaceLinhaEncomenda> formaListaLinhasEncomenda(List<Map.Entry<String, Double>> l) throws ProductNotAvailableException {
       int r=0;
       List<InterfaceLinhaEncomenda> lista = new ArrayList<>();
       List<String> exceptions=new ArrayList<>();
       for (Map.Entry<String,Double> e : l) {
           InterfaceLinhaEncomenda p = this.stock.get(e.getKey());
           if (p==null || p.getQuantidade()<e.getValue() ) {
               r=1;
               exceptions.add(e.getKey());
           }
           else {
               lista.add(new LinhaEncomenda(p.getcodProduto(),p.getDescricao(),p.getPreco()*e.getValue(),e.getValue()));
           }
       }
       if (r==1) {
           throw new ProductNotAvailableException("Os seguintes não estão disponíveis: " +exceptions.toString());
       }
       return lista;
   }

   @Override
   public Map<String, List<String>> atualizaLoja(LocalDateTime t) {
       Map<String, List<String>> messages = new HashMap<>();
       List<String> lista;
       for (InterfaceEncomenda e : this.pedidosEmEspera.values()) {
           if (e.getDataEntrega().plusMinutes((long)this.tempoAtendimento).isBefore(t)) {
               this.pedidosEmEspera.remove(e.getCodEncomenda());
               this.pedidosProntos.put(e.getCodEncomenda(), e);
               if (messages.containsKey(e.getDestino())) {
                   lista = messages.get(e.getDestino());
               }
               else
                   lista = new ArrayList<>();
               lista.add("A sua Encomenda de id " + e.getCodEncomenda() + " está pronta para ser enviada");
               messages.put(e.getDestino(), lista);
           }
       }
       return messages;
   }

}
