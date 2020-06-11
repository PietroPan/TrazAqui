package MVC.Model.Entregadores;
import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import Common.*;

public abstract class Entregador extends BasicInfo implements InterfaceEntregador, Serializable {
    private float raio;
    private boolean levaMedical;
    private float velocidadeDeEntrega;
    private float classificacao;
    private int vezesClassificado;
    private boolean aEntregar;

    /**
     * Construtor cópia
     * @param e
     */
    public Entregador(Entregador e) {
        super(e);
        this.raio=e.getRaio();
        this.levaMedical=e.getMedical();
        this.velocidadeDeEntrega=e.getVelocidade();
        this.classificacao=e.getClassificacao();
        this.vezesClassificado=e.getVezesClassificado();
        this.aEntregar=false;
    }

    /**
     * Construtor vazio
     */
    public Entregador() {
        super();
        this.raio=0;
        this.levaMedical=false;
        this.velocidadeDeEntrega=0;
        this.vezesClassificado=0;
        this.classificacao=5;
        this.aEntregar=false;
    }

    /**
     * Cosntrutor parametrizado
     * @param nome nome
     * @param cod código
     * @param pos posição
     * @param password password
     * @param raio raio de ação
     * @param levaMedical leva ou não encomendas médicas
     * @param velocidadeDeEntrega velocidade
     * @param c classificação
     * @param vC vezes classificado
     */
    public Entregador(String nome, String cod, Point2D pos, String password, float raio, boolean levaMedical, float velocidadeDeEntrega, float c, int vC) {
        super(nome,cod,pos,password);
        this.raio=raio;
        this.levaMedical=levaMedical;
        this.velocidadeDeEntrega=velocidadeDeEntrega;
        this.classificacao=c;
        this.vezesClassificado=vC;
        this.aEntregar=false;
    }

    /**
     * Setter para o estado de um entregador
     * @param aEntregar Parametro a copiar
     */
    @Override
    public void setAEntregar(boolean aEntregar){
        this.aEntregar=aEntregar;
    }

    /**
     * Getter para o estado de um entregador
     * @return True se este se encontra a entregar
     */
    @Override
    public boolean isAEntregar(){
        return this.aEntregar;
    }

    /**
     * Setter para o raio de um entregador
     * @param raio Raio de um entregador
     */
   @Override
   public void setRaio(float raio) {
       this.raio=raio;
   }
    /**
     * Setter para o parametro medical de entregador
     * @param medical True se leva encomendas médicas
     */
   @Override
   public void setMedical(boolean medical) {
        this.levaMedical=medical;
   }

    /**
     * Setter para a Velocidade de um entregador
     * @param vel Velocidade a copiar
     */
   @Override
   public void setVelocidade(float vel){
       this.velocidadeDeEntrega=vel;
   }

    /**
     * Setter para a classificação de um entregador
     * @param c Classificação a copiar
     */
   @Override
   public void setClassificacao(float c) {this.classificacao=c;}

    /**
     * Getter para o raio de um entregador
     * @return Raio de um entregador
     */
   @Override
   public float getRaio() {
       return this.raio;
   }

    /**
     * Getter para o parametro medical de um entregador
     * @return True se leva encomendas médicas
     */
   @Override
   public boolean getMedical() {
       return this.levaMedical;
   }

    /**
     * Getter para a velocidade de um entregador
     * @return Velocidade de um entregador
     */
   @Override
   public float getVelocidade() {
       return this.velocidadeDeEntrega;
   }

    /**
     * Getter para a classificação de um entregador
     * @return Classificação de um entregador
     */
   @Override
   public float getClassificacao() { return this.classificacao;}


    /**
     * Getter para o numero de vezes classificado de um entregador
     * @return Numero de vezes classificado de um entregador
     */    @Override
    public int getVezesClassificado() {
        return vezesClassificado;
    }

    /**
     * Setter para o numero de vezes classificado de um entregador
     * @param vezesClassificado Novo número de vezes classificado
     */
    @Override
    public void setVezesClassificado(int vezesClassificado) {
        this.vezesClassificado = vezesClassificado;
    }

    /**
     * Método equals de um entregador
     * @param v Objeto ao qual comaparar
     * @return
     */
    @Override
    public boolean equals(Object v) {
	    if (v==null || !v.getClass().equals(this.getClass()))
	 	   return false;
	    InterfaceEntregador o=(InterfaceEntregador)v;
	    return this.getCodigo().equals(o.getCodigo());
    }

    /**
     * Método clone de um entregador
     * @return Entregador ao qual o método clone foi chamado clonado
     */
    @Override
    abstract public InterfaceEntregador clone();

    /**
     * Classificar um entregador
     * @param c Classificação a dar-lhe
     */
    @Override
    public void classifica(float c) { this.classificacao=((this.classificacao*(this.vezesClassificado++)+c)/(this.vezesClassificado)); }
}