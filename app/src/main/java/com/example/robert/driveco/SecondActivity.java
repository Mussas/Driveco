package com.example.robert.driveco;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class SecondActivity extends AppCompatActivity
{
    private FirebaseAuth firebaseAuth;
    private Button logout;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_second);

        firebaseAuth = FirebaseAuth.getInstance();
        //logout = (Button)findViewById(R.id.btnLogout);
        //Logout button click event on the middle of the page

    }
    private void Logout()
    {
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(SecondActivity.this, MainActivity.class));
    }
    //This assigns the Menu that we created in the resources
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    //Handles the onclick events on the menu
    //Here you would handle the different options on the menu
    //In the switch statements
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.logoutMenu:
                Logout();
        }
        return super.onOptionsItemSelected(item);
    }
}
