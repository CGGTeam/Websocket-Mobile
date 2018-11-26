package cgodin.qc.ca.projet.asynctasks;

import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import cgodin.qc.ca.projet.MainActivity;
import cgodin.qc.ca.projet.MyLogin;
import cgodin.qc.ca.projet.models.Compte;
import cgodin.qc.ca.projet.models.CompteImpl;
import okhttp3.OkHttpClient;

public class RequeteInfoCompte extends AsyncTask<String, Void, String> {

    private MainActivity activity;

    public RequeteInfoCompte(MainActivity activity){
        this.activity = activity;
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

        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(url)
                .addHeader("cookie", "JSESSIONID=" + MyLogin.JSESSIONID )
                .addHeader("rest","oui")
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .get()

                .build();
        String responseData = "vide";
        try {
            okhttp3.Response response = client.newCall(request).execute();
            responseData = response.body().string();
            MyLogin.compteCourant = (CompteImpl) JsonUtils.jsonToObject(responseData, Compte.class);
        } catch (IOException e) {
            e.printStackTrace();

            Log.d("ERROR", " ERROR =" + e);
        }
        return responseData;
    }

    protected void onPostExecute(String feed) {
        Log.d("onPostExecute", " onPostExecute =" + feed);
        activity.updateHeaderInfo(feed);
    }
}