package MVC.Model;
/**
 * Write a description of class Programa here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

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
    
    public Data() {
     this.users=new Utilizadores();
     this.lojas=new Lojas();
     this.entregadores=new Entregadores();
     this.horas=LocalDateTime.now();
    }

    @Override
    public LocalDateTime getHoras() {
        return this.horas;
    }

    @Override
    public void setHoras(LocalDateTime d) {
        this.horas=d;
    }

    @Override public InterfaceUtilizador getUser(String cod) throws UtilizadorInexistenteException {
        return users.getUser(cod);
    }

    @Override public void addUser(InterfaceUtilizador u) {
        this.users.addUser(u);
    }

    @Override public InterfaceEntregador getEntregador(String cod) throws EntregadorInexistenteException {
        return this.entregadores.getEntregador(cod);
    }

    @Override public void addEntregador(InterfaceEntregador e) {
        this.entregadores.setEntregador(e.getCodigo(),e.clone());
    }

    @Override public InterfaceLoja getLoja(String cod) throws LojaInexistenteException {
        return this.lojas.getLoja(cod);
    }

    @Override public void addLoja(InterfaceLoja l) {
        this.lojas.setLoja(l.getCodigo(),l);
    }

    @Override public void readFile() throws java.io.IOException {
        this.historico=new Historico();
        BufferedReader bufferAll = new BufferedReader (new FileReader(Const.fileToRead));
        String buffer;
        Random r = new Random();
        Point2D pos;
        while((buffer=bufferAll.readLine())!=null) {
           String [] idAndInfo = buffer.split(":",2);
           String[] tokens=idAndInfo[1].split(",",0);
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
            }
       }
   }

   @Override
   public boolean encomendaNotReady(String id,String user) {
        InterfaceEncomenda a = getEncomenda(id);
        return this.lojas.encomendaNotReady(id,a.getOrigem()) && a.getDestino().equals(user);
   }

   @Override
   public List<InterfaceLinhaEncomenda> formaListaDeLinhasEncomenda(String loja, List<Map.Entry<String, Double>> l) throws ProductNotAvailableException {
        return lojas.formaListadeLinhasEncomenda(loja,l);
   }

   @Override
   public void encomenda(InterfaceEncomenda e, double preco) throws NotEnoughMoneyException, LojaInexistenteException {
        InterfaceLoja l = this.lojas.getLoja(e.getOrigem());
        int tam = l.getTamFila();
        if (tam==-1) tam= new Random().nextInt(9)+1;
        e.setDataEntrega(this.getHoras().plusMinutes((long)(tam*l.getTempoAtendimento())));
       this.lojas.addEncomenda(e.getOrigem(),e);
       this.users.pay(e.getDestino(),preco);
   }

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
   }

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

   @Override public double calculaDistTotal(Point2D p1,Point2D p2,Point2D p3) {
       double d = p1.distance(p2);
       d += p2.distance(p3);
       return d;
   }

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

    @Override
    public boolean isNear (InterfaceEntregador cod,InterfaceLoja loja,InterfaceUtilizador uti){
        return ((cod.getPosicao().distance(loja.getPosicao()) < cod.getRaio())&&(cod.getPosicao().distance(uti.getPosicao()) < cod.getRaio() ));
    }

    @Override
    public int classificaEnt(String ent,String user,float clas){
        int i=this.historico.checkClass(ent,user);
        if (i==0) {
            this.historico.changeStat(ent, user);
            this.entregadores.classificaUser(ent, clas);
        }
        return i;
    }

    @Override public double getDistTotal(String idEntregador,String idEnc) throws EntregadorInexistenteException, LojaInexistenteException, UtilizadorInexistenteException {
        InterfaceEntregador e = getEntregador(idEntregador);
        InterfaceEncomenda enc = getEncomenda(idEnc);
        if (enc==null) System.out.println("olaaa");
        return calculaDistTotal(lojas.getLoja(enc.getOrigem()).getPosicao(),e.getPosicao(),users.getUser(enc.getDestino()).getPosicao());
    }

    @Override public void resetMessages(String cod) {
        if (cod.contains("u")) {
            this.users.resetMessages(cod);
        }
        if ((cod.contains("t")) || (cod.contains("v"))) {
            this.entregadores.resetMessages(cod);
        }

    }

    @Override
    public void maquinaTempo(int horas, int minutos) {
        this.horas=this.horas.plusMinutes(horas*60+minutos);
    }

    @Override
    public void atualizaEstado() {
        this.users.atualizaEstado(this.lojas.atualizaEstado(getHoras()));
        this.atualizaHistorico(this.entregadores.atualizaEstado(getHoras()));
        atualizaPedidos(this.entregadores.getAllFree());
        this.users.atualizaEstado(this.entregadores.checkEvent(getHoras()));
    }

    @Override
    public void atualizaPedidos(List<String> trans){
        this.users.atualizaPedidos(trans);
    }

    @Override
    public List<InterfaceLinhaEncomenda> getStock(String l) throws NullPointerException {
        return this.lojas.getStock(l);
    }

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
            this.users.addMessageToUser(enc.getDestino(),"Tem uma Proposta para encomenda de código "+enc.getCodEncomenda()+"da Transportadora "+trans);
        }
        return r;
    }

    @Override
    public void addEncomendaVol (InterfaceEncomenda enc,String vol){
        this.entregadores.addEncomenda(vol,enc);
    }

    @Override
    public boolean isAEntregar(String cod){
        return this.entregadores.isAEntregar(cod);
    }

    @Override
    public String checkStatPedido(String enc,String trans,String user){
        return this.users.checkStatPedido(enc,trans,user);
    }

    @Override
    public boolean existePedido(String trans,String enc){
        return this.entregadores.existePedido(trans,enc);
    }

    @Override
    public List<TriploHist> getHistorico(String cod){
        if (cod.contains("v")||cod.contains("t")) return this.historico.getEnt(cod);
        else if (cod.contains("u")) return this.historico.getUser(cod);
        else return this.historico.getLoja(cod);
    }

    @Override
    public List<TriploHist> getHistoricoByDate(LocalDateTime after,LocalDateTime before,List<TriploHist> l){
        Comparator<TriploHist> c = Comparator.comparing((TriploHist triploHist) -> triploHist.getEnc().getDataInicio()).thenComparing(triploHist -> triploHist.getEnc().getDataEntrega());
        return l.stream().filter(i->i.getEnc().getDataInicio().isAfter(after)&&i.getEnc().getDataEntrega().isBefore(before)).sorted(c).collect(Collectors.toList());
    }

    @Override
    public List<TriploHist> getHistoricoByEnt(String ent,List<TriploHist> l){
        Comparator<TriploHist> c = Comparator.comparing(TriploHist::getEnt);
        return l.stream().filter(i -> i.getEnt().equals(ent)).sorted(c).collect(Collectors.toList());
    }

    @Override
    public void atualizaHistorico(Map<String,List<InterfaceEncomenda>> m){
        for(Map.Entry<String,List<InterfaceEncomenda>> i : m.entrySet()){
            for (InterfaceEncomenda j : i.getValue()){
                this.users.addMessageToUser(j.getDestino(), "A sua Encomenda de id "+j.getCodEncomenda()+" foi entregue");
                this.historico.add(i.getKey(),j);
            }
        }
    }

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

    @Override
    public void mudarPreco(String loja, String cod, double preco){
        this.lojas.mudarPreco(loja,cod,preco);
    }

    @Override
    public void mudarQuantidade(String loja, String cod, double qnt){
        this.lojas.mudarQuantidade(loja,cod,qnt);
    }

    @Override
    public void addToStock(String loja,InterfaceLinhaEncomenda l){
        this.lojas.addSToStock(loja,l);
    }

    @Override
    public void removeFromStock(String loja, String cod){
        this.lojas.removeFromStock(loja,cod);
    }
}
