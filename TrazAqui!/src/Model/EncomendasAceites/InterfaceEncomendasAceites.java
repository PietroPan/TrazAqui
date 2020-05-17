import java.util.Set;

public interface InterfaceEncomendasAceites {
    void setCodEncomendas(Set<String> codEncomendas);

    Set<String> getCodEncomendas();

    boolean equals(Object obj);

    String toString();

    InterfaceEncomendasAceites clone();

    boolean existe(String id);

    void add(String e);
}
