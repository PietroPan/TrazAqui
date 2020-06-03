package MVC.Model.Entregadores;

import java.util.List;
import Common.*;

public interface InterfaceTransportadora extends InterfaceBasicInfo, InterfaceEntregador {
    void setNIF(String NIF);

    void setCustoKm(double custoKm);

    void setCustoKg(double custoKg);

    void setNumeroEnc(int n);

    void setEncomendas(List<InterfaceEncomenda> lE);

    void addEncomenda(InterfaceEncomenda e);

    String getNIF();

    double getCustoKg();

    double getCustoKm();

    int getNumEnc();

    List<InterfaceEncomenda> getEncomendaAtual();

    String toString();

    InterfaceEntregador clone();

    boolean hasRoomAndMed(boolean med);
}
