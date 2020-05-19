
/**
 * Write a description of class Programa here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

import java.awt.geom.Point2D;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class Menu implements InterfaceMenu {
    private InterfaceData info;
    private String codUser;
    private Printer p;

    public Menu() {
        this.info=new Data();
        this.codUser="";
        this.p=new Printer ();
    }

    @Override
    public LocalDateTime StringToLocalDateTime(String s) {
        String[] a=s.split(":",0);
        if (a.length!=5)
            p.invalid("Formato");
        List<Integer> i = Arrays.stream(a).map(Integer::parseInt).collect(Collectors.toList());
        try {
            return LocalDateTime.of(i.get(0), i.get(1), i.get(2), i.get(3), i.get(4));
        }
        catch (DateTimeException d) {
            p.invalid("Formato de InterfaceData");
            return null;
        }
    }

    @Override
    public String signIn() {
        Scanner read = new Scanner(System.in);
        String cod="";
        int i=0;
        while (i<1 || i>4) {
            p.showLoginOptions();
            try {
                i = Integer.parseInt(read.nextLine());
            }
            catch (NumberFormatException f) {
                p.invalid("Input");
                i=5;
            }
            switch (i) {
                case (1):
                    cod = initUser();
                    break;
                case (2):
                    cod = initEntregador(1);
                    break;
                case (3):
                    cod = initEntregador(0);
                    break;
                case (4):
                    cod = initLoja();
                    break;
                default:
                    p.invalid("Opção");
                    break;
            }
        }
        return cod;
    }

    @Override
    public boolean login(String cod, String password) {
        boolean r;
        if (cod.length()==0) return false;
        char beg =cod.charAt(0);
        switch (beg) {
            case ('u') :
                try {
                InterfaceUtilizador u = this.info.getUser(cod);
                r= u!=null && password.equals(u.getPassword());
                }
                catch (UtilizadorInexistenteException d) {
                    p.naoRegistado("InterfaceUtilizador");
                    return false;
                }
                break;
            case ('l'):
                try {
                InterfaceLoja l = this.info.getLoja(cod);
                r= l!=null && password.equals(l.getPassword());
                }
                catch (LojaInexistenteException d) {
                    p.naoRegistado("InterfaceLoja");
                    return false;
                }
                break;
            case ('v'):
            case ('t'):
                try {
                    InterfaceEntregador e = this.info.getEntregador(cod);
                    r= e!=null && password.equals(e.getPassword());
                }
                catch (EntregadorInexistenteException d) {
                    p.naoRegistado("InterfaceEntregador");
                    r=false;
                }
                break;
            default:
                p.invalid("Código de acesso");
                r=false;
                break;
        }
        return r;
    }

    @Override
    public String initUser() {
        Scanner read= new Scanner(System.in);
        InterfaceUtilizador user;
        p.askUserCod();
        String userCod = read.nextLine();
        p.askUserName();
        String name = read.nextLine();
        p.askPassword();
        String password =read.nextLine();
        p.askBalance();
        double balance = Double.parseDouble(read.nextLine());
        p.askLocalizacao("x");
        float x = Float.parseFloat(read.nextLine());
        p.askLocalizacao("y");
        float y = Float.parseFloat(read.nextLine());
        user = new Utilizador(userCod,password,name,balance,new Point2D.Double(x,y),new HashSet<>(),new ArrayList<>());
        info.addUser(user);
        return userCod;
    }

    @Override
    public String initEntregador(int i) {
        Scanner read= new Scanner(System.in);
        p.askCod();
        String cod=read.nextLine();
        p.askUserName();
        String name = read.nextLine();
        p.askPassword();
        String password =read.nextLine();
        p.askLocalizacao("x");
        float x = Float.parseFloat(read.nextLine());
        p.askLocalizacao("y");
        float y = Float.parseFloat(read.nextLine());
        p.askRaio();
        float r = Float.parseFloat(read.nextLine());
        p.askMedical();
        String d =read.nextLine();
        boolean medical = d.toUpperCase().equals("S");
        p.askVelocidadeNormal();
        float v = Float.parseFloat(read.nextLine());
        if (i==1) {
            this.info.addEntregador(new Voluntario(name,cod,new Point2D.Double(x,y),password,r,medical,v,0,0,new ArrayList<>(),new Encomenda(),new ArrayList<>()));
        }
        else {
            p.askNIF();
            String nif = read.nextLine();
            p.askCusto("Kg");
            float kg =Float.parseFloat(read.nextLine());
            p.askCusto("Km");
            float km = Float.parseFloat(read.nextLine());
            p.askNEncomendas();
            int n = Integer.parseInt(read.nextLine());
            this.info.addEntregador(new Transportadora(name,cod,new Point2D.Double(x,y),password,r,nif,km,kg,medical,v,0,0,n,new ArrayList<>(),new ArrayList<>()));
        }
        return cod;
    }

    @Override
    public String initLoja() {
        Scanner read= new Scanner(System.in);
        p.askCod();
        String cod=read.nextLine();
        p.askUserName();
        String name = read.nextLine();
        p.askPassword();
        String password =read.nextLine();
        p.askLocalizacao("x");
        float x = Float.parseFloat(read.nextLine());
        p.askLocalizacao("y");
        float y = Float.parseFloat(read.nextLine());
        p.askTamFila();
        int tF = Integer.parseInt(read.nextLine());
        p.askTempoAtendimento();
        float t = Float.parseFloat(read.nextLine());
        this.info.addLoja(new Loja(cod,name,new Point2D.Double(x,y),password,tF,t,new HashMap<>(),new HashMap<>()));
        return cod;
    }

    @Override
    public String init() {
        Scanner read = new Scanner(System.in);
        int i=0;
        p.askCod();
        String cod=read.nextLine();
        p.askPassword();
        String password = read.nextLine();
        while (!login(cod,password)) {
            p.askNew();
            String option = read.nextLine().toUpperCase();
            if (option.equals("S")) {
                codUser=signIn();
                p.showObrigado();
                break;
            }
            else {
                p.askCod();
                cod=read.nextLine();
                p.askPassword();
                password = read.nextLine();
            }
        }
        return cod;
    }

    @Override
    public int escolheMenu() {
        int r=1;
        this.info.atualizaEstado();
        switch (codUser.charAt(0)) {
            case ('u'):
                try {
                    r = menuUser();
                } catch (UtilizadorInexistenteException | LojaInexistenteException u) {
                    p.exception(u.getLocalizedMessage());
                }
                break;
            case ('v'):
                try {
                    r = menuVoluntario();
                } catch (EntregadorInexistenteException | UtilizadorInexistenteException | LojaInexistenteException e) {
                    p.exception(e.getLocalizedMessage());
                }
                break;
            case ('t'):
                r = menuTransportadora();
                break;
            case ('l'):
                r = menuLoja();
                break;
        }
        return r;
    }

    @Override
    public int menuUser() throws UtilizadorInexistenteException, LojaInexistenteException {
        Random rand = new Random();
        String id,idVoluntario,opcao;
        p.apresentaUnreadMessages(this.info.getUser(codUser).getMessages());
        this.info.resetMessages(codUser);
        p.showUserOptions();
        Scanner read= new Scanner(System.in);
        while(!(opcao=read.nextLine()).equals("0")) {
            switch (opcao) {
                case ("1"):
                    float peso =0;
                    p.askLojaID();
                    String loja=read.nextLine();
                    p.askMedical();
                    boolean med = read.nextLine().toUpperCase().equals("S");
                    List<InterfaceLinhaEncomenda> list=new ArrayList<>();
                    InterfaceEncomenda enc = new Encomenda("e"+rand.nextInt(10000),med,0,loja,codUser,list, LocalDateTime.now());
                    p.askLinhaEnc();
                    opcao=read.nextLine().toUpperCase();
                    while (opcao.equals("S")) {
                        p.askCodProduto();
                        String codProd=read.nextLine();
                        p.askDescricao();
                        String desc=read.nextLine();
                        p.askQuantidade();
                        double qnt=Double.parseDouble(read.nextLine());
                        InterfaceLinhaEncomenda l = new LinhaEncomenda(codProd,desc,rand.nextFloat()*100*qnt,qnt);
                        peso+=rand.nextFloat()*5*qnt;
                        list.add(l);
                        p.askLinhaEnc();
                        opcao=read.nextLine().toUpperCase();
                    }
                    if (list.isEmpty()) {
                        p.nadaAApresentar();
                        break;
                    }
                    enc.setPedido(list);
                    enc.setPeso(peso);
                    enc.setDataEntrega(LocalDateTime.now());
                    double preco=enc.calculaValorTotal();
                    p.apresentaEncomenda(enc.toString());
                    p.apresentaPrecoEnc(preco);
                    p.askConfirmacao();
                    opcao=read.nextLine().toUpperCase();
                    if (opcao.equals("S")) {
                        try {
                            this.info.encomenda(enc,preco);
                        }
                        catch (NotEnoughMoneyException e) {
                            p.exception(e.getLocalizedMessage());
                        }
                    }
                    break;
                case ("2"):
                    Set<String[]> opcoes;
                    p.askEncomendaId();
                    id=read.nextLine();
                    if (info.getEncomenda(id) == null) {
                        p.invalid("Encomenda");
                        break;
                    } else if (info.encomendaACaminho(id,codUser)) {
                        p.encomendaACaminho(info.getEncomenda(id).getDataEntrega());
                        break;
                    }
                    else if (info.encomendaNotReady(id,codUser)) {
                        p.encomendaNotReady();
                        break;
                    }
                    else {
                        if (!((idVoluntario = this.info.voluntarioAvailable(id)).equals("n/a"))) {
                            this.info.askVoluntario(idVoluntario, id);
                            p.voluntarioLivre();
                            break;
                        }
                        opcoes = info.getEntregadoresDisp(id);
                        p.apresentaEntregadores(opcoes);
                        p.askEntregadorId();
                        String idEntregador = read.nextLine();
                        double time = Double.parseDouble(opcoes.stream().filter(l -> l[0].equals(idEntregador)).findFirst().get()[4]);
                        this.info.aceitar(idEntregador, id, time);
                        p.encomendaACaminho(info.getEncomenda(id).getDataEntrega());
                        break;
                    }
                case ("3") :

                    break;
                case ("4") :
                    Set<Map.Entry<Boolean,String>> encomendasID = this.info.getUser(this.codUser).getPedidosEntregues();
                    Set<String> classifica= encomendasID.stream().filter(k -> !k.getKey()).map(l -> this.info.getEncomendaPassado(l.getValue()).toString()).collect(Collectors.toSet());
                    if (classifica.isEmpty()) {
                        p.nadaAApresentar();
                        break;
                    }
                    p.apresentaUserEncomendas(classifica);
                    p.askEncomendaId();
                    String eID = read.nextLine();
                    if (encomendasID.stream().noneMatch(l -> l.getValue().equals(eID))) {
                        p.invalid("ID de Encomenda");
                        break;
                    }
                    p.askClassificacao();
                    float c = Float.parseFloat(read.nextLine());
                    if (c<0 || c>10) {
                        p.invalid("Classificação");
                        break;
                    }
                    this.info.classifica(encomendasID,eID,codUser,c);
                    break;
                case("5"):
                    return 1;
                default:
                    p.invalid("Opção");
                break;
            }
            p.showUserOptions();
        }
        return 0;
    }

    @Override
    public int menuVoluntario() throws EntregadorInexistenteException, UtilizadorInexistenteException, LojaInexistenteException {
        String opcao;
        Scanner read = new Scanner(System.in);
        p.showVoluntarioOptions();
        while (!(opcao=read.nextLine()).equals("0")) {
            switch (opcao) {
                case ("1"):
                    List<String> ls = this.info.getVoluntarioRequests(codUser);
                    p.apresentaListRequest(ls);
                    p.askEncomendaId();
                    String encomenda = read.nextLine();
                    if (!encomenda.equals("-1")) {
                     this.info.aceitar(codUser,encomenda,this.info.getTempoEsperado(codUser,encomenda));
                    }
                    this.info.denyAll(codUser);
                    break;
                case ("2"):
                    return 1;
                default:
                    p.invalid("Opção");
                    break;
            }
            p.showVoluntarioOptions();
        }
        return 0;
    }

    @Override
    public int menuTransportadora() {
        return 0;
    }

    @Override
    public int menuLoja() {
        return 0;
    }

    @Override
    public void menu() {
        Scanner read = new Scanner(System.in);
        int r=1;
        info=new Data();
        try {
            info.readFile();
        } catch (FileNotFoundException e) {
            p.fileNotFound();
        } catch (IOException e) {
            p.invalid("Formato de linhas de texto");
        }
        while (r!=0) {
            p.showMainMenu();
            switch (read.nextLine()) {
                case ("1"):
                    codUser = init();
                    r=escolheMenu();
                    break;
                case ("2"):
                    codUser= signIn();
                    r=escolheMenu();
                    break;
                case("3"):
                    p.askQuantoTempo();
                    try {
                        String[] s = read.nextLine().split(":",0);
                        int horas = Integer.parseInt(s[0]);
                        int minutos = Integer.parseInt(s[1]);
                        this.info.maquinaTempo(horas,minutos);
                    }
                    catch (NumberFormatException | NullPointerException d) {
                        p.exception(d.getLocalizedMessage());
                    }
                    break;
                case("4"):
                    r=0;
                    break;
                default:
                    p.invalid("Opção");
                    break;
            }
        }

        p.showBye();
    }
}
