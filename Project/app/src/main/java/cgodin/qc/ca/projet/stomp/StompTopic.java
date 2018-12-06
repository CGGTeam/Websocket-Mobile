package cgodin.qc.ca.projet.stomp;

public class StompTopic {
    public static class Subscribe {
        public static String CHAT_PUBLIC = "/topic/public/chat";
        public static String CHAT_PRIVE = "/topic/private/chat";
        public static String COMMANDE = "/topic/battle/lobby";
    }

    public static class Send {
        public static String CHAT_PUBLIC = "/app/public/chat";
        public static String CHAT_PRIVE = "/app/private/chat";
        public static String COMMANDE = "/app/battle/command";
        public static String HEARTBEAT = "/app/battle/heartbeat";
    }
}
