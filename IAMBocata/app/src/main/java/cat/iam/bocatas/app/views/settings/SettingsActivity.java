package cat.iam.bocatas.app.views.settings;

import android.content.Intent;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import cat.iam.bocatas.app.R;
import cat.iam.bocatas.app.service.SessionManager;
import cat.iam.bocatas.app.views.login.GoogleLoginActivity;

/**
 * Activitat que mostra les preferencies de l'aplicació
 * */

public class SettingsActivity extends PreferenceActivity {

    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        final SessionManager sessionManager = new SessionManager(this);

        Preference closeSession = findPreference(getString(R.string.keyLogoutPreferenceButton));
        closeSession.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                if(sessionManager.isLoggedIn()) sessionManager.logoutUser();
                else FirebaseAuth.getInstance().signOut();
                return false;
            }
        });

        mAuth = FirebaseAuth.getInstance();
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if( firebaseAuth.getCurrentUser() == null && !sessionManager.isLoggedIn()) {
                    Intent intent = new Intent(SettingsActivity.this, GoogleLoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };


    }


    @Override
    protected void onResume() {
        super.onResume();
        mAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mAuthStateListener!=null) {
            mAuth.removeAuthStateListener(mAuthStateListener);
        }

    }
}
