package com.example.cora.sportverwaltungveranstalter.activity.account;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.cora.sportverwaltungveranstalter.R;
import com.example.cora.sportverwaltungveranstalter.activity.base.ConnectionToDatabase;
import com.example.cora.sportverwaltungveranstalter.businesslogic.connection.DatabaseConnection;
import com.example.cora.sportverwaltungveranstalter.businesslogic.data.Account;

import java.util.InputMismatchException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        this.button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {


                    String username = editText_username.getText().toString();
                    String email = editText_email.getText().toString();
                    String password = editText_password.getText().toString();
                    String passwordConfirm = editText_passwordConfrim.getText().toString();

                    check(email, username, password, passwordConfirm);
                    int code = connection.registerVeranstalter(new Account(email, username, password));
                    int code = connection.registerVeranstalter();

                }catch(NullPointerException nex){

                }catch(Exception ex){

                }
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
