package MVC.Model.Entregadores;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import Common.*;

public interface InterfaceVoluntario extends InterfaceBasicInfo, InterfaceEntregador {
    InterfaceEncomenda getEncomenda();

    List<String> getPedidos();

    String toString();

    InterfaceEntregador clone();

    void addEncomenda(InterfaceEncomenda e);

    void addPedido(String enc);

    void atualizaAtual(InterfaceEncomenda enc);

    Map.Entry<String,String> checkEvent(LocalDateTime t);

    String timeLeft(String enc,LocalDateTime l);
}