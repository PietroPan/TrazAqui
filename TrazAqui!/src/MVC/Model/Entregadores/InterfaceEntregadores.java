package MVC.Model.Entregadores;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import Common.*;
import Exceptions.*;

public interface InterfaceEntregadores {
    Map<String, InterfaceEntregador> getEntregadores();

    void setEntregadores(Map<String, InterfaceEntregador> entregadores);

    InterfaceEntregador getEntregador(String e) throws EntregadorInexistenteException;

    void setEntregador(String s, InterfaceEntregador e);

    @Override
    String toString();

    void addPedidoVoluntario(String idV, String enc);

    void addEncomenda(String s, InterfaceEncomenda e);

    boolean encomendaACaminho(String s, String id);

    InterfaceEncomenda getEncomendaPassado(String id);

    void classifica(InterfaceEncomenda e, float c);

    void denyAll(String cod);

    List<InterfaceEncomenda> atualizaEstado(LocalDateTime t);

    void addPedido(InterfaceEncomenda enc,String trans);

    void alteraPedido(InterfaceEncomenda enc,String trans,String stat);

    void addToHistorico(String ent,InterfaceEncomenda enc);

    void clearAtual(String trans);
}
