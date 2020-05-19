import java.time.LocalDateTime;
import java.util.Map;

public interface InterfaceLoja extends InterfaceBasicInfo {
    void setTamFila(int tF);

    void setTempoAtendimento(float t);

    void setPedidos(Map<String, InterfaceEncomenda> lE);

    int getTamFila();

    float getTempoAtendimento();

    Map<String, InterfaceEncomenda> getPedidos();

    Map<String, InterfaceEncomenda> getPedidosEspera();

    String toString();

    boolean equals(Object loja);

    InterfaceLoja clone();

    void addPronta(InterfaceEncomenda e);

    InterfaceEncomenda getEncomenda(String id);

    void addNotReady(InterfaceEncomenda e);

    void removeNotReady(String s);

    void removeReady(String cod);

    boolean isReady(String id);

    boolean isNotReady(String id);

    void atualizaLoja(LocalDateTime t);
}
