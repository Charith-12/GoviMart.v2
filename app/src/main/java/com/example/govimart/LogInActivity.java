package com.example.govimart;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LogInActivity extends AppCompatActivity {
    TextView CreateNewAccount , ForgotPassword;
    EditText Email,Password;
    Button LogIn;

    private FirebaseAuth mAuth;
    String email,password;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        CreateNewAccount = (TextView)findViewById(R.id.tv_CreateNewAccount);
        Email = (EditText)findViewById(R.id.et_EmailLogIN);
        Password = (EditText)findViewById(R.id.et_PasswordLogIn);
        LogIn = (Button)findViewById(R.id.btn_LogIn);
        ForgotPassword = (TextView)findViewById(R.id.tv_FogotPassword);




        mAuth = FirebaseAuth.getInstance();



        LogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                UserLogIn();
                LoginUser();


            }
        });



        CreateNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new  Intent(LogInActivity.this,NewUserAccountCreate1.class);
                startActivity(intent);

            }
        });

    }

    public boolean ValidateDataInput(){
        String password = Password.getText().toString().trim();
        String email = Email.getText().toString().trim();

        if(email.isEmpty()){
            Email.setError("Please retype your email");
            Email.requestFocus();
            return false;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Email.setError("Please enter a valid Email");
            Email.requestFocus();
            return false;
        }

        if (password.isEmpty()){
            Password.setError("Password is Required");
            Password.requestFocus();
            return false;
        }

        else {
            return true;
        }


    }

    public void  LoginUser(){

        /*
        Intent intent = new  Intent(LogInActivity.this,MainActivity.class);
        startActivity(intent);
        */

        /**/
        if (ValidateDataInput()){
            UserLogIn();
        }

         /**/
    }

    public void UserLogIn(){

        password = Password.getText().toString().trim();
        email = Email.getText().toString().trim();
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Log.d("TAG", "signInWithEmail:success");
                    Toast.makeText(LogInActivity.this, "Authentication Success!", Toast.LENGTH_SHORT).show();
                    Intent intent = new  Intent(LogInActivity.this,MainActivity.class);
                    startActivity(intent);

                }
                else {
                    Log.w("TAG", "signInWithEmail:success");
                    Toast.makeText(LogInActivity.this, "Authentication Failed.", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }






}