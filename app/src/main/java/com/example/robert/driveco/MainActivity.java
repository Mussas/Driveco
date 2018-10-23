package com.example.robert.driveco;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity
{
    private EditText email;
    private EditText password;
    private TextView failedAttemptsTextview;
    private Button login;
    private int failedAttemptCounter = 0;
    private TextView userRegistration;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private TextView userForgotPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupUIViews();
        //Get firebase instance
        firebaseAuth = firebaseAuth.getInstance();
        //Progress Dialog
        progressDialog= new ProgressDialog(this);
        //Get the current firebase user from the database
        FirebaseUser user = firebaseAuth.getCurrentUser();
        //If user is logged then call the finish() function which completes the action
        //Then send them to the secondActivity page
        if(user != null)
        {
            finish();
            startActivity(new Intent(MainActivity.this, SecondActivity.class));
        }
        login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                validate(email.getText().toString(), password.getText().toString());
            }
        });

        //Directing user from Log in page upon the click of the Signup button to the Registration page
        userRegistration.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
            }
        });

        //Takes you to the reset page
        //Make sure to enable the log in button
        userForgotPassword.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(MainActivity.this, ResetActivity.class));
            }
        });
    }
    private void setupUIViews()
    {
        email = (EditText)findViewById(R.id.emailText);
        password = (EditText)findViewById(R.id.passwordText);
        failedAttemptsTextview = (TextView) findViewById(R.id.tvFailedAttempts);
        login = (Button)findViewById(R.id.loginButton);
        //Set the textview to 4 attempts remaining at the beginning of the process
        failedAttemptsTextview.setText("Attempts remaining: 4");
        userRegistration = (TextView)findViewById(R.id.tvRegister);
        userForgotPassword = (TextView)findViewById(R.id.tvResetPassword);

    }
    //If validation is successful then direct him to the homepage which is SecondActivity.class
    //This is where you would put a loading icon/dialog box
    private void validate(String email, String password)
    {
        progressDialog.setMessage("Signing in...");
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>()
        {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task)
            {
                //The dialog gets destroyed once Firebase authenticates the login user
                progressDialog.dismiss();
                if(task.isSuccessful())
                {
                    checkEmailVerification();
                }
                else
                {
                    //If login fails than the counter increases
                    Toast.makeText(MainActivity.this, "Login Failed!", Toast.LENGTH_SHORT).show();
                    failedAttemptCounter++;
                    failedAttemptsTextview.setText("Failed Attempts: " + failedAttemptCounter);
                    if(failedAttemptCounter == 4)
                    {
                        login.setEnabled(false);
                    }
                }
            }
        });

    }
    //Functions that checks if a user has verified his email address
    private void checkEmailVerification()
    {
        FirebaseUser firebaseUser = firebaseAuth.getInstance().getCurrentUser();
        Boolean emailflag = firebaseUser.isEmailVerified();

        if(emailflag)
        {
            finish();
            startActivity(new Intent(MainActivity.this, SecondActivity.class));
            Toast.makeText(MainActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(MainActivity.this, "Verify your Email", Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
        }
    }
}
