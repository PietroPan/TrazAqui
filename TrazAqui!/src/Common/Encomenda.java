package Common;
/**
 * Escreva a descrição da classe Common.Encomenda aqui.
 * 
 * @author (seu nome) 
 * @version (número de versão ou data)
 */

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Encomenda implements InterfaceEncomenda, Serializable {
    private String codEncomenda;
    private boolean medical;
    private float peso;
    private String lojaOrigem;
    private String userDestino;
    private List<InterfaceLinhaEncomenda> pedido;
    private LocalDateTime dataEntrega;
    private LocalDateTime dataInicio;

    /**
     * Construtor vazio
     */
    public Encomenda() {
        this.codEncomenda="Common.Encomenda Standard";
        this.medical=false;
        this.peso=0;
        this.lojaOrigem="InterfaceLoja Standard";
        this.userDestino="User Standard";
        this.pedido=new ArrayList<>();
        this.dataEntrega= LocalDateTime.now();
        this.dataInicio= LocalDateTime.now();
    }

    /**
     * Construtor com alguns parametros
     * @param enc codigo de encomenda
     * @param medical boolean para saber se é médica ou não
     * @param peso peso da encomenda
     * @param loja loja de origem
     * @param user utilizador de destino
     * @param pedido lista de linhas de encomenda pedidas
     * @param t1 data de inicio
     * @param t2 data de entrega
     */
    public Encomenda(String enc, boolean medical, float peso, String loja, String user, List<InterfaceLinhaEncomenda> pedido, LocalDateTime t1,LocalDateTime t2) {
        this.codEncomenda=enc;
        this.medical=medical;
        this.peso=peso;
        this.lojaOrigem=loja;
        this.userDestino=user;
        this.pedido=pedido.stream().map(l -> l.clone()).collect(Collectors.toList());
        this.dataEntrega=t1;
        this.dataInicio=t2;
    }

    /**
     * Construtor cópia
     * @param e Encomenda a copiar
     */
    public Encomenda(InterfaceEncomenda e) {
        this.codEncomenda=e.getCodEncomenda();
        this.medical=e.getMedical();
        this.peso=e.getPeso();
        this.lojaOrigem=e.getOrigem();
        this.userDestino=e.getDestino();
        this.pedido=e.getPedido();
        this.dataEntrega=e.getDataEntrega();
        this.dataInicio=e.getDataInicio();
    }

    /**
     * Setter de código de encomenda
     * @param enc código a dar set
     */
    @Override
    public void setCodEncomenda(String enc) {
        this.codEncomenda=enc;
    }

    /**
     * Setter do parametro medical
     * @param medical boolean a dar set
     */
    @Override
    public void setMedical(boolean medical) {
        this.medical=medical;
    }

    /**
      Setter de peso
     * @param peso peso a dar set
     */
    @Override
    public void setPeso(float peso) {
        this.peso=peso;
    }

    /**
     * Setter de origem
     * @param loja loja de origem
     */
    @Override
    public void setOrigem(String loja) {
        this.lojaOrigem=loja;
    }

    /**
     * Setter de destino
     * @param user utilizador destino
     */
    @Override
    public void setDestino(String user) {
        this.userDestino=user;
    }

    /**
     * Setter do pedido
     * @param lE pedido a dar set
     */
    @Override
    public void setPedido(List<InterfaceLinhaEncomenda> lE) {
        this.pedido=lE.stream().map(l -> l.clone()).collect(Collectors.toList());
    }

    /**
     * Getter do codigo de encomenda
     * @return
     */
    @Override
    public String getCodEncomenda() {
        return this.codEncomenda;
    }

    /**
     * Getter do parametro medical
     * @return boolean medical
     */
    @Override
    public boolean getMedical() {
        return this.medical;
    }

    /**
     * Getter do parametro peso
     * @return float peso da encomenda
     */
    @Override
    public float getPeso() {
        return this.peso;
    }

    /**
     * Getter da loja de origem de uma encomenda
     * @return loja de origem de uma encomenda
     */
    @Override
    public String getOrigem() {
        return this.lojaOrigem;
    }

    /**
     * Getter do destino de uma encomenda
     * @return Destino de uma encomenda
     */
    @Override
    public String getDestino() {
        return this.userDestino;
    }

    /**
     * Getter do pedido
     * @return Lista de LinhasEncomenda copia
     */
    @Override
    public List<InterfaceLinhaEncomenda> getPedido() {
        return this.pedido.stream().map(InterfaceLinhaEncomenda::clone).collect(Collectors.toList());
    }

    /**
     * Getter para o parametro Data de Entrega
     * @return Data de entrega
     */
    @Override
    public LocalDateTime getDataEntrega() {
        return dataEntrega;
    }

    /**
     * Setter para o parametro Data de Entrega
     * @param t Data de Entrega a copiar
     */
    @Override
    public void setDataEntrega(LocalDateTime t) {
        this.dataEntrega = t;
    }

    /**
     * Getter para a data de realização de uma encomenda
     * @return Data de início de uma encomenda
     */
    @Override
    public LocalDateTime getDataInicio() {
        return dataInicio;
    }

    /**
     * Setter da data de início de uma encomenda
     * @param dataInicio Data de início a colocar no lugar
     */
    @Override
    public void setDataInicio(LocalDateTime dataInicio) {
        this.dataInicio = dataInicio;
    }

    /**
     * Método equals
     * @param enc Encomenda a qual comparar
     * @return true se têm o mesmo código
     */
    @Override
    public boolean equals(Object enc) {
        InterfaceEncomenda e;
        if (enc==null || enc.getClass()!=enc.getClass()) 
            return false;
        e=(InterfaceEncomenda)enc;
        return e.getCodEncomenda().equals(this.codEncomenda);
    }

    /**
     * Método toString
     * @return String com a informação relevante de encomenda
     */
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("\nCodigo de Encomenda: ").append(this.codEncomenda)
        .append("\nLoja de Origem: ").append(this.lojaOrigem)
        .append("\nUtilizador de Destino: ").append(this.userDestino)
        .append("\nEncomenda Médica: ").append(this.medical)
        .append("\nPeso(Kgs): ").append(this.peso)
        .append("\nPedido: ").append(this.pedido.toString());
        return s.toString();
    }

    @Override
    public String toString2() {
        StringBuilder s = new StringBuilder();
        s.append("\nCodigo de Encomenda: ").append(this.codEncomenda)
                .append("\nLoja de Origem: ").append(this.lojaOrigem)
                .append("\nUtilizador de Destino: ").append(this.userDestino)
                .append("\nDemorou "+ ChronoUnit.MINUTES.between(this.dataInicio,this.dataEntrega)+ " minutos");
        return s.toString();
    }

    /**
     * Método clone
     * @return Cópia da encomenda a qual clone foi chamado
     */
    @Override
    public InterfaceEncomenda clone() {
        return new Encomenda(this);
    }

    /**
     * Calcular preço total de uma encomenda
     * @return preço total de uma encomenda
     */
    @Override
    public double calculaValorTotal() {
        return this.pedido.stream().map(InterfaceLinhaEncomenda::getPreco).reduce(0.0, Double::sum);
    }

    /**
     * Calcular tempo estimado de Entrega
     * @return Tempo de entrega estimado
     */
    @Override
    public double calculaTempoDemorado(){
        Duration duration = Duration.between(this.getDataEntrega(),this.getDataInicio());
        return (double)duration.getSeconds();
    }

    /**
     * Remover produto de Pedido
     * @param cod Código de produto a remover da lista
     */
    @Override
    public void removeProduto(String cod){
        List<InterfaceLinhaEncomenda> rm=new ArrayList<>();
        for (InterfaceLinhaEncomenda i : this.pedido){
            if (i.getcodProduto().equals(cod)) rm.add(i);
        }
        this.pedido.removeAll(rm);
    }
}
