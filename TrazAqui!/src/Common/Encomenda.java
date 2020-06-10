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
    
    @Override
    public void setCodEncomenda(String enc) {
        this.codEncomenda=enc;
    }
    
    @Override
    public void setMedical(boolean medical) {
        this.medical=medical;
    }
    
    @Override
    public void setPeso(float peso) {
        this.peso=peso;
    }
    
    @Override
    public void setOrigem(String loja) {
        this.lojaOrigem=loja;
    }
    
    @Override
    public void setDestino(String user) {
        this.userDestino=user;
    }
    
    @Override
    public void setPedido(List<InterfaceLinhaEncomenda> lE) {
        this.pedido=lE.stream().map(l -> l.clone()).collect(Collectors.toList());
    }
    
    @Override
    public String getCodEncomenda() {
        return this.codEncomenda;
    }
    
    @Override
    public boolean getMedical() {
        return this.medical;
    }
    
    @Override
    public float getPeso() {
        return this.peso;
    }
    
    @Override
    public String getOrigem() {
        return this.lojaOrigem;
    }
    
    @Override
    public String getDestino() {
        return this.userDestino;
    }
    
    @Override
    public List<InterfaceLinhaEncomenda> getPedido() {
        return this.pedido.stream().map(InterfaceLinhaEncomenda::clone).collect(Collectors.toList());
    }

    @Override
    public LocalDateTime getDataEntrega() {
        return dataEntrega;
    }

    @Override
    public void setDataEntrega(LocalDateTime t) {
        this.dataEntrega = t;
    }

    @Override
    public LocalDateTime getDataInicio() {
        return dataInicio;
    }

    @Override
    public void setDataInicio(LocalDateTime dataInicio) {
        this.dataInicio = dataInicio;
    }

    @Override
    public boolean equals(Object enc) {
        InterfaceEncomenda e;
        if (enc==null || enc.getClass()!=enc.getClass()) 
            return false;
        e=(InterfaceEncomenda)enc;
        return e.getCodEncomenda().equals(this.codEncomenda);
    }
    
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("\nCodigo de Encomenda: ").append(this.codEncomenda)
        .append("\nInterfaceLoja de Origem: ").append(this.lojaOrigem)
        .append("\nInterfaceUtilizador de Destino: ").append(this.userDestino)
        .append("\nEncomenda Médica: ").append(this.medical)
        .append("\nPeso(Kgs): ").append(this.peso)
        .append("\nPedido: ").append(this.pedido.toString());
        return s.toString();
    }

    
    @Override
    public InterfaceEncomenda clone() {
        return new Encomenda(this);
    }

    @Override
    public double calculaValorTotal() {
        return this.pedido.stream().map(InterfaceLinhaEncomenda::getPreco).reduce(0.0, Double::sum);
    }

    @Override
    public double calculaTempoDemorado(){
        Duration duration = Duration.between(this.getDataEntrega(),this.getDataInicio());
        return (double)duration.getSeconds();
    }

    @Override
    public void removeProduto(String cod){
        List<InterfaceLinhaEncomenda> rm=new ArrayList<>();
        for (InterfaceLinhaEncomenda i : this.pedido){
            if (i.getcodProduto().equals(cod)) rm.add(i);
        }
        this.pedido.removeAll(rm);
    }
}
