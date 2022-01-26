package response;

import Http.HttpStatus;


import java.io.IOException;

public interface ResponsebuilderInterface {
    public void writeHttpResponse(HttpStatus httpStatus, String msg) throws IOException;
}
