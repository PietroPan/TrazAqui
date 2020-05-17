import java.util.List;

public interface InterfaceEntregador extends InterfaceBasicInfo {
    void setRaio(float raio);

    void setMedical(boolean medical);

    void setVelocidade(float vel);

    void setClassificacao(float c);

    void setHistorico(List<InterfaceEncomenda> lE);

    float getRaio();

    boolean getMedical();

    float getVelocidade();

    float getClassificacao();

    List<InterfaceEncomenda> getHistorico();

    int getVezesClassificado();

    void setVezesClassificado(int vezesClassificado);

    boolean hasRoomAndMed(boolean med);

    void addEncomenda(InterfaceEncomenda enc);

    String toString();

    boolean equals(InterfaceEntregador v);

    InterfaceEntregador clone();

    void classifica(float c);
}
