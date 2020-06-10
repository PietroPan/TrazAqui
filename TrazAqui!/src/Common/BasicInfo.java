package Common;

import MVC.Model.Entregadores.Entregador;

import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class BasicInfo implements InterfaceBasicInfo, Serializable {
    private String codigo;
    private String nome;
    private Point2D posicao;
    private String password;
    private List<String> messages;

    /**
     * Construtor cópia
     * @param e BasicInfo ac copiar
     */
    public BasicInfo(BasicInfo e) {
        this.codigo=e.getCodigo();
        this.nome=e.getNome();
        this.posicao=e.getPosicao();
        this.password=e.getPassword();
        this.messages=e.getMessages();
    }

    /**
     * Cosntrutor vazio
     */
    public BasicInfo() {
        this.codigo="";
        this.nome="";
        this.posicao=new Point2D.Double(0,0);
        this.password="Password";
        this.messages=new ArrayList<>();
    }

    /**
     * Construtor parametrizado
     * @param nome nome
     * @param cod codigo
     * @param pos posição
     * @param password password
     */
    public BasicInfo(String nome, String cod, Point2D pos, String password) {
        this.messages=new ArrayList<>();
        this.password=password;
        this.posicao=(Point2D.Double) pos.clone();
        this.nome=nome;
        this.codigo=cod;
    }

    /**
     * Getter de mensagens
     * @return lista de mensagens
     */
    public List<String> getMessages() {
        return new ArrayList<>(messages);
    }

    /**
     * Setter de mensagens
     * @param messages lista a partir da qual vai copiar
     */
    public void setMessages(List<String> messages) {
        this.messages = new ArrayList<>(messages);
    }

    /**
     * Adicionar uma mensagens a lista
     * @param msg mensagem a adicionar
     */
    public void addMessage(String msg){
        this.messages.add(msg);
    }

    /**
     * Getter de password
     * @return password
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * Setter de password
     * @param password password a colocar no lugar
     */
    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Getter da posição
     * @return posição copiada
     */
    @Override
    public Point2D getPosicao() {
        return (Point2D)posicao.clone();
    }

    /**
     * Setter de posição
     * @param posicao posição a colocar no lugar
     */
    @Override
    public void setPosicao(Point2D posicao) {
        this.posicao = (Point2D)posicao.clone();
    }

    /**
     * Getter de nome
     * @return nome
     */
    @Override
    public String getNome() {
        return nome;
    }

    /**
     * Setter de nome
     * @param nome nome a colocar no lugar
     */
    @Override
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Getter de código
     * @return codigo
     */
    @Override
    public String getCodigo() {
        return codigo;
    }

    /**
     * Setter de código
     * @param codigo codigo a colocar no lugar
     */
    @Override
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
}
