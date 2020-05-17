import java.awt.geom.Point2D;

public abstract class BasicInfo implements InterfaceBasicInfo {
    private String codigo;
    private String nome;
    private Point2D posicao;
    private String password;

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public Point2D getPosicao() {
        return (Point2D)posicao.clone();
    }

    @Override
    public void setPosicao(Point2D posicao) {
        this.posicao = (Point2D)posicao.clone();
    }

    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String getCodigo() {
        return codigo;
    }

    @Override
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
}
