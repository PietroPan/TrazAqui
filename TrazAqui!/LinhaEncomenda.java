
/**
 * Write a description of class LinhaEncomenda here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class LinhaEncomenda
{
   private String codProduto;
   private String descricao;
   private int quantidade;
   private double preco;
    
   public LinhaEncomenda() {
        this.codProduto = "n/a";
        this.descricao = "n/a";
        this.preco = 0;
        this.quantidade = 0;
    }
    
   public LinhaEncomenda(String referencia, String descricao, double preco,int quantidade) {
        this.codProduto = referencia;
        this.descricao = descricao;
        this.preco = preco;
        this.quantidade = quantidade;
    }
    
   public LinhaEncomenda(LinhaEncomenda linha) {
        this.codProduto = linha.getcodProduto();
        this.descricao = linha.getDescricao();
        this.preco = linha.getPreco();
        this.quantidade = linha.getQuantidade();
    } 
    
   public String getcodProduto() {
        return this.codProduto;
     }
    
   public void setcodProduto(String codProduto) {
        this.codProduto = codProduto;
    }

   public String getDescricao() {
        return this.descricao;
    }

   public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

   public double getPreco() {
        return this.preco;
    }

   public void setPreco(double preco) {
        this.preco = preco;
    }

   public int getQuantidade() {
        return this.quantidade;
    }

   public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
    
   public LinhaEncomenda clone() {
        return new LinhaEncomenda(this);
    }
    
   public boolean equals(Object obj) {
        if(obj==this) return true;
        if(obj==null || obj.getClass() != this.getClass()) return false;
        LinhaEncomenda le = (LinhaEncomenda) obj;
        return le.getcodProduto().equals(this.codProduto) &&
              le.getDescricao().equals(this.descricao) && 
              le.getPreco() == this.preco;
    }
    
   public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nCodigo Kill: ").append(this.codProduto);
        sb.append("\nDescriçao: ").append(this.descricao);
        sb.append("\nPreço: ").append(Double.toString(this.preco));
        sb.append("\nQuantidade: ").append(Integer.toString(this.quantidade));
        return sb.toString();
    }            
}
