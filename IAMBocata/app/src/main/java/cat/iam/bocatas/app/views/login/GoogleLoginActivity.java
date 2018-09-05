package cat.iam.bocatas.app.views.login;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import cat.iam.bocatas.app.R;
import cat.iam.bocatas.app.serverconnection.DownloaderGoogleLogin;

/**
 * Activitat que permet realitzar el google login
 */

public class GoogleLoginActivity extends BaseActivity {

    private final static String TAG = "LoginActivity";

    private FirebaseAuth mFirebaseAuth;
    private final static int RC_SIGN_IN = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_login);

        SignInButton signInButton = findViewById(R.id.signin_google_button);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googleLogin();
            }
        });

    }


    public void onNoTensCompteGoogle(View view){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private void googleLogin() {

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        mFirebaseAuth = FirebaseAuth.getInstance();

        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed
                Log.w(TAG, "Google sign in failed", e);

            }
        }

    }

    private void firebaseAuthWithGoogle(final GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
        // [START_EXCLUDE silent]
        showProgressDialog();
        // [END_EXCLUDE]

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mFirebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mFirebaseAuth.getCurrentUser();

                            printUserData(user);

                            updateUI(user);

                        } else {

                            updateUI(null);
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                        }

                        // [START_EXCLUDE]
                        hideProgressDialog();
                        // [END_EXCLUDE]
                    }

                });
    }

    private void printUserData(FirebaseUser user) {

        Log.d("USER DATA", user.getEmail());
        Log.d("USER DATA", user.getDisplayName());
        Log.d("USER DATA", user.getPhotoUrl().toString());
        Log.d("USER DATA", user.getUid());
    }


    private void updateUI(FirebaseUser user) {

        if(user != null ) {
            //Login Successfully

           if(user.getEmail().trim().endsWith("iam.cat")) {

                DownloaderGoogleLogin downloaderGoogleLogin = new DownloaderGoogleLogin(this);
                downloaderGoogleLogin.execute(user);

            } else {

                FirebaseAuth.getInstance().signOut();
                Toast.makeText(this, "Not a iam.cat email. Please sign in with a valid email.", Toast.LENGTH_LONG).show();

            }
        } else {
            Toast.makeText(this, "Unable to login.", Toast.LENGTH_LONG).show();
            //Unable to login
        }

    }

}
