import java.net.*;
import java.io.*;

public class Server {
    public static void main(String[] args) {
        int portNumber = 1024;
        try (
                ServerSocket serverSocket = new ServerSocket(portNumber);
        ) {
            while (true) {
                Socket client1=serverSocket.accept();
                PrintWriter out1 = new PrintWriter(client1.getOutputStream(), true);
                out1.println("Now connected, waiting for player 2...");
                Socket client2=serverSocket.accept();
                PrintWriter out2 = new PrintWriter(client2.getOutputStream(), true);
                out2.println("Now connected, Game about to start...");
                new ConnectionThread(client1, client2).start();
            }
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port " + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }
}//nc localhost 1024