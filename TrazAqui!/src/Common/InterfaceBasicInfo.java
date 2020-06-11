package Common;

import java.awt.geom.Point2D;

public interface InterfaceBasicInfo {
    String getPassword();

    void setPassword(String password);

    Point2D getPosicao();

    void setPosicao(Point2D posicao);

    String getNome();

    void setNome(String nome);

    String getCodigo();

    void setCodigo(String codigo);

    void addMessage(String message);
}
