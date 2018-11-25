package cgodin.qc.ca.projet.asynctasks;

import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import cgodin.qc.ca.projet.models.Compte;

public class RequeteListe<T> extends AsyncTask<String, Integer, List<T>>{
    private Handler<T> handler;
    private Class<T> targetType;

    public RequeteListe(Handler<T> handler, Class<T> targetType) {
        this.handler = handler;
        this.targetType = targetType;
    }

    @Override
    protected List<T> doInBackground(String... strings) {
        try {
            URL url = new URL(strings[0]);

            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");

            return readResponse(connection, targetType);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<T> ts) {
        super.onPostExecute(ts);
        if (ts == null) {
            handler.echecRequete();
        } else {
            handler.afficherList(ts);
        }
    }

    private List<T> readResponse(HttpURLConnection connection, Class<T> targetType) {
        try {
            String response = JsonUtils.readFromConnection(connection);
            return JsonUtils.jsonToListOfObject(response, targetType);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            connection.disconnect();
        }
    }

    public interface Handler<T>  {
        void afficherList(List<T> liste);
        void echecRequete();
    }
}
