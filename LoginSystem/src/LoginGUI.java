import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

public class LoginGUI extends JFrame {

    private static final int WIDTH = 700;
    private static final int HIEGHT = 500;
    private LoginSystem loginSystem;
    private Container contentPane;

    public LoginGUI() throws Exception {
        super();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH, HIEGHT);
        setTitle("Login");
        loginSystem = new LoginSystem();

        contentPane = getContentPane();
        this.setDefaultLayout();

    }

    /*
    Method to set the JFrame to a new layout such that the user will be able to register a new entry into UserFile.txt
    */
    private void setRegisterLayout() {
        contentPane.removeAll();
        contentPane.revalidate();
        contentPane.repaint();

        contentPane.setLayout(new BorderLayout());
        
        //North Panel
        JPanel northPanel = new JPanel();
        contentPane.add(northPanel,BorderLayout.NORTH);

        JLabel registerLabel = new JLabel("Please enter a new username and password");
        northPanel.add(registerLabel);

        // Center Panel
        JPanel centerPanel = new JPanel();
        contentPane.add(centerPanel, BorderLayout.CENTER);
        centerPanel.setLayout(new GridLayout(2, 2));

        List<JLabel> labels = new ArrayList<>();
        labels.add(new JLabel("Username:"));
        labels.add(new JLabel("Password:"));

        List<JTextField> textFields = new ArrayList<>();
        textFields.add(new JTextField());
        textFields.add(new JTextField());

        // South Panel
        JPanel southPanel = new JPanel();
        contentPane.add(southPanel, BorderLayout.SOUTH);

        List<JButton> buttons = new ArrayList<>();
        buttons.add(new JButton("Register"));
        buttons.add(new JButton("Cancel"));

        // Action Listeners
        ActionListener registerOrCancel = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String event = e.getActionCommand();
                if (event == "Register") {
                    try {
                        int registerCode = loginSystem.register(textFields.get(0).getText(),textFields.get(1).getText());
                        if(registerCode==0){
                            setCompleteRegisterLayout();
                        }
                        else if(registerCode==1){
                            registerLabel.setText("Failed to register, please use a different Username");
                        }else if(registerCode==2){
                            registerLabel.setText("Failed to register, please make sure you don't use spaces");
                        }

                    }catch (Exception e1){
                        e1.printStackTrace();
                    }
                }
                if (event=="Cancel"){
                    setDefaultLayout();
                }
            }
        };

        //Placement:
        for (int i=0;i<2;i++){
            centerPanel.add(labels.get(i));
            centerPanel.add(textFields.get(i));
            southPanel.add(buttons.get(i));
            buttons.get(i).addActionListener(registerOrCancel);
        }
    }


    /*
    Default Layout for JFrame
    */
    private void setDefaultLayout() {
        contentPane.removeAll();
        contentPane.revalidate();
        contentPane.repaint();

        contentPane.setLayout(new BorderLayout());

        // Center Panel
        JPanel centerPanel = new JPanel();
        contentPane.add(centerPanel, BorderLayout.CENTER);
        centerPanel.setLayout(new GridLayout(2, 2));

        List<JLabel> labels = new ArrayList<>();
        labels.add(new JLabel("Username:"));
        labels.add(new JLabel("Password:"));

        List<JTextField> textFields = new ArrayList<>();
        textFields.add(new JTextField());
        textFields.add(new JTextField());

        // South Panel
        JPanel southPanel = new JPanel();
        contentPane.add(southPanel, BorderLayout.SOUTH);

        List<JButton> buttons = new ArrayList<>();
        buttons.add(new JButton("Login"));
        buttons.add(new JButton("Register"));

        // Action Listeners
        ActionListener loginOrRegister = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String event = e.getActionCommand();
                if (event == "Login") {
                    try {
                        loginSystem.login(textFields.get(0).getText(), textFields.get(1).getText());
                    } catch (FileNotFoundException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }
                if (event=="Register"){
                    setRegisterLayout();
                }
            }
        };

        //Placement:
        for (int i=0;i<2;i++){
            centerPanel.add(labels.get(i));
            centerPanel.add(textFields.get(i));
            southPanel.add(buttons.get(i));
            buttons.get(i).addActionListener(loginOrRegister);
        }
    }

    private void setCompleteRegisterLayout() {
        contentPane.removeAll();
        contentPane.revalidate();
        contentPane.repaint();

        contentPane.setLayout(new BorderLayout());

        JLabel completeLabel = new JLabel ("Successfully Registered");
        JButton rtdButton = new JButton ("Return to Login");


        ActionListener rtdListener = new ActionListener(){
            public void actionPerformed (ActionEvent e){
                String event = e.getActionCommand();
                if (event=="Return to Login")
                    setDefaultLayout();
            }
        };

        contentPane.add(completeLabel,BorderLayout.NORTH);
        contentPane.add(rtdButton,BorderLayout.CENTER);
        rtdButton.addActionListener(rtdListener);
    }

}
