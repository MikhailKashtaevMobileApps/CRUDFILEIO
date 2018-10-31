package com.example.mike.crudfileio.custom_adapters;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mike.crudfileio.ProfilePage;
import com.example.mike.crudfileio.R;
import com.example.mike.crudfileio.model.Profile;

import org.w3c.dom.Text;

import java.util.ArrayList;


public class ProfileAdapter extends ArrayAdapter {

    String TAG = "__TAG__";
    private final int resourceLayout;
    private final Context context;

    public ProfileAdapter(Context context, int resource, ArrayList<Profile> items ){
        super(context, resource, items);
        resourceLayout = resource;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(context);
            v = vi.inflate(resourceLayout, null);
        }

        Profile p = (Profile) getItem(position);

        Log.d(TAG, "getView: "+p.toString());

        if (p != null) {
            ImageView im = v.findViewById(R.id.listItemProfilePhoto );
            TextView profileName = v.findViewById( R.id.listItemProfileName );

            if ( im != null ){
                im.setImageBitmap( p.getProfilePhoto() );
            }
            if ( profileName != null ){
                profileName.setText( p.getName() );
            }

            class listViewClickListener implements View.OnClickListener{
                Profile p;

                public void setProfile(Profile p){
                    this.p = p;
                }

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent( context.getApplicationContext(), ProfilePage.class );
                    intent.putExtra( "profileID", p.getId() );
                    context.startActivity(intent);
                }
            }

            listViewClickListener lvl = new listViewClickListener();
            lvl.setProfile(p);

            v.setOnClickListener( lvl );
        }

        return v;
    }

}
