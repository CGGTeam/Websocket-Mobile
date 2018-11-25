package cgodin.qc.ca.projet.stomp;

import cgodin.qc.ca.projet.models.Compte;

public abstract class Message {
    private Compte de;

    public Message() {
    }

    public Message(Compte de) {
        this.de = de;
    }

    public Compte getDe() {
        return de;
    }

    public void setDe(Compte de) {
        this.de = de;
    }
}

