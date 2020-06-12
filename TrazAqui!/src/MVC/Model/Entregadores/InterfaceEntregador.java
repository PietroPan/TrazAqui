package MVC.Model.Entregadores;

import java.time.LocalDateTime;
import java.util.List;
import Common.*;

public interface InterfaceEntregador extends InterfaceBasicInfo {
    void setAEntregar(boolean aEntregar);

    boolean isAEntregar();

    void setRaio(float raio);

    void setMedical(boolean medical);

    void setVelocidade(float vel);

    void setClassificacao(float c);

    float getRaio();

    boolean getMedical();

    float getVelocidade();

    float getClassificacao();

    int getVezesClassificado();

    void setVezesClassificado(int vezesClassificado);

    List<String> getMessages();

    void setMessages(List<String> messages);

    void addEncomenda(InterfaceEncomenda enc);

    String toString();

    boolean equals(Object v);

    InterfaceEntregador clone();

    InterfaceEncomenda getEncomenda(String id);

    void classifica(float c);

    List<InterfaceEncomenda> atualizaEstado(LocalDateTime t);
}
