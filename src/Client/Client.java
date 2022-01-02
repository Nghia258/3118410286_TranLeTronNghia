/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

/**
 *
 * @author Bi
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.JOptionPane;

public class Client {

    private static String host = "localhost";
    private static int port = 1234;
    private static Socket socket;

    private static BufferedWriter out;
    private static BufferedReader in;

    public void checkname(String name) throws IOException {

        if (name == null) {
            out.write("null" + "\n");
            out.flush();
            System.exit(0);
        } else {
            if (name.equals("")) {
                JOptionPane.showMessageDialog(null, "Tên không được rỗng !");
            } else {
                out.write(name + "\n");
                out.flush();
            }

        }

    }

    public static void main(String[] args) throws IOException, InterruptedException {
        Client client = new Client();
        String name = null, check = "0";
        socket = new Socket(host, port);
        System.out.println("Client connected");
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        while (!check.equals("1")) {
            name = JOptionPane.showInputDialog(null, "Nhập tên ");
            client.checkname(name);
            check = in.readLine();
            if (!check.equals("1")) {
                JOptionPane.showMessageDialog(null, "Tên bị trùng !");
            }
        }
        Chat chat = new Chat(name, in, out);
        chat.setVisible(true);
    }
}
