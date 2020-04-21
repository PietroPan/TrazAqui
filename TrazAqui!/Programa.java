
/**
 * Write a description of class Programa here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.awt.geom.Point2D;
import java.util.Random;

public class Programa
{
    Map<String,Utilizador> users;
    Map<String,Loja> lojas;
    Map<String,Entregador> transportadoras;
    Map<String,Entregador> voluntarios;
    EncomendasAceites aceites;
    
    public Programa () {
     this.users=new HashMap<>();
     this.lojas=new HashMap<>();
     this.transportadoras=new HashMap<>();
     this.voluntarios=new HashMap<>();
     this.aceites=new EncomendasAceites();
    }
    
    public void readFile() throws java.io.FileNotFoundException,java.io.IOException {
       BufferedReader bufferAll = new BufferedReader (new FileReader("./logs.txt"));
       String buffer;
       Random r = new Random();
       Point2D pos;
       while((buffer=bufferAll.readLine())!=null) {
           String [] idAndInfo = buffer.split(":",2);
           String[] tokens=idAndInfo[1].split(",",0);
           switch (idAndInfo[0]) {
               case ("Utilizador") :
                    pos =new Point2D.Double(Double.valueOf(tokens[2]),Double.valueOf(tokens[3]));
                    Utilizador u = new Utilizador(tokens[0],tokens[1],r.nextDouble(),pos);
                    this.users.put(tokens[0],u);
               break;
               case ("Voluntario") :
                    pos =new Point2D.Double(Double.valueOf(tokens[2]),Double.valueOf(tokens[3]));
                    Voluntario v = new Voluntario(tokens[1],tokens[0],pos,Float.valueOf(tokens[4]),r.nextBoolean(),r.nextFloat(),null,new ArrayList<Encomenda>());
                    this.voluntarios.put(tokens[0],v);
               break;
               case ("Transportadora") :
                    pos = new Point2D.Double(Double.valueOf(tokens[2]),Double.valueOf(tokens[3]));
                    Transportadora t = new Transportadora(tokens[1],tokens[0],pos,Float.valueOf(tokens[5]),tokens[4],Double.valueOf(tokens[6]),r.nextDouble(),r.nextBoolean(),r.nextFloat(),r.nextInt(),new ArrayList<Encomenda>(),new ArrayList<Encomenda>());
                    this.transportadoras.put(tokens[0],t);
               break;
               case ("Loja") :
                    pos = new Point2D.Double(Double.valueOf(tokens[2]),Double.valueOf(tokens[3]));
                    Loja l = new Loja(tokens[0],tokens[1],pos,r.nextInt(),r.nextFloat(),new ArrayList<Encomenda>());
                    this.lojas.put(tokens[0],l);
               break;
               case ("Encomenda") :
                   List lista =new ArrayList<LinhaEncomenda>();
                   int i=4,size=tokens.length;
                   while (i+3<size) {
                       lista.add(new LinhaEncomenda(tokens[i],tokens[i+1],Double.valueOf(tokens[i+2]),Integer.valueOf(tokens[i+3])));
                       i+=4;
                   }
                   Encomenda e = new Encomenda(tokens[0],r.nextBoolean(),Float.valueOf(tokens[3]),tokens[2],tokens[1],lista);
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
