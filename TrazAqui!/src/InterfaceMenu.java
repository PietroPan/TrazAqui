import java.time.LocalDateTime;

public interface InterfaceMenu {
    LocalDateTime StringToLocalDateTime(String s);

    boolean login(String cod, String password);

    String initUser();

    String initEntregador(int i);

    String initLoja();

    String init();

    int menuUser() throws UtilizadorInexistenteException, LojaInexistenteException;

    int menuVoluntario() throws EntregadorInexistenteException, UtilizadorInexistenteException, LojaInexistenteException;

    int menuTransportadora();

    int menuLoja();

    void menu();
}
