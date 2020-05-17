import java.time.LocalDateTime;
import java.util.List;

public interface InterfaceEncomenda {
    void setCodEncomenda(String enc);

    void setMedical(boolean medical);

    void setPeso(float peso);

    void setOrigem(String loja);

    void setDestino(String user);

    void setPedido(List<InterfaceLinhaEncomenda> lE);

    String getCodEncomenda();

    boolean getMedical();

    float getPeso();

    String getOrigem();

    String getDestino();

    List<InterfaceLinhaEncomenda> getPedido();

    LocalDateTime getDataEntrega();

    void setDataEntrega(LocalDateTime t);

    boolean equals(Object enc);

    String toString();

    InterfaceEncomenda clone();

    double calculaValorTotal();
}
