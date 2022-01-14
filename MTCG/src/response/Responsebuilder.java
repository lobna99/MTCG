package response;

import Http.HttpStatus;

import java.io.IOException;
import java.io.PrintWriter;


public class Responsebuilder implements ResponsebuilderInterface{
    private static Responsebuilder OBJ =null;
    private PrintWriter output;

    public static Responsebuilder getInstance() {
        if (OBJ == null)
            OBJ = new Responsebuilder();

        return OBJ;
    }
    private Responsebuilder() {
    }

    public void writeHttpResponse(HttpStatus httpStatus, String msg) throws IOException {
        output.write(httpStatus.code+" "+httpStatus.message+"\r\n");
        output.write(msg+"\r\n");
        output.flush();
    }
    public void setOutput(PrintWriter output) {
        this.output = output;
    }
}