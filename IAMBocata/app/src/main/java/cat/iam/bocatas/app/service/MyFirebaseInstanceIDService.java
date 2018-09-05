package cat.iam.bocatas.app.service;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import cat.iam.bocatas.app.R;

/**
 * Classe necessaria del FirebaseCloudMessaging (notificacions)
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(refreshedToken);
    }


    public static String getToken(Context context){

        // Get token
        String token = FirebaseInstanceId.getInstance().getToken();

        // Log and toast
        String msg = context.getString(R.string.msg_token_fmt, token);
        Log.d(TAG, msg);

        return token;
    }

    private void sendRegistrationToServer(String refreshedToken) {
        // TODO: Implement this method to send token to your app server.
        //Implementar que el server s'actualitze el token del usuari

        //FirebaseAuth.getInstance().getCurrentUser().getUid()
        //Update a usuaris al usuari amb aquesta uid

    }

}
