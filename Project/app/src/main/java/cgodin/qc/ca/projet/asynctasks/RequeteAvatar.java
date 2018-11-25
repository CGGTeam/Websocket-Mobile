package cgodin.qc.ca.projet.asynctasks;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import cgodin.qc.ca.projet.MainActivity;
import cgodin.qc.ca.projet.MyLogin;
import okhttp3.OkHttpClient;

public class RequeteAvatar extends AsyncTask<String, Void, byte[]> {

    private MainActivity activity;

    public RequeteAvatar(MainActivity activity){
        this.activity = activity;
    }

    private Exception exception;

    protected byte[] doInBackground(String... urls) {
        Log.d("URL", " URL =" + urls[0]);
        URL url = null;
        try {
            url = new URL(urls[0]);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        OkHttpClient client = new OkHttpClient();

        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(url)
                .addHeader("cookie", "JSESSIONID=" + MyLogin.JSESSIONID )
                .addHeader("rest","oui")
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .get()

                .build();

        okhttp3.Response response = null;
        byte[] bytes  = null;
        try {
            response = client.newCall(request).execute();
            bytes = response.body().bytes();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bytes;
    }

    protected void onPostExecute(byte[] feed) {
        activity.showAvatar(feed);
    }
}