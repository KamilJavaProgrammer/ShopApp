package ShopAppBackend;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class GoldPrice   {

    private String data;

    private double cena;

    public GoldPrice() {
    }

    public GoldPrice(String data, double cena) {
        this.data = data;
        this.cena = cena;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public double getCena() {
        return cena;
    }

    public void setCena(double cena) {
        this.cena = cena;
    }

    @Override
    public String toString() {
        return "GoldPrice{" +
                "data='" + data + '\'' +
                ", cena=" + cena +
                '}';
    }
}
