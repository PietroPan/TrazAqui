
/**
 * Write a description of class Programa here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

import java.io.File;
import java.time.LocalDateTime;
import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.awt.geom.Point2D;
import java.util.stream.Collectors;

public class Data
{
    Utilizadores users;
    Lojas lojas;
    Entregadores entregadores;
    EncomendasAceites aceites;
    
    public Data () {
     this.users=new Utilizadores();
     this.lojas=new Lojas();
     this.entregadores=new Entregadores();
     this.aceites=new EncomendasAceites();
    }

    public Utilizador getUser(String cod) {
        return users.getUser(cod);
    }

    public void addUser(Utilizador u) {
        this.users.addUser(u);
    }

    public Entregador getEntregador(String cod) {
        return this.entregadores.getEntregador(cod);
    }

    public void addEntregador(Entregador e) {
        this.entregadores.setEntregador(e.getCodigo(),e.clone());
    }

    public Loja getLoja(String cod) {
        return this.lojas.getLoja(cod);
    }

    public void addLoja(Loja l) {
        this.lojas.setLoja(l.getCodigo(),l);
    }

    public void readFile() throws java.io.IOException {
        BufferedReader bufferAll = new BufferedReader (new FileReader("src/logs.txt"));
        String buffer;
        Random r = new Random();
        Point2D pos;
        while((buffer=bufferAll.readLine())!=null) {
           String [] idAndInfo = buffer.split(":",2);
           String[] tokens=idAndInfo[1].split(",",0);
           switch (idAndInfo[0]) {
               case ("Utilizador") :
                    pos =new Point2D.Double(Double.parseDouble(tokens[2]),Double.parseDouble(tokens[3]));
                    Utilizador u = new Utilizador(tokens[0],"Password",tokens[1],r.nextDouble(),pos,new HashSet<>());
                    this.users.addUser(u);
               break;
               case ("Voluntario") :
                    pos =new Point2D.Double(Double.parseDouble(tokens[2]),Double.parseDouble(tokens[3]));
                    Voluntario v = new Voluntario(tokens[1],tokens[0],pos,"Password",Float.parseFloat(tokens[4]),r.nextBoolean(),(float)(Math.round((r.nextFloat()+3)*100)/100.0),(float)(Math.round(r.nextFloat()*1000)/100),r.nextInt(),new ArrayList<>(),new Encomenda(),new ArrayList<Encomenda>());
                    this.entregadores.setEntregador(tokens[0],v);
               break;
               case ("Transportadora") :
                    pos = new Point2D.Double(Double.parseDouble(tokens[2]),Double.parseDouble(tokens[3]));
                    Transportadora t = new Transportadora(tokens[1],tokens[0],pos,"Password",Float.parseFloat(tokens[5]),tokens[4],Double.parseDouble(tokens[6]),r.nextDouble()%5,r.nextBoolean(),(float)(Math.round((r.nextFloat()+20)*100)/100.0),(float)(Math.round(r.nextFloat()*1000)/100),r.nextInt(),r.nextInt(),new ArrayList<Encomenda>(),new ArrayList<Encomenda>());
                    this.entregadores.setEntregador(tokens[0],t);
               break;
               case ("Loja") :
                    pos = new Point2D.Double(Double.parseDouble(tokens[2]),Double.parseDouble(tokens[3]));
                    Loja l = new Loja(tokens[0],tokens[1],pos,"Password",r.nextInt()%20,r.nextFloat(),new HashMap<>());
                    this.lojas.setLoja(tokens[0],l);
               break;
               case ("Encomenda") :
                   List<LinhaEncomenda> lista =new ArrayList<LinhaEncomenda>();
                   int i=4,size=tokens.length;
                   while (i+3<size) {
                       lista.add(new LinhaEncomenda(tokens[i],tokens[i+1],Double.parseDouble(tokens[i+2]),Double.parseDouble(tokens[i+3])));
                       i+=4;
                   }
                   Encomenda e = new Encomenda(tokens[0],r.nextBoolean(),Float.parseFloat(tokens[3]),tokens[2],tokens[1],lista, LocalDateTime.now().plusMinutes(r.nextLong()%60));
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

   public boolean encomendaAceite(String id,String user) {
        Encomenda a=getEncomenda(id);
        return this.aceites.existe(id) && a.getDestino().equals(user);
   }

   public String voluntarioAvailable(String enc) {
        String r="n/a";
        Voluntario v = new Voluntario();
        Encomenda encomenda=getEncomenda(enc);
        Loja l=lojas.getLoja(encomenda.getOrigem());
        double tempoMin=-1,tempoAux;
        for (Entregador e : this.entregadores.getEntregadores().values()) {
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

   public void askVoluntario(String idVoluntario,String idEnc) {
        this.entregadores.addPedidoVoluntario(idVoluntario,idEnc);
   }

   public void aceitar(String entrega,String enc,double time) {
        Encomenda e =getEncomenda(enc);
        e.setDataEntrega(LocalDateTime.now().plusMinutes((long)time));
        this.entregadores.addEncomenda(entrega,e);
        this.aceites.add(enc);
        this.lojas.removeReady(entrega,enc);
   }

   public Encomenda getEncomenda(String id) {
        Encomenda r;
        for (Loja l : this.lojas.getLojas().values()) {
            if((r=l.getEncomenda(id))!=null)
                    return r;
        }
        return null;
   }

   public double calculaDistTotal(Point2D p1,Point2D p2,Point2D p3) {
       double d = p1.distance(p2);
       d += p2.distance(p3);
       return d;
   }

   public Set<String[]> getEntregadoresDisp(String id) {
       Set<String[]> setOpcoes=new HashSet<>();
       int r;
       double preco;
       double tempoEst;
       double timeWaiting;
       Encomenda en =getEncomenda(id);
       Loja l =this.lojas.getLoja(en.getOrigem());
       r=l.getTamFila();
       timeWaiting=r*l.getTempoAtendimento();
       for (Entregador e : this.entregadores.getEntregadores().values()) {
           if (e.hasRoomAndMed(en.getMedical())) {
               double d=calculaDistTotal(e.getPosicao(),l.getPosicao(),users.getUser(en.getDestino()).getPosicao());
               String[] info = new String[5];
               preco=d*e.getCustoKm()+en.getPeso()*e.getCustoKg();
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

    public void addEncomendaLoja(Encomenda e) {
        this.lojas.addPronta(e);
    }

    public void classifica(Set<Map.Entry<Boolean,String>> encomendasID,String eID,String codUser,float c) {
        Map.Entry<Boolean,String> encomendaAclassificar = new AbstractMap.SimpleEntry<>(false,eID);
        encomendasID.remove(encomendaAclassificar);
        encomendasID.add(new AbstractMap.SimpleEntry<>(true,eID));
        Utilizador atualizado = this.users.getUser(codUser);
        atualizado.setPedidosEntregues(encomendasID);
        users.addUser(atualizado);
        Encomenda e = getEncomenda(eID);
        entregadores.classifica(e,c);
    }
}
