import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import javax.swing.border.Border;
import javax.swing.BorderFactory;

import java.awt.Component;
import java.awt.Color;
import java.awt.Font;


public class Styles {
    public void setText(JPanel panel, String text,int fontSize,Color color, String alignment){
        JLabel label = new JLabel(text);
        label.setForeground(color);
        label.setFont(new Font("Arial",Font.BOLD,fontSize));

        switch (alignment.toLowerCase()) {
            case "center" -> label.setAlignmentX(Component.CENTER_ALIGNMENT);
            case "left" -> label.setAlignmentX(Component.LEFT_ALIGNMENT);
            case "right" -> label.setAlignmentX(Component.RIGHT_ALIGNMENT);
        }

       panel.add(label);
    }

    public void setTextBoxStyle(JPanel panel, int x, int y, int width, int height){
        JTextField textField = new JTextField();
        Font textFont = new Font("Arial",Font.PLAIN,18);
        textField.setFont(textFont);
        Border textBoxBorder = BorderFactory.createLineBorder(Color.WHITE,3);
        textField.setBorder(textBoxBorder);
        textField.setBounds(x,y,width,height);
        panel.add(textField);
    }
}
