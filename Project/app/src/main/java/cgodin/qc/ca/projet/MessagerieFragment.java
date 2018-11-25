package cgodin.qc.ca.projet;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
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

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import cgodin.qc.ca.projet.stomp.StompTopic;
import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.client.StompClient;
import ua.naiksoftware.stomp.client.StompMessage;


public class MessagerieFragment extends Fragment implements View.OnClickListener {
    View view;
    private StompClient stompClient;

    public MessagerieFragment() {
        // Required empty public constructor
    }

    public void setStompClient(StompClient client) {
        this.stompClient = client;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        stompClient.topic(StompTopic.Subscribe.CHAT_PUBLIC.getTopic()).subscribe(
                stompMessage -> {
                    TextView textView = view.findViewById(R.id.txtDernierMessagePublic);
                    textView.setText(stompMessage.getPayload());
                }
        );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_messagerie,container, false);
        this.view=view;
        (view.findViewById(R.id.btnMessagePublic)).setOnClickListener(this);
        (view.findViewById(R.id.btnMessagePrive)).setOnClickListener(this);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
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
        stompClient.send(StompTopic.Send.CHAT_PUBLIC.getTopic(), "test");
    }

    /**
     * Permet l'envoie de message privé
     */
    private void envoyerMessagePrive(){
        // TODO : implémenter l'envoie de message privé
    }
}
