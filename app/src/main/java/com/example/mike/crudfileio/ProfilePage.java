package com.example.mike.crudfileio;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mike.crudfileio.model.Profile;

import org.w3c.dom.Text;

public class ProfilePage extends AppCompatActivity {

    private TextView profileName;
    private ImageView profilePhoto;
    private long profileID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        profileName = findViewById( R.id.profProfileName );
        profilePhoto = findViewById( R.id.profProfilePhoto );

        profileID = (Long) getIntent().getExtras().get("profileID");
        refresh();
    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh();
    }

    public void refresh(){
        try {
            Profile p = Profile.RetrieveById(this, profileID);
            profileName.setText( p.getName() );
            profilePhoto.setImageBitmap( p.getProfilePhoto() );

        } catch (Exception e) {
            Toast.makeText( this, "Failed to retrieve with id="+String.valueOf(profileID)+", "+e.getMessage(), Toast.LENGTH_SHORT ).show();
            finish();
        }
    }

    public void edit(View view) {
        Intent intent = new Intent( getApplicationContext(), EditProfile.class );
        intent.putExtra( "profileID", profileID );
        startActivity(intent);
    }
}
