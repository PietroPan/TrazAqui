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

    int menuUser() throws UtilizadorInexistenteException, LojaInexistenteException;

    int menuVoluntario() throws EntregadorInexistenteException, UtilizadorInexistenteException, LojaInexistenteException;

    int menuTransportadora();

    int menuLoja();

    void menu();
}
