package com.example.robert.driveco;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetActivity extends AppCompatActivity
{
    private TextView userEmail;
    private Button resetPassword;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);
        setupUIViews();
        firebaseAuth = FirebaseAuth.getInstance();
        resetPassword.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(validate())
                {
                    sendResetEmail();
                }
            }
        });

    }

    private void sendResetEmail()
    {
        firebaseAuth.sendPasswordResetEmail(userEmail.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>()
        {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(getApplicationContext(), "Password reset email was sent successfully", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "Oops password reset email could not be sent!", Toast.LENGTH_SHORT).show();
                        }
                    }
        });
    }
    private void setupUIViews()
    {
        userEmail = (TextView)findViewById(R.id.tvResetEmail);
        resetPassword = (Button)findViewById(R.id.btnResetPassword);
    }

    private boolean validate()
    {
        Boolean result = false;
        String email = userEmail.getText().toString();
        if(email.isEmpty())
        {
            Toast.makeText(this, "Please enter your email address", Toast.LENGTH_SHORT).show();
        }
        else
        {
            result = true;
        }
        return result;
    }
}
