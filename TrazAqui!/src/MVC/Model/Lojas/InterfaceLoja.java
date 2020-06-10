package MVC.Model.Lojas;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import Common.*;
import Exceptions.*;

public interface InterfaceLoja extends InterfaceBasicInfo {
    void setTamFila(int tF);

    void setTempoAtendimento(float t);

    void setPedidos(Map<String, InterfaceEncomenda> lE);

    void setPedidosEspera(HashMap<String, InterfaceEncomenda> m);

    void setStock(HashMap<String, InterfaceLinhaEncomenda> m);

    int getTamFila();

    float getTempoAtendimento();

    Map<String, InterfaceEncomenda> getPedidos();

    Map<String, InterfaceEncomenda> getPedidosEspera();

    Map<String, InterfaceLinhaEncomenda> getStock();

    void addToStock(List<InterfaceLinhaEncomenda> l);

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

    List<InterfaceLinhaEncomenda> formaListaLinhasEncomenda(List<Map.Entry<String, Double>> l) throws ProductNotAvailableException;

    Map<String, List<String>> atualizaLoja(LocalDateTime t);

    void mudarPreco(String cod, double preco);

    void mudarQuantidade(String cod, double preco);

    String gerarCodProd();

    void addSToStock(InterfaceLinhaEncomenda l);

    void removeFromStock(String cod);
}
