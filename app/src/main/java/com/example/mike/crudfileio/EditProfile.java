package com.example.mike.crudfileio;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mike.crudfileio.model.Profile;

public class EditProfile extends AppCompatActivity {

    private ImageView profilePhoto;
    private EditText profileName;
    long profileID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        profilePhoto = findViewById( R.id.profilePhoto );
        profileName = findViewById( R.id.profileName );
        Button btnDelete = findViewById( R.id.btnDelete );

        profileID = (long) getIntent().getExtras().get("profileID");

        if ( profileID > 0 ){
            // Display delete button
            btnDelete.setVisibility(Button.VISIBLE);
        }
    }

    public void takePicture(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 1);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            profilePhoto.setImageBitmap(imageBitmap);
        }
    }

    public void save(View view) {
        if ( profilePhoto.getDrawable() == null || profileName.getText().toString().trim() == "" ){
            Toast.makeText(this, "Please enter name and take a picture", Toast.LENGTH_SHORT).show();
            return;
        }
        Bitmap pic = ((BitmapDrawable)profilePhoto.getDrawable()).getBitmap();
        String name = profileName.getText().toString();

        Profile profile = new Profile(this);
        profile.setName(name);
        profile.setProfilePhoto(pic);
        profile.insert();
    }

    public void delete(View view) throws Exception {
        if ( profileID > 0 ){
            Profile profile = Profile.RetrieveById(this, profileID);
            profile.delete();
        }
    }

}
