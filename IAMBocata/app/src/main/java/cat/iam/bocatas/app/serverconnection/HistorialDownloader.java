package cat.iam.bocatas.app.serverconnection;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import cat.iam.bocatas.app.Constants;
import cat.iam.bocatas.app.R;
import cat.iam.bocatas.app.model.AllInfoObject;
import cat.iam.bocatas.app.model.AllInfoObjectContainer;
import cat.iam.bocatas.app.model.Compra;
import cat.iam.bocatas.app.views.ContainerActivity;
import cat.iam.bocatas.app.views.fragments.HistorialCompresFragment;

/**
 * Classe que descarrega la informació del historial d'un client i crea el llistat de productes
 */

public class HistorialDownloader extends AsyncTask<Void, Integer, String> {

    Activity activity;
    String jsonString;

    public HistorialDownloader(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected String doInBackground(Void ... voids) {

        String id = AllInfoObjectContainer.getAllInfoObject().getUser().getId()+"";

        BufferedReader in = null;

        try {
            in = getDataFromAPI(id);

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
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);

    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        ArrayList<Compra> compres = null;

        try {
            compres = JSONParser.getCompres(s);
            HistorialCompresFragment fragment = HistorialCompresFragment.newInstance(compres);
            ((ContainerActivity)activity).getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer,fragment)
                    .commit();


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public BufferedReader getDataFromAPI(String id) throws IOException {

        String sUrl = Constants.URL_SERVER + Constants.PATH_HISTORIAL_BUYS +
                "&API_KEY="+ Constants.API_KEY+"&userId="+id;

        URL url = new URL(sUrl);

        Log.d("Historial", sUrl);

        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        con.setReadTimeout(5000);
        con.setDoOutput(true);
        con.setRequestMethod("GET");
        con.disconnect();

        return new BufferedReader(new InputStreamReader(con.getInputStream()));
    }
}
