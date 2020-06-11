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

    /**
     * Construtor vazio
     */
   public Transportadora() {
       super();
       this.NIF="n/a";
       this.custoKm=0;
       this.custoKg=0;
       this.numeroDeEncomendas=0;
       this.encomendaAtual=new ArrayList<>();
       this.pedidos=new ArrayList<>();
   }

    /**
     * Construtor parametrizado
     * @param nome nome
     * @param codEmpresa código de transportadora
     * @param pos posição
     * @param password password
     * @param raio raio
     * @param NIF NIF
     * @param custoKm custoKm
     * @param custoKg custoKg
     * @param levaMedical levaMedical
     * @param velocidadeDeEntrega velocidadeEntrega
     * @param c classificação
     * @param vC vezes que foi classificado
     * @param numeroDeEncomendas número de Encomendas
     * @param encomendaAtual encomenda atual
     * @param historicoEncomendas histórico de encomendas
     */
   public Transportadora(String nome, String codEmpresa, Point2D pos, String password, float raio, String NIF, double custoKm, double custoKg, boolean levaMedical, float velocidadeDeEntrega, float c, int vC, int numeroDeEncomendas, List<InterfaceEncomenda> encomendaAtual, List<InterfaceEncomenda> historicoEncomendas) {
       super(nome,codEmpresa,pos,password,raio,levaMedical,velocidadeDeEntrega,c,vC);
       this.NIF=NIF;
       this.custoKm=custoKm;
       this.custoKg=custoKg;
       this.numeroDeEncomendas=numeroDeEncomendas;
       this.encomendaAtual=encomendaAtual.stream().map(InterfaceEncomenda::clone).collect(Collectors.toList());
       this.pedidos=new ArrayList<>();
   }

    /**
     * Construtor cópia
     * @param e Transportadora a copiar
     */
   public Transportadora(Transportadora e) {
       super(e);
       this.NIF=e.NIF;
       this.custoKm=e.getCustoKm();
       this.custoKg=e.getCustoKg();
       this.numeroDeEncomendas=e.getNumEnc();
       this.encomendaAtual=e.getEncomendaAtual();
       this.pedidos=e.getPedidos();
   }

    /**
     * Setter para NIF
     * @param NIF NIF
     */
   @Override
   public void setNIF(String NIF) {
       this.NIF=NIF;
   }

    /**
     * Setter para custoKm
     * @param custoKm custo por Km
     */
   @Override
   public void setCustoKm(double custoKm) {
       this.custoKm=custoKm;
   }

    /**
     * Setter para custoKg
     * @param custoKg custo por Kg
     */
   @Override
   public void setCustoKg(double custoKg) {
       this.custoKg=custoKg;
   }

    /**
     * Setter número de enc
     * @param n número de encomendas
     */
   @Override
   public void setNumeroEnc(int n) {
       this.numeroDeEncomendas=n;
   }

    /**
     * Setter para a lista de encomendas
     * @param lE lista de encomendas
     */
   @Override
   public void setEncomendas(List<InterfaceEncomenda> lE) {
       this.encomendaAtual=lE.stream().map(InterfaceEncomenda::clone).collect(Collectors.toList());
   }

    /**
     * Adição de uma encomenda
     * @param e encomenda a adicionar
     */
   @Override
   public void addEncomenda(InterfaceEncomenda e) {
       this.encomendaAtual.add(e.clone());
   }

    /**
     * Getter para o NIF
     * @return NIF
     */
   @Override
   public String getNIF() {
       return this.NIF;
   }

    /**
     * Getter para custoKg
     * @return custo por Kg
     */
   @Override
   public double getCustoKg() {
       return this.custoKg;
   }

    /**
     * Getter para custoKm
     * @return custo por Km
     */
   @Override
   public double getCustoKm() {
       return this.custoKm;
   }

    /**
     * Getter para numEnc
     * @return número de Encomendas
     */
   @Override
   public int getNumEnc() {
       return this.numeroDeEncomendas;
   }

    /**
     * Getter para a encomenda atual
     * @return lista de encomendas a serem transportadas atualmente
     */
   @Override
   public List<InterfaceEncomenda> getEncomendaAtual() {
       return this.encomendaAtual.stream().map(InterfaceEncomenda::clone).collect(Collectors.toList());
   }

    /**
     * Getter para uma encomenda especifica no carregamento atual
     * @param id código da encomenda
     * @return encomenda
     */
    @Override
    public InterfaceEncomenda getEncomenda(String id) {
       for (InterfaceEncomenda e : this.encomendaAtual) {
           if (e.getCodEncomenda().equals(id))
               return e.clone();
       }
       return null;
    }

    /**
     * Getter para os pedidos
     * @return Lista de pedidos
     */
    @Override
    public List<Map.Entry<InterfaceEncomenda,String>> getPedidos(){
       return new ArrayList<>(this.pedidos);
    }

    /**
     * Método de atualização de estado
     * @param t data a comparar
     * @return Lista de encomendas que foram entregues
     */
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

    /**
     * Método que verifica randomEvents
     * @param t data a comparar
     * @return Dicionario entre código de utilizador e as mensagens de eventos aleatorios que se destinam a ele
     */
    @Override
    public Map<String,String> checkEvent(LocalDateTime t){
       Map<String,String> m = new HashMap<>();
       String[] goodEvents = new String[]{
               " encontrou uma estrela colorida, parece que a sua encomenda já chegou",
               "Parece que está com sorte,",
               "O tempo melhorou,",

       };
        String[] badEvents = new String[]{
                " protestantes roubaram a sua encomenda, lamentamos a inconveniência",
                "Começou a chover torrencialmente,",
                "Ocorreu um acidente,",
                "A sua encomenda ficou presa na Alfândega,",
                "Começou uma tempestade de neve,"
        };
       Random rand = new Random();
       String usr,msg;
       int r1=rand.nextInt(100);
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

    /**
     * Método toString
     * @return String com informação relevante sobre uma transportadora
     */
    @Override
   public String toString() {
        return "Nome de Empresa de Entregas: " + this.getNome() +
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
   }

    /**
     * Método equals
     * @param o objeto ao qual comparar
     * @return true se as suas variaveis tiverem todas o mesmo valor
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Transportadora that = (Transportadora) o;
        return Double.compare(that.getCustoKm(), getCustoKm()) == 0 &&
                Double.compare(that.getCustoKg(), getCustoKg()) == 0 &&
                numeroDeEncomendas == that.numeroDeEncomendas &&
                Objects.equals(getNIF(), that.getNIF()) &&
                Objects.equals(getEncomendaAtual(), that.getEncomendaAtual()) &&
                Objects.equals(getPedidos(), that.getPedidos());
    }

    /**
     * Método de clone
     * @return cópia de entregador ao qual clone foi chamado
     */
    @Override
   public InterfaceEntregador clone() {
       return new Transportadora(this);
   }

    /**
     * Método que verifica se existe espaço numa transportadora
     * @return true se existir, false otherwise
     */
   @Override
   public boolean hasRoom(){
       return (this.encomendaAtual.size()<this.numeroDeEncomendas);
   }

    /**
     * Método que adiciona pedido
     * @param enc encomenda a adicionar
     */
   @Override
    public void addPedido(InterfaceEncomenda enc){
       this.pedidos.add(new AbstractMap.SimpleEntry<>(enc,"p"));
   }

    /**
     * Método que adiciona pedido com estado
     * @param enc encomenda a adicionar
     * @param stat estado correspondente
     */
    @Override
    public void addPedido(InterfaceEncomenda enc,String stat){ this.pedidos.add(new AbstractMap.SimpleEntry<>(enc,stat));    }

    /**
     * Método que adiciona pedidos
     * @param encs encomendas a adicionar
     * @param stat estado relacionado a todas elas
     */
    @Override
    public void addPedidos(List<InterfaceEncomenda> encs,String stat){
       for (InterfaceEncomenda i : encs){
           this.pedidos.add(new AbstractMap.SimpleEntry<>(i,stat));
       }
   }

    /**
     * Método que altera o estado de um pedido
     * @param enc encomenda a alterar o estado
     * @param stat estado a colocar nesta
     */
    @Override
    public void alteraPedido(InterfaceEncomenda enc,String stat){
       this.pedidos=this.getPedidos().stream().filter(i->!(i.getKey().getCodEncomenda().equals(enc.getCodEncomenda()))).collect(Collectors.toList());
       this.addPedido(enc,stat);
   }

    /**
     * Método que altera todos os pedidos que satisfizerem uma certa condição
     * @param stat estado a verificar
     * @param statIf estado a colocar se for igual ao stat
     */
   @Override
   public void alteraTodosPedidosIf(String stat,String statIf){
       List<InterfaceEncomenda> aux = new ArrayList<>();
       for (Map.Entry<InterfaceEncomenda,String> i : this.getPedidos()){
           if (i.getValue().equals(statIf)) aux.add(i.getKey());
       }
       this.pedidos.removeIf(i->i.getValue().equals(statIf));
       addPedidos(aux,stat);
   }

    /**
     * Método que atualiza encomenda
     * @param enc encomenda a atualizar
     */
   @Override
    public void atualizaAtual (InterfaceEncomenda enc){
       this.encomendaAtual.removeIf(i->i.getCodEncomenda().equals(enc.getCodEncomenda()));
       this.encomendaAtual.add(enc);
   }

    /**
     * Método que verifica a existência de um pedido
     * @param enc código da encomenda
     * @return true caso exsita
     */
   @Override
    public boolean existePedido (String enc){
       for (Map.Entry<InterfaceEncomenda,String> i : this.pedidos){
           if (i.getKey().getCodEncomenda().equals(enc)) return true;
       }
       return false;
   }

    /**
     * Verifica o tempo que falta para a entrega de uma encomenda
     * @param enc encomenda a verificar
     * @param l tempo atual
     * @return mensagem com o resultado
     */
   @Override
    public String timeLeft(String enc,LocalDateTime l){
       for (InterfaceEncomenda i : this.encomendaAtual){
           if (i.getCodEncomenda().equals(enc)){
               if (this.isAEntregar()){
                   return "Faltam cerca de " + ChronoUnit.MINUTES.between(l,i.getDataEntrega()) + " minutos para a sua encomenda chegar";
               } else {
                   return "Encomenda não está em movimento";
               }
           }
       }
       return "Informação não disponivel";
   }

    /**
     * Reijeita pedidos a mais
     * @param enc encomenda com pedidos a mais
     */
   @Override
   public void rejeitaPedidos(String enc){
       List<InterfaceEncomenda> aux = new ArrayList<>();
       for (Map.Entry<InterfaceEncomenda,String> i : this.pedidos){
           if (!i.getValue().equals("a")&&i.getKey().getCodEncomenda().equals(enc)) aux.add(i.getKey());
       }
       this.pedidos.removeIf(i->!i.getValue().equals("a")&&i.getKey().getCodEncomenda().equals(enc));
       this.addPedidos(aux,"r");
   }
}
