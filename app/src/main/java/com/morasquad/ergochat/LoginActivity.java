package com.morasquad.ergochat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginActivity extends AppCompatActivity {

    private Toolbar mToolBar;
    private Button mLoginButton;


    private FirebaseAuth mAuth;
    private ProgressDialog mLoginProgress;




    private TextInputLayout mLoginEmail;
    private TextInputLayout mLoginPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        Toolbar mToolBar = (Toolbar)findViewById(R.id.login_toolbar);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Login");

        mLoginProgress = new ProgressDialog(this);

        mLoginEmail =(TextInputLayout)findViewById(R.id.login_email);
        mLoginPassword =(TextInputLayout)findViewById(R.id.login_password);
        mLoginButton =(Button) findViewById(R.id.login_btn);

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String logemail=mLoginEmail.getEditText().getText().toString();
                String logpassword=mLoginPassword.getEditText().getText().toString();

                if (!TextUtils.isEmpty(logemail)|| !TextUtils.isEmpty(logpassword)){


                    mLoginProgress.setTitle("Logging in");
                    mLoginProgress.setMessage("Please wait while we check your credentials.");
                    mLoginProgress.setCanceledOnTouchOutside(false);
                    mLoginProgress.show();

                    loginUser(logemail,logpassword);
                }
            }
        });


    }

    private void loginUser(String logemail, String logpassword) {

mAuth.signInWithEmailAndPassword(logemail,logpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {
        if(task.isSuccessful()){

            mLoginProgress.dismiss();

            Intent mainIntent = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(mainIntent);
            finish();

        }else{

            mLoginProgress.hide();
            Toast.makeText(LoginActivity.this,"Cannot sign in , Please check the form and try again",Toast.LENGTH_LONG).show();
        }



    }
});
    }

    @Override
    public void onBackPressed()
    {
        //super.onBackPressed();
        Intent intenttoHome = new Intent(LoginActivity.this,MainActivity.class);
        startActivity(intenttoHome);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
