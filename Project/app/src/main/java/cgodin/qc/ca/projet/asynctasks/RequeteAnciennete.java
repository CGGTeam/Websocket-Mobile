package cgodin.qc.ca.projet.asynctasks;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import cgodin.qc.ca.projet.AncienneteFragment;
import cgodin.qc.ca.projet.MainActivity;
import cgodin.qc.ca.projet.MyLogin;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class RequeteAnciennete extends AsyncTask<String, Void, String> {

    private AncienneteFragment fragment;

    public RequeteAnciennete(AncienneteFragment fragment){
        this.fragment = fragment;
    }

    private Exception exception;

    protected String doInBackground(String... urls) {

        URL url = null;
        try {
            url = new URL(urls[0]);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .build();

        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(url)
                .addHeader("cookie", "JSESSIONID=" + MyLogin.JSESSIONID )
                .addHeader("rest","oui")
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .post(formBody)

                .build();
        String responseData = "vide";
        try {
            okhttp3.Response response = client.newCall(request).execute();
            responseData = response.body().string();

        } catch (IOException e) {
            e.printStackTrace();

            Log.d("ERROR", " ERROR =" + e);
        }
        return responseData;
    }

    protected void onPostExecute(String feed) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        fragment.postExecuted();
    }
}