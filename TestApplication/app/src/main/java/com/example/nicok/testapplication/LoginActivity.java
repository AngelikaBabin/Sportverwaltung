package com.example.nicok.testapplication;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.database.Cursor;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    private static final String DUMMY_CREDENTIALS = "nico.kandut@gmail.com:niconico";

    private SharedPreferences preferences;
    private ProgressDialog progDailog;

    // UI references.
    private AutoCompleteTextView emailView;
    private EditText passwordView;
    private Button button_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // set UI references
        emailView = findViewById(R.id.email);
        passwordView = findViewById(R.id.password);
        button_login = findViewById(R.id.email_sign_in_button);

        // check for previous login
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String email = preferences.getString("EMAIL", "");
        String password = preferences.getString("PASSWORD", "");

        // and relogin if possible
        if (!email.equals("") && !password.equals("")) {
            emailView.setText(email);
            attemptLogin(email, password);
        }

        // set event handlers
        passwordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    String email = emailView.getText().toString();
                    String password = passwordView.getText().toString();
                    attemptLogin(email, password);
                    return true;
                }
                return false;
            }
        });

        button_login.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailView.getText().toString();
                String password = passwordView.getText().toString();
                attemptLogin(email, password);
            }
        });
    }

    private void attemptLogin(String email, String password) {
        emailView.setError(null);
        passwordView.setError(null);

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            passwordView.setError(getString(R.string.error_invalid_password));
            focusView = passwordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            emailView.setError(getString(R.string.error_field_required));
            focusView = emailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            emailView.setError(getString(R.string.error_invalid_email));
            focusView = emailView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            UserLoginTask authTask = new UserLoginTask(email, password);
            authTask.execute();
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        public void onPreExecute() {
            progDailog = new ProgressDialog(LoginActivity.this);
            progDailog.setMessage("Loading...");
            progDailog.setIndeterminate(false);
            progDailog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progDailog.setCancelable(true);
            progDailog.show();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            boolean result;
            // TODO: attempt authentication against a network service.

            try {
                Thread.sleep(2000); // Simulate network access.
            } catch (InterruptedException e) {
                return false;
            }

            String[] pieces = DUMMY_CREDENTIALS.split(":");
            result = pieces[0].equals(mEmail) && pieces[1].equals(mPassword);

            return result;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            progDailog.dismiss();
            if (success) {
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("EMAIL", mEmail);
                editor.putString("PASSWORD", mPassword);
                editor.apply();

                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            } else {
                passwordView.setError(getString(R.string.error_incorrect_password));
                passwordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            progDailog.cancel();
            SharedPreferences.Editor editor = preferences.edit();
            editor.remove("EMAIL");
            editor.remove("PASSWORD");
            editor.apply();

            Toast.makeText(LoginActivity.this, "Cancelled", Toast.LENGTH_SHORT).show();
        }
    }
}