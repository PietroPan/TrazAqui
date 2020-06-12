package MVC.Model;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.awt.geom.Point2D;
import java.util.stream.Collectors;

import Common.*;
import Exceptions.*;
import MVC.Model.Utilizadores.*;
import MVC.Model.Entregadores.*;
import MVC.Model.Lojas.*;

public class Data implements InterfaceData, Serializable
{
    InterfaceUtilizadores users;
    InterfaceLojas lojas;
    InterfaceEntregadores entregadores;
    LocalDateTime horas;
    InterfaceHistorico historico;

    /**
     * Construtor vazio
     */
    public Data() {
     this.users=new Utilizadores();
     this.lojas=new Lojas();
     this.entregadores=new Entregadores();
     this.horas=LocalDateTime.now();
    }

    /**
     * Getter para o paramtero horas
     * @return horas
     */
    @Override
    public LocalDateTime getHoras() {
        return this.horas;
    }

    /**
     * Setter para o parametro horas
     * @param d horas
     */
    @Override
    public void setHoras(LocalDateTime d) {
        this.horas=d;
    }

    /**
     * Getter para um user no parametro users
     * @param cod código do user
     * @return Utilizador caso exista
     * @throws UtilizadorInexistenteException caso não exista
     */
    @Override public InterfaceUtilizador getUser(String cod) throws UtilizadorInexistenteException {
        return users.getUser(cod);
    }

    /**
     * Adicionar um urilizador
     * @param u utilizador a adicionar
     */
    @Override public void addUser(InterfaceUtilizador u) {
        this.users.addUser(u);
    }

    /**
     * Getter para um entregador no parametro entregadores
     * @param cod codigo do entregador
     * @return o entregador caso exista
     * @throws EntregadorInexistenteException caso não exista
     */
    @Override public InterfaceEntregador getEntregador(String cod) throws EntregadorInexistenteException {
        return this.entregadores.getEntregador(cod);
    }

    /**
     * Adicionar um entregador
     * @param e entregador
     */
    @Override public void addEntregador(InterfaceEntregador e) {
        this.entregadores.setEntregador(e.getCodigo(),e.clone());
    }

    /**
     * Getter para uma loja no parametro lojas
     * @param cod codigo da loja a procurar
     * @return loja caso exista
     * @throws LojaInexistenteException caso não exista
     */
    @Override public InterfaceLoja getLoja(String cod) throws LojaInexistenteException {
        return this.lojas.getLoja(cod);
    }

    /**
     * Adicionar loja
     * @param l loja
     */
    @Override public void addLoja(InterfaceLoja l) {
        this.lojas.setLoja(l.getCodigo(),l);
    }

    /**
     * Função responsável por dar parsing e ler o ficheiro de logs
     * @throws java.io.IOException caso o ficheiro não exista ou esteja corrompido
     */
    @Override public void readFile() throws java.io.IOException {
        this.historico=new Historico();
        BufferedReader bufferAll = new BufferedReader (new FileReader(Const.fileToRead));
        String buffer;
        Random r = new Random();
        String[] tokens = {};
        Point2D pos;
        while((buffer=bufferAll.readLine())!=null) {
           String [] idAndInfo = buffer.split(":",2);
           if (idAndInfo.length>1) {
               tokens = idAndInfo[1].split(",", 0);
               if (tokens[0].length() < 1 || tokens[0].charAt(0) == '<') continue;
           }
           switch (idAndInfo[0]) {
               case ("Utilizador") :
                    pos =new Point2D.Double(Double.parseDouble(tokens[2]),Double.parseDouble(tokens[3]));
                    InterfaceUtilizador u = new Utilizador(tokens[0],"Password",tokens[1],r.nextDouble()*10000,pos,new HashSet<>());
                    this.users.addUser(u);
               break;
               case ("Voluntario") :
                    pos =new Point2D.Double(Double.parseDouble(tokens[2]),Double.parseDouble(tokens[3]));
                    InterfaceEntregador v = new Voluntario(tokens[1],tokens[0],pos,"Password",Float.parseFloat(tokens[4]),r.nextBoolean(),(float)(Math.round((r.nextFloat()+3)*100)/100.0),(float)(Math.round(r.nextFloat()*1000)/100),r.nextInt(),new ArrayList<>(),new Encomenda(),new ArrayList<InterfaceEncomenda>());
                    this.entregadores.setEntregador(tokens[0],v);
               break;
               case ("Transportadora") :
                    pos = new Point2D.Double(Double.parseDouble(tokens[2]),Double.parseDouble(tokens[3]));
                    InterfaceTransportadora t = new Transportadora(tokens[1],tokens[0],pos,"Password",Float.parseFloat(tokens[5]),tokens[4],Double.parseDouble(tokens[6]),r.nextDouble()%5,r.nextBoolean(),(float)(Math.round((r.nextFloat()+20)*100)/100.0),(float)(Math.round(r.nextFloat()*1000)/100),r.nextInt(),r.nextInt(),new ArrayList<InterfaceEncomenda>(),new ArrayList<InterfaceEncomenda>());
                    this.entregadores.setEntregador(tokens[0],t);
               break;
               case ("Loja") :
                    pos = new Point2D.Double(Double.parseDouble(tokens[2]),Double.parseDouble(tokens[3]));
                    InterfaceLoja l = new Loja(tokens[0],tokens[1],pos,"Password",r.nextInt()%20,r.nextFloat(),new HashMap<>(),new HashMap<>(),new HashMap<>());
                    this.lojas.setLoja(tokens[0],l);
               break;
               case ("Encomenda") :
                   Random rand = new Random();
                   List<InterfaceLinhaEncomenda> lista =new ArrayList<>();
                   int i=4,size=tokens.length;
                   while (i+3<size) {

                       lista.add(new LinhaEncomenda(tokens[i],tokens[i+1],Double.parseDouble(tokens[i+2]),Double.parseDouble(tokens[i+3])));
                       i+=4;
                   }
                   InterfaceEncomenda e = new Encomenda(tokens[0],rand.nextBoolean(),Float.parseFloat(tokens[3]),tokens[2],tokens[1],lista, LocalDateTime.now().plusMinutes(r.nextLong()%60),LocalDateTime.now());
                   lojas.addEncomenda(tokens[2],e);
                   for (InterfaceLinhaEncomenda iLE : lista) {
                       iLE.setQuantidade(r.nextDouble()*1000);
                   }
                   lojas.addToStock(tokens[2],lista);
               break;
               case ("Aceite") :
                   InterfaceEncomenda iE = getEncomenda(tokens[0]);
                   this.lojas.removeNotReady(iE);
                   this.lojas.addPronta(iE);
               break;
               default:
                   break;
            }
       }
   }

    /**
     * Verifica se um encomenda de um utilizador está em espera ou pronta
     * @param id codigo da encomenda
     * @param user codigo do user
     * @return true se estiver em espera
     */
   @Override
   public boolean encomendaNotReady(String id,String user) {
        InterfaceEncomenda a = getEncomenda(id);
        return this.lojas.encomendaNotReady(id,a.getOrigem()) && a.getDestino().equals(user);
   }

    /**
     * Método que recebe varios codigos de produtos e quantidades e com isto forma linhas de encomenda indo a loja
     * @param loja codigo da loja onde procurar
     * @param l lista de
     * @return
     * @throws ProductNotAvailableException
     */
   @Override
   public List<InterfaceLinhaEncomenda> formaListaDeLinhasEncomenda(String loja, List<Map.Entry<String, Double>> l) throws ProductNotAvailableException {
        return lojas.formaListadeLinhasEncomenda(loja,l);
   }

    /**
     * Método que coloca uma encomenda
     * @param e encomenda
     * @param preco preço
     * @throws NotEnoughMoneyException caso o utilizador não tenha dinheiro
     * @throws LojaInexistenteException loja onde encomenda não existe
     */
   @Override
   public void encomenda(InterfaceEncomenda e, double preco) throws NotEnoughMoneyException, LojaInexistenteException {
        InterfaceLoja l = this.lojas.getLoja(e.getOrigem());
        int tam = l.getTamFila();
        if (tam==-1) tam= new Random().nextInt(9)+1;
        e.setDataEntrega(this.getHoras().plusMinutes((long)(tam*l.getTempoAtendimento())));
       this.lojas.addEncomenda(e.getOrigem(),e);
       this.users.pay(e.getDestino(),preco);
   }

    /**
     * Método que aceita os pedidos
     * @param enc encomenda a aceitar
     * @param trans transportadora que aceitou os pedidos
     */
   @Override
   public void aceitarPedido(InterfaceEncomenda enc,String trans) {
        enc.setDataEntrega(LocalDateTime.now().plusYears(10));
        this.entregadores.alteraPedido(enc,trans,"a");
        this.entregadores.addEncomenda(trans,enc);
        this.entregadores.addMessage(trans,"A sua proposta para a encomenda "+enc.getCodEncomenda()+" foi aceite!");
        this.users.alteraPedido(enc,trans,"a");
        if (!this.entregadores.hasRoom(trans)){
            this.entregadores.alteraTodosPedidosIf(trans,"s","p");
            this.users.alteraTodosPedidosIf(trans,"s","p");
       }
        this.entregadores.rejeitaPedidos(enc.getCodEncomenda());
        this.users.rejeitaPedidos(enc.getCodEncomenda());
   }

    /**
     * Método que procura uma encomenda com código id
     * @param id código da encomenda
     * @return encomenda
     */
   @Override public InterfaceEncomenda getEncomenda(String id) {
        InterfaceEncomenda r;
        for (InterfaceLoja l : this.lojas.getLojas().values()) {
            if((r=l.getEncomenda(id))!=null)
                    return r;
        }
        for (InterfaceEntregador e : this.entregadores.getEntregadores().values()) {
            if ((r=e.getEncomenda(id))!=null)
                return r;
        }
        return null;
   }

    /**
     * Método que calcula a distância entre 3 pontos
     * @param p1 ponto de origem (loja)
     * @param p2 ponto de meio (entregador)
     * @param p3 ponto de destino (user)
     * @return distância total
     */
   @Override public double calculaDistTotal(Point2D p1,Point2D p2,Point2D p3) {
       double d = p1.distance(p2);
       d += p2.distance(p3);
       return d*1000;
   }

    /**
     * Getter para encomendas disponíveis a um entregador
     * @param cod código de entregador onde procurar
     * @return lista com as encomendas
     * @throws EntregadorInexistenteException caso não exista
     * @throws UtilizadorInexistenteException caso não exista
     */
    @Override
    public List<InterfaceEncomenda> getEncomendasDisp(String cod) throws EntregadorInexistenteException, UtilizadorInexistenteException {
        List<InterfaceEncomenda> r = new ArrayList<>();
        InterfaceEntregador cod1 =  this.getEntregador(cod);
        for (InterfaceLoja l : this.lojas.getLojas().values()){
            for (InterfaceEncomenda e : l.getPedidos().values()){
                if ((isNear(cod1,l,this.getUser(e.getDestino()))) && !(!cod1.getMedical() && e.getMedical())) {
                    r.add(e);
                }
            }
        }
        return r;
    }

    /**
     * Método que verifica se uma loja e um utilizador estão dentro do raio de um entregador
     * @param cod entregador
     * @param loja loja
     * @param uti utilizador
     * @return true caso esteja dentro do raio, false otherwise
     */
    @Override
    public boolean isNear (InterfaceEntregador cod,InterfaceLoja loja,InterfaceUtilizador uti){
        return ((cod.getPosicao().distance(loja.getPosicao()) < cod.getRaio())&&(cod.getPosicao().distance(uti.getPosicao()) < cod.getRaio() ));
    }

    /**
     * Método que classifica entregador
     * @param ent codigo de entregador a classificar
     * @param user codigo de utilizador que quer classificar
     * @param clas classifcação que quer dar
     * @return 0 caso ainda não tivesse classificado
     */
    @Override
    public int classificaEnt(String ent,String user,float clas){
        int i=this.historico.checkClass(ent,user);
        if (i==0) {
            this.historico.changeStat(ent, user);
            this.entregadores.classificaUser(ent, clas);
        }
        return i;
    }

    /**
     * Getter para a distância total
     * @param idEntregador entregador
     * @param idEnc encomenda
     * @return distancia total de entregador a encomenda
     * @throws EntregadorInexistenteException caso não exista
     * @throws LojaInexistenteException caso não exista
     * @throws UtilizadorInexistenteException caso não exista
     */
    @Override
    public double getDistTotal(String idEntregador,String idEnc) throws EntregadorInexistenteException, LojaInexistenteException, UtilizadorInexistenteException {
        InterfaceEntregador e = getEntregador(idEntregador);
        InterfaceEncomenda enc = getEncomenda(idEnc);
        return calculaDistTotal(lojas.getLoja(enc.getOrigem()).getPosicao(),e.getPosicao(),users.getUser(enc.getDestino()).getPosicao());
    }

    /**
     * Método que dá reset as mensagens de um entregador ou de utilizador
     * @param cod codigo
     */
    @Override public void resetMessages(String cod) {
        if (cod.contains("u")) {
            this.users.resetMessages(cod);
        }
        if ((cod.contains("t")) || (cod.contains("v"))) {
            this.entregadores.resetMessages(cod);
        }

    }

    /**
     * Mudar a hora
     * @param horas horas
     * @param minutos minutos
     */
    @Override
    public void maquinaTempo(int horas, int minutos) {
        this.horas=this.horas.plusMinutes(horas*60+minutos);
    }

    /**
     * Atualizar estado consoante o tempo
     */
    @Override
    public void atualizaEstado() {
        this.users.atualizaEstado(this.lojas.atualizaEstado(getHoras()));
        this.atualizaHistorico(this.entregadores.atualizaEstado(getHoras()));
        atualizaPedidos(this.entregadores.getAllFree());
        this.users.atualizaEstado(this.entregadores.checkEvent(getHoras()));
    }

    /**
     * Atualizar pedidos
     * @param trans trasnportadora
     */
    @Override
    public void atualizaPedidos(List<String> trans){
        this.users.atualizaPedidos(trans);
    }

    /**
     * Getter para o stock de uma loja
     * @param l codigo de loja
     * @return lista de stock
     * @throws NullPointerException caso não exista stock
     */
    @Override
    public List<InterfaceLinhaEncomenda> getStock(String l) throws NullPointerException {
        return this.lojas.getStock(l);
    }

    /**
     * Método que gera código para o user
     * @return codigo gerado
     */
    @Override
    public String gerarCodUser() {
        Random rand = new Random();
        String cod = "u";
        boolean b=true;
        while (b){
            int num = rand.nextInt(1000);
            cod=cod.concat(String.valueOf(num));
            b=users.getUsers().containsKey(cod) ;
        }
        return cod;
    }

    /**
     * Método que gera código para a loja
     * @return codigo gerado
     */
    @Override
    public String gerarCodLoja() {
        Random rand = new Random();
        String cod = "l";
        boolean b=true;
        while (b){
            int num = rand.nextInt(1000);
            cod=cod.concat(String.valueOf(num));
            b=lojas.getLojas().containsKey(cod) ;
        }
        return cod;
    }

    /**
     * Método que gera código para o voluntario
     * @return codigo gerado
     */
    @Override
    public String gerarCodVol() {
        Random rand = new Random();
        String cod = "v";
        boolean b=true;
        while (b){
            int num = rand.nextInt(1000);
            cod=cod.concat(String.valueOf(num));
            b=entregadores.getEntregadores().containsKey(cod) ;
        }
        return cod;
    }

    /**
     * Método que gera código para a transportadora
     * @return codigo gerado
     */
    @Override
    public String gerarCodTrans() {
        Random rand = new Random();
        String cod = "t";
        boolean b=true;
        while (b){
            int num = rand.nextInt(1000);
            cod=cod.concat(String.valueOf(num));
            b=entregadores.getEntregadores().containsKey(cod) ;
        }
        return cod;
    }

    /**
     * Método que gera código para a encomenda
     * @return codigo gerado
     */
    @Override
    public String gerarCodEnc() {
        Random rand = new Random();
        String cod = "e";
        boolean b=true;
        while (b){
            int num = rand.nextInt(9999);
            cod=cod.concat(String.valueOf(num));
            for (InterfaceLoja i : this.lojas.getLojas().values()){
                b=i.getPedidos().containsKey(cod)||i.getPedidosEspera().containsKey(cod);
                if (b) break;
            }
        }
        return cod;
    }

    /**
     * Método que realiza uma encomenda
     * @param cod codigo de entregador
     * @throws EntregadorInexistenteException caso não exista
     * @throws LojaInexistenteException caso não exista
     * @throws UtilizadorInexistenteException caso não exista
     */
    @Override
    public void fazerEncomenda(String cod) throws EntregadorInexistenteException , LojaInexistenteException, UtilizadorInexistenteException{
        double sec=0;
        if (cod.contains("t")){
            InterfaceTransportadora trans = (InterfaceTransportadora) this.getEntregador(cod);
            for (InterfaceEncomenda e : trans.getEncomendaAtual()){
                sec += calculaDistTotal(trans.getPosicao(),lojas.getLoja(e.getOrigem()).getPosicao(),users.getUser(e.getDestino()).getPosicao()) / trans.getVelocidade();
                System.out.println("Segundos: "+sec);
                LocalDateTime dataI = getHoras();
                LocalDateTime dataF = getHoras().plusSeconds((long)sec);
                e.setDataInicio(dataI);
                e.setDataEntrega(dataF);
                this.entregadores.atualizaAtual(cod,e);
                this.users.addMessageToUser(e.getDestino(),"A sua encomenda de código "+e.getCodEncomenda()+" está em movimento!");
            }
            this.entregadores.setAEntregar(cod,true);
            this.entregadores.alteraTodosPedidosIf(cod,"s","p");
            this.users.alteraTodosPedidosIf(cod,"s","p");
        }

        else {
            InterfaceVoluntario vol = (InterfaceVoluntario) this.getEntregador(cod);
            InterfaceEncomenda e = vol.getEncomenda();
            sec += calculaDistTotal(lojas.getLoja(e.getOrigem()).getPosicao(),vol.getPosicao(),users.getUser(e.getDestino()).getPosicao()) / vol.getVelocidade();
            e.setDataInicio(getHoras());
            e.setDataEntrega(getHoras().plusSeconds((long)sec));
            this.entregadores.atualizaAtual(cod,e);
            this.entregadores.setAEntregar(cod,true);
            this.users.addMessageToUser(e.getDestino(),"A sua encomenda de código "+e.getCodEncomenda()+" está em movimento!");

        }
    }

    /**
     * Fazer pedido a um utilizador por parte de trasnportadora
     * @param enc codigo de encomenda
     * @param trans transportadora responsável pela encomenda
     * @return lista de booleans para verificar todas as caracteristicas de encomenda (distancia, medical,espaço)
     * @throws EntregadorInexistenteException caso não exista
     * @throws LojaInexistenteException caso não exista
     * @throws UtilizadorInexistenteException caso não exista
     */
    @Override
    public List<Boolean> fazerPedido(InterfaceEncomenda enc,String trans) throws EntregadorInexistenteException, LojaInexistenteException, UtilizadorInexistenteException {
        InterfaceEntregador t = this.entregadores.getEntregador(trans);
        List<Boolean> r = new ArrayList<>();
        r.add(isNear(t,this.getLoja(enc.getOrigem()),this.getUser(enc.getDestino())));
        r.add(!(!t.getMedical() && enc.getMedical()));
        r.add(!this.encomendaNotReady(enc.getCodEncomenda(),enc.getDestino()));

        if (r.get(0) && r.get(1) && r.get(2)){
            this.entregadores.addPedido(enc,trans);
            this.users.addPedido(enc,trans);
            this.users.addMessageToUser(enc.getDestino(),"Tem uma Proposta para encomenda de código "+enc.getCodEncomenda()+" da Transportadora "+trans);
        }
        return r;
    }

    /**
     * Adicionar uma encomenda
     * @param enc encomenda
     * @param vol codigo de voluntario
     */
    @Override
    public void addEncomendaVol (InterfaceEncomenda enc,String vol){
        this.entregadores.addEncomenda(vol,enc);
    }

    /**
     * Método para verificar se uma encomenda está a entregar
     * @param cod codigo de encomenda a procurar
     * @return true se estiver a entregar
     */
    @Override
    public boolean isAEntregar(String cod){
        return this.entregadores.isAEntregar(cod);
    }

    /**
     * Verificar o estado do pedido
     * @param enc codigo de encomenda
     * @param trans codigo de transportadora
     * @param user codigo de utilizador
     * @return codigo do estado do pedido
     */
    @Override
    public String checkStatPedido(String enc,String trans,String user){
        return this.users.checkStatPedido(enc,trans,user);
    }

    /**
     * Verifica se um entregador tem espaço para levar uma encomenda
     * @param enc codigo de encomenda
     * @param user codigo de utilizador
     * @return true se tiver espaço
     */
    @Override
    public boolean isFree(String enc,String user){
        return this.users.isFree(user,enc);
    }

    /**
     * Verifica se existe pedido
     * @param trans codigo de transportadora
     * @param enc codigo de encomenda
     * @return true se existir um pedido
     */
    @Override
    public boolean existePedido(String trans,String enc){
        return this.entregadores.existePedido(trans,enc);
    }

    /**
     * Getter para o historico de um entregador
     * @param cod codigo de entregador
     * @return lista que representa o historico
     */
    @Override
    public List<TriploHist> getHistorico(String cod){
        if (cod.contains("v")||cod.contains("t")) return this.historico.getEnt(cod);
        else if (cod.contains("u")) return this.historico.getUser(cod);
        else return this.historico.getLoja(cod);
    }

    /**
     * Getter para o historico por data
     * @param after tempo final do periodo
     * @param before tempo inicial do periodo
     * @param l historico
     * @return historico apenas no periodo dado
     */
    @Override
    public List<TriploHist> getHistoricoByDate(LocalDateTime after,LocalDateTime before,List<TriploHist> l){
        Comparator<TriploHist> c = Comparator.comparing((TriploHist triploHist) -> triploHist.getEnc().getDataInicio()).thenComparing(triploHist -> triploHist.getEnc().getDataEntrega());
        return l.stream().filter(i->i.getEnc().getDataInicio().isAfter(after)&&i.getEnc().getDataEntrega().isBefore(before)).sorted(c).collect(Collectors.toList());
    }

    /**
     * Getter para o historico por entregadora
     * @param ent codigo de entregador
     * @param l hsitorico
     * @return historico de encomednas realizadas por entregador
     */
    @Override
    public List<TriploHist> getHistoricoByEnt(String ent,List<TriploHist> l){
        Comparator<TriploHist> c = Comparator.comparing(TriploHist::getEnt);
        return l.stream().filter(i -> i.getEnt().equals(ent)).sorted(c).collect(Collectors.toList());
    }

    /**
     * Atualizar historico
     * @param m mapa de codigo de utilizador com as encomendas entregues a este
     */
    @Override
    public void atualizaHistorico(Map<String,List<InterfaceEncomenda>> m){
        for(Map.Entry<String,List<InterfaceEncomenda>> i : m.entrySet()){
            for (InterfaceEncomenda j : i.getValue()){
                this.users.addMessageToUser(j.getDestino(), "A sua Encomenda de id "+j.getCodEncomenda()+" foi entregue");
                this.historico.add(i.getKey(),j);
            }
        }
    }

    /**
     * Historico por preço
     * @param l historico
     * @return historico por preço
     * @throws EntregadorInexistenteException caso não exista
     * @throws LojaInexistenteException caso não exista
     * @throws UtilizadorInexistenteException caso não exista
     */
    @Override
    public List<Map.Entry<Double,TriploPedido>> getByPreco (List<TriploPedido> l) throws EntregadorInexistenteException, LojaInexistenteException, UtilizadorInexistenteException {
        List<Map.Entry<Double,TriploPedido>> r = new ArrayList<>();
        InterfaceTransportadora ent;
        double preco=0;
        for (TriploPedido i : l){
            ent = (InterfaceTransportadora) this.getEntregador(i.getTrans());
            preco = ent.getCustoKm()*getDistTotal(ent.getCodigo(),i.getEnc().getCodEncomenda());
            r.add(new AbstractMap.SimpleEntry<>(preco,i));
        }
        return r;
    }

    /**
     * Ver o total faturado
     * @param trans transportadora
     * @param after tempo de fim do periodo
     * @param before tempo de inicio do periodo
     * @return total faturado nesse periodo
     * @throws EntregadorInexistenteException caso não exista
     * @throws LojaInexistenteException caso não exista
     * @throws UtilizadorInexistenteException caso não exista
     */
    @Override
    public double totalFaturado(String trans,LocalDateTime after, LocalDateTime before) throws EntregadorInexistenteException, LojaInexistenteException, UtilizadorInexistenteException {
        List<TriploHist> aux = this.getHistoricoByDate(after,before,this.getHistorico(trans));
        double r=0;
        InterfaceTransportadora ent;
        for (TriploHist i : aux){
            ent = (InterfaceTransportadora) this.getEntregador(i.getEnt());
            r+=ent.getCustoKm()*getDistTotal(ent.getCodigo(),i.getEnc().getCodEncomenda());
        }
        return r;
    }

    /**
     * Top 10 dos utilizadores
     * @return lista de utilizador com encomendas entregues a ele
     */
    @Override
    public List<Map.Entry<String,Integer>> top10Users(){
        Map<String,Integer> r = new HashMap<>();
        String usr;
        for (TriploHist i : this.historico.getHistorico()){
            usr=i.getEnc().getDestino();
            if (r.containsKey(usr)){
                Integer aux = (r.get(usr))+1;
                r.put(usr,aux);
            }
            else r.put(usr,1);
        }
        SortedSet<Map.Entry<String,Integer>> rTree = new TreeSet<>(
                (t1, t2) -> Integer.compare(t2.getValue(), t1.getValue())
        );
        rTree.addAll(r.entrySet());
        Iterator<Map.Entry<String,Integer>> itr = rTree.iterator();
        List<Map.Entry<String,Integer>> rList = new ArrayList<>();
        int n=0;
        while (itr.hasNext()&&n<10){
            rList.add((itr.next()));
            n++;
        }
        return rList;    }

    /**
     * top 10 trnasportadoras encomendas realizadas
     * @return lista de codigos de encomenda com distancia realizada
     * @throws EntregadorInexistenteException caso não exista
     * @throws LojaInexistenteException caso não exista
     * @throws UtilizadorInexistenteException caso não exista
     */
    @Override
    public List<Map.Entry<String,Double>> top10Trans() throws EntregadorInexistenteException, LojaInexistenteException, UtilizadorInexistenteException {
        Map<String,Double> r = new HashMap<>();
        String trans;
        for (TriploHist i : this.historico.getHistorico()){
            trans=i.getEnt();
            if (trans.contains("t")){
                Double kms = this.getDistTotal(trans,i.getEnc().getCodEncomenda());
                if (r.containsKey(trans)){
                    Double aux = (r.get(trans))+kms;
                    r.put(trans,aux);
                }
                else r.put(trans,kms);
            }
        }
        SortedSet<Map.Entry<String,Double>> rTree = new TreeSet<>(
                (t1, t2) -> Double.compare(t2.getValue(), t1.getValue())
        );
        rTree.addAll(r.entrySet());
        Iterator<Map.Entry<String,Double>> itr = rTree.iterator();
        List<Map.Entry<String,Double>> rList = new ArrayList<>();
        int n=0;
        while (itr.hasNext()&&n<10){
            rList.add((itr.next()));
            n++;
        }
        return rList;
    }

    /**
     * Mudar o preço
     * @param loja codigo de loja
     * @param cod codigo de encomenda
     * @param preco preço
     */
    @Override
    public void mudarPreco(String loja, String cod, double preco){
        this.lojas.mudarPreco(loja,cod,preco);
    }

    /**
     * Mudar quantidade
     * @param loja codigo de loja
     * @param cod codigo de encomenda
     * @param qnt quantidade
     */
    @Override
    public void mudarQuantidade(String loja, String cod, double qnt){
        this.lojas.mudarQuantidade(loja,cod,qnt);
    }

    /**
     * Adicionar ao stock
     * @param loja codigo de loja onde adicionar
     * @param l linha de stock a adicionar
     */
    @Override
    public void addToStock(String loja,InterfaceLinhaEncomenda l){
        this.lojas.addSToStock(loja,l);
    }

    /**
     * Remover de stock um produto
     * @param loja loja
     * @param cod codigo de produto a remover
     */
    @Override
    public void removeFromStock(String loja, String cod){
        this.lojas.removeFromStock(loja,cod);
    }

    /**
     * Tempo que falta para a entrega de uma encomenda
     * @param ent codigo de entregador
     * @param enc codigo de encomenda
     * @return string com o tempo que falta para uma entrega
     */
    @Override
    public String timeLeft(String ent,String enc){
        return this.entregadores.timeLeft(ent,enc,this.getHoras());
    }
}
