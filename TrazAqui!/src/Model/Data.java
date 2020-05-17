
/**
 * Write a description of class Programa here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

import java.time.LocalDateTime;
import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.awt.geom.Point2D;

public class Data implements InterfaceData
{
    InterfaceUtilizadores users;
    InterfaceLojas lojas;
    InterfaceEntregadores entregadores;
    InterfaceEncomendasAceites aceites;
    
    public Data() {
     this.users=new Utilizadores();
     this.lojas=new Lojas();
     this.entregadores=new Entregadores();
     this.aceites=new EncomendasAceites();
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
        BufferedReader bufferAll = new BufferedReader (new FileReader("src/logs.txt"));
        String buffer;
        Random r = new Random();
        Point2D pos;
        while((buffer=bufferAll.readLine())!=null) {
           String [] idAndInfo = buffer.split(":",2);
           String[] tokens=idAndInfo[1].split(",",0);
           switch (idAndInfo[0]) {
               case ("InterfaceUtilizador") :
                    pos =new Point2D.Double(Double.parseDouble(tokens[2]),Double.parseDouble(tokens[3]));
                    InterfaceUtilizador u = new Utilizador(tokens[0],"Password",tokens[1],r.nextDouble(),pos,new HashSet<>(),new ArrayList<>());
                    this.users.addUser(u);
               break;
               case ("InterfaceVoluntario") :
                    pos =new Point2D.Double(Double.parseDouble(tokens[2]),Double.parseDouble(tokens[3]));
                    InterfaceEntregador v = new Voluntario(tokens[1],tokens[0],pos,"Password",Float.parseFloat(tokens[4]),r.nextBoolean(),(float)(Math.round((r.nextFloat()+3)*100)/100.0),(float)(Math.round(r.nextFloat()*1000)/100),r.nextInt(),new ArrayList<>(),new Encomenda(),new ArrayList<InterfaceEncomenda>());
                    this.entregadores.setEntregador(tokens[0],v);
               break;
               case ("InterfaceTransportadora") :
                    pos = new Point2D.Double(Double.parseDouble(tokens[2]),Double.parseDouble(tokens[3]));
                    InterfaceTransportadora t = new Transportadora(tokens[1],tokens[0],pos,"Password",Float.parseFloat(tokens[5]),tokens[4],Double.parseDouble(tokens[6]),r.nextDouble()%5,r.nextBoolean(),(float)(Math.round((r.nextFloat()+20)*100)/100.0),(float)(Math.round(r.nextFloat()*1000)/100),r.nextInt(),r.nextInt(),new ArrayList<InterfaceEncomenda>(),new ArrayList<InterfaceEncomenda>());
                    this.entregadores.setEntregador(tokens[0],t);
               break;
               case ("InterfaceLoja") :
                    pos = new Point2D.Double(Double.parseDouble(tokens[2]),Double.parseDouble(tokens[3]));
                    InterfaceLoja l = new Loja(tokens[0],tokens[1],pos,"Password",r.nextInt()%20,r.nextFloat(),new HashMap<>());
                    this.lojas.setLoja(tokens[0],l);
               break;
               case ("Encomenda") :
                   List<InterfaceLinhaEncomenda> lista =new ArrayList<InterfaceLinhaEncomenda>();
                   int i=4,size=tokens.length;
                   while (i+3<size) {
                       lista.add(new LinhaEncomenda(tokens[i],tokens[i+1],Double.parseDouble(tokens[i+2]),Double.parseDouble(tokens[i+3])));
                       i+=4;
                   }
                   InterfaceEncomenda e = new Encomenda(tokens[0],r.nextBoolean(),Float.parseFloat(tokens[3]),tokens[2],tokens[1],lista, LocalDateTime.now().plusMinutes(r.nextLong()%60));
                   lojas.addEncomenda(tokens[2],e);
               break;
               case ("Aceite") :
                   Set<String> lE = this.aceites.getCodEncomendas();
                   lE.add(tokens[0]);
                   this.aceites=new EncomendasAceites(lE);
               break;
            }
       }
   }

   @Override public boolean encomendaAceite(String id,String user) {
        InterfaceEncomenda a=getEncomenda(id);
        return this.aceites.existe(id) && a.getDestino().equals(user);
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

   @Override public void aceitar(String entrega,String enc,double time) {
        InterfaceEncomenda e =getEncomenda(enc);
        e.setDataEntrega(LocalDateTime.now().plusMinutes((long)time));
        this.entregadores.addEncomenda(entrega,e);
        this.aceites.add(enc);
        this.lojas.removeReady(entrega,enc);
   }

   @Override public InterfaceEncomenda getEncomenda(String id) {
        InterfaceEncomenda r;
        for (InterfaceLoja l : this.lojas.getLojas().values()) {
            if((r=l.getEncomenda(id))!=null)
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

    @Override public void addEncomendaLoja(InterfaceEncomenda e) {
        this.lojas.addPronta(e);
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

    //InterfaceMenu de InterfaceVoluntario

    @Override public List<String> getVoluntarioRequests(String cod) throws EntregadorInexistenteException, UtilizadorInexistenteException, LojaInexistenteException {
        List<String> ls = new ArrayList<>();
        InterfaceVoluntario v =(InterfaceVoluntario) this.entregadores.getEntregador(cod);
        List<String> pedidoIDs =v.getPedidos();
        for (String s : pedidoIDs) {
            InterfaceEncomenda e = getEncomenda(s);
            ls.add("ID de Encomenda: " + e.getCodEncomenda() + "  Peso: " + e.getPeso() + "  Distância a percorrer: " + calculaDistTotal(this.lojas.getLoja(e.getOrigem()).getPosicao(),v.getPosicao(),this.getUser(e.getDestino()).getPosicao()) +"\n");
        }
        return ls;
    }

    @Override public double getTempoEsperado(String idEntregador,String idEnc) throws EntregadorInexistenteException, LojaInexistenteException {
        InterfaceEntregador e = getEntregador(idEntregador);
        InterfaceEncomenda enc = getEncomenda(idEnc);
        return calculaDistTotal(lojas.getLoja(enc.getOrigem()).getPosicao(),e.getPosicao(),lojas.getLoja(enc.getDestino()).getPosicao()) / e.getVelocidade();
    }

    @Override public void denyAll(String cod) throws EntregadorInexistenteException {
        for (String s : ((InterfaceVoluntario)this.entregadores.getEntregador(cod)).getPedidos()) {
            InterfaceEncomenda e = getEncomenda(s);
            this.users.addMessageToUser(e.getDestino(),"Encomenda "+s+" não foi aceite pelo InterfaceVoluntario "+cod);
        }
        this.entregadores.denyAll(cod);
    }

    @Override public void resetMessages(String cod) {
        this.users.resetMessages(cod);
    }

}
