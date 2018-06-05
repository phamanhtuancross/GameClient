/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainGUI.com;

import GameGUI.com.snake.dev.game.titlegame.Game;
import GameGUI.com.snake.dev.game.titlegame.TcpConnection;
import GameGUI.com.snake.dev.game.titlegame.CharacterObjInfo;
import GameGUI.com.snake.dev.game.titlegame.UdpConnection;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

/**
 *
 * @author phamanhtuan
 */
public class RoomGame extends javax.swing.JFrame {

    /**
     * Creates new form RoomGame
     */
    private final String SERVER_IP = "localhost";
    private final int SERVER_PORT = 1234;
    private int UDP_PORT = 5000;
    private Game game;
    private TcpConnection connection;
    private int userId;
    public static List<CharacterObjInfo> listFriends = new ArrayList<CharacterObjInfo>();
    public List<CharacterObjInfo> oldListFriends = new ArrayList<CharacterObjInfo>();
    public static String code = "";
    private String userName = "";

    private Thread thread;
    private boolean isCoonecting = true;
    public static boolean isChangeListFriend;
    public int idInRoomGame = -1;
    private boolean playing;
    UdpConnection udpConnection;

    RoomGame() {
        initComponents();
        isCoonecting = true;
        playing = false;
        init();
    }

    private void init() {

        this.btnStartGame.setVisible(false);
        this.setLocationRelativeTo(null);
        connectionServer();
        userId = (int) connection.getIdFromServer();
        game = new Game("SNAKE");
        udpConnection = new UdpConnection(game, connection);
        game.setID(userId);
        game.setConnection(connection);
        game.setUdpConnection(udpConnection);

        new Thread(udpConnection).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (isCoonecting) {
                    DefaultListModel listModel = new DefaultListModel();
                    listModel.removeAllElements();
                    for (CharacterObjInfo obj : listFriends) {
                        if (obj.Id != userId) {
                            listModel.addElement(obj.name);
                        }
                    }
                    lChat.setModel(listModel);
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!playing) {
//   
                   System.out.println(".");
                    if (idInRoomGame != 0 && code != null && !"".equals(code)) {
                        boolean isRoomgameRunning = connection.checkIsRoomMasterStartGame(code);
                        if (isRoomgameRunning == true) {
                            game.setRoomgameCode(code);
                            game.setIdInRoomGame(idInRoomGame);
                            game.start();
                            playing = true;
                        }
                    }
                }

            }
        }).start();

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//                System.out.println("conecting .....################3");
//                while (isCoonecting) {
//
//                    DefaultListModel listModel = new DefaultListModel();
//                  //  List<CharacterObjInfo> listNameObj = connection(code);
//                    
//                  if(code != ""){
//                      
//                   // listModel.addElement(userName);
//                    for (CharacterObjInfo character : listFriends) {
//                        if(character.code.equals(code)){
//                            listModel.addElement(character.name);
//                        }
//                    }
//                    
//                    listPlayerInRoom.setModel(listModel);
//                  }
//
//                }
//            }
//        }).start();

//        
//        try {
//            friendsOnlineThread.join();
//            friendInRoomGameThread.join();
//            runningRoomGameThread.join();
//            //game.start();
//        } catch (InterruptedException ex) {
//            Logger.getLogger(RoomGame.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

    public void connectionServer() {
        connection = new TcpConnection(game, SERVER_IP, SERVER_PORT);

    }

    public List<CharacterObjInfo> coppyDataFormCharacterObjInfos(List<CharacterObjInfo> dest) {
        List<CharacterObjInfo> source = new ArrayList<CharacterObjInfo>();
        for (CharacterObjInfo character : dest) {
            CharacterObjInfo newObj = new CharacterObjInfo(character.Id, character.name);
            source.add(newObj);
        }

        return source;
    }

    private void reloadListPlayerInRoom(List<CharacterObjInfo> data) {
        DefaultListModel defaultListModel = new DefaultListModel();
        defaultListModel.removeAllElements();

        if (data == null || data.isEmpty()) {
            System.out.println("data is null or data in empty");
            return;
        }

        for (int dataIndex = 0; dataIndex < data.size(); dataIndex++) {
            defaultListModel.addElement(data.get(dataIndex).name);
        }
        listPlayerInRoom.setModel(defaultListModel);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        Right = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        tfUserName = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        btnLogin = new javax.swing.JButton();
        btnCreateRoomGame = new javax.swing.JButton();
        tfCode = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        listPlayerInRoom = new javax.swing.JList<>();
        tfGroupCode = new javax.swing.JTextField();
        btnJoinGroup = new javax.swing.JButton();
        btnStartGame = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        btnExitRoomGame = new javax.swing.JButton();
        Left = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        btnExit = new javax.swing.JButton();
        pnChat = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        lChat = new javax.swing.JList<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new java.awt.CardLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(900, 600));
        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.X_AXIS));

        Right.setBackground(new java.awt.Color(255, 255, 255));
        Right.setPreferredSize(new java.awt.Dimension(800, 600));
        Right.setLayout(new javax.swing.BoxLayout(Right, javax.swing.BoxLayout.Y_AXIS));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setPreferredSize(new java.awt.Dimension(637, 30));
        jPanel2.setLayout(new java.awt.CardLayout());

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 0, 51));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("SNAKE .JAVA");
        jLabel1.setPreferredSize(new java.awt.Dimension(158, 100));
        jPanel2.add(jLabel1, "card2");

        Right.add(jPanel2);

        jPanel3.setBackground(new java.awt.Color(51, 51, 51));

        tfUserName.setBackground(new java.awt.Color(255, 255, 255));
        tfUserName.setForeground(new java.awt.Color(51, 51, 51));
        tfUserName.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel2.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("USERNAME");

        btnLogin.setBackground(new java.awt.Color(255, 0, 102));
        btnLogin.setForeground(new java.awt.Color(255, 255, 255));
        btnLogin.setText("LOGIN");
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });

        btnCreateRoomGame.setText("CREATE ROOM ");
        btnCreateRoomGame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreateRoomGameActionPerformed(evt);
            }
        });

        tfCode.setBackground(new java.awt.Color(255, 255, 255));
        tfCode.setForeground(new java.awt.Color(51, 51, 51));
        tfCode.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        tfCode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfCodeActionPerformed(evt);
            }
        });

        listPlayerInRoom.setBackground(new java.awt.Color(255, 255, 255));
        listPlayerInRoom.setForeground(new java.awt.Color(255, 0, 51));
        listPlayerInRoom.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(listPlayerInRoom);

        tfGroupCode.setBackground(new java.awt.Color(255, 255, 255));
        tfGroupCode.setForeground(new java.awt.Color(51, 51, 51));
        tfGroupCode.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        btnJoinGroup.setBackground(new java.awt.Color(255, 0, 102));
        btnJoinGroup.setForeground(new java.awt.Color(255, 255, 255));
        btnJoinGroup.setText("JOIN GROUP");
        btnJoinGroup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnJoinGroupActionPerformed(evt);
            }
        });

        btnStartGame.setBackground(new java.awt.Color(255, 0, 102));
        btnStartGame.setForeground(new java.awt.Color(255, 255, 255));
        btnStartGame.setText("START GAME");
        btnStartGame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStartGameActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("PLAYER IN ROOMGAME");

        btnExitRoomGame.setBackground(new java.awt.Color(255, 0, 102));
        btnExitRoomGame.setForeground(new java.awt.Color(255, 255, 255));
        btnExitRoomGame.setText("EXIT ROOM");
        btnExitRoomGame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitRoomGameActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnJoinGroup, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(185, 185, 185))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(btnCreateRoomGame, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(tfUserName, javax.swing.GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE)))
                                    .addComponent(tfCode, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(btnStartGame)
                                .addGap(75, 75, 75)))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE)
                            .addComponent(tfGroupCode)
                            .addComponent(btnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(124, Short.MAX_VALUE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnExitRoomGame, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(187, 187, 187))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfUserName, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(btnLogin))
                .addGap(45, 45, 45)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnExitRoomGame)
                        .addGap(30, 30, 30)
                        .addComponent(tfGroupCode, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnJoinGroup))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(tfCode, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnCreateRoomGame)
                        .addGap(18, 18, 18)
                        .addComponent(btnStartGame)))
                .addContainerGap(83, Short.MAX_VALUE))
        );

        Right.add(jPanel3);

        jPanel1.add(Right);

        Left.setBackground(new java.awt.Color(255, 255, 255));
        Left.setLayout(new javax.swing.BoxLayout(Left, javax.swing.BoxLayout.Y_AXIS));

        jPanel4.setPreferredSize(new java.awt.Dimension(291, 100));

        btnExit.setBackground(new java.awt.Color(255, 0, 102));
        btnExit.setForeground(new java.awt.Color(255, 255, 255));
        btnExit.setText("EXIT");
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(0, 133, Short.MAX_VALUE)
                .addComponent(btnExit))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(btnExit)
                .addGap(0, 68, Short.MAX_VALUE))
        );

        Left.add(jPanel4);

        pnChat.setBackground(new java.awt.Color(255, 255, 255));
        pnChat.setLayout(new java.awt.CardLayout());

        lChat.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(lChat);

        pnChat.add(jScrollPane1, "card2");

        Left.add(pnChat);

        jPanel1.add(Left);

        getContentPane().add(jPanel1, "card2");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed
        // TODO add your handling code here:
        userName = tfUserName.getText();

        System.out.println("USER NAME :" + userName);

        if ("".equals(userName) || userName.isEmpty()) {
            JOptionPane.showInternalMessageDialog(null, "username is not empty");
        } else {
            connection.sendUserName(userName, userId);
            this.btnLogin.setText("Logout");
            this.tfUserName.setEnabled(false);
        }

    }//GEN-LAST:event_btnLoginActionPerformed

    private void btnCreateRoomGameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreateRoomGameActionPerformed
        // TODO add your handling code here:
        this.btnStartGame.setVisible(true);
        code = connection.createGameRoom(userName, udpConnection.getPort());
        this.idInRoomGame = 0;
        this.tfCode.setText(code);
        // List<CharacterObjInfo> listPlayer = connection.getListPlayerInGame(code);
        //reloadListPlayerInRoom(listPlayer);
    }//GEN-LAST:event_btnCreateRoomGameActionPerformed

    private void btnJoinGroupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnJoinGroupActionPerformed
        // TODO add your handling code here:
        String roomCode = tfGroupCode.getText();
        boolean isRoomAreRunning = connection.checkIsRoomMasterStartGame(roomCode);
        if (isRoomAreRunning) {
            JOptionPane.showMessageDialog(null, "Room game are running ...!! please try again later..");
            return;
        }
        code = roomCode;
        int udpPort = udpConnection.getPort();

        this.idInRoomGame = connection.joinToGameRoomByGameRoomCode(roomCode, userName, udpPort);

        String message = "Join room failed";
        if (idInRoomGame != -1) {
            message = "join room sucessfully with id:" + idInRoomGame;
        }
        JOptionPane.showMessageDialog(null, message);
    }//GEN-LAST:event_btnJoinGroupActionPerformed

    private void tfCodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfCodeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfCodeActionPerformed

    private void btnStartGameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStartGameActionPerformed
        // TODO add your handling code here:

        if (idInRoomGame == -1 || "".equals(code) || code == null) {
            return;
        }

        game.setRoomgameCode(code);
        game.setIdInRoomGame(idInRoomGame);
        connection.sendStartRoomGameByRoomCode(code);
        game.start();

    }//GEN-LAST:event_btnStartGameActionPerformed

    private void btnExitRoomGameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitRoomGameActionPerformed
        // TODO add your handling code here:
        if(code == null || "".equals(code)){
            return;
        }
        
        if(idInRoomGame == -1){
            return;
        }
        
        connection.sendExitRoomByCharacterInfo(code, idInRoomGame);
        
        
    }//GEN-LAST:event_btnExitRoomGameActionPerformed

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed
        // TODO add your handling code here:
        connection.sendExitProgram(userId);
        System.exit(0);
    }//GEN-LAST:event_btnExitActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(RoomGame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RoomGame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RoomGame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RoomGame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RoomGame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Left;
    private javax.swing.JPanel Right;
    private javax.swing.JButton btnCreateRoomGame;
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnExitRoomGame;
    private javax.swing.JButton btnJoinGroup;
    private javax.swing.JButton btnLogin;
    private javax.swing.JButton btnStartGame;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JList<String> lChat;
    private javax.swing.JList<String> listPlayerInRoom;
    private javax.swing.JPanel pnChat;
    private javax.swing.JTextField tfCode;
    private javax.swing.JTextField tfGroupCode;
    private javax.swing.JTextField tfUserName;
    // End of variables declaration//GEN-END:variables

}
