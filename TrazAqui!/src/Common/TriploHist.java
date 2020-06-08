package Common;

import java.util.Objects;

public class TriploHist {
    String ent;
    boolean stat;
    InterfaceEncomenda enc;

    public TriploHist(){
        this.ent = "n/a";
        this.stat = false;
        this.enc = new Encomenda();
    }

    public TriploHist(String ent, boolean stat, InterfaceEncomenda enc) {
        this.ent = ent;
        this.stat = stat;
        this.enc = enc.clone();
    }

    public TriploHist(TriploHist c){
        this.ent = c.getEnt();
        this.stat = c.isStat();
        this.enc = c.getEnc();
    }

    public String getEnt() {
        return ent;
    }

    public boolean isStat() {
        return stat;
    }

    public InterfaceEncomenda getEnc() {
        return enc.clone();
    }

    public void setEnt(String ent) {
        this.ent = ent;
    }

    public void setStat(boolean stat) {
        this.stat = stat;
    }

    public void setEnc(InterfaceEncomenda enc) {
        this.enc = enc.clone();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TriploHist that = (TriploHist) o;
        return stat == that.stat &&
                Objects.equals(ent, that.ent) &&
                Objects.equals(enc, that.enc);
    }

    public TriploHist clone(){
        return new TriploHist(this);
    }

    @Override
    public String toString() {
        String aux="";
        if (this.enc.toString().contains("l")||this.enc.toString().contains("u")) aux = "\nEntregador: " + ent;
        return this.enc.toString() + aux + printStat(this.isStat());
    }

    public String printStat(boolean stat){
        if (stat) return  "\nJá foi classificada";
        else return "\nAinda não foi classificada";
    }
}
