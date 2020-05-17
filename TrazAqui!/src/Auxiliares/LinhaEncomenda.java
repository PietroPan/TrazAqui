
/**
 * Write a description of class LinhaEncomenda here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class LinhaEncomenda implements InterfaceLinhaEncomenda {
   private String codProduto;
   private String descricao;
   private double quantidade;
   private double preco;
    
   public LinhaEncomenda() {
        this.codProduto = "n/a";
        this.descricao = "n/a";
        this.preco = 0;
        this.quantidade = 0;
    }
    
   public LinhaEncomenda(String referencia, String descricao, double preco, double quantidade) {
        this.codProduto = referencia;
        this.descricao = descricao;
        this.preco = preco;
        this.quantidade = quantidade;
    }
    
   public LinhaEncomenda(InterfaceLinhaEncomenda linha) {
        this.codProduto = linha.getcodProduto();
        this.descricao = linha.getDescricao();
        this.preco = linha.getPreco();
        this.quantidade = linha.getQuantidade();
    } 
    
   @Override
   public String getcodProduto() {
        return this.codProduto;
     }
    
   @Override
   public void setcodProduto(String codProduto) {
        this.codProduto = codProduto;
    }

   @Override
   public String getDescricao() {
        return this.descricao;
    }

   @Override
   public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

   @Override
   public double getPreco() {
        return this.preco;
    }

   @Override
   public void setPreco(double preco) {
        this.preco = preco;
    }

   @Override
   public double getQuantidade() {
        return this.quantidade;
    }

   @Override
   public void setQuantidade(double quantidade) {
        this.quantidade = quantidade;
    }
    
   @Override
   public InterfaceLinhaEncomenda clone() {
        return new LinhaEncomenda(this);
    }
    
   @Override
   public boolean equals(Object obj) {
        if(obj==this) return true;
        if(obj==null || obj.getClass() != this.getClass()) return false;
        InterfaceLinhaEncomenda le = (InterfaceLinhaEncomenda) obj;
        return le.getcodProduto().equals(this.codProduto) &&
              le.getDescricao().equals(this.descricao) && 
              le.getPreco() == this.preco;
    }
    
   @Override
   public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nCodigo Produto: ").append(this.codProduto);
        sb.append("\nDescriçao: ").append(this.descricao);
        sb.append("\nPreço: ").append(this.preco);
        sb.append("\nQuantidade: ").append(this.quantidade);
        return sb.toString();
    }            
}
