package com.example.mike.crudfileio.model;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.ContactsContract;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class Profile {

    Bitmap profilePhoto;
    String name;
    long id;
    Database db;
    Context ctx;
    listOfIDS idList;


    public Profile(Context ctx){
        this.ctx = ctx;
        db = new Database(ctx);
        idList = new listOfIDS();
    }

    private byte[] imgToBytes(Bitmap img){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        img.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    private Bitmap bytesToImg( byte[] bts ){
        return BitmapFactory.decodeByteArray(bts, 0, bts.length);
    }

    public Bitmap getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(Bitmap profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long insert(){
        id = db.insert(this);
        idList.add(id);
        return id;
    }

    public static Profile New(Context ctx, String name, Bitmap img){
        Profile profile = new Profile(ctx);
        profile.setName(name);
        profile.setProfilePhoto(img);
        profile.insert();
        return profile;
    }

    public static Profile RetrieveById(Context ctx, long id) throws Exception {
        Profile profile = new Profile(ctx);
        return profile.db.retrieveById(id);
    }

    public int delete(){
        return db.delete(id);
    }

    public int update(){
        return db.update(this);
    }

    private class Database extends SQLiteOpenHelper {

        Context ctx;

        public Database(Context ctx){
            super( ctx, SCHEMA.TABLE_NAME, null, SCHEMA.VERSION );
            this.ctx = ctx;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL( "CREATE TABLE "+SCHEMA.TABLE_NAME + " ( " +
                    SCHEMA.COL_NAME + " VARCHAR(100), " +
                    SCHEMA.COL_PHOTO + " BLOB " +
                    " ) " );
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }

        private long insert(Profile profile){
            // Getting database
            SQLiteDatabase database = getWritableDatabase();

            // Saving content values
            ContentValues contentValues = new ContentValues();
            contentValues.put( SCHEMA.COL_NAME, profile.name );
            contentValues.put( SCHEMA.COL_PHOTO, imgToBytes(profile.profilePhoto) );

            // Inserting row
            return database.insert( SCHEMA.TABLE_NAME, null, contentValues );
        }

        private Profile retrieveById( long id ) throws Exception {
            SQLiteDatabase database = getReadableDatabase();
            Cursor cursor = database.rawQuery( "SELECT "+
                    SCHEMA.COL_NAME+" , "+
                    SCHEMA.COL_PHOTO+
                    " FROM "+
                    SCHEMA.TABLE_NAME+
                    " WHERE id="+String.valueOf(id), null);
            if ( cursor.moveToFirst() ){
                Profile profile = new Profile(ctx);
                profile.setName( cursor.getString(cursor.getColumnIndex(SCHEMA.COL_NAME)) );
                profile.setProfilePhoto( bytesToImg(cursor.getBlob(cursor.getColumnIndex(SCHEMA.COL_PHOTO))) );
                return profile;
            }
            throw new Exception("Failed to retrieve Profile by ID");
        }

        private int update(Profile profile){
            SQLiteDatabase database = getWritableDatabase();

            ContentValues contentValues = new ContentValues();
            contentValues.put( SCHEMA.COL_NAME, profile.getName() );
            contentValues.put( SCHEMA.COL_PHOTO, profile.imgToBytes(profile.getProfilePhoto()) );

            return database.update( SCHEMA.TABLE_NAME, contentValues, " WHERE ROWID="+String.valueOf(profile.id), null);
        }

        private int delete( long id ){
            SQLiteDatabase database = getWritableDatabase();
            return database.delete( SCHEMA.TABLE_NAME, " WHERE ROWID="+String.valueOf(id), null );
        }
    }

    private class listOfIDS{
        SharedPreferences sp;
        SharedPreferences.Editor edit;

        private static final String KEY = "list";

        public listOfIDS(){
            sp = ctx.getSharedPreferences( getClass().getName(), Context.MODE_PRIVATE );
            edit = sp.edit();
        }

        private String get(){
            return sp.getString(KEY, "");
        }

        private void put(String as){
            edit.putString(KEY, as);
            edit.commit();
        }

        public ArrayList<Long> getAll(){
            ArrayList<Long> ar = new ArrayList<>();
            String ids[] = get().split(",");
            for (String s : ids) {
                ar.add( Long.valueOf(s) );
            }
            return ar;
        }

        public void delete(long id){
            ArrayList<Long> allIDS = getAll();
            allIDS.remove(id);
            String ids = "";

            for (Long aLong : allIDS) {
                ids += String.valueOf(aLong)+" ";
            }
            ids.trim();
            ids.replace(" ", ",");
            put(ids);
        }

        public void add(long id){
            String ids = get();
            if (!ids.equals("")){
                ids += ",";
            }
            ids += String.valueOf(id);
            put(ids);
        }
    }

    private static class SCHEMA {
        private static final String TABLE_NAME = "Profiles.db";

        private static final String COL_NAME = "name";
        private static final String COL_PHOTO = "photo";

        private static final int VERSION = 1;
    }
}
