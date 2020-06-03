package Common;

public interface InterfaceLinhaEncomenda {
    String getcodProduto();

    void setcodProduto(String codProduto);

    String getDescricao();

    void setDescricao(String descricao);

    double getPreco();

    void setPreco(double preco);

    double getQuantidade();

    void setQuantidade(double quantidade);

    InterfaceLinhaEncomenda clone();

    boolean equals(Object obj);

    String toString();

    void removeQuantidade(double quantidade);
}
