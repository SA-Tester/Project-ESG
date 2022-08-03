import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;

abstract class UserInterfaces {
    public JFrame frame;
    public JPanel panel;
    public JLabel label;
    public JButton button;

    abstract JLabel createTitle();
    abstract JPanel createPanel();
    abstract void createJFrame();


    abstract JLabel createJLabel(String labelName, int y);
    abstract JTextField createJTextField(JTextField textField, int y);
    abstract JPasswordField createJPasswordField(JPasswordField passField, int y);
    abstract JButton createJButton(JButton btn, String btnName, int xDistance, int yDistance);
}
