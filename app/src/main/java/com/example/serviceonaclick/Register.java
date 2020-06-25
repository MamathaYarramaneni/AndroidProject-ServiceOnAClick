package com.example.serviceonaclick;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {

    EditText mFullName,mEmail,mPassword;
    Button mRegistration;
    TextView mLogintext;
    FirebaseAuth fAuth;
    ProgressBar progressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFullName=findViewById(R.id.fullname);
        mEmail=findViewById(R.id.email);
        mPassword=findViewById(R.id.password);

        mRegistration=findViewById(R.id.regbutton);
        mLogintext=findViewById(R.id.logtext);
        fAuth=FirebaseAuth.getInstance();
        progressbar=findViewById(R.id.progressBar);

        if(fAuth.getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }
        Spinner mySpinner =(Spinner) findViewById(R.id.spinnerid);
        ArrayAdapter<String> myAdapter=new ArrayAdapter<String>(Register.this,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.spinner1));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);




        mRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String email=mEmail.getText().toString().trim();
                String password=mPassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Email is required");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    mPassword.setError("Password is required");
                    return;
                }
                if(password.length()<8){
                    mPassword.setError("Password must be atleast 8 characters");
                    return;
                }
                progressbar.setVisibility(View.VISIBLE);

                //register the user

                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(Register.this,"User Created",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }
                        else{
                            Toast.makeText(Register.this,"Error !"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            //progressbar.setVisibility();
                        }
                    }
                });
            }
        });

        mLogintext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });
    }
}
