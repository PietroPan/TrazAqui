package MVC.Controller;
/**
 * Write a description of class Programa here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

import java.awt.geom.Point2D;
import java.io.Serializable;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import MVC.View.*;
import MVC.Model.*;
import MVC.Model.Lojas.*;
import MVC.Model.Entregadores.*;
import MVC.Model.Utilizadores.*;
import Common.*;
import Exceptions.*;

public class Controller implements InterfaceController, Serializable {
    private InterfaceData info;
    private String codUser;
    private Printer p;

    public Controller(InterfaceData model, Printer p) {
        this.info=model;
        this.codUser="";
        this.p=p;
    }

    @Override
    public LocalDateTime StringToLocalDateTime(String s) {
        String[] a=s.split(":",0);
        if (a.length!=5) {
            p.invalid("Formato");
            return null;
        }
        List<Integer> i = Arrays.stream(a).map(Integer::parseInt).collect(Collectors.toList());
        try {
            return LocalDateTime.of(i.get(0), i.get(1), i.get(2), i.get(3), 0);
        }
        catch (DateTimeException d) {
            p.invalid("Formato de Data");
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
                try {
                    InterfaceEntregador e = this.info.getEntregador(cod);
                    r= e!=null && password.equals(e.getPassword());
                }
                catch (EntregadorInexistenteException d) {
                    p.naoRegistado("InterfaceEntregador");
                    r=false;
                }
                break;
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
        String userCod = info.gerarCodUser();
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
        user.addMessage("User cridado com sucesso\nCódigo: "+userCod+"\nBem Vindo!");
        info.addUser(user);
        return userCod;
    }

    @Override
    public String initEntregador(int i) {
        Scanner read= new Scanner(System.in);
        String cod;
        if (i==1) cod = info.gerarCodVol();
        else cod = info.gerarCodTrans();
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
            InterfaceVoluntario vol = new Voluntario(name,cod,new Point2D.Double(x,y),password,r,medical,v,0,0,new ArrayList<>(),new Encomenda(),new ArrayList<>());
            vol.addMessage("User cridado com sucesso\nCódigo: "+cod+"\nBem Vindo!");
            this.info.addEntregador(vol);
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
            InterfaceTransportadora trans = new Transportadora(name,cod,new Point2D.Double(x,y),password,r,nif,km,kg,medical,v,0,0,n,new ArrayList<>(),new ArrayList<>());
            trans.addMessage("User cridado com sucesso\nCódigo: "+cod+"\nBem Vindo!");
            this.info.addEntregador(trans);
        }
        return cod;
    }

    @Override
    public String initLoja() {
        Scanner read= new Scanner(System.in);
        String cod = info.gerarCodLoja();
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
        this.info.addLoja(new Loja(cod,name,new Point2D.Double(x,y),password,tF,t,new HashMap<>(),new HashMap<>(),new HashMap<>()));
        return cod;
    }

    @Override
    public String init() {
        Scanner read = new Scanner(System.in);
        p.askCod();
        String cod=read.nextLine();
        p.askPassword();
        String password = read.nextLine();
        while (!login(cod,password)) {
            p.askNew();
            String option = read.nextLine().toUpperCase();
            if (option.equals("S")) {
                cod=signIn();
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
                } catch (UtilizadorInexistenteException | LojaInexistenteException | EntregadorInexistenteException u) {
                    p.exception(u.getLocalizedMessage()+"op1");
                }
                break;
            case ('v'):
                try {
                    r = menuVoluntario();
                } catch (EntregadorInexistenteException | UtilizadorInexistenteException | LojaInexistenteException e) {
                    p.exception(e.getLocalizedMessage()+"op2");
                }
                break;
            case ('t'):
                try {
                    r = menuTransportadora();
                }
                catch (EntregadorInexistenteException | UtilizadorInexistenteException | LojaInexistenteException e) {
                    p.exception(e.getLocalizedMessage()+"op3");
                }
                break;
            case ('l'):
                r = menuLoja();
                break;
        }
        return r;
    }

    @Override
    public int menuUser() throws UtilizadorInexistenteException, LojaInexistenteException, EntregadorInexistenteException {
        Random rand = new Random();
        String opcao;
        p.apresentaUnreadMessages(this.info.getUser(codUser).getMessages());
        this.info.resetMessages(codUser);
        p.showUserOptions();
        Scanner read= new Scanner(System.in);
        while(!(opcao=read.nextLine()).equals("0")) {
            switch (opcao) {
                case ("1"):
                    List<InterfaceLinhaEncomenda> stock;
                    float peso =0;
                    p.askLojaID();
                    String loja=read.nextLine();
                    try {
                        stock = this.info.getStock(loja);
                    }
                    catch (NullPointerException e) {
                        throw new LojaInexistenteException("Loja inexistente");
                    }
                    p.askMedical();
                    boolean med = read.nextLine().toUpperCase().equals("S");
                    List<Map.Entry<String,Double>> list=new ArrayList<>();
                    List<InterfaceLinhaEncomenda> lista = new ArrayList<>();
                    InterfaceEncomenda enc = new Encomenda("e"+rand.nextInt(10000),med,0,loja,codUser,lista, this.info.getHoras(),this.info.getHoras());
                    p.apresentaStock(stock);
                    p.askLinhaEnc();
                    opcao=read.nextLine().toUpperCase();
                    while (opcao.equals("S")) {
                        p.askCodProduto();
                        String codProd=read.nextLine();
                        p.askQuantidade();
                        double qnt=Double.parseDouble(read.nextLine());
                        AbstractMap.SimpleEntry<String,Double> l = new AbstractMap.SimpleEntry<>(codProd,qnt);
                        peso+=rand.nextFloat()*5*qnt;
                        list.add(l);
                        p.askLinhaEnc();
                        opcao=read.nextLine().toUpperCase();
                    }
                    if (list.isEmpty()) {
                        p.nadaAApresentar();
                        break;
                    }
                    try {
                        lista = this.info.formaListaDeLinhasEncomenda(loja,list);
                    }
                    catch (ProductNotAvailableException e) {
                        p.exception(e.getLocalizedMessage());
                        break;
                    }
                    enc.setPedido(lista);
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
                    p.apresentaPedidos1(this.info.getUser(codUser).getPedidos());
                    p.askOferta();
                    String r = read.nextLine();
                    while (r.equals("s")|| r.equals("S")){
                        p.askCodEnc();
                        String encS = read.nextLine();
                        p.askCodTrans();
                        String trans = read.nextLine();
                        String stat = this.info.checkStatPedido(encS,trans,codUser);
                        if (stat.equals("p")) this.info.aceitarPedido(this.info.getEncomenda(encS),trans);
                        p.pedido(stat);
                        p.askOfertaMais();
                        r = read.nextLine();
                    }
                    break;
                case ("3"):
                    List<TriploHist> l = this.info.getHistorico(codUser);
                    p.askByData();
                    r=read.nextLine();
                    if (r.equals("s")||r.equals("S")) {
                        p.askDataInicio();
                        String s = read.nextLine();
                        LocalDateTime l1 = this.StringToLocalDateTime(s);
                        p.askDataFim();
                        s = read.nextLine();
                        LocalDateTime l2 = this.StringToLocalDateTime(s);
                        l = this.info.getHistoricoByDate(l1,l2,l);
                    }
                    p.askByEnt();
                    r=read.nextLine();
                    if (r.equals("s")||r.equals("S")) {
                        p.askEnt();
                        r=read.nextLine();
                        l = this.info.getHistoricoByEnt(r,l);
                    }
                    p.printHist(l);
                    break;
                case("4") :
                    p.printHist(this.info.getHistorico(codUser));
                    p.askEntregadorId();
                    String eID = read.nextLine();
                    p.askClassificacao();
                    float c = Float.parseFloat(read.nextLine());
                    int res = this.info.classificaEnt(eID,codUser,c);
                    p.classificacao(res);
                    break;
                case("5") :
                    return 1;
                default:
                    p.invalid("Opção");
                break;
            }
            p.showUserOptions();
        }
        return 0;
    }


    public int menuVoluntario() throws EntregadorInexistenteException, UtilizadorInexistenteException, LojaInexistenteException {
        String opcao;
        Scanner read = new Scanner(System.in);
        p.apresentaUnreadMessages(this.info.getEntregador(codUser).getMessages());
        this.info.resetMessages(codUser);
        p.showVoluntarioOptions();
        while (!(opcao=read.nextLine()).equals("0")) {
            switch (opcao) {
                case ("1"):
                    if (!this.info.isAEntregar(codUser)) {
                        List<InterfaceEncomenda> ls = this.info.getEncomendasDisp(codUser);
                        p.apresentaStockAll(ls);
                    } else p.acaoIndesponivel();
                    break;
                case ("2"):
                    if (!this.info.isAEntregar(codUser)){
                        p.askCodEnc();
                        String enc = read.nextLine();
                        this.info.addEncomendaVol(this.info.getEncomenda(enc),codUser);
                        p.pedidoAceite();
                        p.fazerEncomenda();
                        String r = read.nextLine();
                        if (r.equals("s") || r.equals("S")) {
                            this.info.fazerEncomenda(codUser);
                        }
                    } else p.acaoIndesponivel();
                    break;
                case ("3"):
                    if (!this.info.isAEntregar(codUser)) {
                        p.fazerEncomenda();
                        String r = read.nextLine();
                        if (r.equals("s") || r.equals("S")) {
                            this.info.fazerEncomenda(codUser);
                        }
                    } else p.acaoIndesponivel();
                    break;
                case ("4"):
                    List<TriploHist> l = this.info.getHistorico(codUser);
                    p.askByData();
                    String r=read.nextLine();
                    if (r.equals("s")||r.equals("S")) {
                        p.askDataInicio();
                        String s = read.nextLine();
                        LocalDateTime l1 = this.StringToLocalDateTime(s);
                        p.askDataFim();
                        s = read.nextLine();
                        LocalDateTime l2 = this.StringToLocalDateTime(s);
                        l = this.info.getHistoricoByDate(l1,l2,l);
                    }
                    p.printHist(l);
                    break;
                case ("5"):
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
    public int menuTransportadora() throws EntregadorInexistenteException, UtilizadorInexistenteException, LojaInexistenteException {
        String opcao;
        Scanner read = new Scanner(System.in);
        p.apresentaUnreadMessages(this.info.getEntregador(codUser).getMessages());
        this.info.resetMessages(codUser);
        p.showTransportadoraOptions();
        InterfaceTransportadora trans = (InterfaceTransportadora) this.info.getEntregador(codUser);
        while (!(opcao=read.nextLine()).equals("0")) {
            switch (opcao) {
                case ("1"):
                    if (!this.info.isAEntregar(codUser)){
                        List<InterfaceEncomenda> ls = this.info.getEncomendasDisp(codUser);
                        p.apresentaStockAll(ls);
                    } else p.acaoIndesponivel();
                    break;
                case ("2"):
                    if (!this.info.isAEntregar(codUser)) {
                        p.askCodEnc();
                        String enc = read.nextLine();
                        p.showValTransporte(this.info.getDistTotal(codUser, enc) * trans.getCustoKm());
                    } else p.acaoIndesponivel();
                    break;
                case ("3"):
                    if (!this.info.isAEntregar(codUser)) {
                        p.askCodEnc();
                        String enc = read.nextLine();
                        if (this.info.existePedido(codUser,enc)) p.existePedido();
                        else {
                            List<Boolean> b = this.info.fazerPedido(this.info.getEncomenda(enc), codUser);
                            if (!b.get(0)) p.naoRaio();
                            if (!b.get(1)) p.naoMedical();
                            if (!b.get(2)) p.naoPronto();
                            if (b.get(0) && b.get(1) && b.get(2)) p.pedidoSucesso();
                        }

                    } else p.acaoIndesponivel();
                    break;
                case ("4"):
                    if (!this.info.isAEntregar(codUser)){
                        trans = (InterfaceTransportadora) this.info.getEntregador(codUser);
                        p.apresentaPedidos2(trans.getPedidos());
                    } else p.acaoIndesponivel();
                    break;
                case ("5"):
                    if (!this.info.isAEntregar(codUser)) {
                        p.fazerEncomenda();
                        String r = read.nextLine();
                        if (r.equals("s") || r.equals("S")) {
                            this.info.fazerEncomenda(codUser);
                            p.encomendaEntregue();
                        }
                    } else p.acaoIndesponivel();
                    break;
                case ("6"):
                    List<TriploHist> l = this.info.getHistorico(codUser);
                    p.askByData();
                    String r=read.nextLine();
                    if (r.equals("s")||r.equals("S")) {
                        p.askDataInicio();
                        String s = read.nextLine();
                        LocalDateTime l1 = this.StringToLocalDateTime(s);
                        p.askDataFim();
                        s = read.nextLine();
                        LocalDateTime l2 = this.StringToLocalDateTime(s);
                        l = this.info.getHistoricoByDate(l1,l2,l);
                    }
                    p.printHist(l);
                    break;
                case ("7"):
                    return 1;
                default:
                    p.invalid("Opção");
                    break;
            }
            p.showTransportadoraOptions();
        }
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