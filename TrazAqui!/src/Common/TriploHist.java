package Common;

import java.util.Objects;

public class TriploHist {
    String ent;
    boolean stat;
    InterfaceEncomenda enc;

    /**
     * Construtor vazio
     */
    public TriploHist(){
        this.ent = "n/a";
        this.stat = false;
        this.enc = new Encomenda();
    }

    /**
     * Construtor parametrizado
     * @param ent código de entregador
     * @param stat boolean de estado
     * @param enc encomenda a copiar
     */
    public TriploHist(String ent, boolean stat, InterfaceEncomenda enc) {
        this.ent = ent;
        this.stat = stat;
        this.enc = enc.clone();
    }

    /**
     * Construtor cópia
     * @param c TriploHist a copiar
     */
    public TriploHist(TriploHist c){
        this.ent = c.getEnt();
        this.stat = c.isStat();
        this.enc = c.getEnc();
    }

    /**
     * Getter de código de entregador
     * @return código de entregador
     */
    public String getEnt() {
        return ent;
    }

    /**
     * Getter para o estado de uma encomenda
     * @return true caso tenha sido classificada
     */
    public boolean isStat() {
        return stat;
    }

    /**
     * Getter para encomenda
     * @return encomenda cópia
     */
    public InterfaceEncomenda getEnc() {
        return enc.clone();
    }

    /**
     * Setter para código de entregador
     * @param ent código a copiar
     */
    public void setEnt(String ent) {
        this.ent = ent;
    }

    /**
     * Setter para estado de uma encomenda
     * @param stat valor a tomar
     */
    public void setStat(boolean stat) {
        this.stat = stat;
    }

    /**
     * Setter para encomenda
     * @param enc encomenda a clonar para copia
     */
    public void setEnc(InterfaceEncomenda enc) {
        this.enc = enc.clone();
    }

    /**
     * Método equals
     * @param o parametro a comparar
     * @return true se tiverem os seus parametros de igual valor
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TriploHist that = (TriploHist) o;
        return stat == that.stat &&
                Objects.equals(ent, that.ent) &&
                Objects.equals(enc, that.enc);
    }

    /**
     * Método clone
     * @return cópia de TriploHist
     */
    public TriploHist clone(){
        return new TriploHist(this);
    }

    /**
     * Método toString
     * @return String com toda a informação relevante em TriploHist
     */
    @Override
    public String toString() {
        if (this.ent.contains("l")||this.ent.contains("u")) return this.enc.toString() + "\nEntregador: " + ent + printStat(this.isStat());
        else return this.enc.toString2() + printStat(this.isStat());
    }

    /**
     * Método auxiliar ao toString
     * @param stat Estado da encomenda
     * @return String com informação relevante sobre o estado da encomenda
     */
    public String printStat(boolean stat){
        if (stat) return  "\nJá foi classificada";
        else return "\nAinda não foi classificada";
    }
}
