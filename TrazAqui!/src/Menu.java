
/**
 * Write a description of class Programa here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

import java.awt.geom.Point2D;
import java.io.FileNotFoundException;
import java.io.IOException;
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

    public Utilizador login (String codUser) {
        return info.getUser(codUser);
    }
    
    public String initUser () {
        Scanner read = new Scanner(System.in);
        p.askUserCod();
        String userCod=read.nextLine();
        Utilizador user;
        while ((user=login(userCod))==null) {
            p.newUser();
            String option = read.nextLine().toUpperCase();
            if (option.equals("S")) {
                p.askUserName();
                String name = read.nextLine();
                p.askBalance();
                double balance = read.nextDouble();
                p.askLocalizacao("x");
                float x = read.nextFloat();
                p.askLocalizacao("y");
                float y =read.nextFloat();
                user = new Utilizador(userCod,name,balance,new Point2D.Double(x,y));
                info.addUser(user);
            }
            else {
                p.askUserCod();
                userCod=read.nextLine();
            }
        }
        return user.getCodUtilizador();
    }

    public void menu() {
        info=new Data();
        try {
            info.readFile();
        } catch (FileNotFoundException e) {
            p.fileNotFound();
        } catch (IOException e) {
            p.wrongFileTextFormat();
        }
        codUser=initUser();
        p.showUserOptions();
    }

}
