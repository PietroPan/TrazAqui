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

    void setHistorico(List<InterfaceEncomenda> lE);

    float getRaio();

    boolean getMedical();

    float getVelocidade();

    float getClassificacao();

    List<InterfaceEncomenda> getHistorico();

    int getVezesClassificado();

    void setVezesClassificado(int vezesClassificado);

    List<String> getMessages();

    void setMessages(List<String> messages);

    boolean hasRoomAndMed(boolean med);

    void addEncomenda(InterfaceEncomenda enc);

    String toString();

    boolean equals(InterfaceEntregador v);

    InterfaceEntregador clone();

    boolean encomendaACaminho(String id, String s);

    InterfaceEncomenda getEncomenda(String id);

    InterfaceEncomenda getEncomendaPassado(String id);

    void classifica(float c);

    List<InterfaceEncomenda> atualizaEstado(LocalDateTime t);

    public void addMessage(String message);

    void addToHistorico(InterfaceEncomenda enc);
}
