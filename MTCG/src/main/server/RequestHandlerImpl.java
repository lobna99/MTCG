package server;

import Http.HTTPRequestImpl;

import java.io.*;
import java.net.Socket;
import java.sql.SQLException;


public class RequestHandlerImpl implements Runnable, RequestHandler {
    private final Socket socket;
    private final PrintWriter output;
    private final BufferedReader in;
    private final HTTPRequestImpl currentRequest = new HTTPRequestImpl();

    public RequestHandlerImpl(Socket s) throws IOException {
        this.socket = s;
        this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        this.output = new PrintWriter(this.socket.getOutputStream(), true);
    }

    @Override
    public void run() {
        try {
            responsebuilder.setOutput(output);
            currentRequest.getHttpsContent(in);
            handlerequests();
        } catch (IOException | InterruptedException | SQLException e) {
            System.err.println(Thread.currentThread().getName() + " Error: ");
            e.printStackTrace();
        } finally {
            closeEverything();
        }
    }

    public void handlerequests() throws IOException, InterruptedException, SQLException {
        PathHandlerImpl pathHandler = new PathHandlerImpl();
        pathHandler.handlePath(currentRequest);
    }

    public void closeEverything() {
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
