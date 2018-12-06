package cgodin.qc.ca.projet.stomp;

public class Lobby implements SerializableLobby {
    private LobbyUserData[] ailleurs;
    private LobbyUserData[] spectateurs;
    private LobbyUserData[] combattants;
    private LobbyUserData rouge;
    private LobbyUserData blanc;
    private LobbyUserData arbitre;

    @Override
    public LobbyUserData[] getAilleurs() {
        return ailleurs;
    }

    @Override
    public LobbyUserData[] getSpectateurs() {
        return spectateurs;
    }

    @Override
    public LobbyUserData[] getCombattants() {
        return combattants;
    }

    @Override
    public LobbyUserData getBlanc() {
        return blanc;
    }

    @Override
    public LobbyUserData getRouge() {
        return rouge;
    }

    @Override
    public LobbyUserData getArbitre() {
        return arbitre;
    }
}
