import java.util.Map;

public interface InterfaceLoja extends InterfaceBasicInfo {
    void setTamFila(int tF);

    void setTempoAtendimento(float t);

    void setPedidos(Map<String, InterfaceEncomenda> lE);

    int getTamFila();

    float getTempoAtendimento();

    Map<String, InterfaceEncomenda> getPedidos();

    String toString();

    boolean equals(Object loja);

    InterfaceLoja clone();

    void addPronta(InterfaceEncomenda e);

    InterfaceEncomenda getEncomenda(String id);

    void removeReady(String cod);
}
