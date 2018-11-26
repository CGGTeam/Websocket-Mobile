package cgodin.qc.ca.projet.stomp;

import com.fasterxml.jackson.core.JsonProcessingException;

import cgodin.qc.ca.projet.asynctasks.JsonUtils;
import ua.naiksoftware.stomp.client.StompClient;

public class HearbeatLoop implements Runnable {
    private static final Long DELAY = 1000L;
    private StompClient stompClient;
    private volatile boolean interrupted;

    public HearbeatLoop(StompClient stompClient) {
        this.stompClient = stompClient;
    }

    public synchronized void interrupt() {
        interrupted = true;
    }

    @Override
    public void run() {
        try {
            while (!interrupted) {
                stompClient.send(StompTopic.Send.HEARTBEAT, JsonUtils.objectToJson(new Courrier("heartbeat")));
                Thread.sleep(DELAY);
            }
        } catch (JsonProcessingException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
