package com.app.spna;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

public class MainActivity extends AppCompatActivity {

    EditText loginEmail, loginPassword;
    Button loginButton;
    boolean doubleBackToExitPressedOnce = false;

    private String HOST = "https://newton16x.000webhostapp.com/Users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginEmail = findViewById(R.id.loginUsername);
        loginPassword = findViewById(R.id.loginPassword);
        loginButton = findViewById(R.id.loginButton);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                String email = loginEmail.getText().toString().toLowerCase();
                String password = loginPassword.getText().toString();

                String URL = HOST + "/login.php";

                Ion.with(MainActivity.this)
                        .load(URL)
                        .setBodyParameter("email", email)
                        .setBodyParameter("password", password)
                        .asJsonObject()
                        .setCallback(new FutureCallback<JsonObject>() {
                            @Override
                            public void onCompleted(Exception e, JsonObject result) {

                                try {
                                    String resultado = result.get("LOGIN").getAsString();


                                    if (resultado.equals("ERRO")) {
                                        Toast.makeText(MainActivity.this, "Email ou palavra passe errado!", Toast.LENGTH_LONG).show();
                                    } else if (resultado.equals("SUCESSO")) {
                                        Toast.makeText(MainActivity.this, "Loged in", Toast.LENGTH_LONG).show();
                                        Intent myIntent = new Intent(view.getContext(), HomeActivity.class);
                                        startActivityForResult(myIntent, 0);
                                    }

                                } catch (Exception err) {
                                    Toast.makeText(MainActivity.this, "Ocorreu um erro", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });

        TextView tv = (TextView) findViewById(R.id.registar);

        tv.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(), RegistActivity.class);
                startActivityForResult(myIntent, 0);
            }
        });
    }



    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }


}
