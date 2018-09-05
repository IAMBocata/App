package cat.iam.bocatas.app.model;

import java.io.Serializable;


/**
 * Classe que conté la infomacio del carrito
 */

public class CheckOutObject implements Serializable{

    private int quantity;
    private final Product product;

    public CheckOutObject(int quantity, Product product) {
        this.product = product;
        this.quantity = quantity;
    }

    public float getTotalPrice() {
        return product.getPrice() * quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void add( int num ) {
        quantity += num;
    }

    public void addOne() {
        quantity++;
    }

    public void removeOne() {
        quantity--;
    }

    public void remove( int num ) {
        quantity -= num;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
