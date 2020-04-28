
/**
 * Escreva a descrição da classe Utilizador aqui.
 * 
 * @author (seu nome) 
 * @version (número de versão ou data)
 */

import java.awt.geom.Point2D;

public class Utilizador extends BasicInfo {
    private double balance;

    public Utilizador() {
        this.setNome("NoName");
        this.setCodigo("n/a");
        this.setPassword("n/a");
        this.setPosicao(new Point2D.Double(0, 0));
        this.balance = 0.00;
    }

    public Utilizador(String codUtilizador, String password, String nome, double balance, Point2D pos) {
        this.setNome(nome);
        this.setCodigo(codUtilizador);
        this.setPassword(password);
        this.setPosicao((Point2D)pos.clone());
        this.balance = balance;
    }

    public Utilizador(Utilizador u) {
        this.setNome(u.getNome());
        this.setCodigo(u.getCodigo());
        this.setPassword(u.getPassword());
        this.setPosicao((Point2D)u.getPosicao().clone());
        this.balance = u.getBalance();
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getBalance() {
        return this.balance;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("Nome: ").append(this.getNome()).append("\nCodigo do Utilizador: ").append(this.getCodigo()).append("\nBalance:").append(this.balance).append("\nPosição: (").append(this.getPosicao().getX()).append(",").append(this.getPosicao().getY()).append(")");
        return s.toString();
    }

    public boolean equals(Object user) {
        Utilizador u;
        if (user == null || user.getClass() != this.getClass()) return false;
        u = (Utilizador) user;
        return this.getCodigo().equals(u.getCodigo()) && this.getPassword().equals(u.getPassword());
    }

    public Utilizador clone() {
        return new Utilizador(this);
    }

}
