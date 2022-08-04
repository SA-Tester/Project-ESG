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
import javax.swing.JOptionPane;
import javax.swing.DefaultComboBoxModel;

import java.awt.Color;
import java.awt.Font;

import java.util.Objects;

public class Signup extends UserInterfaces{
    static JFrame signupFrame = new JFrame();
    static JPanel signupPanel = new JPanel();
    final private static String title = "Sign Up";
    private JTextField nameTextBox;
    private JTextField addressTextBox;
    private JTextField mobileTextBox;
    private JTextField usernameTextBox;
    private JPasswordField inputPasswordTextBox;
    private JPasswordField confirmPasswordFieldTextBox;
    private JComboBox<String> provinceCombo;
    private JComboBox<String> districtCombo;
    private JComboBox<String> cityCombo;
    private JTextField gramaNiladhariTextBox;
    private JComboBox<String> privilegeTypeCombo;
    private JButton signup;
    private JButton cancel;
    final private int width = 800;
    final private int height = 950;
    int[] dim = new Home().getScreenDimensions();

    @Override
    JPanel createPanel() {
        nameTextBox = createJTextField(nameTextBox,0);
        addressTextBox = createJTextField(addressTextBox,60);
        mobileTextBox = createJTextField(mobileTextBox,120);
        usernameTextBox = createJTextField(usernameTextBox,180);
        inputPasswordTextBox = createJPasswordField(inputPasswordTextBox,240);
        confirmPasswordFieldTextBox = createJPasswordField(confirmPasswordFieldTextBox,300);
        provinceCombo = createJComboBox(provinces, 360);
        districtCombo = createJComboBox(getDistricts(provinceCombo.getSelectedIndex()),420);
        cityCombo = createJComboBox(getCities(Objects.requireNonNull(districtCombo.getSelectedItem()).toString()), 480);
        gramaNiladhariTextBox = createJTextField(gramaNiladhariTextBox,540);
        String[] privilegeTypes = {"Admin", "User"};
        privilegeTypeCombo = createJComboBox(privilegeTypes,600);

        signup = createJButton(signup,"Sign Up",50,650);
        cancel = createJButton(cancel,"Cancel",width-200,650);

        int yHeight = 0;
        String[] labelNameList = {"Name","Address","Mobile","Username","Password","Confirm Password", "Province", "District", "City", "Grama-Niladhari Division", "Privilege Type"};
        for(String s: labelNameList){
            signupPanel.add(createJLabel(s,yHeight));
            yHeight += 60;
        }

        //Override Districts and Cities Combo Box depending on the input of Province Combo
        provinceCombo.addActionListener(e -> {
            districtCombo.setModel(new DefaultComboBoxModel<>(getDistricts(provinceCombo.getSelectedIndex())));
            cityCombo.setModel(new DefaultComboBoxModel<>(getCities(districtCombo.getSelectedItem().toString())));
        });

        //Override Cities Combo Box depending on the input of Districts Combo
        districtCombo.addActionListener(e -> cityCombo.setModel(new DefaultComboBoxModel<>(getCities(districtCombo.getSelectedItem().toString()))));

        signup.addActionListener(e -> {
            char[] passIn = inputPasswordTextBox.getPassword();
            char[] confirmPassIn = confirmPasswordFieldTextBox.getPassword();
            String password1 = new String(passIn);
            String password2 = new String(confirmPassIn);

            if(password1.strip().equals(password2.strip())){
                String name = nameTextBox.getText().strip();
                String address = addressTextBox.getText().strip();
                String mobile = mobileTextBox.getText().strip();
                String username = usernameTextBox.getText().strip();
                String province = Objects.requireNonNull(Objects.requireNonNull(provinceCombo.getSelectedItem()).toString());
                String district = Objects.requireNonNull(districtCombo.getSelectedItem().toString());
                String city = Objects.requireNonNull(Objects.requireNonNull(cityCombo.getSelectedItem()).toString());
                String gNOffice = gramaNiladhariTextBox.getText().strip();
                String privilegeType = Objects.requireNonNull(privilegeTypeCombo.getSelectedItem()).toString().toLowerCase();

                if(privilegeType.equals("admin")){
                    String validationMsg = "INVALID";
                    try {
                        String inputEmpID = JOptionPane.showInputDialog(signupFrame, "Enter Employee ID: ", "Verify Admin Privileges", JOptionPane.QUESTION_MESSAGE);
                        String inputOfficeCode = JOptionPane.showInputDialog(signupFrame, "Enter Office Code: ", "Verify Admin Privileges", JOptionPane.QUESTION_MESSAGE);
                        validationMsg = new infoAdmin().checkAdminInfo(inputEmpID, inputOfficeCode);
                    }catch (NullPointerException ne){ne.printStackTrace();}

                    if(validationMsg.equals("VALID")){
                        new intoSignUp().addToSignUpFile(name,address,mobile,username,province,district,city,gNOffice);
                        new intoLogin().addToLoginFile(username,password1,privilegeType);
                        signupFrame.dispose();
                        new Login().createJFrame();
                    }
                    else{
                        JOptionPane.showMessageDialog(signupFrame,"Admin Privileges cannot be granted","Error",JOptionPane.ERROR_MESSAGE);
                    }
                }
                else{
                    new intoSignUp().addToSignUpFile(name,address,mobile,username,province,district,city,gNOffice);
                    new intoLogin().addToLoginFile(username,password1,privilegeType);
                    signupFrame.dispose();
                    new Login().createJFrame();
                }
            }
            else{
                JOptionPane.showMessageDialog(signupFrame,"Passwords does not match","Error",JOptionPane.ERROR_MESSAGE);
            }
        });

        cancel.addActionListener(e -> signupFrame.dispose());

        signupPanel.add(createTitle());
        signupPanel.add(nameTextBox);
        signupPanel.add(addressTextBox);
        signupPanel.add(mobileTextBox);
        signupPanel.add(usernameTextBox);
        signupPanel.add(inputPasswordTextBox);
        signupPanel.add(confirmPasswordFieldTextBox);
        signupPanel.add(provinceCombo);
        signupPanel.add(districtCombo);
        signupPanel.add(cityCombo);
        signupPanel.add(gramaNiladhariTextBox);
        signupPanel.add(privilegeTypeCombo);
        signupPanel.add(signup);
        signupPanel.add(cancel);

        signupPanel.setBackground(Color.BLACK);
        signupPanel.setSize(width,height);
        signupPanel.setLayout(null);
        return signupPanel;
    }

    @Override
    void createJFrame() {
        signupFrame.setName(title.toLowerCase()+"Frame");
        signupFrame.add(createPanel());
        signupFrame.pack();
        signupFrame.setSize(width,height);
        signupFrame.setLocation((dim[0]-width)/2, (dim[1]-height)/2);
        signupFrame.setVisible(true);
        signupFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }
    @Override
    JLabel createTitle() {
        JLabel titleLabel = new JLabel(title);
        titleLabel.setBounds(width/2-40,20,width, 40);
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
    JPasswordField createJPasswordField(JPasswordField passField, int y) {
        passField = new JPasswordField();
        passField.setFont(new Font("Arial",Font.PLAIN,20));
        passField.setBounds(400,100+y,350,30);
        Border border = BorderFactory.createLineBorder(Color.WHITE,3);
        passField.setBorder(border);
        return passField;
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

    JComboBox<String> createJComboBox(String[] list, int yHeight){
        JComboBox<String> comboBox = new JComboBox<>(list);
        comboBox.setFont(new Font("Arial",Font.PLAIN,20));
        comboBox.setBounds(400,100+yHeight,350,30);
        Border border = BorderFactory.createLineBorder(Color.WHITE,1);
        comboBox.setBorder(border);
        return comboBox;
    }

    private static class intoSignUp extends Files.SignUpDetails{
        void addToSignUpFile(String name, String address, String mobile, String username,String province, String district, String city, String gNOffice){
            Files.SignUpDetails.writeToSignUpDetails(name,address,mobile,username,province,district,city,gNOffice);
        }
    }

    private static class infoAdmin extends Files.AdminDetails{
        String checkAdminInfo(String empCode, String officeCode){
            return Files.AdminDetails.readAdminDetails(empCode,officeCode);
        }
    }

    private static class intoLogin extends Files.LoginInfo{
        void addToLoginFile(String username, String password, String privilegeType){
            Files.LoginInfo.writeToLoginInfo(username,password,privilegeType);
        }
    }
}
