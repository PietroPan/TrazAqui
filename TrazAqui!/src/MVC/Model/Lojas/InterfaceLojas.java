package MVC.Model.Lojas;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import Common.*;
import Exceptions.*;

public interface InterfaceLojas {
    Map<String, InterfaceLoja> getLojas();

    void setLojas(Map<String, InterfaceLoja> lojas);

    InterfaceLoja getLoja(String e) throws LojaInexistenteException;

    void setLoja(String s, InterfaceLoja l);

    @Override
    String toString();

    void addEncomenda(String s, InterfaceEncomenda e);

    void removeNotReady(InterfaceEncomenda e);

    void removeReady(String s, String e);

    void addPronta(InterfaceEncomenda e);

    void addToStock(String idLoja, List<InterfaceLinhaEncomenda> l);

    boolean encomendaNotReady(String id, String loja);

    List<InterfaceLinhaEncomenda> formaListadeLinhasEncomenda(String loja, List<Map.Entry<String, Double>> l) throws ProductNotAvailableException;

    Map<String, List<String>> atualizaEstado(LocalDateTime t);

    List<InterfaceLinhaEncomenda> getStock(String l);

    void mudarPreco(String loja, String cod, double preco);

    void mudarQuantidade(String loja, String cod, double quant);

    void addSToStock(String loja, InterfaceLinhaEncomenda l);

    void removeFromStock(String loja, String cod);
}
