package Json;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

public class ParseJson implements ParseJsonInterface {

    @Override
    public JsonNode getJsonnode(String content) {
        ObjectMapper mapper = new ObjectMapper();
        if (content != "") {
            try {
                return mapper.readTree(content);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
