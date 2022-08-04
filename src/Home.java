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

public class Home{
    JFrame homeFrame; //Identifier of Main Window
    protected int[] dim = getScreenDimensions();////Array holding screen width and height. Only used by hte Home class
    static MapTemplate mp = new MapTemplate();

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
        titleBar.setPreferredSize(new Dimension(dim[0],100)); //Runtime Polymorphism: Because setSize also take new Dimension(double, double) inputs
        titleBar.setLayout(new BoxLayout(titleBar,BoxLayout.Y_AXIS)); //Used to add content vertically to the titleBar
        titleBar.setBorder(new EmptyBorder(15,15,15,15)); //Set Padding to Panel

        setText(titleBar,"ESG",30);
        titleBar.add(Box.createRigidArea(new Dimension(0,5))); //Add a vertical Space Between 2 titles
        setText(titleBar,"Environmental Social Governance",20);

        return titleBar;
    }

    public static class Left{
        public static JButton markAsCompleted = new JButton();
        private static double selectedLat;
        private static double selectedLon;

        private JPanel addLeftPanel(){
            JPanel leftPanel = new JPanel();
            leftPanel.setBackground(Color.DARK_GRAY);
            leftPanel.setPreferredSize(new Dimension(300,800));//d[1]-200
            leftPanel.setLayout(null);
            leftPanel.setBorder(new EmptyBorder(15,15,15,15));

            JComboBox<Object> locationList = new JComboBox<>();
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

            for(String s: placeMarkNameList){
                locationList.addItem(s);
            }
            locationList.setBounds(20, 20, 200, 40);
            locationList.setFont(new Font("Arial",Font.PLAIN,18));
            locationList.setBorder(BorderFactory.createLineBorder(Color.WHITE,1));
            leftPanel.add(locationList);

            JButton searchButton = new JButton();
            searchButton.setSize(50,41);
            searchButton.setLocation(230,20);
            searchButton.setBackground(Color.WHITE);
            leftPanel.add(searchButton);

            searchButton.addActionListener(e -> {
                if(locationList.getSelectedIndex() > -1){
                    selectedLat = Double.parseDouble(placeMarkLatList.get(locationList.getSelectedIndex()));
                    selectedLon = Double.parseDouble(placeMarkLonList.get(locationList.getSelectedIndex()));
                    mp.goTo(selectedLat,selectedLon);
                }
            });

            try{
                Icon searchIcon = new ImageIcon("images/search.png");
                searchButton.setIcon(searchIcon);
            }catch (Exception e){e.printStackTrace();}

            markAsCompleted.addActionListener(e -> {
                if (placeMarkNameList.size() > 0) {
                    int index = locationList.getSelectedIndex();
                    mp.removePlaceMark(selectedLat, selectedLon);
                    placeMarkNameList.remove(index);
                    locationList.removeItem(locationList.getSelectedItem());
                }
            });
            leftPanel.add(markAsCompleted);

            return leftPanel;
        }
    }

    //Add map to main window
    private JPanel addMap() {
        JPanel wwdPanel = new MapTemplate.MapPanel(new Dimension(dim[0] - 670, dim[1] - 260), true);
        mp.addPlaceMark(50, 60, "Place 1");
        mp.addPlaceMark(100, 35, "Place 2");
        return wwdPanel;
    }

    //Add user history panel to main window
    public static class Right{
        public static JButton postARequest = new JButton();
        private JPanel addRightPanel(){
            JPanel rightPanel = new JPanel();
            rightPanel.setBackground(Color.DARK_GRAY);
            rightPanel.setLayout(null);
            rightPanel.setPreferredSize(new Dimension(300,800)); //dim[1] - 200
            postARequest.addActionListener(e -> new Requests().createJFrame());
            rightPanel.add(postARequest);

            return rightPanel;
        }
    }

    //Add Bottom Bar to main window
    private JPanel addBottomBar(){
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.GRAY);
        bottomPanel.setPreferredSize(new Dimension(dim[0],100));
        bottomPanel.setLayout(new BorderLayout(5,0));

        JButton homeButton = new JButton();
        homeButton.setBackground(Color.WHITE);
        homeButton.setPreferredSize(new Dimension(100,100));
        bottomPanel.add(homeButton,BorderLayout.WEST);

        JTextArea console = new JTextArea();
        console.setPreferredSize(new Dimension(dim[0]-200,80));
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
            new Login().createJFrame();
        });

        homeButton.addActionListener(e -> {
            this.homeFrame.dispose();
            new Home().createHomeInterface();
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
        homePanel.add(new Left().addLeftPanel(),BorderLayout.WEST);
        homePanel.add(addMap(),BorderLayout.CENTER);
        homePanel.add(new Right().addRightPanel(),BorderLayout.EAST);

        homeFrame = new JFrame();
        homeFrame.setSize(dim[0],dim[1]);
        homeFrame.setTitle("ESG");
        homeFrame.add(homePanel);
        homeFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        homeFrame.setVisible(true);
    }
}
