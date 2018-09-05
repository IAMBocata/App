package cat.iam.bocatas.app.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe que conté la infomacio de la compra
 */

public class Compra implements Serializable{

    private List<CheckOutObject> check;
    private String date;
    private float price;
    private String state;

    public Compra(List<CheckOutObject> check, String date, float price, String state) {
        this.check = check;
        this.date = date;
        this.price = price;
        this.state = state;
    }

    public List<CheckOutObject> getCheck() {
        return check;
    }

    public void setCheck(List<CheckOutObject> check) {
        this.check = check;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getState() {
        return state;
    }
}
