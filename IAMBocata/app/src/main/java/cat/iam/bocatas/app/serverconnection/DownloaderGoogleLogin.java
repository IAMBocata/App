package cat.iam.bocatas.app.serverconnection;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import cat.iam.bocatas.app.Constants;
import cat.iam.bocatas.app.R;
import cat.iam.bocatas.app.model.AllInfoObject;
import cat.iam.bocatas.app.model.AllInfoObjectContainer;
import cat.iam.bocatas.app.views.ContainerActivity;
import cat.iam.bocatas.app.views.login.GoogleLoginActivity;

/**
 * Classe que descarrega la informació de la app quan el usuari el s'identifica amb google
 */

public class DownloaderGoogleLogin extends AsyncTask<FirebaseUser, Void, String> {

    private Activity activity;

    public DownloaderGoogleLogin(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected String doInBackground(FirebaseUser... users) {

        try {
            BufferedReader in = getDataFromAPI(users[0]);

            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            in.close();

            String jsonString = response.toString();

            Log.d("json", jsonString);

            return jsonString;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String json) {

        if (json.contains("\"auth\":false")) {
            Toast.makeText(activity.getBaseContext(), activity.getString(R.string.googleloginerror), Toast.LENGTH_SHORT).show();
            activity.startActivity(new Intent(activity, GoogleLoginActivity.class));
            return;
        }

        AllInfoObject allInfoObject = null;

        try {
            allInfoObject = JSONParser.getAllInfoObject(json);
            AllInfoObjectContainer.setAllInfoObject(allInfoObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Intent intent = new Intent(activity, ContainerActivity.class);
        intent.putExtra("allInfo", allInfoObject);
        activity.startActivity(intent);
        activity.finish();
    }

    public BufferedReader getDataFromAPI(FirebaseUser firebaseUser) throws IOException {

        String id="";

        for (UserInfo info : firebaseUser.getProviderData()) {
            if (info.getProviderId().contains("google")) id = info.getUid();
        }

        String fullurl = Constants.URL_SERVER + Constants.PATH_GOOGLE_LOGIN_DOWNLOADER +
                "?googleName=" + firebaseUser.getDisplayName().replace(' ', '+') + "&googleId=" + id
                    + "&mail=" + firebaseUser.getEmail() + "&API_KEY=" + Constants.API_KEY +
                        "&photoUrl=" + firebaseUser.getPhotoUrl();

        Log.d("URL", fullurl);

        URL url = new URL(fullurl);

        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        con.setReadTimeout(5000);
        con.setDoOutput(true);
        con.setRequestMethod("GET");

        return new BufferedReader(new InputStreamReader(con.getInputStream()));
    }
}
