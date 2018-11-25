package cgodin.qc.ca.projet;

/*
import android.util.Log;

import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.client.StompClient;

public class MyStomp {

    private StompClient mStompClient;


    public void subscribeStomp()
    {
        Log.d("STOMP","brancherStomp");
        //mStompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, "ws://10.0.2.2:8100/websocketandroid");

        //mStompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, "ws://424v.cgodin.qc.ca:8100/websocketandroid");
        mStompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, "ws://424v.cgodin.qc.ca:8100/websocketandroid");
        mStompClient.lifecycle().subscribe(lifecycleEvent -> {
            switch (lifecycleEvent.getType()) {

                case OPENED:
                    Log.d("STOMP", "Stomp connection opened");
                    break;

                case ERROR:
                    Log.e("STOMP", "Error", lifecycleEvent.getException());
                    break;

                case CLOSED:
                    Log.d("STOMP", "Stomp connection closed");
                    break;
            }
        });

        Log.d("STOMP","brancherStomp-over");
        mStompClient.connect();
        Log.d("STOMP","brancherStomp-connect");

        mStompClient.topic("/message/reponseandroid").subscribe(topicMessage -> {
            Log.d("STOMP", topicMessage.getPayload());
        });

    }
    public void stopStomp()
    {
        Log.d("STOMP","terminerStomp");
        mStompClient.disconnect();
    }

    public void sendStomp()
    {
        Log.d("STOMP","envoyerStomp");
        Long maintenant = System.currentTimeMillis();

        String t = new Long(maintenant).toString() ;

        mStompClient.send("/messages/message/android",  "{\"de\":\"Vénérable\",\"texte\":\"envoyé par Android\",\"creation\":" + t + ",\"id_avatar\":\"v1@dojo\"}").subscribe();

    }
}
*/