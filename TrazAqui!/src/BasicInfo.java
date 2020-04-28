import java.awt.geom.Point2D;

public abstract class BasicInfo {
    private String codigo;
    private String nome;
    private Point2D posicao;
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Point2D getPosicao() {
        return (Point2D)posicao.clone();
    }

    public void setPosicao(Point2D posicao) {
        this.posicao = (Point2D)posicao.clone();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
}
