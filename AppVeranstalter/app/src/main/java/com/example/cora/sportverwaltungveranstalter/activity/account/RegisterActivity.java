package com.example.cora.sportverwaltungveranstalter.activity.account;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cora.sportverwaltungveranstalter.R;
import com.example.cora.sportverwaltungveranstalter.activity.base.BaseActivity;
import com.example.cora.sportverwaltungveranstalter.businesslogic.connection.AsyncWebserviceTask;
import com.example.cora.sportverwaltungveranstalter.businesslogic.data.Account;

import java.util.InputMismatchException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.cora.sportverwaltungveranstalter.businesslogic.misc.HttpMethod.POST;

/**
 * @babin
 */
public class RegisterActivity extends BaseActivity {
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    Button button_register;
    EditText editText_username, editText_email, editText_password, editText_passwordConfrim;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initComponents();
        registerEventHandlers();
    }

    public void initComponents(){
        button_register = (Button)findViewById(R.id.button_register);
        editText_username = (EditText) findViewById(R.id.editText_username);
        editText_email = (EditText)findViewById(R.id.editText_email);
        editText_password = (EditText)findViewById(R.id.editText_password);
        editText_passwordConfrim = (EditText) findViewById(R.id.editText_passwordConfirm);
    }

    public void registerEventHandlers(){
        this.button_register.setOnClickListener(view -> {

                try {
                    //view contents
                    String username = editText_username.getText().toString();
                    String email = editText_email.getText().toString();
                    String password = editText_password.getText().toString();
                    String passwordConfirm = editText_passwordConfrim.getText().toString();

                    check(email, username, password, passwordConfirm);

                    //create Veranstalter
                    Account account = new Account(email, username, password);
                    String json = gson.toJson(account);

                    //register in database
                    AsyncWebserviceTask task = new AsyncWebserviceTask(POST, "veranstalter", this, getApplicationContext());
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
        if(name == null || name.length() < 3){
            throw new InputMismatchException(getString(R.string.error_invalidName));
        }

        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        if(!matcher.find()) {
            throw new InputMismatchException(getString(R.string.error_invalidEmail));
        }

        if(password == null || password.length() < 6) {
            throw new InputMismatchException(getString(R.string.error_invalidPassword));
        }

        if(!password.equals(passwordConfirm)) {
            throw new InputMismatchException(getString(R.string.error_invalidConfirmPassword));
        }
    }
}
