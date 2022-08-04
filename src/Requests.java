import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.BorderFactory;
import javax.swing.JPasswordField;
import javax.swing.border.Border;
import javax.swing.WindowConstants;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

import java.awt.Color;
import java.awt.Font;

import java.util.Objects;

public class Requests extends UserInterfaces{

    static JFrame requestFrame = new JFrame();
    static JPanel requestPanel = new JPanel();
    private JTextField itemNameTextBox;
    private JTextField quantityTextBox;
    private JComboBox<String> preferredProvinceCombo; //Set to saved settings at signup
    private JComboBox<String> preferredDistrictCombo;
    private JComboBox<String> preferredCityCombo;
    private JTextField priceTextBox;
    private JButton needItButton;
    private JButton haveItButton;
    private JButton confirm;
    private JButton cancel;
    private static final String title = "Post a Request";
    int[] dim = new Home().getScreenDimensions();
    private static final int width = 800;
    private static final int height = 750;

    @Override
    JPanel createPanel() {
        itemNameTextBox = createJTextField(itemNameTextBox,0);
        quantityTextBox = createJTextField(quantityTextBox,60);
        preferredProvinceCombo = createJComboBox(provinces,120);
        preferredDistrictCombo = createJComboBox(getDistricts(preferredProvinceCombo.getSelectedIndex()),180);
        preferredCityCombo = createJComboBox(getCities(Objects.requireNonNull(preferredDistrictCombo.getSelectedItem()).toString()),240);
        priceTextBox = createJTextField(priceTextBox,300);
        needItButton = createJButton(needItButton,"I Need It", 250, 360);
        haveItButton = createJButton(haveItButton, "I Have It", 430, 360);
        confirm = createJButton(confirm, "Confirm", 50, 450);
        cancel = createJButton(cancel,"Cancel", width-250, 450);

        int yHeight = 0;
        String[] labelNames = {"Item Name", "Quantity", "Preferred Province", "Preferred District","Preferred City", "Price (If Any)"};
        for (String s: labelNames){
            requestPanel.add(createJLabel(s,yHeight));
            yHeight += 60;
        }

        preferredProvinceCombo.addActionListener(e -> {
            preferredDistrictCombo.setModel(new DefaultComboBoxModel<>(getDistricts(preferredProvinceCombo.getSelectedIndex())));
            preferredCityCombo.setModel(new DefaultComboBoxModel<>(getCities(preferredDistrictCombo.getSelectedItem().toString())));
        });

        preferredDistrictCombo.addActionListener(e -> preferredCityCombo.setModel(new DefaultComboBoxModel<>(getCities(preferredDistrictCombo.getSelectedItem().toString()))));
        confirm.addActionListener(e -> {
            //Post the Request to Map, JTextArea, Update Places List on left (post co-ords to placeMark file),
        });

        cancel.addActionListener(e -> requestFrame.dispose());

        requestPanel.add(createTitle());
        requestPanel.add(itemNameTextBox);
        requestPanel.add(quantityTextBox);
        requestPanel.add(preferredProvinceCombo);
        requestPanel.add(preferredDistrictCombo);
        requestPanel.add(preferredCityCombo);
        requestPanel.add(priceTextBox);
        requestPanel.add(needItButton);
        requestPanel.add(haveItButton);
        requestPanel.add(confirm);
        requestPanel.add(cancel);
        requestPanel.setLayout(null);
        requestPanel.setBackground(Color.BLACK);
        requestPanel.setSize(width,height);
        return requestPanel;
    }

    @Override
    void createJFrame() {
        requestFrame.setName(title.toLowerCase()+"Frame");
        requestFrame.add(createPanel());
        requestFrame.pack();
        requestFrame.setSize(width,height);
        requestFrame.setLocation((dim[0]-width)/2, (dim[1]-height)/2);
        requestFrame.setVisible(true);
        requestFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }
    @Override
    JLabel createTitle() {
        JLabel titleLabel = new JLabel(title);
        titleLabel.setBounds(width/2-100,20,width, 40);
        titleLabel.setFont(new Font("Arial", Font.BOLD,35));
        titleLabel.setForeground(Color.WHITE);
        return titleLabel;
    }

    @Override
    JLabel createJLabel(String labelName, int y) {
        JLabel label = new JLabel(labelName+": ");
        label.setFont(new Font("Arial",Font.BOLD,20));
        label.setForeground(Color.WHITE);
        label.setBounds(50,100+y,350,30);
        return label;
    }

    @Override
    JTextField createJTextField(JTextField textField, int y) {
        textField = new JTextField();
        textField.setFont(new Font("Arial",Font.PLAIN,20));
        textField.setBounds(400,100+y,350,30);
        Border border = BorderFactory.createLineBorder(Color.WHITE,3);
        textField.setBorder(border);
        return textField;
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
    JComboBox<String> createJComboBox(String[] list, int yHeight) {
        JComboBox<String> comboBox = new JComboBox<>(list);
        comboBox.setFont(new Font("Arial",Font.PLAIN,20));
        comboBox.setBounds(400,100+yHeight,350,30);
        Border border = BorderFactory.createLineBorder(Color.WHITE,1);
        comboBox.setBorder(border);
        return comboBox;
    }

    @Override
    JPasswordField createJPasswordField(JPasswordField passField, int y) {
        return null;
    }
}