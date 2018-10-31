package com.example.mike.crudfileio;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }

    public void addProfile(View view) {
        Intent intent = new Intent( getApplicationContext(), EditProfile.class );
        intent.putExtra("profileID", 0);
        startActivity(intent);
    }

    public void goToProfilePage(View view){
        // Check out users profile or something
        Intent intent = new Intent( getApplicationContext(), ProfilePage.class );
        startActivity(intent);
    }
}
