package Json;

import org.codehaus.jackson.JsonNode;

import java.io.IOException;

public interface ParseJson {

    public JsonNode getJsonnode(String content) throws IOException;
}
