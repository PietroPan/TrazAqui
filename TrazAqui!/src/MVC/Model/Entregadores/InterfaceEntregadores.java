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

    void addEncomenda(String s, InterfaceEncomenda e);

    Map<String,List<InterfaceEncomenda>> atualizaEstado(LocalDateTime t);

    void addPedido(InterfaceEncomenda enc,String trans);

    void alteraPedido(InterfaceEncomenda enc,String trans,String stat);

    void addMessage(String cod, String message);

    void atualizaAtual(String trans,InterfaceEncomenda enc);

    void resetMessages(String cod);

    void setAEntregar(String cod,boolean b);

    boolean isAEntregar(String cod);

    boolean hasRoom(String trans);

    void alteraTodosPedidosIf(String trans,String stat,String statIf);

    List<String> getAllFree();

    boolean existePedido(String trans,String enc);

    void classificaUser(String cod,float clas);

    Map<String,List<String>> checkEvent (LocalDateTime t);

    String timeLeft(String ent,String enc,LocalDateTime l);

    void rejeitaPedidos(String enc);
}
