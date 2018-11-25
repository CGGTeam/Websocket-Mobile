package cgodin.qc.ca.projet.stomp;

import java.util.List;

import cgodin.qc.ca.projet.MyLogin;
import ua.naiksoftware.stomp.StompHeader;

public class StompUtils {
    public static void addAuthHeadersForUser(List<StompHeader> headers, String username, String password) {
        headers.add(new StompHeader("login", username));
        headers.add(new StompHeader("passcode", password));
        headers.add(new StompHeader("Cookie", "JSESSIONID=" + MyLogin.JSESSIONID));
    }
}
