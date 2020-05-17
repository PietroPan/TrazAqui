import java.util.Map;

public interface InterfaceLojas {
    Map<String, ĨnterfaceLoja> getLojas();

    void setLojas(Map<String, ĨnterfaceLoja> lojas);

    ĨnterfaceLoja getLoja(String e) throws LojaInexistenteException;

    void setLoja(String s, ĨnterfaceLoja l);

    @Override
    String toString();

    void addEncomenda(String s, InterfaceEncomenda e);

    void removeReady(String s, String e);

    void addPronta(InterfaceEncomenda e);
}
