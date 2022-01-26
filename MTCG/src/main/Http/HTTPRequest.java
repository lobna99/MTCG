package Http;

import java.io.BufferedReader;
import java.io.IOException;

public interface HTTPRequest {
    public String getContent();

    public String getMethod();

    public String getPath();

    public String getToken();

}
