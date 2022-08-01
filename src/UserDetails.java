/*import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.BorderFactory;
import javax.swing.JPasswordField;
import javax.swing.border.Border;

import java.awt.Color;
import java.awt.Font;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;*/

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.BorderFactory;
import javax.swing.JPasswordField;
import javax.swing.border.Border;

import java.awt.Color;
import java.awt.Font;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

import java.util.ArrayList;

public class UserDetails{
    private JFrame frame;
    private String[] fieldNames;
    ArrayList <JTextField> textFields = new ArrayList<>();
    protected JPasswordField passField;

    int[] dim = new Home().getScreenDimensions();

    //ENCAPSULATION ==============================================================
    public String[] getFieldNames() {
        return fieldNames;
    }

    public void setFieldNames(String[] inputFieldNames){
        fieldNames = inputFieldNames;
    }
    //=============================================================================

    protected JLabel setTitle(String title, int panelWidth){
        JLabel titleLabel = new JLabel(title);
        titleLabel.setBounds(panelWidth/2-35,20,panelWidth, 40);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(new Font("Arial", Font.BOLD,35));
        titleLabel.setForeground(Color.WHITE);
        return titleLabel;
    }
    protected JLabel addLabels(String labelName, int y){
        JLabel label = new JLabel(labelName+": ");
        label.setFont(new Font("Arial",Font.BOLD,20));
        label.setForeground(Color.WHITE);
        label.setBounds(50,100+y,250,30);
        return label;
    }

    protected JTextField addTextFields(String textFieldName, int y, int afterLabelPaddingX,int widthOfTextField){
        JTextField textField = new JTextField();
        textField.setName(textFieldName.toLowerCase()+"TextBox");
        textField.setFont(new Font("Arial",Font.PLAIN,20));
        textField.setBounds(afterLabelPaddingX,100+y,widthOfTextField,30);

        Border border = BorderFactory.createLineBorder(Color.WHITE,3);
        textField.setBorder(border);

        textFields.add(textField);
        return textField;
    }

    protected JPasswordField addPasswordField(int y,int afterLabelPaddingX, int widthOfTextField){
        JPasswordField passField = new JPasswordField();
        passField.setName("passwordTextBox");
        passField.setFont(new Font("Arial",Font.PLAIN,20));
        passField.setBounds(afterLabelPaddingX,100+y,widthOfTextField,30);

        Border border = BorderFactory.createLineBorder(Color.WHITE,3);
        passField.setBorder(border);

        this.passField = passField;
        return passField;
    }

    protected JButton addButton(String btnName, int x, int y){
        JButton btn = new JButton(btnName);
        btn.setBounds(x,150+y,150,50);
        btn.setForeground(Color.WHITE);
        btn.setBackground(Color.DARK_GRAY);
        btn.setFont(new Font("Arial",Font.BOLD,18));

        if(btnName.equals("Cancel")){
            btn.addActionListener(e->{
                this.frame.dispose();
            });
        }

        else if(btnName.equals("Login")){
            Login login = new Login();
            btn.addActionListener(e->{
                for(JTextField textField: textFields){
                    if(textField.getName().equals("usernameTextBox")){
                        login.inputUsername = textField.getText();
                    }
                }
                char[] passIn = this.passField.getPassword();
                login.inputPassword = new String(passIn);
                login.outLogin();
            });
        }
        return btn;
    }

    public void createWindow(int width, int height, String title, String[] fieldNames, int afterLabelPaddingX,int widthOfTextField){
        JPanel panel = new JPanel();
        panel.setName(title+"Panel");
        panel.setBackground(Color.BLACK);
        panel.setSize(width,height);
        panel.setLayout(null);

        panel.add(setTitle(title,width));

        this.fieldNames = fieldNames;
        for(int i=0; i<fieldNames.length; i++){
            panel.add(addLabels(fieldNames[i],i*70));

            if(fieldNames[i].equals("Password")){
                panel.add(addPasswordField(i*70,afterLabelPaddingX,widthOfTextField));
            }
            else {
                panel.add(addTextFields(fieldNames[i], i * 70, afterLabelPaddingX, widthOfTextField));
            }
        }

        if(panel.getName().equals("LoginPanel")){
            Hyperlinks hyperLinks = new Hyperlinks();
            panel.add(hyperLinks.addHyperLink("Forgot Password?",width-195,90+fieldNames.length*70-1));
            panel.add(hyperLinks.addHyperLink("Signup", 50, 85+fieldNames.length*70-1));
        }

        panel.add(addButton(title,50,fieldNames.length*70));
        panel.add(addButton("Cancel", width-200,fieldNames.length*70));

        frame = new JFrame();
        frame.setName(title+"Frame");
        frame.add(panel);
        frame.pack();
        frame.setSize(width,height);
        frame.setLocation((dim[0]-width)/2, (dim[1]-height)/2);
        frame.setVisible(true);
    }

    //ABSTRACTION ===================================================================================================================================
    //Mouse Listener is an Interface Provided in java.awt.event.MouseListener class
    //When the interface is called for use all the abstract methods should be implemented along with them even they are not used.
    private class Hyperlinks extends JLabel implements MouseListener{
        protected JLabel addHyperLink(String linkTitle, int x, int y){
            JLabel hyperLink = new JLabel(linkTitle);
            hyperLink.setName(linkTitle.strip() + "Label");
            hyperLink.setForeground(Color.BLUE);
            hyperLink.setFont(new Font("Arial", Font.PLAIN,18));
            hyperLink.setBounds(x,y,150,30);
            hyperLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            hyperLink.addMouseListener(this);
            return hyperLink;
        }
        @Override
        public void mouseClicked(MouseEvent e) {
            Object getMouseSource = e.getSource();
            if (getMouseSource instanceof JLabel label) {
                if (label.getName().equals("SignupLabel")) {
                    if(frame.getName().equals("LoginFrame")) {
                        frame.dispose();
                    }

                    String[] signUpLabels = {"Name","Address","Mobile","Username","Password","Closest Regional Office","Privilege Type"};
                    fieldNames = new String[7];
                    for(int i=0; i<7; i++){
                        fieldNames[i] = signUpLabels[i];
                    }
                    createWindow(700,780,"Signup",fieldNames,300,350);
                }
            }
        }

        public void mouseEntered(MouseEvent e){}
        public void mouseExited(MouseEvent e){}
        public void mousePressed(MouseEvent e){}
        public void mouseReleased(MouseEvent e){}
    }
    //===============================================================================================================================================
}
