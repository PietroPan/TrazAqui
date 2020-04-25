
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
        this.users.put(user.getCodUtilizador(),user);
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
                    Utilizador u = new Utilizador(tokens[0],tokens[1],r.nextDouble(),pos);
                    this.users.put(tokens[0],u);
               break;
               case ("Voluntario") :
                    pos =new Point2D.Double(Double.parseDouble(tokens[2]),Double.parseDouble(tokens[3]));
                    Voluntario v = new Voluntario(tokens[1],tokens[0],pos,Float.parseFloat(tokens[4]),r.nextBoolean(),(float)(Math.round((r.nextFloat()+3)*100)/100.0),(float)(Math.round(r.nextFloat()*1000)/100),r.nextFloat(),new Encomenda(),new ArrayList<Encomenda>());
                    this.entregadores.put(tokens[0],v);
               break;
               case ("Transportadora") :
                    pos = new Point2D.Double(Double.parseDouble(tokens[2]),Double.parseDouble(tokens[3]));
                    Transportadora t = new Transportadora(tokens[1],tokens[0],pos,Float.parseFloat(tokens[5]),tokens[4],Double.parseDouble(tokens[6]),r.nextDouble()%5,r.nextBoolean(),(float)(Math.round((r.nextFloat()+20)*100)/100.0),(float)(Math.round(r.nextFloat()*1000)/100),r.nextInt(),new ArrayList<Encomenda>(),new ArrayList<Encomenda>());
                    this.entregadores.put(tokens[0],t);
               break;
               case ("Loja") :
                    pos = new Point2D.Double(Double.parseDouble(tokens[2]),Double.parseDouble(tokens[3]));
                    Loja l = new Loja(tokens[0],tokens[1],pos,r.nextInt()%20,r.nextFloat(),new HashMap<>());
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

   public boolean aceitar(String entrega,String enc) {
        if (entrega.charAt(0)=='v') {
            float c = ((Voluntario) this.getEntregador(entrega)).getChanceAceitar();
            Random r = new Random();
            if (r.nextFloat() > c)
                return false;
        }
        Entregador r = this.entregadores.get(entrega); //Do I need to clone();
        Encomenda e =getEncomenda(enc);
        r.addEncomenda(e);
        this.aceites.add(enc);
        Loja l=this.lojas.get(e.getOrigem()); //Do I need to clone();
        l.removeReady(enc);
        return true;
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
       Random rand = new Random();
       int r;
       double preco;
       double tempoEst;
       double timeWaiting;
       Encomenda en =getEncomenda(id);
       Loja l =this.lojas.get(en.getOrigem());
       r=l.getTamFila();
       if (r==-1) //se nao houver informações da fila gera um rand entre 1 e 10;
           r=rand.nextInt()%10;
       timeWaiting=r*l.getTempoAtendimento();
       for (Entregador e : this.entregadores.values()) {
           if (e.hasRoomAndMed(en.getMedical())) {
               double d=calculaDistTotal(e.getPos(),lojas.get(en.getOrigem()).getPos(),users.get(en.getDestino()).getPos());
               String[] info = new String[5];
               if (e.getClass().equals(v.getClass())) {
                   preco = 0;
               }
               else {
                   preco=d*e.getCustoKm()+en.getPeso()*e.getCustoKg();
               }
               tempoEst=d/e.getVelocidade()+timeWaiting;
               info[0]=e.getCodEntregador();
               info[1]=e.getNome();
               info[2]=Float.toString(e.getClassificacao());
               info[3]=Double.toString(Math.round(preco*100)/100.0);
               info[4]=Double.toString(Math.round(tempoEst*100)/100.0);
               setOpcoes.add(info);
           }
       }
       return setOpcoes;
    }

}
