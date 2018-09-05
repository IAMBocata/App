package cat.iam.bocatas.app.serverconnection;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import cat.iam.bocatas.app.Constants;
import cat.iam.bocatas.app.R;
import cat.iam.bocatas.app.model.AllInfoObject;
import cat.iam.bocatas.app.model.AllInfoObjectContainer;
import cat.iam.bocatas.app.service.SessionManager;
import cat.iam.bocatas.app.views.ContainerActivity;
import cat.iam.bocatas.app.views.login.GoogleLoginActivity;
import cat.iam.bocatas.app.views.login.LoginActivity;
import cat.iam.bocatas.app.views.login.SplashActivity;

/**
 * Classe que descarrega la informació de la app quan el usuari el s'identifica
 */

public class DownloaderLogin extends AsyncTask<String, Integer, String> {

    private Button buttonLogin;
    Activity activity;
    String jsonString;
    String email;
    String pass;

    public DownloaderLogin(Activity activity) {
        this.activity = activity;
    }

    public void setButtonLogin(Button buttonLogin) {
        this.buttonLogin = buttonLogin;
    }

    @Override
    protected String doInBackground(String... strings) {

        String mail = strings[0];
        String password = strings[1];

        email = mail;
        pass = password;

        BufferedReader in = null;

        try {
            in = getDataFromAPI(mail, password);

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

        if(s.contains("\"auth\":false")) {
            Toast.makeText(activity.getBaseContext(), activity.getString(R.string.loginerror), Toast.LENGTH_SHORT).show();
            buttonLogin.setClickable(true);
            return;
        } else {


        AllInfoObject allInfoObject = null;

        try {
            allInfoObject = JSONParser.getAllInfoObject(s);
            AllInfoObjectContainer.setAllInfoObject(allInfoObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        SessionManager session = new SessionManager(activity.getBaseContext());
        session.createLoginSession(email, pass);

        Intent intent = new Intent(activity, ContainerActivity.class);
        intent.putExtra("allInfo", allInfoObject);
        activity.startActivity(intent);

        }
    }

    public BufferedReader getDataFromAPI(String mail, String password) throws IOException {

        URL url = new URL(Constants.URL_SERVER + Constants.PATH_LOGIN +
                            "mail=" + mail + "&password=" + password + "&API_KEY="
                                + Constants.API_KEY);

        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        con.setReadTimeout(5000);
        con.setDoOutput(true);
        con.setRequestMethod("GET");
        con.disconnect();

        return new BufferedReader(new InputStreamReader(con.getInputStream()));
    }
}
