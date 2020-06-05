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
                    InterfaceUtilizador u = new Utilizador(tokens[0],"Password",tokens[1],r.nextDouble()*10000,pos,new HashSet<>(),new ArrayList<>());
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

    @Override public boolean encomendaACaminho(String id,String user) {
        return this.entregadores.encomendaACaminho(id,user);
   }

   @Override
   public boolean encomendaNotReady(String id,String user) {
        InterfaceEncomenda a = getEncomenda(id);
        return this.lojas.encomendaNotReady(id,a.getOrigem()) && a.getDestino().equals(user);
   }

   @Override public String voluntarioAvailable(String enc) throws UtilizadorInexistenteException, LojaInexistenteException {
        String r="n/a";
        InterfaceVoluntario v = new Voluntario();
        InterfaceEncomenda encomenda=getEncomenda(enc);
        InterfaceLoja l=lojas.getLoja(encomenda.getOrigem());
        double tempoMin=-1,tempoAux;
        for (InterfaceEntregador e : this.entregadores.getEntregadores().values()) {
            if (e.getClass().equals(v.getClass()) && e.hasRoomAndMed(getEncomenda(enc).getMedical())) {
                tempoAux = e.getVelocidade() * calculaDistTotal(e.getPosicao(), l.getPosicao(), users.getUser(encomenda.getDestino()).getPosicao())+l.getTamFila()*l.getTempoAtendimento();
                if (tempoMin == -1 || tempoAux < tempoMin) {
                    tempoMin = tempoAux;
                    r = e.getCodigo();
                }
            }
        }
        return r;
   }

   @Override public void askVoluntario(String idVoluntario,String idEnc) {
        this.entregadores.addPedidoVoluntario(idVoluntario,idEnc);
   }

   @Override
   public List<InterfaceLinhaEncomenda> formaListaDeLinhasEncomenda(String loja, List<Map.Entry<String, Double>> l) throws ProductNotAvailableException {
        return lojas.formaListadeLinhasEncomenda(loja,l);
   }

   @Override
   public void encomenda(InterfaceEncomenda e, double preco) throws NotEnoughMoneyException {
       this.lojas.addEncomenda(e.getOrigem(),e);
       this.users.pay(e.getDestino(),preco);
   }



   @Override public void aceitar(String entrega,String enc,double time) {
        InterfaceEncomenda e =getEncomenda(enc);
        //e.setDataEntrega(LocalDateTime.now().plusMinutes((long)time));
        this.entregadores.addEncomenda(entrega,e);
        this.lojas.removeReady(e.getOrigem(),enc);
        this.users.addMessageToUser(e.getDestino(),"O entregador "+entrega+" aceitou o seu pedido");
   }

   @Override
   public void aceitarPedido(InterfaceEncomenda enc,String trans) {
        this.entregadores.alteraPedido(enc,trans,"a");
        this.users.alteraPedido(enc,trans,"a");
   }

   @Override
   public InterfaceEncomenda getEncomendaPassado(String id) {
        InterfaceEncomenda r=this.entregadores.getEncomendaPassado(id);
        return r;
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

   @Override public Set<String[]> getEntregadoresDisp(String id) throws UtilizadorInexistenteException, LojaInexistenteException {
       Set<String[]> setOpcoes=new HashSet<>();
       int r;
       double preco;
       double tempoEst;
       double timeWaiting;
       InterfaceEncomenda en =getEncomenda(id);
       InterfaceLoja l =this.lojas.getLoja(en.getOrigem());
       r=l.getTamFila();
       timeWaiting=r*l.getTempoAtendimento();
       for (InterfaceEntregador e : this.entregadores.getEntregadores().values()) {
           if (e.hasRoomAndMed(en.getMedical())) {
               double d=calculaDistTotal(e.getPosicao(),l.getPosicao(),users.getUser(en.getDestino()).getPosicao());
               String[] info = new String[5];
               preco=d*((InterfaceTransportadora)e).getCustoKm()+en.getPeso()*((InterfaceTransportadora)e).getCustoKg();
               tempoEst=d/e.getVelocidade()+timeWaiting;
               info[0]=e.getCodigo();
               info[1]=e.getNome();
               info[2]=Float.toString(e.getClassificacao());
               info[3]=Double.toString(Math.round(preco*100)/100.0);
               info[4]=Double.toString(Math.round(tempoEst*100)/100.0);
               setOpcoes.add(info);
           }
       }
       return setOpcoes;
    }

    @Override
    public List<InterfaceEncomenda> getEncomendasDisp(String trans){
        List<InterfaceEncomenda> r = new ArrayList<>();
        for (InterfaceLoja l : this.lojas.getLojas().values()){
            r.addAll(l.getPedidos().values());
        }
        return r;
    }

    @Override public void classifica(Set<Map.Entry<Boolean,String>> encomendasID,String eID,String codUser,float c) throws UtilizadorInexistenteException {
        Map.Entry<Boolean,String> encomendaAclassificar = new AbstractMap.SimpleEntry<>(false,eID);
        encomendasID.remove(encomendaAclassificar);
        encomendasID.add(new AbstractMap.SimpleEntry<>(true,eID));
        InterfaceUtilizador atualizado = this.users.getUser(codUser);
        atualizado.setPedidosEntregues(encomendasID);
        users.addUser(atualizado);
        InterfaceEncomenda e = getEncomenda(eID);
        entregadores.classifica(e,c);
    }

    //Menu de Voluntario

    @Override public List<String> getVoluntarioRequests(String cod) throws EntregadorInexistenteException, UtilizadorInexistenteException, LojaInexistenteException {
        List<String> ls = new ArrayList<>();
        InterfaceVoluntario v =(InterfaceVoluntario) this.entregadores.getEntregador(cod);
        List<String> pedidoIDs =v.getPedidos();
        for (String s : pedidoIDs) {
            InterfaceEncomenda e = getEncomenda(s);
            ls.add("ID de Common.Encomenda: " + e.getCodEncomenda() + "  Peso: " + e.getPeso() + "  Distância a percorrer: " + calculaDistTotal(this.lojas.getLoja(e.getOrigem()).getPosicao(),v.getPosicao(),this.getUser(e.getDestino()).getPosicao()) +"\n");
        }
        return ls;
    }

    @Override public double getTempoEsperado(String idEntregador,String idEnc) throws EntregadorInexistenteException, LojaInexistenteException, UtilizadorInexistenteException {
        InterfaceEntregador e = getEntregador(idEntregador);
        InterfaceEncomenda enc = getEncomenda(idEnc);
        if (enc==null) System.out.println("olaaa");
        return calculaDistTotal(lojas.getLoja(enc.getOrigem()).getPosicao(),e.getPosicao(),users.getUser(enc.getDestino()).getPosicao()) / e.getVelocidade();
    }

    @Override public double getDistTotal(String idEntregador,String idEnc) throws EntregadorInexistenteException, LojaInexistenteException, UtilizadorInexistenteException {
        InterfaceEntregador e = getEntregador(idEntregador);
        InterfaceEncomenda enc = getEncomenda(idEnc);
        if (enc==null) System.out.println("olaaa");
        return calculaDistTotal(lojas.getLoja(enc.getOrigem()).getPosicao(),e.getPosicao(),users.getUser(enc.getDestino()).getPosicao());
    }

    @Override public void denyAll(String cod) throws EntregadorInexistenteException {
        for (String s : ((InterfaceVoluntario)this.entregadores.getEntregador(cod)).getPedidos()) {
            InterfaceEncomenda e = getEncomenda(s);
            this.users.addMessageToUser(e.getDestino(),"Common.Encomenda "+s+" não foi aceite pelo InterfaceVoluntario "+cod);
        }
        this.entregadores.denyAll(cod);
    }

    @Override public void resetMessages(String cod) {
        this.users.resetMessages(cod);
    }

    @Override
    public void maquinaTempo(int horas, int minutos) {
        this.horas=this.horas.plusMinutes(horas*60+minutos);
    }

    @Override
    public void atualizaEstado() {
        List<InterfaceEncomenda> r;
        Map<String,List<String>> m;
        m=this.lojas.atualizaEstado(getHoras());
        r=this.entregadores.atualizaEstado(getHoras());
        this.users.atualizaEstado(r,m);
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
                sec += calculaDistTotal(lojas.getLoja(e.getOrigem()).getPosicao(),trans.getPosicao(),users.getUser(e.getDestino()).getPosicao()) / trans.getVelocidade();
                e.setDataInicio(LocalDateTime.now());
                e.setDataEntrega(LocalDateTime.now().plusSeconds((long)sec));
                this.entregadores.addToHistorico(cod,e);
                this.users.addEntregue(e.getDestino(),e.getCodEncomenda());
            }
            this.entregadores.clearAtual(cod);
        }
        else {
            InterfaceVoluntario vol = (InterfaceVoluntario) this.getEntregador(cod);
            InterfaceEncomenda e = vol.getEncomenda();
            sec += calculaDistTotal(lojas.getLoja(e.getOrigem()).getPosicao(),vol.getPosicao(),users.getUser(e.getDestino()).getPosicao()) / vol.getVelocidade();
            e.setDataInicio(LocalDateTime.now());
            e.setDataEntrega(LocalDateTime.now().plusSeconds((long)sec));
            this.entregadores.addToHistorico(cod,e);
            this.users.addEntregue(e.getDestino(),e.getCodEncomenda());
        }
    }

    @Override
    public void fazerPedido(InterfaceEncomenda enc,String trans){
        this.entregadores.addPedido(enc,trans);
        this.users.addPedido(enc,trans);
    }
}
