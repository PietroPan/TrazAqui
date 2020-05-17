import java.util.Map;

public interface InterfaceLojas {
    Map<String, InterfaceLoja> getLojas();

    void setLojas(Map<String, InterfaceLoja> lojas);

    InterfaceLoja getLoja(String e) throws LojaInexistenteException;

    void setLoja(String s, InterfaceLoja l);

    @Override
    String toString();

    void addEncomenda(String s, InterfaceEncomenda e);

    void removeReady(String s, String e);

    void addPronta(InterfaceEncomenda e);
}
