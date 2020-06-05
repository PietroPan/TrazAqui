package Common;

import java.util.Objects;

public class TriploStrings {
    private String cod1;
    private String cod2;
    private char stat;

    public TriploStrings(){
        this.cod1="n/a";
        this.cod2="n/a";
        this.stat='0';
    }

    public TriploStrings(String cod1, String cod2, char stat) {
        this.cod1 = cod1;
        this.cod2 = cod2;
        this.stat = stat;
    }

    public String getCod1() {
        return cod1;
    }

    public void setCod1(String cod1) {
        this.cod1 = cod1;
    }

    public String getCod2() {
        return cod2;
    }

    public void setCod2(String cod2) {
        this.cod2 = cod2;
    }

    public char getStat() {
        return stat;
    }

    public void setStat(char stat) {
        this.stat = stat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TriploStrings that = (TriploStrings) o;
        return stat == that.stat &&
                Objects.equals(cod1, that.cod1) &&
                Objects.equals(cod2, that.cod2);
    }

    @Override
    public String toString() {
        return "TriploStrings{" +
                "cod1='" + cod1 + '\'' +
                ", cod2='" + cod2 + '\'' +
                ", stat=" + stat +
                '}';
    }
}
