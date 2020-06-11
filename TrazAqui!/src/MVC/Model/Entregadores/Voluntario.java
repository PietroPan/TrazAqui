package MVC.Model.Entregadores;
import java.awt.geom.Point2D;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import Common.*;

public class Voluntario extends Entregador implements InterfaceVoluntario, Serializable {
    private InterfaceEncomenda encomendaAtual;
    private List<String> pedidos;

    /**
     * Construtor vazio
     */
    public Voluntario() {
        super();
        this.pedidos=new ArrayList<>();
        this.encomendaAtual=new Encomenda();
    }

    /**
     * Construtor parametrizado
     * @param nome nome
     * @param codEntregador código de entregador
     * @param pos posição de entregador
     * @param password password
     * @param raio raio de ação
     * @param levaMedical boolean de trasnportes médicos
     * @param velocidadeDeEntrega velocidadeDeEntrega
     * @param c classificação
     * @param vC vezesClassificado
     * @param pedidos Lisat de pedidos
     * @param e encomendaAtual
     * @param lE histórico de pedidos realizados
     */
    public Voluntario(String nome, String codEntregador, Point2D pos, String password, float raio, boolean levaMedical, float velocidadeDeEntrega, float c, int vC, ArrayList<String> pedidos, InterfaceEncomenda e, List<InterfaceEncomenda> lE) {
        super(nome,codEntregador,pos,password,raio,levaMedical,velocidadeDeEntrega,c,vC);
        this.pedidos= new ArrayList<>(pedidos);
        this.encomendaAtual=e.clone();
   }

    /**
     * Construtor cópia
     * @param v Voluntário a copiar
     */
   public Voluntario(Voluntario v) {
       super(v);
       this.pedidos=v.getPedidos();
       this.encomendaAtual=v.getEncomenda();
   }

    /**
     * Getter do parametro encomenda
     * @return cópia de uma encomenda
     */
   @Override
   public InterfaceEncomenda getEncomenda() {
       return this.encomendaAtual.clone();
   }

    /**
     * Getter de uma encomenda de código
     * @param id código de encomenda
     * @return encomenda que possui esse código
     */
   @Override
   public InterfaceEncomenda getEncomenda(String id) {
        InterfaceEncomenda e = this.encomendaAtual;
        if (e.getCodEncomenda().equals(id))
            return e.clone();
        else
            return null;
   }

    /**
     * Getter para os pedidos
     * @return cópia de pedidos
     */
   @Override
   public List<String> getPedidos() {
        return new ArrayList<>(this.pedidos);
   }

    /**
     * Método toString
     * @return String com a informação relevante de voluntário
     */
   @Override
   public String toString() {
       return "Nome da Empresa: " + this.getNome() +
               "\nCodigo do InterfaceVoluntario: " + this.getCodigo() +
               "\nPosiçao: (" + this.getPosicao().getY() + "," + this.getPosicao().getX() + ")" +
               "\nRaio de açao: " + this.getRaio() +
               "\nTransporta encomendas Medicas: " + this.getMedical() +
               "\nVelocidade media(Km/h): " + this.getVelocidade() +
               "\nCommon.Encomenda Atual: " + this.encomendaAtual;
   }

    /**
     * Método equals
     * @param o objeto ao qual comparar
     * @return true se forem iguais
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Voluntario that = (Voluntario) o;
        return Objects.equals(encomendaAtual, that.encomendaAtual) &&
                Objects.equals(getPedidos(), that.getPedidos());
    }

    /**
     * Método clone
     * @return cópia da entidade a qual clone foi chamado
     */
    @Override
    public InterfaceEntregador clone() {
        return new Voluntario(this);
    }

    /**
     * Adicionar encomenda
     * @param e adicionaEncomenda ao voluntário
     */
   @Override
   public void addEncomenda(InterfaceEncomenda e) {
        this.encomendaAtual=e.clone();
    }

    /**
     * Adiciona um pedido
     * @param enc código de encomenda a adicionar
     */
   @Override
   public void addPedido(String enc) {
        this.pedidos.add(enc);
   }

    /**
     * Método que atualiza o estado de um voluntário
     * @param t data a qual comparar a sua encomenda
     * @return Lista de encomendas que foram entregues
     */
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

    /**
     * Método que atualiza a encomenda atual
     * @param enc encomenda a colocar no lugar
     */
    @Override
    public void atualizaAtual(InterfaceEncomenda enc){
        this.encomendaAtual=enc.clone();
    }

    /**
     * Método que está responsável pelos vários randomEvents
     * @param t hora a comparar
     * @return Entry que a utilizador faz corresponder uma mensagem
     */
    @Override
    public Map.Entry<String,String> checkEvent(LocalDateTime t){
        String[] goodEvents = new String[]{
                " encontrou uma estrela colorida, parece que a sua encomenda já chegou",
                "Parece que está com sorte,",
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
        String usr="X",msg="";
        int r1=rand.nextInt(100);
        if (this.encomendaAtual.getDestino().contains("v")){
            if (r1<5||r1==100){
                usr=this.encomendaAtual.getDestino();
                if (r1+5<this.getClassificacao()){
                    if (r1==100){
                        msg = "O voluntario "+this.getCodigo()+goodEvents[0];
                        this.encomendaAtual.setDataEntrega(t);
                    }
                    else {
                        msg = goodEvents[(rand.nextInt(2)+1)]+" o voluntario "+this.getCodigo()+" vai chegar mais cedo";
                        long time = ChronoUnit.MINUTES.between(t,this.encomendaAtual.getDataEntrega());
                        this.encomendaAtual.setDataEntrega(this.encomendaAtual.getDataEntrega().minusMinutes(time/2));
                    }
                } else {
                    if (r1==100){
                        msg = "O voluntario "+this.getCodigo()+"informa que"+badEvents[0];
                        this.encomendaAtual=new Encomenda();
                    }
                    else {
                        msg = badEvents[(rand.nextInt(4)+1)]+" o voluntario "+this.getCodigo()+" vai chegar mais tarde";
                        long time = ChronoUnit.MINUTES.between(t,this.encomendaAtual.getDataEntrega());
                        this.encomendaAtual.setDataEntrega(this.encomendaAtual.getDataEntrega().plusMinutes(time/2));
                    }
                }
            }
        }
        return new AbstractMap.SimpleEntry<>(usr,msg);
    }

    @Override
    public String timeLeft(String enc,LocalDateTime l){
        if (this.encomendaAtual.getCodEncomenda().equals(enc)){
            if(this.isAEntregar()){
                return "Faltam " + ChronoUnit.MINUTES.between(l,this.encomendaAtual.getDataEntrega()) + "minutos para a sua encomenda chegar";
            }
            else return "Encomenda não está em movimento";
        }
        return "Informação não disponivel";
    }
}
