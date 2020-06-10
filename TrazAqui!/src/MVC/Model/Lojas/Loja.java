package MVC.Model.Lojas;
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

    /**
     * Construtor vazio
     */
   public Loja() {
       super();
       this.tamanhoFila = 0;
       this.tempoAtendimento=0;
       this.stock=new HashMap<>();
       this.pedidosProntos=new HashMap<>();
       this.pedidosEmEspera=new HashMap<>();
   }

    /**
     * Construtor parametrizado
     * @param codLoja codigo
     * @param nome nome
     * @param pos posição
     * @param password password
     * @param tF tamanhoFila
     * @param tA tempoAtendimento
     * @param lE lista de encomendas prontas
     * @param le lista de encomendas em espera
     * @param stock stock de uma loja
     */
   public Loja(String codLoja, String nome, Point2D pos, String password, int tF, float tA, Map<String, InterfaceEncomenda> lE, Map<String, InterfaceEncomenda> le, Map<String, InterfaceLinhaEncomenda> stock) {
       super(nome,codLoja,pos,password);
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

    /**
     * Construtor cópia
     * @param l loja a copiar
     */
   public Loja(InterfaceLoja l) {
       super((BasicInfo) l);
       this.tamanhoFila = l.getTamFila();
       this.tempoAtendimento=l.getTempoAtendimento();
       this.stock=l.getStock();
       this.pedidosProntos=l.getPedidos();
       this.pedidosEmEspera=l.getPedidosEspera();
   }

    /**
     * Setter para o tamanho da fila
     * @param tF tamanho da fila
     */
   @Override
   public void setTamFila(int tF) {
       this.tamanhoFila=tF;
   }

    /**
     * Setter para o tempoAtendimento
     * @param t tempo de atendimento
     */
   @Override
   public void setTempoAtendimento(float t) {
       this.tempoAtendimento=t;
   }

    /**
     * Setter de pedidos
     * @param lE lista de pedidos prontos a copiar
     */
   @Override
   public void setPedidos(Map<String, InterfaceEncomenda> lE) {
       this.pedidosProntos=new HashMap<>();
       for (Map.Entry<String, InterfaceEncomenda> entry : lE.entrySet()) {
           this.pedidosProntos.put(entry.getKey(),entry.getValue().clone());
       }
   }

    /**
     * Setter para pedidos em espera
     * @param m lista de pedidos em espera a copiar
     */
   @Override
   public void setPedidosEspera(HashMap<String, InterfaceEncomenda> m) {
       this.pedidosEmEspera=new HashMap<>();
       for (Map.Entry<String, InterfaceEncomenda> entry : m.entrySet()) {
           this.pedidosEmEspera.put(entry.getKey(),entry.getValue().clone());
       }
   }

    /**
     * Setter para o stock
     * @param m stock a copiar
     */
   @Override
   public void setStock(HashMap<String, InterfaceLinhaEncomenda> m) {
       this.stock=new HashMap<>();
       for (Map.Entry<String, InterfaceLinhaEncomenda> entry : m.entrySet()) {
           this.stock.put(entry.getKey(),entry.getValue().clone());
       }
   }

    /**
     * Getter para o tamanho da fila
     * @return tamanho da fila
     */
   @Override
   public int getTamFila() {
       Random r =new Random();
       if (this.tamanhoFila==-1)
           return r.nextInt()%10;
       return this.tamanhoFila;
   }

    /**
     * Getter para o tempo de Atendimento
     * @return tempo de atendimento
     */
   @Override
   public float getTempoAtendimento() {
       return this.tempoAtendimento;
   }

    /**
     * Getter para os pedidos prontos
     * @return dicionário de pedidos prontos cópia
     */
   @Override
   public Map<String, InterfaceEncomenda> getPedidos() {
       Map<String, InterfaceEncomenda> m=new HashMap<>();
       for (Map.Entry<String, InterfaceEncomenda> entry : this.pedidosProntos.entrySet()) {
           m.put(entry.getKey(),entry.getValue().clone());
       }
       return m;
   }

    /**
     * Getter para os pedidos em espera
     * @return dicionário para os pedidos em espera cópia
     */
   @Override
   public Map<String, InterfaceEncomenda> getPedidosEspera() {
       Map<String, InterfaceEncomenda> m=new HashMap<>();
       for (Map.Entry<String, InterfaceEncomenda> entry : this.pedidosEmEspera.entrySet()) {
           m.put(entry.getKey(),entry.getValue().clone());
       }
       return m;
   }

    /**
     * Getter para o stock
     * @return dicionário cópia de stock
     */
   @Override
   public Map<String, InterfaceLinhaEncomenda> getStock() {
       Map<String, InterfaceLinhaEncomenda> m=new HashMap<>();
       for (Map.Entry<String, InterfaceLinhaEncomenda> entry : this.stock.entrySet()) {
           m.put(entry.getKey(),entry.getValue().clone());
       }
       return m;
   }

    /**
     * Adicionar lista de linhas d encomenda ao stock
     * @param l lista de linhas de encomenda
     */
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

    /**
     * Adicionar linha de encomenda ao stock
     * @param l linha de encomenda a adicionar
     */
   @Override
   public void addSToStock(InterfaceLinhaEncomenda l){
       String cod = gerarCodProd();
       l.setcodProduto(cod);
       this.stock.put(cod,l);
   }

    /**
     * Remover do stock
     * @param cod código de stock a remover
     */
   @Override
   public void removeFromStock(String cod){
       this.stock.remove(cod);
   }

    /**
     * Mudar preço de artigo
     * @param cod código de produto
     * @param preco preço
     */
   @Override
   public void mudarPreco(String cod, double preco){
       InterfaceLinhaEncomenda prod = this.stock.get(cod);
       prod.setPreco(preco);
       this.stock.put(cod,prod);
   }

    /**
     * Mudar quantidade em stock de um produto
     * @param cod código de produto
     * @param quant quantidade
     */
    @Override
    public void mudarQuantidade(String cod, double quant){
        InterfaceLinhaEncomenda prod = this.stock.get(cod);
        prod.setQuantidade(quant);
        this.stock.put(cod,prod);
    }

    /**
     * Método toString
     * @return informação relevante de loja
     */
   @Override
   public String toString() {
       return "Codigo de Loja: " + this.getCodigo() +
               "\nNome da Loja: " + this.getNome() +
               "\nPosiçao: " + this.getPosicao().getX() + "," + this.getPosicao().getY() + ")" +
               "\nTamanho da Fila: " + this.tamanhoFila +
               "\nTempo de Atendimento: " + this.tempoAtendimento +
               "\nEncomendas Prontas a sair: " + this.pedidosProntos;
   }

    /**
     * Método equals
     * @param loja loja a qual comparar
     * @return true se possuirem o mesmo código
     */
   @Override
   public boolean equals(Object loja) {
       InterfaceLoja l;
       if (loja==null || loja.getClass()==this.getClass()) return false;
       l=(InterfaceLoja)loja;
       return l.getCodigo().equals(this.getCodigo());
   }

    /**
     * Método clone
     * @return cópia de loja
     */
   @Override
   public InterfaceLoja clone() {
       return new Loja(this);
   }

    /**
     * Getter de encomenda com um certo id
     * @param id código de encomenda
     * @return encomenda
     */
   @Override
   public InterfaceEncomenda getEncomenda(String id) {
       InterfaceEncomenda e=this.pedidosProntos.get(id);
       if (e!=null) {
           return e;
       }
       else return this.pedidosEmEspera.get(id);
   }

    /**
     * Adicionar encomenda aos pedidos prontos
     * @param e encomenda a adicionar
     */
    @Override
    public void addPronta(InterfaceEncomenda e) {
        this.pedidosProntos.put(e.getCodEncomenda(),e.clone());
    }

    /**
     * Adicionar encomenda aos pedidos em espera
     * @param e encomenda a adicionar
     */
    @Override
    public void addNotReady(InterfaceEncomenda e) {
       InterfaceLinhaEncomenda p;
       this.pedidosEmEspera.put(e.getCodEncomenda(),e.clone());
       for (InterfaceLinhaEncomenda l :e.getPedido()) {
           if ((p=this.stock.get(l.getcodProduto()))!=null)
            p.removeQuantidade(l.getQuantidade());
       }
    }

    /**
     * Remover encomenda aos pedidos em espera
     * @param s encomenda a remover
     */
    @Override
    public void removeNotReady(String s) {
       this.pedidosEmEspera.remove(s);
    }

    /**
     * Remover encomenda aos pedidos prontos
     * @param cod encomenda a remover
     */
   @Override
   public void removeReady(String cod) {
       this.pedidosProntos.remove(cod);
   }

    /**
     * Verificar se uma encomenda está pronta
     * @param id código de encomenda a verificar
     * @return true se existir
     */
   @Override
   public boolean isReady(String id) {
       return pedidosProntos.get(id) != null;
   }

    /**
     * Metódo que verifica se uma encomedna está em espera
     * @param id código de encomenda a verificar
     * @return true se existir
     */
   @Override
   public boolean isNotReady(String id) {
       return this.pedidosEmEspera.get(id) != null;
   }

    /**
     * Método que recebe várias linhas de encomenda e forma uma lista com estas
     * @param l linhas de encomendas
     * @return lisat das linhasd e encomenda
     * @throws ProductNotAvailableException caso seja pedido um produto inexistente
     */
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

    /**
     * Método responsável por atualizar a loja mudando as suas encomendas em espera para prontas caso ja tenho passado do tempo
     * @param t data a qual comparar
     * @return dicionario entre codigo de user a corresponder a uma lista de mensagens de um utilizador
     */
   @Override
   public Map<String, List<String>> atualizaLoja(LocalDateTime t) {
       Map<String, List<String>> messages = new HashMap<>();
       Map<String,InterfaceEncomenda> aux = new HashMap<>();
       List<String> lista;
       for (InterfaceEncomenda e : this.pedidosEmEspera.values()) {
           if (e.getDataEntrega().isBefore(t)) {
               aux.put(e.getCodEncomenda(),e.clone());
               if (messages.containsKey(e.getDestino())) {
                   lista = messages.get(e.getDestino());
               }
               else
                   lista = new ArrayList<>();
               lista.add("A sua Encomenda de id " + e.getCodEncomenda() + " está pronta para ser enviada");
               messages.put(e.getDestino(), lista);
           }
       }
       this.pedidosEmEspera.entrySet().removeIf(l->aux.containsKey(l.getKey()));
       this.pedidosProntos.putAll(aux);
       return messages;
   }

    /**
     * Método responsável por gerar um código de produto
     * @return código de produto
     */
    @Override
    public String gerarCodProd() {
        Random rand = new Random();
        String cod = "p";
        boolean b=true;
        while (b){
            int num = rand.nextInt(999);
            cod=cod.concat(String.valueOf(num));
            b=stock.containsKey(cod);
        }
        return cod;
    }
}