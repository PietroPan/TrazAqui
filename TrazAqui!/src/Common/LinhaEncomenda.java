package Common;

import java.io.Serializable;

/**
 * Write a description of class Common.LinhaEncomenda here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class LinhaEncomenda implements InterfaceLinhaEncomenda, Serializable {
   private String codProduto;
   private String descricao;
   private double quantidade;
   private double preco;

    /**
     * Construtor vazio
     */
   public LinhaEncomenda() {
        this.codProduto = "n/a";
        this.descricao = "n/a";
        this.preco = 0;
        this.quantidade = 0;
    }

    /**
     * Construtor com parametros
     * @param referencia referencia do produto
     * @param descricao descrição de um produto
     * @param preco preço de um produto
     * @param quantidade quantidade de um produto
     */
   public LinhaEncomenda(String referencia, String descricao, double preco, double quantidade) {
        this.codProduto = referencia;
        this.descricao = descricao;
        this.preco = preco;
        this.quantidade = quantidade;
    }

    /**
     * Construtor cópia
     * @param linha LinhaEncomenda a copiar
     */
   public LinhaEncomenda(InterfaceLinhaEncomenda linha) {
        this.codProduto = linha.getcodProduto();
        this.descricao = linha.getDescricao();
        this.preco = linha.getPreco();
        this.quantidade = linha.getQuantidade();
    }

    /**
     * Getter para o código de um produto
     * @return string com o código de um produto
     */
   @Override
   public String getcodProduto() {
        return this.codProduto;
     }

    /**
     * Setter para o código de um produto
     * @param codProduto código a copiar
     */
    @Override
   public void setcodProduto(String codProduto) {
        this.codProduto = codProduto;
    }

    /**
     * Getter para a descrição de um produto
     * @return descrição em forma de String
     */
   @Override
   public String getDescricao() {
        return this.descricao;
    }

    /**
     * Setter para a descrição de um produto
     * @param descricao descrição a dar set
     */
   @Override
   public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    /**
     * Getter para o preç de um produto
     * @return preeço de um produto em forma de double
     */
   @Override
   public double getPreco() {
        return this.preco;
    }

    /**
     * Setter de preço de um produto
     * @param preco preço de um produto a dar set
     */
   @Override
   public void setPreco(double preco) {
        this.preco = preco;
    }

    /**
     * Getter para a quantidade de um produto
     * @return quantidade de um produto
     */
   @Override
   public double getQuantidade() {
        return this.quantidade;
    }

    /**
     * Setter para a quantidade
     * @param quantidade valor da quantidade a colocar no lugar
     */
   @Override
   public void setQuantidade(double quantidade) {
        this.quantidade = quantidade;
    }

    /**
     * REmover qunatidade
     * @param d quantidade a remover
     */
    @Override
    public void removeQuantidade(double d) {
       this.quantidade-=d;
    }

    /**
     * Método de clone
     * @return Nova linha de encomenda clonada
     */
   @Override
   public InterfaceLinhaEncomenda clone() {
        return new LinhaEncomenda(this);
    }

    /**
     * Método equals
     * @param obj objeto ao qual comparar
     * @return true se tiverem o mesmo preço,descrição e produto
     */
   @Override
   public boolean equals(Object obj) {
        if(obj==this) return true;
        if(obj==null || obj.getClass() != this.getClass()) return false;
        InterfaceLinhaEncomenda le = (InterfaceLinhaEncomenda) obj;
        return le.getcodProduto().equals(this.codProduto) &&
              le.getDescricao().equals(this.descricao) && 
              le.getPreco() == this.preco;
    }

    /**
     * Método toString
     * @return String com a informação de uma linha de encomenda
     */
   @Override
   public String toString() {
       return "Codigo Produto: " + this.codProduto +
               "\nDescriçao: " + this.descricao +
               "\nPreço: " + this.preco +
               "\nQuantidade: " + this.quantidade;
    }

}
