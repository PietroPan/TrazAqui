
/**
 * Escreva a descrição da classe Utilizador aqui.
 * 
 * @author (seu nome) 
 * @version (número de versão ou data)
 */

import java.awt.geom.Point2D;

public class Utilizador {
    private String nome;
    private String codUtilizador;
    private String password;
    private double balance;
    private Point2D pos;

    public Utilizador() {
        this.nome = "NoName";
        this.codUtilizador = "n/a";
        this.password = "n/a";
        this.balance = 0.00;
        this.pos = new Point2D.Double(0, 0);
    }

    public Utilizador(String codUtilizador, String password, String nome, double balance, Point2D pos) {
        this.nome = nome;
        this.codUtilizador = codUtilizador;
        this.password = password;
        this.balance = balance;
        this.pos = (Point2D.Double) pos.clone();
    }

    public Utilizador(Utilizador u) {
        this.nome = u.nome;
        this.codUtilizador = u.codUtilizador;
        this.password = u.getPassword();
        this.balance = u.balance;
        this.pos = (Point2D.Double) u.pos.clone();
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCodUtilizador(String codUtilizador) {
        this.codUtilizador = codUtilizador;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setPos(Point2D pos) {
        this.pos = (Point2D.Double) pos.clone();
    }

    public String getNome() {
        return this.nome;
    }

    public String getCodUtilizador() {
        return this.codUtilizador;
    }

    public double getBalance() {
        return this.balance;
    }

    public Point2D.Double getPos() {
        return (Point2D.Double) this.pos.clone();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("Nome: ").append(this.nome).append("\nCodigo do Utilizador: ").append(this.codUtilizador).append("\nBalance:").append(this.balance).append("\nPosição: (").append(this.pos.getX()).append(",").append(this.pos.getY()).append(")");
        return s.toString();
    }

    public boolean equals(Object user) {
        Utilizador u;
        if (user == null || user.getClass() != this.getClass()) return false;
        u = (Utilizador) user;
        return this.codUtilizador.equals(u.codUtilizador) && this.password.equals(u.getPassword());
    }

    public Utilizador clone() {
        return new Utilizador(this);
    }

}
