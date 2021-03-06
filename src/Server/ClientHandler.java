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
import Server.Server;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientHandler implements Runnable {

    private Socket socket;
    BufferedReader in;
    BufferedWriter out;
    private String ten;
    private boolean Ban = false;
    ClientHandler targerClient;

    public ClientHandler(Socket s) throws IOException {
        this.socket = s;
        this.in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        this.out = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
    }

    public boolean sendMess(String mess) {
        try {
            out.write(mess);
            out.newLine();
            out.flush();
            return true;
        } catch (IOException ex) {
            return false;
        }
    }

    public String getMess() throws IOException {
        String mess = null;
        try {
            mess = in.readLine();
        } catch (Exception e) {
            mess = null;
        }
        return mess;
    }

    public boolean checknamelist(String ten) {
        for (ClientHandler client : Server.listuser) {
            if (ten.equals(client.ten)) {
                return false;
            }
        }
        return true;
    }

    public void checkname() throws IOException {
        String check = "0";
        String name = "";
        while (!check.equals("1")) {
            try {
                name = getMess();
            } catch (IOException ex) {
                Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (name == null) {
                System.out.println("Client tho??t v?? t??n null");
                return;
            }
            if (checknamelist(name) == true) {
                check = "1";
            }
            sendMess(check);
            this.ten = name;
            Server.listuser.add(this);
            System.out.println("Client : " + name + " accepted");
        }

    }

    public void sendMesstoUser(String name, String mess) throws IOException {
        for (ClientHandler client : Server.listuser) {
            if (name.equals(client.ten)) {
                client.sendMess(mess);
            }
        }
    }

    public void deletelistUser(String name) {
        for (int i = 0; i < Server.listuser.size(); i++) {
            ClientHandler client = Server.listuser.get(i);
            if (name.equals(client.ten)) {
                Server.listuser.remove(client);
                System.out.println("X??a :" + client.toString() + " kh???i vecter");
            }
        }
    }

    public boolean sendlistUser() {
        String listUser = "list;";
        for (ClientHandler client : Server.listuser) {
            if (!ten.equals(client.ten) && !client.Ban) {
                listUser += (client.ten + ";");
            }
        }
        if (!listUser.equals("list;")) {
            sendMess(listUser);
            return true;
        }
        System.out.println("List : " + listUser);
        return false;
    }

    public String cmdChat(String mess) {
        //chat_to;a;b
        String userchat = "k.o";
        if (mess.contains("chat_to;")) {
            //a
            userchat = mess.split(";")[1];
            System.out.println("userchat l???nh chat_to : " + userchat);
            //b
            String accchat = mess.split(";")[2];
            System.out.println("G???i acc_chat cmd: " + accchat);
            sendMess("acc_to;" + accchat);
        } else {
            //acc_to;b
            if (mess.contains("acc_to;")) {
                //b
                userchat = mess.split(";")[1];
                System.out.println("userchat l???nh acc_to : " + userchat);
            } else {
                System.out.println("K c?? cmd chat");
            }
        }
        return userchat;
    }

    private ClientHandler getTargetClient(String targetName) {
        for (ClientHandler client : Server.listuser) {
            if (targetName.equals(client.ten)) {
                return client;
            }
        }
        return null;
    }

    public void run() {
        try {
            //Ki???m t??n user
            checkname();
        } catch (IOException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (sendlistUser() == true) {
            try {
                System.out.println("Tui l?? " + this.ten + " ??ang tim target TRUE");
                String nhap = getMess();
                String kq = cmdChat(nhap);
                Ban = true;
                targerClient = getTargetClient(kq);
                System.out.println("tui l?? : " + ten + " ??ang target :" + targerClient.toString());
                targerClient.sendMess("acc_to;" + ten);
            } catch (IOException ex) {
                Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                System.out.println("Tui l?? " + this.ten + " ??ang tim target FALSE");
                String nhap = getMess();
                String kq = cmdChat(nhap);
                Ban = true;
                targerClient = getTargetClient(kq);
                System.out.println("tui l?? : " + ten + " ??ang target :" + targerClient.toString());
            } catch (IOException ex) {
                Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        String chat = "";
        while (!chat.equals("1")) {
            int i = 0;
            while (i != 1) {
                try {
                    //  System.out.println("V??o trong while "+ this.ten);
                    //                System.out.println("Nh???n : "+getMess()+ " t??? " + this.ten);
                    chat = getMess();
                    String noidungchat;
                    if (chat.contains("chat;")) {
                        noidungchat = chat.split(";")[1];
                        System.out.println("tin nh???n nh???n t??? " + this.ten + " :" + noidungchat);
                        targerClient.sendMess(this.ten + " : " + noidungchat);
                    } else {

                        if (chat.contains("OuTeXistluon;")) {
                            System.out.println("Client " + ten + " tho??t");
                            targerClient.sendMess("OuTeXist;");
                            deletelistUser(ten);
                            return;
                        } else {
                            if (chat.contains("OuTeXist;")) {
                                Ban = false;
                                System.out.println("Tui l?? " + this.ten + " ??ang tim target FALSE");
                                String nhap = getMess();
                                String kq = cmdChat(nhap);
                                Ban = true;
                                targerClient = getTargetClient(kq);
                                System.out.println("tui l?? : " + ten + " ??ang target :" + targerClient.toString());
                                break;

                            } else {
                                if (chat == null) {
                                    System.out.println("Client " + ten + " tho??t");
                                    deletelistUser(ten);
                                    break;
                                }

                            }
                        }

                    }
                    // System.out.println("Xong 1 l???n while "+ this.ten);
                } catch (IOException ex) {
                    Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        try {
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
