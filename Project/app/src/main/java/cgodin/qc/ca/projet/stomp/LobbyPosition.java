package cgodin.qc.ca.projet.stomp;

public class LobbyPosition {
    private Integer position;
    private String role;

    public LobbyPosition() {
    }

    public LobbyPosition(String role) {
        this.role = role;
    }

    public LobbyPosition(String role, int position) {
        this.position = position;
        this.role = role;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public LobbyRole getRole() {
        return LobbyRole.valueOf(role);
    }
}
