
/**
 * Write a description of class Programa here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

import java.io.File;
import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.awt.geom.Point2D;

public class Data
{
    Map<String,Utilizador> users;
    Map<String,Loja> lojas;
    Map<String,Entregador> entregadores;
    EncomendasAceites aceites;
    
    public Data () {
     this.users=new HashMap<>();
     this.lojas=new HashMap<>();
     this.entregadores=new HashMap<>();
     this.aceites=new EncomendasAceites();
    }
    
    public Utilizador getUser(String cod) {
        Utilizador u = this.users.get(cod);
        if (u==null) return null;
        return u.clone();
    }

    public Entregador getEntregador(String ent) {
        return this.entregadores.get(ent).clone();
    }
    
    public void addUser(Utilizador user){
        this.users.put(user.getCodigo(),user);
    }
    
    public void readFile() throws java.io.FileNotFoundException,java.io.IOException {
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
                    Utilizador u = new Utilizador(tokens[0],"Password",tokens[1],r.nextDouble(),pos);
                    this.users.put(tokens[0],u);
               break;
               case ("Voluntario") :
                    pos =new Point2D.Double(Double.parseDouble(tokens[2]),Double.parseDouble(tokens[3]));
                    Voluntario v = new Voluntario(tokens[1],tokens[0],pos,"Password",Float.parseFloat(tokens[4]),r.nextBoolean(),(float)(Math.round((r.nextFloat()+3)*100)/100.0),(float)(Math.round(r.nextFloat()*1000)/100),new ArrayList<>(),new Encomenda(),new ArrayList<Encomenda>());
                    this.entregadores.put(tokens[0],v);
               break;
               case ("Transportadora") :
                    pos = new Point2D.Double(Double.parseDouble(tokens[2]),Double.parseDouble(tokens[3]));
                    Transportadora t = new Transportadora(tokens[1],tokens[0],pos,"Password",Float.parseFloat(tokens[5]),tokens[4],Double.parseDouble(tokens[6]),r.nextDouble()%5,r.nextBoolean(),(float)(Math.round((r.nextFloat()+20)*100)/100.0),(float)(Math.round(r.nextFloat()*1000)/100),r.nextInt(),new ArrayList<Encomenda>(),new ArrayList<Encomenda>());
                    this.entregadores.put(tokens[0],t);
               break;
               case ("Loja") :
                    pos = new Point2D.Double(Double.parseDouble(tokens[2]),Double.parseDouble(tokens[3]));
                    Loja l = new Loja(tokens[0],tokens[1],pos,"Password",r.nextInt()%20,r.nextFloat(),new HashMap<>());
                    this.lojas.put(tokens[0],l);
               break;
               case ("Encomenda") :
                   List<LinhaEncomenda> lista =new ArrayList<LinhaEncomenda>();
                   int i=4,size=tokens.length;
                   while (i+3<size) {
                       lista.add(new LinhaEncomenda(tokens[i],tokens[i+1],Double.parseDouble(tokens[i+2]),Double.parseDouble(tokens[i+3])));
                       i+=4;
                   }
                   Encomenda e = new Encomenda(tokens[0],r.nextBoolean(),Float.parseFloat(tokens[3]),tokens[2],tokens[1],lista);
                   Loja loja=lojas.get(tokens[2]);
                   loja.addPronta(e);
                   lojas.put(tokens[2],loja);
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
        Loja l=lojas.get(encomenda.getOrigem());
        double tempoMin=-1,tempoAux;
        for (Entregador e : this.entregadores.values()) {
            if (e.getClass().equals(v.getClass()) && e.hasRoomAndMed(getEncomenda(enc).getMedical())) {
                tempoAux = e.getVelocidade() * calculaDistTotal(e.getPosicao(), l.getPosicao(), users.get(encomenda.getDestino()).getPosicao())+l.getTamFila()*l.getTempoAtendimento();
                if (tempoMin == -1 || tempoAux < tempoMin) {
                    tempoMin = tempoAux;
                    r = e.getCodigo();
                }
            }
        }
        return r;
   }

   public void askVoluntario(String idVoluntario,String idEnc) {
       ((Voluntario)this.entregadores.get(idVoluntario)).addPedido(idEnc);
   }

   public void aceitar(String entrega,String enc) {
        Entregador r = this.entregadores.get(entrega); //Do I need to clone();
        Encomenda e =getEncomenda(enc);
        r.addEncomenda(e);
        this.aceites.add(enc);
        Loja l=this.lojas.get(e.getOrigem()); //Do I need to clone();
        l.removeReady(enc);
   }

   public Encomenda getEncomenda(String id) {
        Encomenda r;
        for (Loja l : this.lojas.values()) {
            if((r=l.getEncomenda(id))!=null)
                    return r;
        }
        return null;
   }

   public double calculaDistTotal(Point2D p1,Point2D p2,Point2D p3) {
        double d=p1.distance(p2);
        d+=p2.distance(p3);
        return d;
   }

   public Set<String[]> getEntregadoresDisp(String id) {
       Voluntario v=new Voluntario();
       Set<String[]> setOpcoes=new HashSet<>();
       int r;
       double preco;
       double tempoEst;
       double timeWaiting;
       Encomenda en =getEncomenda(id);
       Loja l =this.lojas.get(en.getOrigem());
       r=l.getTamFila();
       timeWaiting=r*l.getTempoAtendimento();
       for (Entregador e : this.entregadores.values()) {
           if (e.hasRoomAndMed(en.getMedical())) {
               double d=calculaDistTotal(e.getPosicao(),lojas.get(en.getOrigem()).getPosicao(),users.get(en.getDestino()).getPosicao());
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
        this.lojas.get(e.getOrigem()).addPronta(e);
    }


}
