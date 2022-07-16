import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Box;
import javax.swing.WindowConstants;
import javax.swing.BoxLayout;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import java.awt.Dimension;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.BorderLayout;

public class Home {
    Styles st = new Styles();
    int[] d = getScreenDimensions();
    int[] getScreenDimensions(){
        int[] dim = new int[2];
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        dim[0] = (int)d.getWidth();
        dim[1] = (int)d.getHeight();

        return dim;
    }

    private JPanel setTitle(){
        JPanel titleBar = new JPanel();
        titleBar.setBackground(Color.BLACK);
        titleBar.setPreferredSize(new Dimension(d[0],100)); //Polymorphism: Because setSize also take new Dimension(double, double) inputs
        titleBar.setLayout(new BoxLayout(titleBar,BoxLayout.Y_AXIS)); //Used to add content vertically to the titleBar
        titleBar.setBorder(new EmptyBorder(15,15,15,15)); //Set Padding to Panel

        st.setText(titleBar,"ESG",30,Color.WHITE,"center");
        titleBar.add(Box.createRigidArea(new Dimension(0,5))); //Add a vertical Space Between 2 titles
        st.setText(titleBar,"Environmental Social Governance",20,Color.WHITE,"center");

        return titleBar;
    }

    private JPanel addSearch(){
        JPanel locationPanel = new JPanel();
        locationPanel.setBackground(Color.DARK_GRAY);
        locationPanel.setPreferredSize(new Dimension(300,d[1]-200));
        locationPanel.setLayout(null);
        locationPanel.setBorder(new EmptyBorder(15,15,15,15));

        st.setTextBoxStyle(locationPanel,20,20,200,40);

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
        homePanel.add(addMap(),BorderLayout.CENTER);
        homePanel.add(addUserHistoryPanel(),BorderLayout.EAST);

        JFrame homeFrame = new JFrame();
        homeFrame.setSize(dim[0],dim[1]);
        homeFrame.setTitle("ESG");
        homeFrame.add(homePanel);
        homeFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        homeFrame.setVisible(true);
    }
}
