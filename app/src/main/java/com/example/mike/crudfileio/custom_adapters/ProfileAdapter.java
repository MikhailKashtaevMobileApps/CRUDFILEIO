package com.example.mike.crudfileio.custom_adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.mike.crudfileio.model.Profile;

import java.util.ArrayList;


public class ProfileAdapter extends ArrayAdapter {

    private final int resourceLayout;
    private final Context context;

    public ProfileAdapter(Context context, int resource, ArrayList<Profile> items ){
        super(context, resource, items);
        resourceLayout = resource;
        this.context = context;
    }
/*
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(context);
            v = vi.inflate(resourceLayout, null);
        }

        Profile p = (Profile) getItem(position);

        if (p != null) {
            ImageView im = (ImageView) v.findViewById(R.id.profilePhoto );
            TextView tt2 = (TextView) v.findViewById(R.id.categoryId);
            TextView tt3 = (TextView) v.findViewById(R.id.description);

            if (tt1 != null) {
                tt1.setText(p.getId());
            }

            if (tt2 != null) {
                tt2.setText(p.getCategory().getId());
            }

            if (tt3 != null) {
                tt3.setText(p.getDescription());
            }
        }

        return v;
    }
*/
}
