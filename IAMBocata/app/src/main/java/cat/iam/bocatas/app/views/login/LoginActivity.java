package cat.iam.bocatas.app.views.login;

import android.support.design.widget.TextInputEditText;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import cat.iam.bocatas.app.R;
import cat.iam.bocatas.app.serverconnection.DownloaderLogin;

/**
 * Activitat que permet realitzar el login amb correu i contrasenya
 */

public class LoginActivity extends BaseActivity {

    TextInputEditText inputMail;
    TextInputEditText inputPassword;
    private Button buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.inputMail      = (TextInputEditText) findViewById(R.id.laEtMail);
        this.inputPassword  = (TextInputEditText) findViewById(R.id.laEtPassword);
        this.buttonLogin = (Button) findViewById(R.id.laBtnLogin);

        this.inputPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {

                if (i == KeyEvent.KEYCODE_ENTER ) {

                    String mail = inputMail.getText().toString();
                    String password = inputPassword.getText().toString();


                    login(mail, password);
                    return false;
                }

                return true;
            }
        });
    }

    public void onClickLogin(View view){
        login(inputMail.getText().toString(), inputPassword.getText().toString());
    }

    public void login(String mail, String password) {
        DownloaderLogin d = new DownloaderLogin(this);
        d.setButtonLogin(buttonLogin);
        d.execute(mail, password);
        buttonLogin.setClickable(false);
    }


}
