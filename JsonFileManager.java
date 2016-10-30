package com.appbuilders.metrotimes;

import android.content.Context;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Erick on 28/10/2016.
 */

public class JsonFileManager {

    protected Context context;

    public JsonFileManager(Context context) {

        this.context = context;
    }

    @Nullable
    protected File makeRootPath() {

        File path = Environment.getExternalStorageDirectory();
        path = new File( path.getPath() + "/Android/data/" + context.getPackageName() );

        if(!path.exists()){
            path.mkdirs();
        }

        return path;
    }

    @NonNull
    public static Boolean checkRootPath(Context context) {

        File path = Environment.getExternalStorageDirectory();
        path = new File( path.getPath() + "/Android/data/" + context.getPackageName() );

        return path.exists();
    }

    @Nullable
    public static File getRootPath(Context context) {

        File path = Environment.getExternalStorageDirectory();
        path = new File( path.getPath() + "/Android/data/" + context.getPackageName() );

        if ( path.exists() ) {
            return path;
        }

        return null;
    }

    // Funciones para crear directorios generales
    @Nullable
    protected File makeFolder(String folderName) {

        File path = Environment.getExternalStorageDirectory();
        path = new File( path.getPath() + "/Android/data/" + context.getPackageName() + "/" + folderName );

        if ( !path.exists() ) {
            path.mkdirs();
        }

        return path;
    }

    @Nullable
    protected Boolean checkFolder(String folderName) {

        File path = Environment.getExternalStorageDirectory();
        path = new File( path.getPath() + "/Android/data/" + context.getPackageName() + "/" + folderName );

        return path.exists();
    }

    protected File getFolder(String folderName) {

        File path = Environment.getExternalStorageDirectory();
        path = new File( path.getPath() + "/Android/data/" + context.getPackageName() + "/" + folderName );

        if ( path.exists() ) {
            return path;
        }
        return null;
    }

    // Funciones para crear archivos
    @Nullable
    protected File makeFile( String subFolder, String nameFile ) {

        File path;

        if ( subFolder.compareTo("") == 0 ) {
            path = getRootPath(context);
        } else {
            path = this.getFolder(subFolder);
        }

        return new File( path + "/" + nameFile );
    }

    @Nullable
    protected Boolean checkFile( String subFolder, String nameFile ) {

        File path;

        if ( subFolder.compareTo("") == 0 ) {

            path = getRootPath(context);
        } else {

            path = this.getFolder(subFolder);
        }

        File f = new File( path + "/" + nameFile );

        return f.exists();
    }

    // Funciones para salvar informacion, y obtener informacion
    protected void saveData( String subFolder, String body, String fileName ) {
        try {

            File path;

            if ( subFolder.compareTo("") == 0 ) {
                path = getRootPath(context);
            } else {
                path = this.getFolder(subFolder);
            }

            FileWriter file = new FileWriter( path + "/" + fileName );
            file.write(body);
            file.flush();
            file.close();
        } catch (IOException e) {
            Log.d("DXGO", "Error in Writing: " + e.getLocalizedMessage());
        }
    }

    @Nullable
    protected String getData( String subFolder, String fileName ) {
        try {

            File path;

            if ( subFolder.compareTo("") == 0 ) {
                path = getRootPath(context);
            } else {
                path = this.getFolder(subFolder);
            }

            File f = new File( path + "/" + fileName );

            FileInputStream is = new FileInputStream(f);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            return new String(buffer);
        } catch (IOException e) {
            Log.e("DXGO", "Error in Reading: " + e.getLocalizedMessage());
            return null;
        }
    }

    // Funciones de manipulacion de JSON y String
    @Nullable
    public static JSONObject stringToJSON(String body ) {

        JSONObject obj;

        try {
            obj = new JSONObject(body);
            return obj;
        } catch( Throwable t ) {
            Log.e("DXGO", "Couldnt parse information to JSON");
            return null;
        }
    }

    @NonNull
    public static Boolean checkValue(JSONArray json, String key, String value ) {

        return json.toString().contains("\"" + key + "\":\"" + value + "\"");
    }

    @NonNull
    public static Boolean checkId(JSONArray json, String id ) {

        return json.toString().contains("\"id\":\"" + id + "\"");
    }
}
