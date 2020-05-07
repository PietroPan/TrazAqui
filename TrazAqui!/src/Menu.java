
/**
 * Write a description of class Programa here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

import java.awt.geom.Point2D;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Menu
{
    private Data info;
    private String codUser;
    private Printer p;

    public Menu() {
        this.info=new Data();
        this.codUser="";
        this.p=new Printer ();
    }

    public Utilizador login (String codUser,String password)
    {
        Utilizador r=info.getUser(codUser);
        if (r!=null && r.getPassword().equals(password) )
            return r.clone();
        return null;
    }
    
    public String initUser () {
        Scanner read = new Scanner(System.in);
        p.askUserCod();
        String userCod=read.nextLine();
        p.askPassword();
        String password = read.nextLine();
        Utilizador user;
        while ((user=login(userCod,password))==null) {
            p.askNewUser();
            String option = read.nextLine().toUpperCase();
            if (option.equals("S")) {
                p.askUserName();
                String name = read.nextLine();
                p.askPassword();
                password =read.nextLine();
                p.askBalance();
                double balance = Double.parseDouble(read.nextLine());
                p.askLocalizacao("x");
                float x = Float.parseFloat(read.nextLine());
                p.askLocalizacao("y");
                float y = Float.parseFloat(read.nextLine());
                user = new Utilizador(userCod,password,name,balance,new Point2D.Double(x,y));
                info.addUser(user);
            }
            else {
                p.askUserCod();
                userCod=read.nextLine();
                p.askPassword();
                password = read.nextLine();
            }
        }
        return user.getCodigo();
    }

    public void menuUser() {
        Random rand = new Random();
        String id,idVoluntario;
        codUser=initUser();
        p.showUserOptions();
        String opcao;
        Scanner read= new Scanner(System.in);
        while(!(opcao=read.nextLine()).equals("0")) {
            switch (opcao) {
                case ("1"):
                    float peso =0;
                    p.askLojaID();
                    String loja=read.nextLine();
                    p.askMedical();
                    boolean med = read.nextLine().toUpperCase().equals("S");
                    List<LinhaEncomenda> list=new ArrayList<>();
                    Encomenda enc = new Encomenda("e"+rand.nextInt(10000),med,0,loja,codUser,list);
                    p.askLinhaEnc();
                    opcao=read.nextLine().toUpperCase();
                    while (opcao.equals("S")) {
                        p.askCodProduto();
                        String codProd=read.nextLine();
                        p.askDescricao();
                        String desc=read.nextLine();
                        p.askQuantidade();
                        double qnt=Double.parseDouble(read.nextLine());
                        LinhaEncomenda l = new LinhaEncomenda(codProd,desc,rand.nextFloat()*100*qnt,qnt);
                        peso+=rand.nextFloat()*5*qnt;
                        list.add(l);
                        p.askLinhaEnc();
                        opcao=read.nextLine().toUpperCase();
                    }
                    if (list.isEmpty()) {
                        p.nadaEncomendado();
                        break;
                    }
                    enc.setPedido(list);
                    enc.setPeso(peso);
                    p.apresentaEncomenda(enc.toString());
                    p.apresentaPrecoEnc(enc.calculaValorTotal());
                    p.askConfirmacao();
                    opcao=read.nextLine().toUpperCase();
                    if (opcao.equals("S")) {
                        this.info.addEncomendaLoja(enc);
                    }
                    break;
                case ("2") :
                    p.askEncomendaId();
                    id=read.nextLine();
                    if (info.encomendaAceite(id,codUser)) {
                        p.encomendaACaminho(info.getEncomenda(id));
                        break;
                    }
                    else {
                        if (info.getEncomenda(id) == null) {
                            p.invalid("Encomenda");
                            break;
                        } else {
                            if (!((idVoluntario=this.info.voluntarioAvailable(id)).equals("n/a"))) {
                                this.info.askVoluntario(idVoluntario,id);
                                p.voluntarioLivre();
                                break;
                            }
                            p.apresentaEntregadores(info.getEntregadoresDisp(id));
                        }
                    }
                    p.askEntregadorId();
                    this.info.aceitar(read.nextLine(),id);
                    p.encomendaACaminho(info.getEncomenda(id));
                    break;
                case ("3") :

                    break;
                case ("4") :
                    break;
                default:
                    p.invalid("Opção");
                break;
            }
            p.showUserOptions();
        }
    }

    public void menuVoluntario() {
    }

    public void menuTransportadora() {

    }

    public void menuLoja() {

    }

    public void menu() {
        String opcao;
        info=new Data();
        try {
            info.readFile();
        } catch (FileNotFoundException e) {
            p.fileNotFound();
        } catch (IOException e) {
            p.invalid("Formato de linhas de texto");
        }
        Scanner read = new Scanner(System.in);
        p.showLoginOptions();
        while (!(opcao=read.nextLine()).equals("0")) {
            switch (opcao) {
                case ("1"):
                    menuUser();
                    break;
                case ("2"):
                    menuVoluntario();
                    break;
                case ("3"):
                    menuTransportadora();
                    break;
                case ("4"):
                    menuLoja();
                    break;
            }
        }
        p.showBye();
    }
}
