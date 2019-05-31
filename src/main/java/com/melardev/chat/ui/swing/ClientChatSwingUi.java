package com.melardev.chat.ui.swing;


import com.melardev.chat.ui.mediators.UiMediator;
import org.apache.commons.lang3.tuple.Pair;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Map;
import java.util.UUID;

public class ClientChatSwingUi extends JFrame implements AppWindow {
    private final JLabel labelPassword;
    private final JButton btnSend;
    private JTextField txtFieldUsername;
    private JPasswordField passwordField;
    private JTextField txtFieldHost;
    private JTextField txtFieldPort;
    private JTextField txtMessages;
    private JTextField txtActiveUsers;

    private JTextField txtFieldClientMsg;
    private JTextArea txtAreaMessages;
    private JList<Pair<String, String>> listUsers;

    private DefaultListModel<Pair<String, String>> model;
    private JButton btnAttach;
    private UiMediator uiMediator;


    /**
     * Create the frame.
     */
    public ClientChatSwingUi(UiMediator uiMediator) {
        this.uiMediator = uiMediator;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 483, 471);

        JScrollPane scrollMsgs = new JScrollPane();

        JLabel labelUsername = new JLabel("Username");

        txtFieldUsername = new JTextField();
        txtFieldUsername.setColumns(10);
        txtFieldUsername.setText("admin");

        labelPassword = new JLabel("password");

        passwordField = new JPasswordField();
        passwordField.setText("password");

        txtFieldHost = new JTextField();
        txtFieldHost.setText("localhost");
        txtFieldHost.setColumns(10);

        txtFieldPort = new JTextField();
        txtFieldPort.setText("3002");
        txtFieldPort.setColumns(10);
        txtFieldPort.setEnabled(false);

        JLabel labelHost = new JLabel("Host");

        JLabel labelPort = new JLabel("Port :");

        JScrollPane scrollClientMsg = new JScrollPane();

        btnSend = new JButton("Send");
        btnSend.addActionListener(e -> sendMessage());

        JScrollPane scrollUsers = new JScrollPane();

        JButton btnConnect = new JButton("Connect");
        btnConnect.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                connectionToServer();
            }
        });

        BufferedImage attachImage = null;
        try {
            /*
             * URL url = null;
             * System.out.println(new File("/").getAbsolutePath());
             * if(new File("resources/images/fattach_32.png").exists())
             * url = this.getClass().getResource("fattach_32.png");
             */
            File f = new File("res/images/fattach_32.png");
            if (f.exists())
                attachImage = ImageIO.read(f);
        } catch (IOException e1) {
            System.out.println(Paths.get("").toAbsolutePath().toString());
            System.out.println(System.getProperty("user.dir"));
            e1.printStackTrace();
        }
        if (attachImage == null)
            btnAttach = new JButton("attach");
        else {
            btnAttach = new JButton(new ImageIcon(attachImage));
            btnAttach.setBorder(BorderFactory.createEmptyBorder());
            btnAttach.setContentAreaFilled(false);
        }
        btnAttach.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                onAttachFile();
            }
        });

        GroupLayout groupLayout = new GroupLayout(getContentPane());
        groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                .addGroup(groupLayout.createSequentialGroup().addContainerGap().addGroup(groupLayout
                        .createParallelGroup(
                                Alignment.LEADING)
                        .addGroup(
                                groupLayout.createSequentialGroup()
                                        .addGroup(
                                                groupLayout
                                                        .createParallelGroup(
                                                                Alignment.LEADING)
                                                        .addGroup(groupLayout.createSequentialGroup()
                                                                .addGroup(groupLayout
                                                                        .createParallelGroup(Alignment.LEADING)
                                                                        .addComponent(labelUsername)
                                                                        .addComponent(labelHost))
                                                                .addPreferredGap(
                                                                        ComponentPlacement.RELATED)
                                                                .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                                                                        .addGroup(groupLayout.createSequentialGroup()
                                                                                .addComponent(txtFieldUsername, GroupLayout.PREFERRED_SIZE,
                                                                                        GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                                                .addPreferredGap(ComponentPlacement.UNRELATED)
                                                                                .addComponent(labelPassword))
                                                                        .addGroup(groupLayout.createSequentialGroup()
                                                                                .addComponent(txtFieldHost, GroupLayout.PREFERRED_SIZE,
                                                                                        GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                                                .addPreferredGap(ComponentPlacement.UNRELATED)
                                                                                .addComponent(labelPort, GroupLayout.PREFERRED_SIZE, 46,
                                                                                        GroupLayout.PREFERRED_SIZE)))
                                                                .addGap(18)
                                                                .addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
                                                                        .addComponent(passwordField).addComponent(txtFieldPort))
                                                                .addPreferredGap(ComponentPlacement.RELATED).addComponent(btnConnect))
                                                        .addGroup(groupLayout.createSequentialGroup()
                                                                .addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
                                                                        .addComponent(scrollClientMsg).addComponent(scrollMsgs))
                                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                                .addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
                                                                        .addComponent(scrollUsers, GroupLayout.PREFERRED_SIZE, 126,
                                                                                GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(btnSend, GroupLayout.PREFERRED_SIZE, 124,
                                                                                GroupLayout.PREFERRED_SIZE))))
                                        .addGap(7))
                        .addGroup(groupLayout.createSequentialGroup().addComponent(btnAttach).addContainerGap(368,
                                Short.MAX_VALUE)))));
        groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
                .addGroup(groupLayout.createSequentialGroup().addContainerGap()
                        .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(labelUsername)
                                .addComponent(txtFieldUsername, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                        GroupLayout.PREFERRED_SIZE)
                                .addComponent(labelPassword)
                                .addComponent(passwordField, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnConnect))
                        .addPreferredGap(ComponentPlacement.RELATED)
                        .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                                .addComponent(txtFieldHost, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                        GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtFieldPort, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                        GroupLayout.PREFERRED_SIZE)
                                .addComponent(labelHost).addComponent(labelPort))
                        .addGap(18)
                        .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE, true)
                                .addComponent(scrollMsgs)
                                .addComponent(scrollUsers))
                        .addPreferredGap(ComponentPlacement.RELATED).addComponent(btnAttach).addGap(2)
                        .addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false).addComponent(scrollClientMsg)
                                .addComponent(btnSend, GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)).addGap(59)));

        txtFieldClientMsg = new JTextField();
        scrollClientMsg.setViewportView(txtFieldClientMsg);
        txtFieldClientMsg.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    sendMessage();
                }
            }
        });
        txtFieldClientMsg.setEnabled(false);

        listUsers = new JList<>();
        listUsers.setCellRenderer(new UserListCellRenderer());
        model = new DefaultListModel<>();
        scrollUsers.setViewportView(listUsers);
        listUsers.setModel(model);

        txtActiveUsers = new JTextField();
        txtActiveUsers.setText("Active Users");
        scrollUsers.setColumnHeaderView(txtActiveUsers);
        txtActiveUsers.setColumns(10);

        txtAreaMessages = new JTextArea();
        scrollMsgs.setViewportView(txtAreaMessages);

        txtMessages = new JTextField();
        txtMessages.setText("Messages");
        scrollMsgs.setColumnHeaderView(txtMessages);
        txtMessages.setColumns(10);
        getContentPane().setLayout(groupLayout);

        toggleLoggedInState(false);

        setSize(1200, 900);
    }

    private void toggleLoggedInState(boolean isLoggedIn) {
        txtActiveUsers.setEnabled(isLoggedIn);
        btnAttach.setEnabled(isLoggedIn);
        btnSend.setEnabled(isLoggedIn);
        txtAreaMessages.setEnabled(isLoggedIn);
        txtFieldClientMsg.setEnabled(isLoggedIn);
        listUsers.setEnabled(isLoggedIn);

        txtFieldHost.setEnabled(!isLoggedIn);
        txtFieldPort.setEnabled(!isLoggedIn);
        txtFieldUsername.setEnabled(!isLoggedIn);
        labelPassword.setEnabled(!isLoggedIn);
    }

    protected void onAttachFile() {
        if (uiMediator != null) {
            JFileChooser fChooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Audio", "mp3", "flv");
            fChooser.setFileFilter(filter);
            if (fChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                Pair<String, String> dstUser = listUsers.getSelectedValue();
                if (dstUser == null || dstUser.getValue().equals("All")) {
                    txtAreaMessages.append("You can not send Files to All users");
                } else {
                    uiMediator.onSendFileRequested(fChooser.getSelectedFile().getAbsolutePath(), dstUser.getKey());
                }
            }
        }
    }

    private void sendMessage() {
        if (uiMediator != null) {
            String msgTxt = txtFieldClientMsg.getText();
            if (!msgTxt.isEmpty()) {
                Pair<String, String> selectedUser = listUsers.getSelectedValue();
                if (selectedUser != null) {

                    if (selectedUser.getValue().equals("All")) {
                        // Public message
                        uiMediator.sendPublicMessageRequested(msgTxt);
                    } else {
                        // Private message
                        uiMediator.sendPrivateMessageRequested(msgTxt, selectedUser.getKey());
                        setTextAreaMsg("Sent MP to " + selectedUser.getValue() + " -> " + msgTxt);
                    }
                    setTextAreaClientMsg("");
                    txtFieldClientMsg.requestFocus();
                } else {
                    // Public message
                    uiMediator.sendPublicMessageRequested(msgTxt);
                }
            }
            txtFieldClientMsg.setText("");
        }
    }

    private void setTextAreaClientMsg(String message) {
        txtFieldClientMsg.setText(message);
    }

    public void setTextAreaMsg(String txt) {
        txtAreaMessages.append(txt + "\n");
    }

    public void addUsersJList(Map<String, String> users) {

        for (Map.Entry<String, String> entrySet : users.entrySet()) {

            boolean exists = false;
            for (int i = 0; i < model.getSize(); i++) {
                if (entrySet.getKey().equals(model.get(i).getKey())) {
                    exists = true;
                    break;
                }
            }
            if (!exists) {
                String username = entrySet.getValue();
                String userId = entrySet.getKey();
                model.addElement(Pair.of(userId, username));
            }
        }
    }

    private void connectionToServer() {
        String username = txtFieldUsername.getText();
        String password = new String(passwordField.getPassword());
        String hostname = txtFieldHost.getText();
        String port = (txtFieldPort.getText());

        if (!(username.isEmpty() && password.isEmpty() && hostname.isEmpty() && port.isEmpty())) {
            uiMediator.loginRequested(username, password);
        }
    }

    public void removeUsersJlist(String content) {
        UUID uuid = UUID.fromString(content);
        for (int index = 0; index < model.getSize(); index++) {
            if (uuid.equals(model.get(index).getKey())) {
                model.remove(index);
            }
        }
    }

    public void initModel() {
        model.addElement(Pair.of("", "All"));
        listUsers.setSelectedIndex(0);
    }

    @Override
    public UiMediator getUiMediator() {
        return uiMediator;
    }

    @Override
    public void setUiMediator(UiMediator uiMediator) {
        this.uiMediator = uiMediator;
    }

    public void onLoggedIn(String username) {
        setTitle(username);
        setTextAreaMsg("Connected and authenticated successfully ....");
        initModel();
        toggleLoggedInState(true);
    }

    static class UserListCellRenderer extends JLabel implements ListCellRenderer<Pair<String, String>> {

        @Override
        public Component getListCellRendererComponent(JList<? extends Pair<String, String>> list, Pair<String, String> value,
                                                      int index, boolean isSelected, boolean cellHasFocus) {
            String username = value.getValue();
            String userId = value.getKey();

            if (cellHasFocus) {
                setBackground(list.getSelectionBackground());
                setForeground(list.getSelectionForeground());
            } else {
                setBackground(list.getBackground());
                setForeground(list.getForeground());
            }
            setText(username);
            return this;
        }
    }
}
