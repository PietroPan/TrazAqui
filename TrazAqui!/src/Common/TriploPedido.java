package Common;

import java.util.Objects;

public class TriploPedido {
    InterfaceEncomenda enc;
    String trans;
    String stat;

    public TriploPedido() {
        this.enc=new Encomenda();
        this.trans="n/a";
        this.stat="n/a";
    }

    public TriploPedido(InterfaceEncomenda enc, String trans, String stat) {
        this.enc = enc;
        this.trans = trans;
        this.stat = stat;
    }

    public InterfaceEncomenda getEnc() {
        return enc;
    }

    public void setEnc(InterfaceEncomenda enc) {
        this.enc = enc;
    }

    public String getTrans() {
        return trans;
    }

    public void setTrans(String trans) {
        this.trans = trans;
    }

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TriploPedido that = (TriploPedido) o;
        return Objects.equals(enc, that.enc) &&
                Objects.equals(trans, that.trans) &&
                Objects.equals(stat, that.stat);
    }

    @Override
    public String toString() {
        return enc +
                "\nTransportadora: " + this.trans +
                "\nStatus: " + printStatus(this.stat);
    }

    public String printStatus(String stat){
        switch (stat) {
            case "a": return "Aceite";
            case "p": return "Pendente";
            case "c": return "Cancelada";
            default: return "Error";
        }
    }
}
