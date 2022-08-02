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
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.border.EmptyBorder;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.Icon;
import javax.swing.WindowConstants;
import javax.swing.JComboBox;


import java.awt.Component;
import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.BorderLayout;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Scanner;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Home{
    //Identifier of Main Window
    static JFrame homeFrame;
    static JPanel leftPanel = new JPanel();
    //Array holding screen width and height. Only used by hte Home class
    protected int[] d = getScreenDimensions();
    MapTemplate mp = new MapTemplate();
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

    //Set Title of a JPanel
    private JPanel setTitle(){
        JPanel titleBar = new JPanel();
        titleBar.setBackground(Color.BLACK);
        titleBar.setPreferredSize(new Dimension(d[0],100)); //Runtime Polymorphism: Because setSize also take new Dimension(double, double) inputs
        titleBar.setLayout(new BoxLayout(titleBar,BoxLayout.Y_AXIS)); //Used to add content vertically to the titleBar
        titleBar.setBorder(new EmptyBorder(15,15,15,15)); //Set Padding to Panel

        setText(titleBar,"ESG",30);
        titleBar.add(Box.createRigidArea(new Dimension(0,5))); //Add a vertical Space Between 2 titles
        setText(titleBar,"Environmental Social Governance",20);

        return titleBar;
    }

    //Add the Search Panel to the main window
    public JPanel addLeftPanel(){
        //Create JPanel containing Search and Admin Buttons
        leftPanel.setBackground(Color.DARK_GRAY);
        leftPanel.setPreferredSize(new Dimension(300,d[1]-200));
        leftPanel.setLayout(null);
        leftPanel.setBorder(new EmptyBorder(15,15,15,15));

        ArrayList<String> placeMarkNameList = new ArrayList<>();
        ArrayList<String> placeMarkLatList = new ArrayList<>();
        ArrayList<String> placeMarkLonList = new ArrayList<>();
        try {
            File placeMarks = new File("data/PlaceMarkDetails.csv");
            Scanner scanner = new Scanner(placeMarks);
            while(scanner.hasNextLine()){
                String[] data = scanner.nextLine().split(",");
                placeMarkNameList.add(data[0]);
                placeMarkLatList.add(data[1]);
                placeMarkLonList.add(data[2]);
            }
            scanner.close();
        }catch (IOException e){
            e.printStackTrace();
        }

        JComboBox<Object> locationList = new JComboBox<>(placeMarkNameList.toArray());
        locationList.setBounds(20, 20, 200, 40);
        locationList.setFont(new Font("Arial",Font.PLAIN,18));
        locationList.setBorder(BorderFactory.createLineBorder(Color.WHITE,1));
        leftPanel.add(locationList);

        JButton searchButton = new JButton();
        searchButton.setSize(50,41);
        searchButton.setLocation(230,20);
        searchButton.setBackground(Color.WHITE);
        leftPanel.add(searchButton);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //mp.removePlaceMark(50,60);
                double selectedLat = Double.parseDouble(placeMarkLatList.get(locationList.getSelectedIndex()));
                double selectedLon = Double.parseDouble(placeMarkLonList.get(locationList.getSelectedIndex()));
                mp.goTo(selectedLat,selectedLon);
            }
        });

        try{
            Icon searchIcon = new ImageIcon("images/search.png");
            searchButton.setIcon(searchIcon);
        }catch (Exception e){e.printStackTrace();}

        return leftPanel;
    }

    //Add map to main window
    private JPanel addMap() {
        JPanel wwdPanel = new MapTemplate.MapPanel(new Dimension(d[0] - 670, d[1] - 260), true);
        mp.addPlaceMark(50, 60, "Place 1");
        mp.addPlaceMark(100, 35, "Place 2");
        return wwdPanel;
    }

    //Add user history panel to main window
    private JPanel addUserHistoryPanel(){
        JPanel userPanel = new JPanel();
        userPanel.setBackground(Color.DARK_GRAY);
        userPanel.setPreferredSize(new Dimension(300,d[1]-200));
        return userPanel;
    }

    //Add Bottom Bar to main window
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
            new Login().createLoginInterface();
        });

        homeButton.addActionListener(e -> {
            homeFrame.dispose();
            createHomeInterface();
        });

        return bottomPanel;
    }

    //Create the Main interface (Home)
    void createHomeInterface(){

        int[] dim = getScreenDimensions();
        JPanel homePanel = new JPanel();
        homePanel.setSize(dim[0],dim[1]);
        homePanel.setLayout(new BorderLayout());

        homePanel.add(setTitle(),BorderLayout.NORTH);
        homePanel.add(addBottomBar(),BorderLayout.SOUTH);
        homePanel.add(addLeftPanel(),BorderLayout.WEST);
        homePanel.add(addMap(),BorderLayout.CENTER);
        homePanel.add(addUserHistoryPanel(),BorderLayout.EAST);

        homeFrame = new JFrame();
        homeFrame.setSize(dim[0],dim[1]);
        homeFrame.setTitle("ESG");
        homeFrame.add(homePanel);
        homeFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        homeFrame.setVisible(true);
    }
}
