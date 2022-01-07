package server;

import java.io.BufferedWriter;
import java.io.IOException;

public interface Response {
    public void writeHttpGetRequest(BufferedWriter streamWriter, HttpStatus httpStatus) throws IOException;
}
