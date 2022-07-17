import javax.swing.*;
import javax.swing.border.Border;

import java.awt.Color;
import java.awt.Font;
import java.awt.Component;

public class UserDetails {
    JPanel panel;
    JFrame frame;
    String[] fieldNames;
    int[] dim = new Home().getScreenDimensions();

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
        label.setBounds(50,100+y,150,30);
        return label;
    }

    protected JTextField addTextFields(String textFieldName, int y){
        JTextField textField = new JTextField();
        textField.setName(textFieldName.toLowerCase()+"TextBox");
        textField.setFont(new Font("Arial",Font.PLAIN,20));
        textField.setBounds(200,100+y,250,30);

        Border border = BorderFactory.createLineBorder(Color.WHITE,3);
        textField.setBorder(border);
        return textField;
    }

    protected JPasswordField addPasswordField(int y){
        JPasswordField passField = new JPasswordField();
        passField.setName("passwordTextBox");
        passField.setFont(new Font("Arial",Font.PLAIN,20));
        passField.setBounds(200,100+y,250,30);

        Border border = BorderFactory.createLineBorder(Color.WHITE,3);
        passField.setBorder(border);
        return passField;
    }

    protected JButton addButton(String btnName, int x, int y){
        JButton btn = new JButton(btnName);
        btn.setBounds(x,150+y,150,50);
        btn.setForeground(Color.WHITE);
        btn.setBackground(Color.DARK_GRAY);
        btn.setFont(new Font("Arial",Font.BOLD,18));
        return btn;
    }

    public void createWindow(int width, int height, String title, String[] fieldNames){
        panel = new JPanel();
        panel.setBackground(Color.BLACK);
        panel.setSize(width,height);
        panel.setLayout(null);

        panel.add(setTitle(title,width));

        this.fieldNames = fieldNames;
        for(int i=0; i<fieldNames.length; i++){
            panel.add(addLabels(fieldNames[i],i*70));

            if(fieldNames[i].equals("Password")){
                panel.add(addPasswordField(i*70));
            }
            else {
                panel.add(addTextFields(fieldNames[i], i * 70));
            }
        }

        panel.add(addButton(title,50,fieldNames.length*70));
        panel.add(addButton("Cancel", 300,fieldNames.length*70));

        frame = new JFrame();
        frame.add(panel);
        frame.pack();
        frame.setSize(width,height);
        frame.setLocation((dim[0]-width)/2, (dim[1]-height)/2);
        frame.setVisible(true);
    }
}
