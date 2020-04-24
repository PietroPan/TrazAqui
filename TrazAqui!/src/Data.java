
/**
 * Write a description of class Programa here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

import java.io.File;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.awt.geom.Point2D;
import java.util.Random;

public class Data
{
    Map<String,Utilizador> users;
    Map<String,Loja> lojas;
    Map<String,Entregador> transportadoras;
    Map<String,Entregador> voluntarios;
    EncomendasAceites aceites;
    
    public Data () {
     this.users=new HashMap<>();
     this.lojas=new HashMap<>();
     this.transportadoras=new HashMap<>();
     this.voluntarios=new HashMap<>();
     this.aceites=new EncomendasAceites();
    }
    
    public Utilizador getUser(String cod) {
        Utilizador u = this.users.get(cod);
        if (u==null) return null;
        return u.clone();
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
                    Voluntario v = new Voluntario(tokens[1],tokens[0],pos,Float.parseFloat(tokens[4]),r.nextBoolean(),r.nextFloat(),new Encomenda(),new ArrayList<Encomenda>());
                    this.voluntarios.put(tokens[0],v);
               break;
               case ("Transportadora") :
                    pos = new Point2D.Double(Double.parseDouble(tokens[2]),Double.parseDouble(tokens[3]));
                    Transportadora t = new Transportadora(tokens[1],tokens[0],pos,Float.parseFloat(tokens[5]),tokens[4],Double.parseDouble(tokens[6]),r.nextDouble(),r.nextBoolean(),r.nextFloat(),r.nextInt(),new ArrayList<Encomenda>(),new ArrayList<Encomenda>());
                    this.transportadoras.put(tokens[0],t);
               break;
               case ("Loja") :
                    pos = new Point2D.Double(Double.parseDouble(tokens[2]),Double.parseDouble(tokens[3]));
                    Loja l = new Loja(tokens[0],tokens[1],pos,r.nextInt(),r.nextFloat(),new ArrayList<Encomenda>());
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
                   List<String> lE = this.aceites.GetCodEncomendas();
                   lE.add(tokens[0]);
                   this.aceites=new EncomendasAceites(lE);
               break;
            }
       }
   }
}
