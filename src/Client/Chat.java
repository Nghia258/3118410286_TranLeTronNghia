/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Bi
 */
public class Chat extends javax.swing.JFrame {

    private static BufferedWriter out;
    private static BufferedReader in;
    private static String name;

    ExecutorService executor = Executors.newFixedThreadPool(4);

  
    public Chat(String name, BufferedReader in, BufferedWriter out) {
        initComponents();
        this.in = in;
        this.out = out;
        this.name = name;
        jLabel2.setText(name);
        ReceiveMessage recv = new ReceiveMessage(in);
        executor.execute(recv);
    }

    public void sendMesstoServer(String mess) throws IOException {
        System.out.println("Chat :");
        System.out.println("Input from client: " + mess);
        out.write(mess);
        out.newLine();
        out.flush();
    }

    public void acceptUser(String mess) {
        StringTokenizer st = new StringTokenizer(mess, ";");
        while (st.hasMoreTokens()) {
            String ten = st.nextToken();
            int rep = JOptionPane.showConfirmDialog(null, "Bạn có muốn chat với " + ten + " không", null, JOptionPane.YES_NO_OPTION);
            if (rep == JOptionPane.YES_OPTION) {
                try {
                    System.out.println("Tui là :" + name);
                    sendMesstoServer("chat_to;" + ten + ";" + name);
                } catch (IOException ex) {
                    Logger.getLogger(Chat.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            } else {
                continue;
            }
        }
    }

    class ReceiveMessage implements Runnable {

        private BufferedReader in;
        volatile String data;

        public ReceiveMessage(BufferedReader i) {

            this.in = i;
        }

        public void run() {
            try {
                while (true) {
                    data = in.readLine();

                    System.out.println(data);

                    if (data.contains("list;")) {
                        acceptUser(data.substring(5));
                        System.out.println("danh sách user :" + data.substring(5));
                    } else {
                        if (data.contains("acc_to;")) {
                            sendMesstoServer(data);
                            System.out.println("accept user :" + data.substring(7));
                        } else {
                            if (data.contains("OuTeXist;")) {
                                JOptionPane.showMessageDialog(null, "Người chat với bạn đã thoát !");
                                sendMesstoServer("OuTeXist;");
                                //System.exit(0);
                                //break;
                            } else {
                                if (txtPanel.getText().equals("")) {
                                    txtPanel.setText(data);
                                } else {
                                    txtPanel.setText(txtPanel.getText() + "\n" + data);
                                }
                            }
                        }
                    }
                }
            } catch (IOException e) {
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSeparator1 = new javax.swing.JSeparator();
        txtChat = new javax.swing.JTextField();
        btnSend = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtPanel = new javax.swing.JTextPane();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        btnSend.setText("Gửi");
        btnSend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSendActionPerformed(evt);
            }
        });

        jScrollPane2.setViewportView(txtPanel);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel2.setText("Chat");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtChat, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnSend, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 414, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(187, 187, 187)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 344, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSend, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtChat, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSendActionPerformed

        String text;
        text = txtChat.getText();
        if (text.equals("")) {
            JOptionPane.showMessageDialog(null, "Hãy nhập gì đó để chat !");
        } else {
            if (txtPanel.getText().equals("")) {
                txtPanel.setText("You : " + text);
            } else {
                txtPanel.setText(txtPanel.getText() + "\n" + "You : " + text);
            }
            txtChat.setText("");
            try {
                sendMesstoServer("chat;" + text);
            } catch (IOException ex) {
                Logger.getLogger(Chat.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btnSendActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        try {
            sendMesstoServer("OuTeXistluon;" + name);
        } catch (IOException ex) {
            Logger.getLogger(Chat.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Đóng chat");
    }//GEN-LAST:event_formWindowClosing

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Chat(name, in, out).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSend;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField txtChat;
    private javax.swing.JTextPane txtPanel;
    // End of variables declaration//GEN-END:variables
}
