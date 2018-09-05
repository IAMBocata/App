package cat.iam.bocatas.app.serverconnection;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import cat.iam.bocatas.app.Constants;
import cat.iam.bocatas.app.model.AllInfoObjectContainer;
import cat.iam.bocatas.app.model.Product;

/**
 * Classe que es comunica amb el servidor per a actualizar la informació del servidor quan un client deixa de agradar-li un producte
 */

public class DislikeConnection extends AsyncTask<Product, Void, String> {

    String jsonString;

    @Override
    protected String doInBackground(Product... products) {

        String parameters = "productId=" + products[0].getId() +
                            "&userId=" + AllInfoObjectContainer.getAllInfoObject().getUser().getId() +
                            "&API_KEY=" + Constants.API_KEY;

        BufferedReader in = null;

        try {
            in = like(parameters);

            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            in.close();

            jsonString = response.toString();

            Log.d("json", jsonString);

            return jsonString;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        try {
            if (JSONParser.parseLikeOrDislike(s)) {
                Log.d("Like", "Dislike OK");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public BufferedReader like(String parameters) throws IOException {

        URL url = new URL(Constants.URL_SERVER + Constants.PATH_DISLIKE + parameters);

        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        con.setReadTimeout(5000);
        con.setDoOutput(true);
        con.setRequestMethod("GET");
        con.disconnect();

        return new BufferedReader(new InputStreamReader(con.getInputStream()));
    }
}
