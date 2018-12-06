package cgodin.qc.ca.projet.stomp;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReponseCommandeDeserializer extends JsonDeserializer<DonneesReponseCommande>{

    @Override
    public DonneesReponseCommande deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);

        ObjectNode node = p.getCodec().readTree(p);
        String typeCommande = node.get("typeCommande").asText();
        SanitizedLobbyUser de = mapper.treeToValue(node.get("de"), SanitizedLobbyUser.class);

        List<String> parametres = new ArrayList<>();
        for (JsonNode itemNode : node.get("parametres")) {
            parametres.add(itemNode.toString());
        }
        String[] parametresArr = new String[parametres.size()];
        parametres.toArray(parametresArr);

        return new DonneesReponseCommande(parametresArr, typeCommande, de);
    }
}
