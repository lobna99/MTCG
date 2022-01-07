package main.server;

import java.io.*;

public class HTTPRequest {
    private final String url = "http://localhost:10001";
    private String content;
    private String method;
    private String path;
    public HTTPRequest(){

    }

    public String getUrl() {
        return url;
    }
    public String getContent(BufferedReader in,BufferedWriter out) {
        return (content!=null) ? content : getHttpsContent(in,out);
    }



    public String getHttpsContent(BufferedReader in,BufferedWriter out){
        if( content!=null && !content.isBlank() ) {
            return content;
        }

        try {
            //writeHttpGetRequest(out, url);
            int contentLength = readHttpHeader(in);
            return content = readHttpBody(in, contentLength);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void writeHttpGetRequest(BufferedWriter streamWriter, String websiteDomain) throws IOException {
        streamWriter.write("GET / HTTP/1.1\r\n");
        streamWriter.write("Host: " + websiteDomain + "\r\n");
        streamWriter.write("Accept: */*\r\n" );
        streamWriter.write("\r\n" );
        streamWriter.flush();
    }

    private int readHttpHeader(BufferedReader streamReader) throws IOException {
        String line;
        String[] method;
        int count=0;
        int contentLength = 0;
        while ( (line=streamReader.readLine())!=null ) {

            if (line.isBlank() )
                break;
            if (count==0){
                int cnt=0;
                method=line.split(" ",3);
            for (String a : method){
                if(cnt==0) {
                    this.method = a;
                }else if(cnt==1) {
                    this.path = a;
                }
                ++cnt;
            }
            }
            //System.out.println(line);
            if (line.toLowerCase().startsWith("content-length:") ) {
                contentLength = Integer.parseInt(line.substring(15).trim());
            }
            ++count;
        }
        return contentLength;
    }

    private static String readHttpBody(BufferedReader contentReader, int contentLength) throws IOException {
        StringBuilder sb = new StringBuilder(10000);
        char[] buf = new char[1024];
        int totalLen = 0;
        int len;
        while ((len = contentReader.read(buf)) != -1) {
            sb.append(buf, 0, len);
            totalLen += len;
            if( totalLen >= contentLength )
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

}
