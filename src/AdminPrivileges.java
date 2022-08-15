import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.WindowConstants;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JScrollPane;

import java.awt.Color;
import java.awt.Font;
import java.awt.Component;
import java.awt.Dimension;

public class AdminPrivileges extends UserInterfaces{
    JFrame adminFrame = new JFrame();
    private final int width = 1500;
    private final int height = 900;
    private final int[] dim = new Home().getScreenDimensions();

    @Override
    JPanel createPanel() {
        JPanel adminPanel = new JPanel();

        adminPanel.add(createTitle());
        adminPanel.add(createJTable());

        adminPanel.setSize(width,height);
        adminPanel.setBackground(Color.BLACK);
        adminPanel.setLayout(null);
        return adminPanel;
    }

    @Override
    void createJFrame() {
        adminFrame.add(createPanel());
        adminFrame.setSize(width,height);
        adminFrame.setLocation(dim[0]/2-width/2, dim[1]/2-height/2);
        adminFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        adminFrame.setVisible(true);
    }
    @Override
    JLabel createTitle() {
        String title = "Ongoing Transfers";
        JLabel titleLabel = new JLabel(title);
        titleLabel.setBounds(width/2-150,30,500, 40);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(new Font("Arial", Font.BOLD,35));
        titleLabel.setForeground(Color.WHITE);
        return titleLabel;
    }

    JScrollPane createJTable(){
        String[][] data = {{"H1","hello","0451232444","yellow","0778945221", "central", "Kandy","Ampitiya"}};
        String[] columns = {"Request ID", "Posted By", "Telephone", "Reserved By", "Telephone", "Transferred Province", "Transferred District", "Transferred City"};

        JTable transferTable = new JTable(data, columns);
        transferTable.setBounds(50,100,width-100,height-300);

        transferTable.getTableHeader().setFont(new Font("Arial", Font.BOLD,15));
        transferTable.getTableHeader().setPreferredSize(new Dimension(100,30));

        transferTable.setFont(new Font("Arial", Font.PLAIN,15));
        transferTable.setRowHeight(25);

        JScrollPane scrollPane = new JScrollPane(transferTable);
        scrollPane.setBounds(50,100,width-100,height-300);

        return scrollPane;
    }

    @Override
    JButton createJButton(JButton btn, String btnName, int xDistance, int yDistance) {
        btn = new JButton(btnName);
        btn.setBounds(xDistance,150+yDistance,150,50);
        btn.setForeground(Color.WHITE);
        btn.setBackground(Color.DARK_GRAY);
        btn.setFont(new Font("Arial",Font.BOLD,18));
        return btn;
    }

    @Override
    JLabel createJLabel(String labelName, int y) {
        /*JLabel label = new JLabel(labelName+": ");
        label.setName(labelName.toLowerCase()+"Label");
        label.setFont(new Font("Arial",Font.BOLD,20));
        label.setForeground(Color.WHITE);
        label.setBounds(50,100+y,250,30);
        return label;*/
        return null;
    }

    @Override
    JTextField createJTextField(JTextField textField, int y) {return null;}
    @Override
    JPasswordField createJPasswordField(JPasswordField passField, int y) {return null;}
    @Override
    JComboBox<String> createJComboBox(String[] list, int yHeight) {return null;}
}
