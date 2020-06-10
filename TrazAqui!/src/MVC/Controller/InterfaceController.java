package MVC.Controller;

import java.time.LocalDateTime;
import Exceptions.*;

public interface InterfaceController {
    LocalDateTime StringToLocalDateTime(String s);

    String signIn();

    boolean login(String cod, String password);

    String initUser();

    String initEntregador(int i);

    String initLoja();

    String init();

    int escolheMenu();

    int menuUser() throws UtilizadorInexistenteException, LojaInexistenteException, EntregadorInexistenteException;

    int menuVoluntario() throws EntregadorInexistenteException, UtilizadorInexistenteException, LojaInexistenteException;

    int menuTransportadora() throws EntregadorInexistenteException, UtilizadorInexistenteException, LojaInexistenteException;

    int menuLoja() throws LojaInexistenteException;

    void menu();

    int menuSystem() throws UtilizadorInexistenteException, LojaInexistenteException, EntregadorInexistenteException;
}