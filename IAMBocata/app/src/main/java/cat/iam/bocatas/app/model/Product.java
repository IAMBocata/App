package cat.iam.bocatas.app.model;

import java.io.Serializable;
import java.util.List;

/**
 * Classe que conté la infomacio dels productes
 */

public class Product implements Serializable {

    private int id;
    private String name;
    private String description;
    private float price;
    private String category;
    private List<String> ingredients;


    private boolean liked;
    private boolean oftheday;
    private String urlPhoto;

    public Product() {

    }

    public Product(String name, String description, float price, String category, List<String> ingredients) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.ingredients = ingredients;
    }

    public Product(int id, String name, String description, float price, String urlPhoto, String category,
                   List<String> ingredients, boolean liked, boolean oftheday) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.ingredients = ingredients;
        this.urlPhoto = urlPhoto;
        this.liked = liked;
        this.oftheday = oftheday;
    }

    public Product(String name, String description, float price, String category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public String getUrlPhoto() {
        return urlPhoto;
    }

    public void setUrlPhoto(String urlPhoto) {
        this.urlPhoto = urlPhoto;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    public String toStringIngredients() {

        String retorn = "";

        for (String nom : ingredients ) {
            retorn += nom+"#";
        }

        return retorn;

    }

    public boolean isOftheday() {
        return oftheday;
    }

    public void setOftheday(boolean oftheday) {
        this.oftheday = oftheday;
    }

    @Override
    public String toString() {
        return "id: " + id + " name: " + name + " liked: " + liked;
    }
}
