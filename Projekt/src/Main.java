package main;

import server.Server;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Server server = new Server(10001);

        try {
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //main.TestConnection.executeSqlStmt("heast","water");
        //main.TestConnection.getSample();
    }


}
