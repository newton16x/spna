package com.app.spna;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class RegistActivity extends AppCompatActivity {

    EditText editName, editSurname, editEmail, editPassword, editPassword2, editPhone;
    Button registButton;
    boolean doubleBackToExitPressedOnce = false;
    private String HOST ="https://newton16x.000webhostapp.com/Users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        editName = findViewById(R.id.registName);
        editSurname = findViewById(R.id.registApelido);
        editEmail = findViewById(R.id.registEmail);
        editPassword = findViewById(R.id.registPassword);
        editPassword2 = findViewById(R.id.registPassword2);
        editPhone = findViewById(R.id.registTelemovel);
        registButton = findViewById(R.id.registButton);

        registButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editName.getText().toString();
                String surname  = editSurname.getText().toString();
                String email = editEmail.getText().toString().toLowerCase();
                String password = editPassword.getText().toString();
                String password2 = editPassword2.getText().toString();
                String phone = editPhone.getText().toString();
                Boolean create = true;

                if(name.isEmpty()){
                    editName.setError("Nome obrigatório");
                    create = false;
                }
                if(surname.isEmpty()){
                    editSurname.setError("Apelido obrigatório");
                    create = false;
                }
                if(email.isEmpty() || !email.contains("@")){
                    editEmail.setError("Email invalido");
                    create = false;
                }
                if(password.isEmpty() || !password.equals(password2)){
                    editPassword.setError("password invalida");
                    editPassword2.setError("password invalida");
                    editPassword.setText("");
                    editPassword2.setText("");
                    create = false;
                }
                if(phone.length()!=9){
                    editPhone.setError("Deve conter 9 numeros");
                    create= false;
                }

                String URL = HOST + "/create.php";

                if(create){
                    Ion.with(RegistActivity.this)
                            .load(URL)
                            .setBodyParameter("name",name)
                            .setBodyParameter("surname",surname)
                            .setBodyParameter("email",email)
                            .setBodyParameter("password",password)
                            .setBodyParameter("phone",phone)
                            .asJsonObject()
                            .setCallback(new FutureCallback<JsonObject>() {
                                @Override
                                public void onCompleted(Exception e, JsonObject result) {

                                    try{
                                        String resultado = result.get("REGISTO").getAsString();

                                        if(resultado.equals("EMAIL_ERRO")){
                                            Toast.makeText(RegistActivity.this, "Email já existe", Toast.LENGTH_LONG).show();
                                            editEmail.setText("");

                                        }else if(resultado.equals("SUCESSO")) {
                                            Toast.makeText(RegistActivity.this, "Registado com sucesso", Toast.LENGTH_LONG).show();
                                            // fechar atividade do registo
                                            Intent intent = new Intent();
                                            setResult(RESULT_OK, intent);
                                            finish();
                                        }else{
                                            Toast.makeText(RegistActivity.this, "Ocorreu um erro - O email já existe!", Toast.LENGTH_LONG).show();
                                            cleanFields();
                                        }
                                    }catch (Exception err){
                                        Toast.makeText(RegistActivity.this,"Ocorreu um erro! " + err,Toast.LENGTH_LONG).show();
                                        cleanFields();
                                    }
                                }
                            });
                }


            }
        });

        TextView tv=(TextView)findViewById(R.id.registVoltar);

        tv.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }


    public void cleanFields(){
        editName.setText("");
        editSurname.setText("");
        editEmail.setText("");
        editPassword.setText("");
        editPassword2.setText("");
        editPhone.setText("");
    }

//    @Override
//    public void onBackPressed() {
//        if (doubleBackToExitPressedOnce) {
//            super.onBackPressed();
//            return;
//        }
//
//        this.doubleBackToExitPressedOnce = true;
//        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
//
//        new Handler().postDelayed(new Runnable() {
//
//            @Override
//            public void run() {
//                doubleBackToExitPressedOnce=false;
//            }
//        }, 2000);
//    }




}
