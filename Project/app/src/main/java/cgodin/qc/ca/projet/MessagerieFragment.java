package cgodin.qc.ca.projet;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.core.JsonProcessingException;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import cgodin.qc.ca.projet.asynctasks.JsonUtils;
import cgodin.qc.ca.projet.stomp.Courrier;
import cgodin.qc.ca.projet.stomp.Reponse;
import cgodin.qc.ca.projet.stomp.StompTopic;
import io.reactivex.CompletableObserver;
import io.reactivex.disposables.Disposable;
import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.client.StompClient;
import ua.naiksoftware.stomp.client.StompMessage;


public class MessagerieFragment extends Fragment implements View.OnClickListener {
    View view;
    private StompClient stompClient;
    private int[] idsPublicPermis = new int[] { 3, 4 };
    private int[] idsPrivePermis = new int[] { 2, 3, 4};
    private List<Disposable> subscriptions = new ArrayList<>();

    public MessagerieFragment() {
        // Required empty public constructor
    }

    public void setStompClient(StompClient client) {
        this.stompClient = client;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private boolean estConnecte() {
        return !MyLogin.JSESSIONID.equals("");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_messagerie,container, false);
        this.view=view;
        Button btnPublic = view.findViewById(R.id.btnMessagePublic);
        Button btnPrive = view.findViewById(R.id.btnMessagePrive);

        btnPublic.setOnClickListener(this);
        btnPrive.setOnClickListener(this);
        subscriptions.add(stompClient.topic(StompTopic.Subscribe.CHAT_PUBLIC.getTopic()).subscribe(
                stompMessage -> {
                    TextView textView = view.findViewById(R.id.txtDernierMessagePublic);
                    Reponse reponseServeur = JsonUtils.jsonToObject(stompMessage.getPayload(), Reponse.class);
                    textView.setText(reponseServeur.getTexte());
                }
        ));

        if (estConnecte()) {
            subscriptions.add(stompClient.topic(StompTopic.Subscribe.CHAT_PRIVE.getTopic()).subscribe(
                    stompMessage -> {
                        TextView textView = view.findViewById(R.id.txtDernierMessagePrive);
                        Reponse reponseServeur = JsonUtils.jsonToObject(stompMessage.getPayload(), Reponse.class);
                        textView.setText(reponseServeur.getTexte());
                    }
            ));
        }

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        for (Disposable subscription : subscriptions) {
            subscription.dispose();
        }
        subscriptions.clear();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnMessagePublic :
                envoyerMessagePublic();
                break;
            case R.id.btnMessagePrive :
                envoyerMessagePrive();
                break;
        }
    }

    /**
     * Permet l'envoie de message public
     */
    private void envoyerMessagePublic(){
        Courrier message = new Courrier();
        if (MyLogin.compteCourant == null) {
            message.setTexte("anonyme");
        } else {
            message.setTexte(LocalDateTime.now().toString() + " " + MyLogin.compteCourant.getCourriel() + " public");
        }
        try {
            stompClient.send(StompTopic.Send.CHAT_PUBLIC.getTopic(), JsonUtils.objectToJson(message))
                    .subscribe(new CompletableObserver() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            Log.d("STOMP", "Subscribe");
                        }

                        @Override
                        public void onComplete() {
                            Log.d("STOMP", "Completed");
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.d("STOMP", "Erreur", e);
                        }
                    });
        } catch (JsonProcessingException e) {
            Toast.makeText(getContext(), "Échec de la sérialisation", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Permet l'envoie de message privé
     */
    private void envoyerMessagePrive(){
        Courrier message = new Courrier();
        if (MyLogin.compteCourant == null) {
            message.setTexte("anonyme");
        } else {
            message.setTexte(LocalDateTime.now().toString() + " " + MyLogin.compteCourant.getCourriel() + " prive");
        }
        try {
            stompClient.send(StompTopic.Send.CHAT_PRIVE.getTopic(), JsonUtils.objectToJson(message))
                    .subscribe(new CompletableObserver() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            Log.d("STOMP", "Subscribe");
                        }

                        @Override
                        public void onComplete() {
                            Log.d("STOMP", "Completed");
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.d("STOMP", "Erreur", e);
                        }
                    });
        } catch (JsonProcessingException e) {
            Toast.makeText(getContext(), "Échec de la sérialisation", Toast.LENGTH_SHORT).show();
        }
    }
}
