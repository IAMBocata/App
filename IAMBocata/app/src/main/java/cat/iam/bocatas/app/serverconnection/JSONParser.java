package cat.iam.bocatas.app.serverconnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cat.iam.bocatas.app.model.AllInfoObject;
import cat.iam.bocatas.app.model.AllInfoObjectContainer;
import cat.iam.bocatas.app.model.CheckOutObject;
import cat.iam.bocatas.app.model.Compra;
import cat.iam.bocatas.app.model.Product;
import cat.iam.bocatas.app.model.User;

/**
 * Classe que es parseja les dades que rep del servidor i crea les classes pertinents
 */

public class JSONParser {

    /**
     * Parseja les totes les dades que es descarguen nomes inicia l'aplicacio
     */


    public static AllInfoObject getAllInfoObject(String s) throws JSONException {

        JSONObject allJson = new JSONObject(s);

        // -------------------------------- Recoger información del usuario del JSON ---------------
        JSONObject user = allJson.getJSONObject("userdata");

        User u = new User(Integer.parseInt(user.getString("id")),
                            user.getString("googleId"),
                            user.getString("name"),
                            user.getString("mail"),
                            user.getString("photoUrl"),
                            user.getString("qrPhotoUrl"),
                            user.getString("registerDate"),
                            Float.parseFloat(user.getString("money")),
                            false);

        // -------------------------------- Recoger información de los productos del JSON ----------
        JSONArray productsJson = allJson.getJSONArray("products");
        List<Product> products = new ArrayList<Product>();

        for (int i = 0; i < productsJson.length(); i++) {

            JSONObject thisProductJSON = productsJson.getJSONObject(i);

            /*// Recoger el array de intgredientes
            List<String> ingredients = new ArrayList<String>();
            JSONArray jsonArrayIngredients = thisProductJSON.getJSONArray("ingredients");

            for (int j = 0; j < jsonArrayIngredients.length(); j++) {
                ingredients.add(jsonArrayIngredients.getString(j));
            }

            // Construimos el objecto
            Product p = new Product(Integer.parseInt(thisProductJSON.getString("id")),
                                    thisProductJSON.getString("name"),
                                    thisProductJSON.getString("description"),
                                    Float.parseFloat(thisProductJSON.getString("price")),
                                    thisProductJSON.getString("photoPath"),
                                    thisProductJSON.getString("category"),
                                    ingredients,
                                    thisProductJSON.getBoolean("liked"),
                                    thisProductJSON.getBoolean("oftheday"));*/
            Product p = readProduct(thisProductJSON);

            products.add(p);
        }

        // PARAMETERS

        JSONObject parameters = allJson.getJSONObject("parameters");
        int timeOpen = Integer.parseInt(parameters.getString("TIME_OPEN"));
        int timeClose = Integer.parseInt(parameters.getString("TIME_CLOSE"));
        int margin = Integer.parseInt(parameters.getString("MARGIN_MIN"));
        boolean running = Boolean.parseBoolean(parameters.getString("RUNNING").toLowerCase());

        AllInfoObject allInfoObject = new AllInfoObject(u, products, running, timeOpen, timeClose, margin);
        return allInfoObject;
    }

    /**
     * Parseja un objecte i retorna un producte
     */

    private static Product readProduct(JSONObject thisProductJSON) throws JSONException {

        // Recoger el array de intgredientes
        List<String> ingredients = new ArrayList<String>();
        JSONArray jsonArrayIngredients = thisProductJSON.getJSONArray("ingredients");

        for (int j = 0; j < jsonArrayIngredients.length(); j++) {
            ingredients.add(jsonArrayIngredients.getString(j));
        }

        // Construimos el objecto
        Product p = new Product(Integer.parseInt(thisProductJSON.getString("id")),
                thisProductJSON.getString("name"),
                thisProductJSON.getString("description"),
                Float.parseFloat(thisProductJSON.getString("price")),
                thisProductJSON.getString("photoPath"),
                thisProductJSON.getString("category"),
                ingredients,
                thisProductJSON.getBoolean("liked"),
                thisProductJSON.getBoolean("oftheday"));


        return p;

    }

    /**
     * Parseja un objecte i retorna un producte
     */
    public static boolean parseBuy(String s) throws JSONException {
        JSONObject allJson = new JSONObject(s);
        return allJson.getBoolean("done");
    }

    /**
     * Parseja un objecte i retorna un producte
     */
    public static boolean parseLikeOrDislike(String s) throws JSONException {
        JSONObject allJson = new JSONObject(s);
        return allJson.getBoolean("done");
    }


    /**
     * Parseja les dades per obtindre el historial de compres
     */
    public static ArrayList<Compra> getCompres(String s) throws JSONException {

        JSONObject allJson = new JSONObject(s);

        JSONArray compresJson = allJson.getJSONArray("buys");
        ArrayList<Compra> compres = new ArrayList<>();

        for (int i = 0; i < compresJson.length(); i++) {

            JSONObject thisProductJSON = compresJson.getJSONObject(i);

            String date = thisProductJSON.getString("buydate");
            float price = 0;

            // Recoger el array Productes
            List<CheckOutObject> checkOut = new ArrayList<>();
            JSONArray jsonArraycheckOut = thisProductJSON.getJSONArray("checkouts");

            for (int j = 0; j < jsonArraycheckOut.length(); j++) {
                JSONObject compra = jsonArraycheckOut.getJSONObject(j);
                int quantitat = compra.getInt("quantity");
                int idProduct = compra.getInt("idProduct");
                Product p = AllInfoObjectContainer.getAllInfoObject().getProductById(idProduct);
                price += quantitat*p.getPrice();
                checkOut.add(new CheckOutObject(quantitat, p));

            }

            compres.add(new Compra(checkOut, date, price, thisProductJSON.getString("state")));

        }


        return compres;

    }

}