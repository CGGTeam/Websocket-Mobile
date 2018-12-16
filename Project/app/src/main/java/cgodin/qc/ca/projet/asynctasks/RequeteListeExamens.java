package cgodin.qc.ca.projet.asynctasks;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import cgodin.qc.ca.projet.MyLogin;
import cgodin.qc.ca.projet.models.Combat;

public class RequeteListeExamens<T> extends AsyncTask<String, Integer, List<T>>{
    private Handler<T> handler;
    private Class<T> targetType;

    public RequeteListeExamens(Handler<T> handler, Class<T> targetType) {
        this.handler = handler;
        this.targetType = targetType;
    }

    @Override
    protected List<T> doInBackground(String... strings) {
        try {
            URL url = new URL(strings[0]);

            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestProperty("cookie", "JSESSIONID=" + MyLogin.JSESSIONID);
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
        try {
            if (ts == null) {
                handler.echecRequete();
            } else {
                handler.remplirListeExamens(ts);
            }
        } catch (Exception ignored) {}
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
        void remplirListeExamens(List<T> liste);
        void echecRequete();
    }
}
