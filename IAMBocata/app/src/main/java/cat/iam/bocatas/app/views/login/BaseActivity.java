package cat.iam.bocatas.app.views.login;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;

/**
 * Activitat basica que mostra un dialeg del progress
 */

    public class BaseActivity extends AppCompatActivity {


        public ProgressDialog mProgressDialog;

        public void showProgressDialog() {
            if (mProgressDialog == null) {
                mProgressDialog = new ProgressDialog(this);
                mProgressDialog.setMessage("Loanding...");
                mProgressDialog.setIndeterminate(true);
            }

            mProgressDialog.show();
        }

        public void hideProgressDialog() {
            if (mProgressDialog != null && mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }
        }


        @Override
        public void onStop() {
            super.onStop();
            hideProgressDialog();
        }

    }
