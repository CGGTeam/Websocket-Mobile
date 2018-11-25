package cgodin.qc.ca.projet;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import ua.naiksoftware.stomp.ConnectionProvider;
import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.client.StompClient;
import ua.naiksoftware.stomp.client.StompMessage;

public class MyStomp {
    private static final String URL = "ws://424v.cgodin.qc.ca";
    private static final Stomp.ConnectionProvider PROVIDER = Stomp.ConnectionProvider.OKHTTP;

    private StompClient stompClient;

    public MyStomp() {
        stompClient = Stomp.over(PROVIDER, URL);
    }

    public void connect () {
        stompClient.connect();
    }

    public void subscribeTo(String topic, Subscriber subscriber) {
        this.stompClient.topic(topic).subscribe(new Subscriber<StompMessage>() {
            @Override
            public void onSubscribe(Subscription s) {

            }

            @Override
            public void onNext(StompMessage stompMessage) {

            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    public void disconnect() {
        stompClient.disconnect();
    }
}
