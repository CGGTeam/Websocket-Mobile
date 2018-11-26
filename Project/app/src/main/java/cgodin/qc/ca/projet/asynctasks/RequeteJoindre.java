package cgodin.qc.ca.projet.asynctasks;

import android.os.AsyncTask;
import android.util.Log;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import cgodin.qc.ca.projet.AncienneteFragment;
import cgodin.qc.ca.projet.MyLogin;
import cgodin.qc.ca.projet.stomp.Commande;
import cgodin.qc.ca.projet.stomp.StompTopic;
import cgodin.qc.ca.projet.stomp.TypeCommande;
import io.reactivex.CompletableObserver;
import io.reactivex.disposables.Disposable;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import ua.naiksoftware.stomp.client.StompClient;

public class RequeteJoindre extends AsyncTask<String, Void, String> {
    private StompClient client;

    public RequeteJoindre(StompClient client) {
        this.client = client;
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Commande commande = new Commande(TypeCommande.JOINDRE);
        try {
            client.send(StompTopic.Send.COMMANDE, JsonUtils.objectToJson(commande))
            .subscribe();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}