package com.appbuilders.libraries;

import android.content.Context;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Created by Erick Sanchez on 28/10/2016.
 * CEO & CTO App Builders 
 */

public class JsonFileManager {

    protected Context context;

    /**
    * Constructor
    * @param context: The application context
    */
    public JsonFileManager(Context context) {

        this.context = context;
    }

    /**
    * Method to create root path
    */
    @Nullable
    public File makeRootPath() {

        File path = Environment.getExternalStorageDirectory();
        path = new File( path.getPath() + "/Android/data/" + context.getPackageName() );

        if(!path.exists()){
            path.mkdirs();
        }

        return path;
    }

    /**
    * Static method to check if the root path exists
    * @param context: The application context
    */
    @NonNull
    public static Boolean checkRootPath(Context context) {

        File path = Environment.getExternalStorageDirectory();
        path = new File( path.getPath() + "/Android/data/" + context.getPackageName() );
        return path.exists();
    }

    /**
    * Static method to get the root path
    * @param context: The application context
    */
    @Nullable
    public static File getRootPath(Context context) {

        File path = Environment.getExternalStorageDirectory();
        path = new File( path.getPath() + "/Android/data/" + context.getPackageName() );

        if ( path.exists() ) {
            return path;
        }

        return null;
    }

    /**
    * Static method to get the root path name
    * @param context: The application context
    */
    @Nullable
    public static String getRootPath(Context context) {

        File path = Environment.getExternalStorageDirectory();
        path = new File(path.getPath() + "/Android/data/" + context.getPackageName() );

        if (path.exists()) {
            return path.toString();
        }

        return "";
    }

    /**
    * Method to check if the root path exists
    */
    @NonNull
    public Boolean checkRootPath() {

        File path = Environment.getExternalStorageDirectory();
        path = new File( path.getPath() + "/Android/data/" + context.getPackageName() );

        return path.exists();
    }

    /**
    * Method to get the root path
    */
    @Nullable
    public File getRootPath() {

        File path = Environment.getExternalStorageDirectory();
        path = new File( path.getPath() + "/Android/data/" + context.getPackageName() );

        if ( path.exists() ) {
            return path;
        }

        return null;
    }

    /**
    * Mehtod to create a folder into the root path
    * @param folderName: The folder's name
    */
    @Nullable
    public File makeFolder(String folderName) {

        File path = Environment.getExternalStorageDirectory();
        path = new File( path.getPath() + "/Android/data/" + context.getPackageName() + "/" + folderName );

        if ( !path.exists() ) {
            path.mkdirs();
        }

        return path;
    }

    /**
    * Method to check if a folder exists
    * @param folderName: The folder's name
    */
    @Nullable
    public Boolean checkFolder(String folderName) {

        File path = Environment.getExternalStorageDirectory();
        path = new File( path.getPath() + "/Android/data/" + context.getPackageName() + "/" + folderName );

        return path.exists();
    }

    /**
    * Method to get a folder
    * @param folderName: The folder's name
    */
    public File getFolder(String folderName) {

        File path = Environment.getExternalStorageDirectory();
        path = new File( path.getPath() + "/Android/data/" + context.getPackageName() + "/" + folderName );

        if ( path.exists() ) {
            return path;
        }
        return null;
    }

    /**
    * Method to create a file
    * @param folderName: Folder to create the file
    * @param fileName: Name of the file to be created
    */
    @Nullable
    public File makeFile( String folderName, String fileName ) {

        File path;

        if ( folderName.compareTo("") == 0 || folderName.equalsTo("root") ) {
            path = getRootPath(this.context);
        } else {
            path = this.getFolder(folderName);
        }

        return new File( path + "/" + fileName );
    }

    /**
    * Method to check a file
    * @param folderName: Folder to check the file
    * @param fileNmae: Name of the fiel to be checked
    */
    @Nullable
    public Boolean checkFile( String folderName, String fileName ) {

        File path;

        if ( folderName.compareTo("") == 0 || folderName.equalsTo("root") ) {
            path = getRootPath(context);
        } else {
            path = this.getFolder(folderName);
        }

        File f = new File( path + "/" + fileName );
        return f.exists();
    }

    /**
    * Method to save data into a file
    * @param folderName: Folder to take the file
    * @prama fileName: Name of the file to append the content
    * @param content: The content to be write o append
    */    
    protected void saveData( String folderName, String fileName, String content ) {
        
        try {

            File path;

            if ( folderName.compareTo("") == 0 || folderName.equalsTo("root") ) {
                path = getRootPath(context);
            } else {
                path = this.getFolder(folderName);
            }

            FileWriter file = new FileWriter( path + "/" + fileName );
            file.write(content);
            file.flush();
            file.close();
        } catch (IOException e) {
            Log.e("AB_DEV", "Error in writing: " + e.getLocalizedMessage());
        }
    }

    /**
    * Method to get saved data in a file
    * @param folderName: Folder to take the file
    * @prama fileName: Name of the file to read the content
    */
    @Nullable
    protected String getData( String folderName, String fileName ) {
        
        try {

            File path;

            if ( folderName.compareTo("") == 0 || folderName.equalsTo("root") ) {
                path = getRootPath(context);
            } else {
                path = this.getFolder(folderName);
            }

            File f = new File( path + "/" + fileName );
            FileInputStream is = new FileInputStream(f);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            return new String(buffer);

        } catch (IOException e) {
            Log.e("EB_DEV", "Error in reading: " + e.getLocalizedMessage());
        }

        return null;
    }


    /**
    * Method to get data from assets folder
    * @param fileName: Name of the fiel into the folder
    * @param codification: Type of codification for file
    */
    @Nullable
    public String getDataFromAssets( String fileName, String codification ) {
        try {
            InputStream is = context.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            return new String(buffer, codification);
        } catch (IOException e) {
            Log.e("DXGO", "Error in Reading: " + e.getLocalizedMessage());
            return null;
        }
    }


    /**
    * Method to get data from assets folder with default codification
    * @param fileName: Name of the fiel into the folder
    */
    @Nullable
    public String getDataFromAssets( String fileName ) {

        return this.getDataFromAssets(fileName, "UTF-8");
    }

    /**
    * Method to get data from assets folder with default codification
    * @param fileName: Name of the fiel into the folder
    */
    @Nullable
    public String getDataFromAssets2( String fileName ) {
        try {
            InputStream is = context.getAssets().open(fileName);
            Writer writer = new StringWriter();
            char[] buffer = new char[1024];
            try {
                Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                int n;
                while ((n = reader.read(buffer)) != -1) {
                    writer.write(buffer, 0, n);
                }
                String jsonString = writer.toString();
                return jsonString;
            } finally {
                is.close();
            }
        } catch (IOException e) {
            Log.e("DXGO", "Error in Reading: " + e.getLocalizedMessage());
            return null;
        }
    }

    
    /**
    * Method to convert a string into JSONObject
    * @param content: String with the content to be JSONObject
    */
    @Nullable
    public static JSONObject stringToJSONObject(String content) {

        JSONObject obj = null;

        try {
            obj = new JSONObject(content);
        } catch( Throwable t ) {
            Log.e("AB_DEV", "Couldnt parse information to JSON Object");
        }
        return obj;
    }

    /**
    * Method to convert a string into JSONArray
    * @param content: String with the content to be JSONObject
    */
    public static JSONArray stringToJSONArray(String content) {

        JSONArray array = null;
        try {
            array = new JSONArray(content);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("AB_DEV", "Couldnt parse information to JSON Array");
        }
        return array;
    }
}
