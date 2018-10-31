package com.example.mike.crudfileio;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mike.crudfileio.custom_adapters.ProfileAdapter;
import com.example.mike.crudfileio.model.Profile;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    String TAG = "__TAG__";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Retrieving all the data from
        ArrayList<Profile> profiles;
        try {
            profiles = new Profile(this).getAll();
        } catch (Exception e) {
            Toast.makeText( this, "Failed to retrieve the data", Toast.LENGTH_SHORT ).show();
            Log.d(TAG, "onCreate: "+e.getMessage());
            return;
        }

        ListView lv = findViewById( R.id.profileList );
        ProfileAdapter profileAdapter = new ProfileAdapter(this, R.layout.list_item_activity_main, profiles);
        lv.setAdapter( profileAdapter );

    }

    public void addProfile(View view) {
        Intent intent = new Intent( getApplicationContext(), EditProfile.class );
        intent.putExtra("profileID", 0);
        startActivity(intent);
    }
}
