package MVC.Controller;
/**
 * Write a description of class Programa here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

import java.awt.geom.Point2D;
import java.io.*;
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
    private InterfacePrinter p;

    /**
     * Construtor parametrizado
     * @param model Modelo
     * @param p View
     */
    public Controller(InterfaceData model, InterfacePrinter p) {
        this.info=model;
        this.codUser="";
        this.p=p;
    }

    /**
     * Método que transforma informação presente em String em LocalDateTime
     * @param s String a utilzar
     * @return LocalDateTime com a informação de s
     */
    @Override
    public LocalDateTime StringToLocalDateTime(String s) {
        String[] a=s.split(":",0);
        if (a.length!=4) {
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

    /**
     * Método responsável pelo sign in de uma entidade
     * @return codigo de utulizador criado
     */
    @Override
    public String signIn() {
        Scanner read = new Scanner(System.in);
        String cod="";
        int i=0;
        while (i<1 || i>4) {
            p.showLoginOptions();
            try {
                i = Integer.parseInt(read.nextLine());
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
                    case (0):
                        return null;
                    default:
                        p.invalid("Opção");
                        break;
                }
            }
            catch (NumberFormatException f) {
                p.invalid("Input");
                i=5;
            }
        }
        return cod;
    }

    /**
     *
     * Método responsável pelo login de uma entidade
     * @param cod código de login
     * @param password password da entidade
     * @return true se essa entidade existir false otherwise
     */
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
                catch (EntregadorInexistenteException d){
                    p.naoRegistado("Entregador");
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

    /**
     * Método de sign-in de um utilizador
     * @return código deste
     */
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
        user = new Utilizador(userCod,password,name,balance,new Point2D.Double(x,y),new HashSet<>());
        user.addMessage("User cridado com sucesso\nCódigo: "+userCod+"\nBem Vindo!");
        info.addUser(user);
        return userCod;
    }

    /**
     * Método de sign-in de um entregador
     * @param i 1 se for voluntario,otherwise é transportadora
     * @return código da entidade de retorno
     */
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

    /**
     * método de sign-in de uma Loja
     * @return Código de uma loja
     */
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
        InterfaceLoja loja = new Loja(cod,name,new Point2D.Double(x,y),password,tF,t,new HashMap<>(),new HashMap<>(),new HashMap<>());
        loja.addMessage("User cridado com sucesso\nCódigo: "+cod+"\nBem Vindo!");
        this.info.addLoja(loja);
        return cod;
    }

    /**
     * Método responsável por entrar no sistema atravês de uma entidade
     * @return String do código de entidade registado atualmente
     */
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
                if (cod==null) return null;
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

    /**
     * Método que escolhe o menu a apresentar dependendo da entidade registada no momento
     * @return 0 se decidiu sair 1 se ainda está a utilizar a aplicação
     */
    @Override
    public int escolheMenu() {
        int r=1;
        this.info.atualizaEstado();
        switch (codUser.charAt(0)) {
            case ('u'):
                try {
                    r = menuUser();
                } catch (UtilizadorInexistenteException | LojaInexistenteException | EntregadorInexistenteException u) {
                    p.exception(u.getLocalizedMessage()+" op1");
                }
                break;
            case ('v'):
                try {
                    r = menuVoluntario();
                } catch (EntregadorInexistenteException | UtilizadorInexistenteException | LojaInexistenteException e) {
                    p.exception(e.getLocalizedMessage()+" op2");
                }
                break;
            case ('t'):
                try {
                    r = menuTransportadora();
                }
                catch (EntregadorInexistenteException | UtilizadorInexistenteException | LojaInexistenteException e) {
                    p.exception(e.getLocalizedMessage()+" op3");
                }
                break;
            case ('l'):
                try {
                    r = menuLoja();
                } catch (LojaInexistenteException e) {
                    p.exception(e.getLocalizedMessage()+" op4");
                }
                break;
        }
        return r;
    }

    /**
     * Método responsável pela interação com o utilizador
     * @return 0 se decidiu sair 1 se ainda está a utilizar a aplicação
     * @throws UtilizadorInexistenteException caso em algum momento um utilizadpr não esteja registado no sistema
     * @throws LojaInexistenteException caso alguma loja não esteja registada no sistema
     * @throws EntregadorInexistenteException caso algum entregador não esteja registado no sistema
     */
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
                        p.exception("Loja inexistente");
                        break;
                    }
                    p.askMedical();
                    boolean med = read.nextLine().toUpperCase().equals("S");
                    List<Map.Entry<String,Double>> list=new ArrayList<>();
                    List<InterfaceLinhaEncomenda> lista = new ArrayList<>();
                    InterfaceEncomenda enc = new Encomenda(this.info.gerarCodEnc(),med,0,loja,codUser,lista, this.info.getHoras(),this.info.getHoras());

                    p.apresentaTotalProdutosStock(stock);

                    int linhasTabela=4,colunasTabela=3;
                    try{
                        p.askLinhasTabela(); linhasTabela = Integer.parseInt(read.nextLine());
                        p.askColunasTabela(); colunasTabela = Integer.parseInt(read.nextLine());
                    } catch (NumberFormatException e)
                    {
                        p.exception(e.getLocalizedMessage()+"\nUsando valores default tabela 4*3\n");
                    }


                    if(colunasTabela>stock.size()) colunasTabela = stock.size();
                    if(colunasTabela<1||colunasTabela>5) colunasTabela = 3;
                    if(linhasTabela<1 || linhasTabela>5) linhasTabela = 4;
                    int npaginas = p.getNumeroPaginas(stock.size(),linhasTabela,colunasTabela);

                    int pagina = 1;
                    try
                    {
                        p.askPagina(npaginas); pagina = Integer.parseInt(read.nextLine());
                    } catch(NumberFormatException e)
                    {
                        p.exception(e.getLocalizedMessage()+"\nSeguindo para pagina 1...\n");
                    }


                    if(pagina>npaginas) pagina = npaginas;
                    if(pagina<1) pagina=1;

                    String opcaoMenuTabela = "1";
                    while(!opcaoMenuTabela.equals("q"))
                    {
                        p.apresentaStock(stock,pagina,linhasTabela,colunasTabela);
                        p.apresentaMenuTabela(); opcaoMenuTabela = read.nextLine();

                        switch(opcaoMenuTabela)
                        {
                            case "n": // proxima pagina
                                pagina++;
                                if(pagina>npaginas) pagina--;
                                break;
                            case "p": // pagina anterior
                                pagina--;
                                if(pagina<1) pagina++;
                                break;
                            case "a": // deseja adicionar produto
                                p.askCodProduto();
                                String codProd=read.nextLine();
                                p.askQuantidade();
                                double qnt = 0;
                                try
                                {
                                    qnt=Double.parseDouble(read.nextLine());
                                } catch (NumberFormatException e)
                                {
                                    p.exception(e.getLocalizedMessage()+"\nCancelando linha de encomenda!\n");
                                    break;
                                }
                                if (qnt<=0) {
                                    p.exception("Quantidade inválida");
                                    break;
                                }
                                AbstractMap.SimpleEntry<String,Double> l = new AbstractMap.SimpleEntry<>(codProd,qnt);
                                peso+=rand.nextFloat()*5*qnt;
                                list.add(l);
                                break;
                            case "r": // remover produto
                                p.askCodProdutoRm();
                                List<Map.Entry<String,Double>> aux = new ArrayList<>();
                                codProd=read.nextLine();
                                for (Map.Entry<String,Double> i : list){
                                    if (i.getKey().equals(codProd)){
                                        aux.add(i);
                                    }
                                    list.removeAll(aux);
                                }
                            case "q": break;
                            default:
                                try
                                {
                                    pagina = Integer.parseInt(opcaoMenuTabela);
                                    if(pagina>npaginas) pagina = npaginas;
                                    if(pagina<1) pagina=1;
                                } catch (NumberFormatException e)
                                {
                                    p.exception("Opção inválida");
                                }
                                break;
                        }

                    }
                    //
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
                    p.apresentaPedidos1(this.info.getByPreco(this.info.getUser(codUser).getPedidos()));
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
                        if (l1==null) break;
                        p.askDataFim();
                        s = read.nextLine();
                        LocalDateTime l2 = this.StringToLocalDateTime(s);
                        if (l2==null) break;
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
                    try {
                        float c = Float.parseFloat(read.nextLine());
                        int res = this.info.classificaEnt(eID,codUser,c);
                        p.classificacao(res);
                    }
                    catch (NumberFormatException e){
                        p.classInv();
                    }
                    break;
                case("5") :
                    p.askCodEnc();
                    String codEnc = read.nextLine();
                    p.askEnt2();
                    String codEnt = read.nextLine();
                    if (codEnt.contains("t")){
                        String stat = this.info.checkStatPedido(codEnc,codEnt,codUser);
                        if (stat.equals("a")) System.out.println(this.info.timeLeft(codEnt,codEnc));
                        else p.pedido(stat);
                    }
                    else if (codEnt.contains("v")){
                        System.out.println(this.info.timeLeft(codEnt,codEnc));
                    }
                    break;
                case("6") :
                    return 1;
                default:
                    p.invalid("Opção");
                break;
            }
            p.showUserOptions();
        }
        return 0;
    }


    /**
     * Método responsável por toda a interação com o voluntário
     * @return 0 se decidiu sair 1 se ainda está a utilizar a aplicação
     * @throws EntregadorInexistenteException Caso em algum momento um entregador não exista
     * @throws UtilizadorInexistenteException Case em algum momento um utilziador não exista
     * @throws LojaInexistenteException Caso em algum momento uma loja não exista
     */
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
                    } else p.acaoIndisponivel();
                    break;
                case ("2"):
                    if (!this.info.isAEntregar(codUser)){
                        p.askCodEnc();
                        String enc = read.nextLine();
                        try {
                            if (this.info.isFree(enc,this.info.getEncomenda(enc).getDestino())){
                                this.info.addEncomendaVol(this.info.getEncomenda(enc),codUser);
                                p.pedidoAceite();
                                p.fazerEncomenda();
                                String r = read.nextLine();
                                if (r.equals("s") || r.equals("S")) {
                                    this.info.fazerEncomenda(codUser);
                                }
                            }
                            else p.foiAceite();
                        }
                        catch (NullPointerException e){
                            p.encInv();
                        }
                    } else p.acaoIndisponivel();
                    break;
                case ("3"):
                    if (!this.info.isAEntregar(codUser)) {
                        p.fazerEncomenda();
                        String r = read.nextLine();
                        if (r.equals("s") || r.equals("S")) {
                            this.info.fazerEncomenda(codUser);
                        }
                    } else p.acaoIndisponivel();
                    break;
                case ("4"):
                    List<TriploHist> l = this.info.getHistorico(codUser);
                    p.askByData();
                    String r=read.nextLine();
                    if (r.equals("s")||r.equals("S")) {
                        p.askDataInicio();
                        String s = read.nextLine();
                        LocalDateTime l1 = this.StringToLocalDateTime(s);
                        if (l1==null) break;
                        p.askDataFim();
                        s = read.nextLine();
                        LocalDateTime l2 = this.StringToLocalDateTime(s);
                        if (l2==null) break;
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

    /**
     * Método responsável pela interação com a transportadora no seu menu
     * @return 0 se decidiu sair 1 se ainda está a utilizar a aplicação
     * @throws EntregadorInexistenteException Entregador não registado
     * @throws UtilizadorInexistenteException Utilizador não registado
     * @throws LojaInexistenteException Loja não registada
     */
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
                    } else p.acaoIndisponivel();
                    break;
                case ("2"):
                    if (!this.info.isAEntregar(codUser)) {
                        try {
                            p.askCodEnc();
                            String enc = read.nextLine();
                            p.showValTransporte(this.info.getDistTotal(codUser, enc) * trans.getCustoKm());
                        }
                        catch (NullPointerException e){
                            p.encInv();
                        }
                    } else p.acaoIndisponivel();
                    break;
                case ("3"):
                    if (!this.info.isAEntregar(codUser)) {
                        p.askCodEnc();
                        String enc = read.nextLine();
                        try {
                            if (this.info.existePedido(codUser,enc)) p.existePedido();
                            else {
                                List<Boolean> b = this.info.fazerPedido(this.info.getEncomenda(enc), codUser);
                                if (!b.get(0)) p.naoRaio();
                                if (!b.get(1)) p.naoMedical();
                                if (!b.get(2)) p.naoPronto();
                                if (b.get(0) && b.get(1) && b.get(2)) p.pedidoSucesso();
                            }
                        }
                        catch (NullPointerException e){
                            p.encInv();
                        }

                    } else p.acaoIndisponivel();
                    break;
                case ("4"):
                    if (!this.info.isAEntregar(codUser)){
                        trans = (InterfaceTransportadora) this.info.getEntregador(codUser);
                        p.apresentaPedidos2(trans.getPedidos());
                    } else p.acaoIndisponivel();
                    break;
                case ("5"):
                    if (!this.info.isAEntregar(codUser)) {
                        if (((InterfaceTransportadora) this.info.getEntregador(codUser)).getEncomendaAtual().size()!=0) {
                            p.fazerEncomenda();
                            String r = read.nextLine();
                            if (r.equals("s") || r.equals("S")) {
                                this.info.fazerEncomenda(codUser);
                                p.encomendaEntregue();
                            }
                        }
                        else {
                            p.nadaAApresentar();
                            break;
                        }
                    } else p.acaoIndisponivel();
                    break;
                case ("6"):
                    List<TriploHist> l = this.info.getHistorico(codUser);
                    p.askByData();
                    String r=read.nextLine();
                    if (r.equals("s")||r.equals("S")) {
                        p.askDataInicio();
                        String s = read.nextLine();
                        LocalDateTime l1 = this.StringToLocalDateTime(s);
                        if (l1==null) break;
                        p.askDataFim();
                        s = read.nextLine();
                        LocalDateTime l2 = this.StringToLocalDateTime(s);
                        if (l2==null) break;
                        l = this.info.getHistoricoByDate(l1,l2,l);
                    }
                    p.printHist(l);
                    break;
                case("7"):
                    p.askByData();
                    r=read.nextLine();
                    if (r.equals("s")||r.equals("S")) {
                        p.askDataInicio();
                        String s = read.nextLine();
                        LocalDateTime l1 = this.StringToLocalDateTime(s);
                        if (l1==null) break;
                        p.askDataFim();
                        s = read.nextLine();
                        LocalDateTime l2 = this.StringToLocalDateTime(s);
                        if (l2==null) break;
                        p.showTotalFat(this.info.totalFaturado(codUser,l1,l2));
                    }
                    break;
                case ("8"):
                    return 1;
                default:
                    p.invalid("Opção");
                    break;
            }
            p.showTransportadoraOptions();
        }
        return 0;
    }

    /**
     * Método responsável pela interação com a loja no seu menu
     * @return 0 se decidiu sair 1 se ainda está a utilizar a aplicação
     */
    @Override
    public int menuLoja() throws LojaInexistenteException {
        String opcao;
        Scanner read = new Scanner(System.in);
        p.apresentaUnreadMessages(this.info.getLoja(codUser).getMessages());
        this.info.resetMessages(codUser);
        p.showLojaOptions();
        while(!(opcao=read.nextLine()).equals("0")){
            switch (opcao){
                case "1":
                    List<InterfaceLinhaEncomenda> stock = this.info.getStock(codUser);
                    p.apresentaTotalProdutosStock(stock);

                    int linhasTabela=4,colunasTabela=3;
                    try{
                        p.askLinhasTabela(); linhasTabela = Integer.parseInt(read.nextLine());
                        p.askColunasTabela(); colunasTabela = Integer.parseInt(read.nextLine());
                    } catch (NumberFormatException e)
                    {
                        p.exception(e.getLocalizedMessage()+"\nUsando valores default tabela 4*3\n");
                    }


                    if(colunasTabela>stock.size()) colunasTabela = stock.size();
                    if(colunasTabela<1||colunasTabela>5) colunasTabela = 3;
                    if(linhasTabela<1 || linhasTabela>5) linhasTabela = 4;
                    int npaginas = p.getNumeroPaginas(stock.size(),linhasTabela,colunasTabela);

                    int pagina = 1;
                    try
                    {
                        p.askPagina(npaginas); pagina = Integer.parseInt(read.nextLine());
                    } catch(NumberFormatException e)
                    {
                        p.exception(e.getLocalizedMessage()+"\nSeguindo para pagina 1...\n");
                    }


                    if(pagina>npaginas) pagina = npaginas;
                    if(pagina<1) pagina=1;

                    String opcaoMenuTabela = "1";
                    while(!opcaoMenuTabela.equals("q"))
                    {
                        p.apresentaStock(stock,pagina,linhasTabela,colunasTabela);
                        p.apresentaMenuTabelaLoja(); opcaoMenuTabela = read.nextLine();
                        npaginas = p.getNumeroPaginas(stock.size(),linhasTabela,colunasTabela);
                        switch(opcaoMenuTabela)
                        {
                            case "n": // proxima pagina
                                pagina++;
                                if(pagina>npaginas) pagina--;
                                break;
                            case "p": // pagina anterior
                                pagina--;
                                if(pagina<1) pagina++;
                                break;
                            case "a": // deseja adicionar produto
                                double qnt;
                                p.askDescricao();
                                String des =read.nextLine();
                                p.askPrecoProd();
                                double preco = Double.parseDouble(read.nextLine());
                                p.askQuantidadeProd();
                                try {
                                    qnt = Double.parseDouble(read.nextLine());
                                }
                                catch (NumberFormatException e) {
                                    p.exception("Quantidade inválida");
                                    break;
                                }
                                if (qnt<=0) {
                                    p.exception("Quantidade inválida");
                                    break;
                                }
                                InterfaceLinhaEncomenda l = new LinhaEncomenda("",des,preco,qnt);
                                this.info.addToStock(codUser,l);
                                stock = this.info.getStock(codUser);
                                break;
                            case "r": // remover produto
                                p.askCodProdutoRm();
                                String cod = read.nextLine();
                                this.info.removeFromStock(codUser,cod);
                                stock = this.info.getStock(codUser);
                                break;
                            case "s": // Mudar Quantidade
                                p.askCodProdutoAlt();
                                String codP = read.nextLine();
                                p.askQuantidadeProd();
                                try {
                                    qnt = Double.parseDouble(read.nextLine());
                                }
                                catch (NumberFormatException e) {
                                    p.exception("Quantidade inválida");
                                    break;
                                }
                                if (qnt<=0) {
                                    p.exception("Quantidade inválida");
                                    break;
                                }
                                this.info.mudarQuantidade(codUser,codP,qnt);
                                stock=this.info.getStock(codUser);
                                break;
                            case "c": // Mudar Preco
                                p.askCodProdutoAlt();
                                codP = read.nextLine();
                                p.askPrecoProd();
                                preco = Double.parseDouble(read.nextLine());
                                this.info.mudarPreco(codUser,codP,preco);
                                stock=this.info.getStock(codUser);
                                break;
                            case "q": break;
                            default:
                                try
                                {
                                    pagina = Integer.parseInt(opcaoMenuTabela);
                                    if(pagina>npaginas) pagina = npaginas;
                                    if(pagina<1) pagina=1;
                                } catch (NumberFormatException e)
                                {
                                    p.exception("Opção Inválida");
                                }
                                break;
                        }
                    }
                    break;
                case("2"):
                    return 1;
                case("3"):
                    return 0;
                default:
                    p.invalid("Opção");
                    break;
            }
        }
        return 0;
    }

    /**
     * Método responsável pela interação com o utilizador no Menu de queries e outras informações sobre o sistema
     * @return 0 se decidiu sair 1 se ainda está a utilizar a aplicação
     * @throws UtilizadorInexistenteException Utilizador não registado
     * @throws LojaInexistenteException Loja não registada
     * @throws EntregadorInexistenteException Entregador não registado
     */
    @Override
    public int menuSystem() throws UtilizadorInexistenteException, LojaInexistenteException, EntregadorInexistenteException {
        String opcao;
        Scanner read = new Scanner(System.in);
        p.showSystemMenu();
        while (!(opcao=read.nextLine()).equals("0")){
            switch (opcao){
                case ("1"):
                    p.showTop10Users(this.info.top10Users());
                    break;
                case ("2"):
                    p.showTop10Trans(this.info.top10Trans());
                    break;
                case ("3"):
                    p.askFileName();
                    String r = "resources/"+read.nextLine();
                    try{
                        saveState(r,this.info);
                    }
                    catch (IOException e) {
                        p.exception(e.getLocalizedMessage());
                    }
                    break;
                case ("4"):
                    p.askFileName();
                    r = "resources/"+read.nextLine();
                    try{
                        this.info=loadState(r);
                    } catch (IOException | ClassNotFoundException e) {
                        p.exception(e.getLocalizedMessage());
                    }
                    break;
                case ("5"):
                    return 1;
            }
            p.showSystemMenu();
        }
        return 0;
    }

    /**
     * Método responsável por interagir com todas as entidades no menu principal e daí decidir qual menu apresentar a seguir
     */
    @Override
    public void menu() {
        Scanner read = new Scanner(System.in);
        int r=1;
        while (r!=0) {
            p.showMainMenu();
            switch (read.nextLine()) {
                case ("1"):
                    codUser = init();
                    if (codUser==null)  {
                        r=1;
                        break;
                    }
                    r=escolheMenu();
                    break;
                case ("2"):
                    codUser= signIn();
                    if (codUser==null) {
                        r=1;
                        break;
                    }
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
                    catch (NumberFormatException | NullPointerException | IndexOutOfBoundsException d) {
                        p.exception("WRONG FORMAT");
                    }
                    break;
                case("4"):
                    try {
                        r=menuSystem();
                    }
                    catch (EntregadorInexistenteException | UtilizadorInexistenteException | LojaInexistenteException e) {
                        p.LOL();
                    }
                    break;
                case("5"):
                    r=0;
                    break;
                default:
                    p.invalid("Opção");
                    break;
            }
        }
        p.showBye();
        String s = read.nextLine();
        if (s.toUpperCase().equals("S")){
            FileWriter bufferAll;
            try{
                p.showFeed();
                bufferAll = new FileWriter(Const.fileFeedback);
                while(read.hasNext()) {
                    s=read.nextLine()+"\n";
                    bufferAll.write(s);
                }
                bufferAll.close();
            } catch (IOException e) {
                p.exception(e.getLocalizedMessage());
            }
        }
    }

    /**
     * Método responsável por salvar o estado atual através de objectStreams
     * @param ficheiro Nome do ficheiro onde salvar
     * @param model Modelo a salvar
     * @throws IOException Caso o ficheiro não exista
     */
    public void saveState(String ficheiro, InterfaceData model) throws IOException {
        FileOutputStream fos = new FileOutputStream(ficheiro);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(model);
        oos.close();
        fos.close();
    }

    /**
     * Método responsável por carregar o estado de um ficheiro através de objectStreams
     * @param ficheiro Nome do ficheiro onde procurar a informação salva
     * @return Nova Data com a informação lida do ficheiro
     * @throws IOException Caso o ficheiro não exista
     * @throws ClassNotFoundException Caso a classe guardada tenha um formato diferente daquela a que tentamos ler
     */
    public InterfaceData loadState(String ficheiro) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(ficheiro);
        ObjectInputStream ois = new ObjectInputStream(fis);
        InterfaceData g = (Data) ois.readObject();
        ois.close();
        fis.close();
        return g;
    }
}