/*
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.border.EmptyBorder;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.Icon;
import javax.swing.WindowConstants;
import javax.swing.border.Border;


import java.awt.Component;
import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.BorderLayout;
*/

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.border.EmptyBorder;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.Icon;
import javax.swing.WindowConstants;
import javax.swing.border.Border;


import java.awt.Component;
import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.BorderLayout;

public class Home{
    //Identifier of Main Window
    JFrame homeFrame;

    //Array holding screen width and height. Only used by hte Home class
    protected int[] d = getScreenDimensions();
    int[] getScreenDimensions(){
        int[] dim = new int[2];
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        dim[0] = (int)d.getWidth();
        dim[1] = (int)d.getHeight();

        return dim;
    }

    //When called add any text to a panel which is implemented via BorderLayout
    private void setText(JPanel panel, String text, int fontSize){
        JLabel label = new JLabel(text);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Arial",Font.BOLD,fontSize));

        switch ("center".toLowerCase()) {
            case "center" -> label.setAlignmentX(Component.CENTER_ALIGNMENT);
            case "left" -> label.setAlignmentX(Component.LEFT_ALIGNMENT);
            case "right" -> label.setAlignmentX(Component.RIGHT_ALIGNMENT);
        }
        panel.add(label);
    }

    //Formatting for Text Boxes
    private void setTextBoxStyle(JPanel panel){
        JTextField textField = new JTextField();
        Font textFont = new Font("Arial",Font.PLAIN,18);
        textField.setFont(textFont);
        Border textBoxBorder = BorderFactory.createLineBorder(Color.WHITE,3);
        textField.setBorder(textBoxBorder);
        textField.setBounds(20, 20, 200, 40);
        panel.add(textField);
    }

    private JPanel setTitle(){
        JPanel titleBar = new JPanel();
        titleBar.setBackground(Color.BLACK);
        titleBar.setPreferredSize(new Dimension(d[0],100)); //Polymorphism: Because setSize also take new Dimension(double, double) inputs
        titleBar.setLayout(new BoxLayout(titleBar,BoxLayout.Y_AXIS)); //Used to add content vertically to the titleBar
        titleBar.setBorder(new EmptyBorder(15,15,15,15)); //Set Padding to Panel

        setText(titleBar,"ESG",30);
        titleBar.add(Box.createRigidArea(new Dimension(0,5))); //Add a vertical Space Between 2 titles
        setText(titleBar,"Environmental Social Governance",20);

        return titleBar;
    }

    private JPanel addSearch(){
        JPanel locationPanel = new JPanel();
        locationPanel.setBackground(Color.DARK_GRAY);
        locationPanel.setPreferredSize(new Dimension(300,d[1]-200));
        locationPanel.setLayout(null);
        locationPanel.setBorder(new EmptyBorder(15,15,15,15));

        setTextBoxStyle(locationPanel);

        JButton searchButton = new JButton();
        searchButton.setSize(50,41);
        searchButton.setLocation(230,20);
        searchButton.setBackground(Color.WHITE);

        try{
            Icon searchIcon = new ImageIcon("images/search.png");
            searchButton.setIcon(searchIcon);
        }catch (Exception e){e.printStackTrace();}

        locationPanel.add(searchButton);
        return locationPanel;
    }

    private JPanel addMap(){
        JPanel wwdPanel = new MapTemplate.MapPanel(new Dimension(d[0] - 670, d[1] - 260),true);
        MapTemplate mp = new MapTemplate();
        mp.addPlaceMark(50,60,1000);
        return wwdPanel;
    }

    private JPanel addUserHistoryPanel(){
        JPanel userPanel = new JPanel();
        userPanel.setBackground(Color.DARK_GRAY);
        userPanel.setPreferredSize(new Dimension(300,d[1]-200));
        return userPanel;
    }

    private JPanel addBottomBar(){
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.GRAY);
        bottomPanel.setPreferredSize(new Dimension(d[0],100));
        bottomPanel.setLayout(new BorderLayout(5,0));

        JButton homeButton = new JButton();
        homeButton.setBackground(Color.WHITE);
        homeButton.setPreferredSize(new Dimension(100,100));
        bottomPanel.add(homeButton,BorderLayout.WEST);

        JTextArea console = new JTextArea();
        console.setPreferredSize(new Dimension(d[0]-200,80));
        console.setBackground(Color.BLACK);
        console.setForeground(Color.WHITE);
        console.setBorder(new EmptyBorder(20,20,20,20));
        console.setText("Location:\tItem:\tQuantity:\tPrice:\t");
        bottomPanel.add(console,BorderLayout.CENTER);

        JButton loginButton = new JButton();
        loginButton.setBackground(Color.WHITE);
        loginButton.setPreferredSize(new Dimension(100,100));
        bottomPanel.add(loginButton,BorderLayout.EAST);

        try{
            Icon homeIcon = new ImageIcon("images/home.png");
            homeButton.setIcon(homeIcon);
            Icon loginIcon = new ImageIcon("images/login.png");
            loginButton.setIcon(loginIcon);
        }catch (Exception e) {e.printStackTrace();}

        loginButton.addActionListener(e->{
            UserDetails ud = new UserDetails();
            ud.fieldNames = new String[2];
            ud.fieldNames[0] = "Username";
            ud.fieldNames[1] = "Password";
            ud.createWindow(500,430, "Login", ud.fieldNames,200,250);
        });

        homeButton.addActionListener(e -> {
            homeFrame.dispose();
            createHomeInterface();
        });

        return bottomPanel;
    }

    void createHomeInterface(){

        int[] dim = getScreenDimensions();
        JPanel homePanel = new JPanel();
        homePanel.setSize(dim[0],dim[1]);
        homePanel.setLayout(new BorderLayout());

        homePanel.add(setTitle(),BorderLayout.NORTH);
        homePanel.add(addBottomBar(),BorderLayout.SOUTH);
        homePanel.add(addSearch(),BorderLayout.WEST);
        //homePanel.add(addMap(),BorderLayout.CENTER);
        homePanel.add(addUserHistoryPanel(),BorderLayout.EAST);

        homeFrame = new JFrame();
        homeFrame.setSize(dim[0],dim[1]);
        homeFrame.setTitle("ESG");
        homeFrame.add(homePanel);
        homeFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        homeFrame.setVisible(true);
    }
}
