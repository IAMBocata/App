package cat.iam.bocatas.app.views.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Timer;
import java.util.TimerTask;

import cat.iam.bocatas.app.R;
import cat.iam.bocatas.app.serverconnection.DownloaderGoogleLogin;
import cat.iam.bocatas.app.service.SessionManager;

/**
 * Activitat que mostra el logo i comprova si el usuari esta identificat i ho gestiona
 */


public class SplashActivity extends AppCompatActivity {

    // Set the duration of the splash screen
    private static final long SPLASH_SCREEN_DELAY = 3000;
    private static final String TAG = "SplashScreen";

    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        // Session Manager
        session = new SessionManager(this);

        // Set portrait orientation
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // Hide title bar
        //requestWindowFeature(Window.FEATURE_NO_TITLE);

        TimerTask task = new TimerTask() {
            @Override
            public void run() {

                // Start the next activity

                if(user!=null) {

                    DownloaderGoogleLogin downloaderGoogleLogin = new DownloaderGoogleLogin(SplashActivity.this);
                    downloaderGoogleLogin.execute(user);
                    //SplashActivity.this.finish();
                } else if(session.isLoggedIn()) {

                    session.checkLogin();

                } else {
                    Intent mainIntent = new Intent(SplashActivity.this, GoogleLoginActivity.class);
                    startActivity(mainIntent);
                    //SplashActivity.this.finish();
                }



            }
        };

        // Simulate a long loading process on application startup.
        Timer timer = new Timer();
        timer.schedule(task, SPLASH_SCREEN_DELAY);

    }

}
