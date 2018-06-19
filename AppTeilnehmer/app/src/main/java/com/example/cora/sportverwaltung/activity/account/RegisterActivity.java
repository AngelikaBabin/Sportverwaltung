package com.example.cora.sportverwaltung.activity.account;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cora.sportverwaltung.R;
import com.example.cora.sportverwaltung.activity.base.ExposingActivity;
import com.example.cora.sportverwaltung.businesslogic.connection.AsyncTaskHandler;
import com.example.cora.sportverwaltung.businesslogic.connection.AsyncWebserviceTask;
import com.example.cora.sportverwaltung.businesslogic.data.Account;

import java.util.InputMismatchException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.cora.sportverwaltung.businesslogic.misc.HttpMethod.POST;

/**
 * @kandut validation, async listener and code cleanup
 * @kumnig gui structure and activity code
 * @rajic gui design
 */

public class RegisterActivity extends ExposingActivity implements AsyncTaskHandler {
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    Button button_register;
    EditText editText_email, editText_name, editText_password, editText_passwordConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initUIReferences();
        initEventhandlers();
    }

    private void initUIReferences() {
        button_register = findViewById(R.id.button_register);
        editText_email = findViewById(R.id.editText_email);
        editText_name = findViewById(R.id.textView_username);
        editText_password = findViewById(R.id.editText_password);
        editText_passwordConfirm = findViewById(R.id.editText_confirmPassword);
    }

    private void initEventhandlers() {
        button_register.setOnClickListener(view -> {
            try {
                // get view contents
                String email = editText_email.getText().toString();
                String name = editText_name.getText().toString();
                String password = editText_password.getText().toString();
                String passwordConfirm = editText_passwordConfirm.getText().toString();

                // constraints
                check(email, name, password, passwordConfirm);

                // create Teilnehmer
                Account account = new Account(email, name, password);
                String json = gson.toJson(account);

                // register in database
                AsyncWebserviceTask task = new AsyncWebserviceTask(POST, "teilnehmer", this, getApplicationContext());
                task.execute(null, json);

            } catch (NullPointerException ex) {
                Toast.makeText(RegisterActivity.this, "account already exists", Toast.LENGTH_LONG).show();
                ex.printStackTrace();
            } catch (Exception ex) {
                Toast.makeText(RegisterActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
                ex.printStackTrace();
            }
        });
    }

    private void check(String email, String name, String password, String passwordConfirm) {
        if (name == null || name.length() < 3) {
            throw new InputMismatchException(getString(R.string.error_invalidName));
        }

        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        if (!matcher.find()) {
            throw new InputMismatchException(getString(R.string.error_invalidEmail));
        }

        if (password == null || password.length() < 6) {
            throw new InputMismatchException(getString(R.string.error_invalidPassword));
        }

        if (!password.equals(passwordConfirm)) {
            throw new InputMismatchException(getString(R.string.error_invalidConfirmPassword));
        }
    }

    @Override
    public void onPreExecute() {
        progDialog = new ProgressDialog(this);
        progDialog.setMessage("Register...");
        progDialog.setIndeterminate(false);
        progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDialog.setCancelable(false);
        progDialog.show();
    }

    @Override
    public void onSuccess(int statusCode, String content) {
        progDialog.dismiss();
        startActivity(new Intent(RegisterActivity.this, VerifyActivity.class));
    }

    @Override
    public void onError(Error err) {
        Toast.makeText(RegisterActivity.this, err.getMessage() + " - could not create account", Toast.LENGTH_LONG).show();
    }
}
