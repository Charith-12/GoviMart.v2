package com.example.govimart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class NewUserAccountCreate2 extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText Password,RetypePassword;
    Spinner Role;
    Button Register,Back;
    CheckBox Agree;

    String RolesSelected;

    FirebaseAuth fAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user_account_create2);

        Password = (EditText)findViewById(R.id.et_Password);
        RetypePassword = (EditText)findViewById(R.id.et_RetypePassword);
        Role = (Spinner) findViewById(R.id.spi_Role);
        Register = (Button) findViewById(R.id.btn_Register);
        Back = (Button)findViewById(R.id.btn_Back2);
        Agree = (CheckBox)findViewById(R.id.cb_Agree);

        fAuth = FirebaseAuth.getInstance();




        ArrayAdapter<String> RoleAdepter = new ArrayAdapter<String>(NewUserAccountCreate2.this,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.Role));
        RoleAdepter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Role.setAdapter(RoleAdepter);
        Role.setOnItemSelectedListener(this);







        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenCongratulationActivity();

            }
        });

    }

    public boolean ValidateDataInput(){
        String password = Password.getText().toString().trim();
        String passwordRe = RetypePassword.getText().toString().trim();

        if (password.isEmpty()){
            Password.setError("Password is Required");
            Password.requestFocus();
            return false;
        }
        if(password.length()<6){
            Password.setError("Minimum Length of Password should be 6 ");
            Password.requestFocus();
            return false;
        }
        if(passwordRe.isEmpty()){
            RetypePassword.setError("Please retype your password");
            RetypePassword.requestFocus();
            return false;
        }
        if(!passwordRe.equals(password)){
            RetypePassword.setError("Password don't Match");
            RetypePassword.requestFocus();
            return false;
        }

        else {
            return true;
        }


    }



    public void OpenCongratulationActivity(){

        Bundle NewUserDataBundaleRecived= getIntent().getExtras();
        final String FirstName = NewUserDataBundaleRecived.getString("FirstName");
        final String LastName = NewUserDataBundaleRecived.getString("LastName");
        final String MobileNo = NewUserDataBundaleRecived.getString("MobileNo");
        final String Email = NewUserDataBundaleRecived.getString("Email");
        final String Gender = NewUserDataBundaleRecived.getString("Gender");

        if(ValidateDataInput()) {

            Intent intent=new Intent(NewUserAccountCreate2.this,Congatulation.class);
            final String password = Password.getText().toString().trim();
            final String role = RolesSelected.toString();

            FirebaseFirestore db = FirebaseFirestore.getInstance();
            Map<String,Object> user = new HashMap<>();
            user.put("FirstName",FirstName);
            user.put("LastName",LastName);
            user.put("MobileNo",MobileNo);
            user.put("Email",Email);
            user.put("Gender",Gender);
            user.put("password",password);
            user.put("Role",role);

            db.collection("Users")
                    .add(user)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d("TAG","uplord scuess");
                            Toast.makeText(NewUserAccountCreate2.this,"User done",Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w("TAG","Failed");
                            Toast.makeText(NewUserAccountCreate2.this,"user failed",Toast.LENGTH_LONG).show();
                        }
                    });

            startActivity(intent);
            fAuth.createUserWithEmailAndPassword(Email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Log.d("TAG","Suceffully user created");
                                Toast.makeText(NewUserAccountCreate2.this,"User Created",Toast.LENGTH_LONG).show();
                            }
                            else {
                                Log.w("TAG","faillled");
                                Toast.makeText(NewUserAccountCreate2.this,"failed",Toast.LENGTH_LONG).show();

                            }
                        }
                    });
            finish();
        }



    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        RolesSelected=parent.getItemAtPosition(position).toString();
//        Toast.makeText(parent.getContext(),RolesSelected,Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


}