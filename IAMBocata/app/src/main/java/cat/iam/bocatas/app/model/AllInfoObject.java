package cat.iam.bocatas.app.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe que conte tota la informació que necessita l'aplicació.
 */

public class AllInfoObject implements Serializable {

    private User user;
    private List<Product> productList;
    private List<CheckOutObject> checkouts;


    public boolean running;
    public int timeOpen;
    public int timeClose;
    public int marginMins;

    public AllInfoObject() {

    }

    public AllInfoObject(User user, List<Product> productList) {
        this.user = user;
        this.productList = productList;
        checkouts = new ArrayList<CheckOutObject>();
    }

    public AllInfoObject(User user, List<Product> productList, boolean running,
                         int timeOpen, int timeClose, int marginMins) {
        this.user = user;
        this.productList = productList;
        this.checkouts = new ArrayList<CheckOutObject>();
        this.running = running;
        this.timeOpen = timeOpen;
        this.timeClose = timeClose;
        this.marginMins = marginMins;
    }

    public User getUser() {
        return user;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public List<CheckOutObject> getCheckouts() {
        return checkouts;
    }

    public void addCheckout(CheckOutObject checkOutObject) {
        checkouts.add(checkOutObject);
    }

    public void cleanCheckOut() {
        checkouts.clear();
    }

    public Product getProductById(int id) {

        for (Product p : productList) {
            if (p.getId() == id ) return p;
        }

        return null;

    }

}
