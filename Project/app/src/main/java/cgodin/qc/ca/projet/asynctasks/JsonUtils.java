package cgodin.qc.ca.projet.asynctasks;

        import com.fasterxml.jackson.core.JsonProcessingException;
        import com.fasterxml.jackson.core.type.TypeReference;
        import com.fasterxml.jackson.databind.ObjectMapper;

        import java.io.BufferedReader;
        import java.io.IOException;
        import java.io.InputStreamReader;
        import java.net.HttpURLConnection;
        import java.util.List;

public class JsonUtils {
    public static <T> T jsonToObject(String jsonString, Class<T> targetType) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        return  mapper.readValue(jsonString, targetType);
    }

    public static <T> List<T> jsonToListOfObject(String jsonString) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        return  mapper.readValue(jsonString, new TypeReference<List<T>>(){});
    }

    public static String objectToJson(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonStr = mapper.writeValueAsString(object);

        return jsonStr;
    }

    public static String readFromConnection(HttpURLConnection connection) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = in.readLine()) != null) {
            sb.append(line);
            sb.append("\n");
        }
        in.close();

        return sb.toString();
    }
}
