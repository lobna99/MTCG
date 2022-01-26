package Json;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

public class ParseJsonImpl implements ParseJson {

    @Override
    public JsonNode getJsonnode(String content) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        if (content != "") {
            return mapper.readTree(content);
        }
        return null;
    }
}
