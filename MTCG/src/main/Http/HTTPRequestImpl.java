package Http;

import java.io.*;

public class HTTPRequestImpl implements HTTPRequest {
    private final String url = "http://localhost:10001";
    private String content;
    private String method;
    private String path;
    private String token = "";

    public HTTPRequestImpl() {
    }

    public String getContent() {
        return content;
    }

    public void getHttpsContent(BufferedReader in) {
        if (content != null && !content.isBlank()) {
            return;
        }
        try {
            int contentLength = readHttpHeader(in);
            if (contentLength != 0) {
                content = readHttpBody(in, contentLength);
            } else {
                content = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //-----READ HTTP READER AND EXTRACT EACH PARAM
    private int readHttpHeader(BufferedReader streamReader) throws IOException {
        String line;
        String[] method;
        int count = 0;
        int contentLength = 0;
        while ((line = streamReader.readLine()) != null) {
            if (line.isBlank())
                break;
            if (count == 0) {
                method = line.split(" ", 3);
                this.method = method[0];
                this.path = method[1];
            }
            if (line.toLowerCase().startsWith("authorization: ")) {
                this.token = line.split(" ", 3)[2].split("-", 2)[0];
            }
            if (line.toLowerCase().startsWith("content-length:")) {
                contentLength = Integer.parseInt(line.substring(15).trim());
            }
            ++count;
        }
        return contentLength;
    }

    //user contentlength to read body
    private static String readHttpBody(BufferedReader contentReader, int contentLength) throws IOException {
        StringBuilder sb = new StringBuilder(10000);
        char[] buf = new char[1024];
        int totalLen = 0;
        int len;
        while ((len = contentReader.read(buf)) != -1) {
            sb.append(buf, 0, len);
            totalLen += len;
            if (totalLen >= contentLength)
                break;
        }
        return sb.toString();
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getToken() {
        return token;
    }
}
