package cgodin.qc.ca.projet.stomp;

public class StompTopic {
    public enum Subscribe {
        CHAT_PUBLIC("/topic/public/chat"),
        CHAT_PRIVE("/topic/private/chat");
        private String topic;

        Subscribe(String topic) {
            this.topic = topic;
        }

        public String getTopic() {
            return topic;
        }
    }

    public enum Send {
        CHAT_PUBLIC("/app/public/chat"),
        CHAT_PRIVE("/app/private/chat");
        private String topic;

        Send(String topic) {
            this.topic = topic;
        }

        public String getTopic() {
            return topic;
        }
    }
}
