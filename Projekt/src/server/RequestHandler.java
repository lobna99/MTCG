package main.server;
import java.io.*;
import java.net.Socket;

public class RequestHandler implements Runnable{
    private Socket socket;
    private BufferedWriter output;
    private BufferedReader in;


    public RequestHandler(Socket s) throws IOException {
        this.socket=s;
        this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        this.output =  new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
    }

    @Override
    public void run() {
        try {
            HTTPRequest resp=new HTTPRequest();
            String r=resp.getHttpsContent(in,output);
            in.close();
            output.close();
            //response = this.app.handleRequest(request);
            //output.write(response.get());
        } catch (IOException e) {
            System.err.println(Thread.currentThread().getName() + " Error: " + e.getMessage());
        } finally {
            try {
                if (output != null) {
                    output.close();
                }
                if (in != null) {
                    in.close();
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void registration(String userdata){
        Player ronaldo = new ObjectMapper().readValue(userdata, Player.class);
    }
}
