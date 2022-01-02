/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

/**
 *
 * @author Bi
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    public static int port = 1234;
    public static int numThread = 4;
    private ServerSocket server = null;
    private Socket socket = null;
    public static Vector<ClientHandler> listuser = new Vector<>();
    public static Vector<ClientHandler> hangdoi = new Vector<>();
    
    public Server(int port) throws IOException{
         ExecutorService executor = Executors.newFixedThreadPool(numThread);
        server = new ServerSocket(port);
        System.out.println("Server binding at port " + port);
        System.out.println("Waiting for client...");
        int i=0;
        while (i!=1) {
            socket = server.accept();
            ClientHandler client = new ClientHandler(socket);
            executor.execute(client);
        }
        try {
            server.close();
            socket.close();
        } catch (IOException e) {
            System.err.print(e);
        }
    }

    public static void main(String[] args) throws IOException {
       Server server = new Server(port);
    }
}
