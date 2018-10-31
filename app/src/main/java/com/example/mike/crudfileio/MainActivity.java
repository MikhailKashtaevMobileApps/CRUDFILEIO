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
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = findViewById( R.id.profileList );

        try {
            refresh();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume()  {
        super.onResume();
        try {
            refresh();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void refresh() throws Exception{
        // Retrieving all the data from
        ArrayList<Profile> profiles;
        profiles = new Profile(this).getAll();
        ProfileAdapter profileAdapter = new ProfileAdapter(this, R.layout.list_item_activity_main, profiles);
        lv.setAdapter( profileAdapter );
    }

    public void addProfile(View view) {
        Intent intent = new Intent( getApplicationContext(), EditProfile.class );
        intent.putExtra("profileID", new Long(0));
        startActivity(intent);
    }
}
