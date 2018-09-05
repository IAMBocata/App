package cat.iam.bocatas.app.serverconnection;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import cat.iam.bocatas.app.Constants;
import cat.iam.bocatas.app.model.AllInfoObjectContainer;
import cat.iam.bocatas.app.views.ContainerActivity;
import cat.iam.bocatas.app.views.fragments.CheckOutFragment;
import cat.iam.bocatas.app.views.fragments.MainFragment;

/**
 * Classe que realitza la conexio amb el server i envia la informació de la compra realitzada per l'usuari
 */

public class BuyConnection extends AsyncTask<String, Integer, String> {

    CheckOutFragment fragment;
    String jsonString;

    public BuyConnection(CheckOutFragment fragment) {
        this.fragment = fragment;
    }

    @Override
    protected String doInBackground(String... strings) {

        BufferedReader in = null;
        try {
            in = buy(strings[0]);

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
            boolean b = JSONParser.parseBuy(s);

            if (b) {
                AllInfoObjectContainer.getAllInfoObject().cleanCheckOut();
                fragment.getAdapter().notifyDataSetChanged();
                Toast.makeText(fragment.getActivity(), "Compra realitzada.", Toast.LENGTH_SHORT).show();
                ((ContainerActivity)fragment.getActivity()).loadThisFragment(new MainFragment());
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public BufferedReader buy(String parameters) throws IOException {

        URL url = new URL(Constants.URL_SERVER + Constants.PATH_BUY + parameters);

        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        con.setReadTimeout(5000);
        con.setDoOutput(true);
        con.setRequestMethod("GET");
        con.disconnect();

        return new BufferedReader(new InputStreamReader(con.getInputStream()));
    }
}
