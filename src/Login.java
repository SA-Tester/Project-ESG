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

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Cursor;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class Login extends UserInterfaces{
    public static String currentLogin;
    JFrame loginFrame = new JFrame();
    JPanel loginPanel = new JPanel();
    final private static String title = "Login";
    private JTextField userNameTextBox;
    private String inputUsername;
    private JPasswordField passwordTextBox;
    private String inputPassword;
    private JButton login;
    private JButton cancel;
    final private int width = 500;
    final private int height = 430;
    final private int[] dim = new Home().getScreenDimensions();
    private String msg;

    @Override
    JPanel createPanel() {
        userNameTextBox = createJTextField(userNameTextBox, 0);
        passwordTextBox = createJPasswordField(passwordTextBox, 60);
        JLabel forgotPassword = new Hyperlinks().createHyperLink("Forgot Password?", 50);
        JLabel needToSignUp = new Hyperlinks().createHyperLink("SignUp", width - 109);
        login = createJButton(login,"Login",50,130);
        cancel = createJButton(cancel,"Cancel",width-200,130);

        login.addActionListener(e -> {
            inputUsername = userNameTextBox.getText();
            char[] passIn = passwordTextBox.getPassword();
            inputPassword = new String(passIn);

            msg = new VerifyLogin().checkLogin();
            if(msg.equals("ADMIN PRIVILEGES")){
                addShowOngoingTransfers();
                addPostARequest();
                addReserve();
                addUserHistory();
                addReservedActionsPanel();
                loginFrame.dispose();
            }
            else if(msg.equals("USER PRIVILEGES")){
                addPostARequest();
                addReserve();
                addUserHistory();
                addReservedActionsPanel();
                loginFrame.dispose();
            }
            else{
                JOptionPane.showMessageDialog(loginFrame,"Wrong Credentials","Error",JOptionPane.ERROR_MESSAGE);
            }
        });

        cancel.addActionListener(e -> loginFrame.dispose());

        loginPanel.setBackground(Color.BLACK);
        loginPanel.setSize(width,height);
        loginPanel.setLayout(null);

        loginPanel.add(createTitle());
        loginPanel.add(createJLabel("Username", 0));
        loginPanel.add(userNameTextBox);
        loginPanel.add(createJLabel("Password",60));
        loginPanel.add(passwordTextBox);
        loginPanel.add(forgotPassword);
        loginPanel.add(needToSignUp);
        loginPanel.add(login);
        loginPanel.add(cancel);

        return loginPanel;
    }

    @Override
    void createJFrame() {
        loginFrame.setName(title.toLowerCase()+"Frame");
        loginFrame.add(createPanel());
        loginFrame.pack();
        loginFrame.setSize(width,height);
        loginFrame.setLocation((dim[0]-width)/2, (dim[1]-height)/2);
        loginFrame.setVisible(true);
        loginFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    @Override
    JLabel createTitle() {
        JLabel titleLabel = new JLabel(title);
        titleLabel.setBounds(width/2-35,20,width, 40);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(new Font("Arial", Font.BOLD,35));
        titleLabel.setForeground(Color.WHITE);
        return titleLabel;
    }

    @Override
    JLabel createJLabel(String labelName, int y) {
        JLabel label = new JLabel(labelName+": ");
        label.setName(labelName.toLowerCase()+"Label");
        label.setFont(new Font("Arial",Font.BOLD,20));
        label.setForeground(Color.WHITE);
        label.setBounds(50,100+y,250,30);
        return label;
    }

    @Override
    JTextField createJTextField(JTextField textField, int y) {
        textField = new JTextField();
        textField.setFont(new Font("Arial",Font.PLAIN,20));
        textField.setBounds(200,100+y,250,30);
        Border border = BorderFactory.createLineBorder(Color.WHITE,3);
        textField.setBorder(border);
        return textField;
    }

    @Override
    JPasswordField createJPasswordField(JPasswordField passField, int y) {
        passField = new JPasswordField();
        passField.setFont(new Font("Arial",Font.PLAIN,20));
        passField.setBounds(200,100+y,250,30);
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

    @Override
    JComboBox<String> createJComboBox(String[] list, int yHeight) {
        return null;
    }

    private class Hyperlinks extends JLabel implements MouseListener {
        protected JLabel createHyperLink(String linkTitle, int x){
            JLabel hyperLink = new JLabel(linkTitle);
            hyperLink.setName(linkTitle.strip() + "Label");
            hyperLink.setForeground(Color.BLUE);
            hyperLink.setFont(new Font("Arial", Font.PLAIN,18));
            hyperLink.setBounds(x, 220,150,30);
            hyperLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            hyperLink.addMouseListener(this);
            return hyperLink;
        }
        @Override
        public void mouseClicked(MouseEvent e) {
            Object getMouseSource = e.getSource();
            if (getMouseSource instanceof JLabel label) {
                if (label.getName().equals("SignUpLabel")) {
                    loginFrame.dispose();
                    new Signup().createJFrame();
                }
                else if(label.getName().equals("Forgot Password?Label")){
                   showResetPasswordDialog();
                }
            }
        }
        public void mouseEntered(MouseEvent e){}
        public void mouseExited(MouseEvent e){}
        public void mousePressed(MouseEvent e){}
        public void mouseReleased(MouseEvent e){}
    }

    private void addShowOngoingTransfers() {
        Home.Left.onGoingButton.setText("View Ongoing Transfers");
        Home.Left.onGoingButton.setBounds(33, dim[1] - 330, 230, 40);
        Color green = new Color(50, 145, 35);
        Home.Left.onGoingButton.setBackground(green);
        Home.Left.onGoingButton.setForeground(Color.WHITE);
        Home.Left.onGoingButton.setFont(new Font("Arial", Font.PLAIN, 18));
        Home.Left.onGoingButton.setBorder(BorderFactory.createBevelBorder(1));
        Home.Left.getJTextArea().setSize(260,650);
    }

    private void addPostARequest(){
        Home.Right.postARequest.setText("Post");
        Home.Right.postARequest.setBounds(90, dim[1] - 330, 140, 40);
        Color green = new Color(50, 145, 35);
        Home.Right.postARequest.setBackground(green);
        Home.Right.postARequest.setForeground(Color.WHITE);
        Home.Right.postARequest.setFont(new Font("Arial", Font.PLAIN, 18));
        Home.Right.postARequest.setBorder(BorderFactory.createBevelBorder(1));
    }

    private void addReserve(){
        Home.Right.reserve.setText("Reserve Item");
        Home.Right.reserve.setBounds(90,dim[1]-400,140,40);
        Color green = new Color(50, 145, 35);
        Home.Right.reserve.setBackground(green);
        Home.Right.reserve.setForeground(Color.WHITE);
        Home.Right.reserve.setFont(new Font("Arial", Font.PLAIN,18));
        Home.Right.reserve.setBorder(BorderFactory.createBevelBorder(1));
    }

    private void addUserHistory(){
        Home.Right.userHistoryPanel.setSize(262,300);
        Home.Right.historyScroll.setSize(262,300);
        Home.Right.userHistoryPanel.setText("User History\n\n");

        UserHistory.getUserHistory();
        for(int i=0; i<UserHistory.history.size(); i++){//username,date, msg,itemName, qty
            String[] data = UserHistory.history.get(i);
            Home.Right.addToUserHistory(data[1],data[2],data[3],data[4],false);
        }
    }

    private void addReservedActionsPanel(){
        Home.Right.reservedActionsPanel.setSize(262,320);
        Home.Right.reservedScroll.setSize(262, 320);
        Home.Right.reservedActionsPanel.setText("Reserved Transactions\n");

        ArrayList<String[]> userReservedTransactions = Files.Reserved.getUserReservedTransactions(currentLogin);
        for (String[] userReservedTransaction : userReservedTransactions) {
            Home.Right.reservedActionsPanel.append("\nItem Name: " + userReservedTransaction[0] + "\n" + "Name: " + userReservedTransaction[1] + "\n"
                    + "Telephone: " + userReservedTransaction[2] + "\n");
        }
    }

    private void showResetPasswordDialog(){
        JPanel passwordPanel = new JPanel();
        passwordPanel.setSize(500,300);
        passwordPanel.setBackground(Color.BLACK);
        passwordPanel.setLayout(null);

        JLabel title = new JLabel("Password Confirmation");
        title.setBounds(120,20,300, 40);
        title.setFont(new Font("Arial", Font.BOLD,25));
        title.setForeground(Color.WHITE);
        passwordPanel.add(title);

        JLabel timerCount = new JLabel("");
        timerCount.setBounds(170,60,250,40);
        timerCount.setForeground(Color.WHITE);
        timerCount.setFont(new Font("Arial", Font.BOLD,18));
        passwordPanel.add(timerCount);

        JLabel telNoLabel = new JLabel("Telephone Number: ");
        telNoLabel.setFont(new Font("Arial",Font.BOLD,17));
        telNoLabel.setForeground(Color.WHITE);
        telNoLabel.setBounds(40,120,350,30);
        passwordPanel.add(telNoLabel);

        JTextField telNoText = new JTextField();
        telNoText.setFont(new Font("Arial",Font.BOLD,17));
        Border border = BorderFactory.createLineBorder(Color.WHITE,3);
        telNoText.setBorder(border);
        telNoText.setBounds(220,120,250,30);
        passwordPanel.add(telNoText);

        JButton okayButton = new JButton("OK");
        okayButton.setBounds(40,180,100,50);
        okayButton.setBackground(Color.BLACK);
        okayButton.setForeground(Color.WHITE);
        okayButton.setFont(new Font("Arial",Font.BOLD,17));
        passwordPanel.add(okayButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBounds(370,180,100,50);
        cancelButton.setBackground(Color.BLACK);
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFont(new Font("Arial",Font.BOLD,17));
        passwordPanel.add(cancelButton);

        JFrame passwordFrame = new JFrame();
        passwordFrame.add(passwordPanel);
        passwordFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        passwordFrame.setBounds(dim[0]/2-250, dim[1]/2-150,500,300);
        passwordFrame.setVisible(true);

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            int i = 30;
            @Override
            public void run() {
                timerCount.setText("Time Remaining: " + i/60 + ":" + i%60);
                i--;

                if(i<0){
                    timer.cancel();
                    timerCount.setText("Time Over !!!");
                    okayButton.setEnabled(false);
                }
            }
        }, 0, 1000);

        okayButton.addActionListener(e->{
            String telephone = telNoText.getText();

            if(!userNameTextBox.getText().isEmpty()){
                Files.SignUpDetails.readSignUpDetails(userNameTextBox.getText());

                if(telephone.equals(Files.SignUpDetails.getTelephoneNumber())){
                    passwordFrame.dispose();
                    showNewPasswordFrame();
                }
                else{
                    JOptionPane.showMessageDialog(passwordFrame,"Wrong Telephone Number", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            else{
                JOptionPane.showMessageDialog(passwordFrame,"Enter username","Error",JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e-> passwordFrame.dispose());
    }

    private void showNewPasswordFrame(){
        JPanel newPasswordPanel = new JPanel();
        newPasswordPanel.setSize(600,250);
        newPasswordPanel.setBackground(Color.BLACK);
        newPasswordPanel.setLayout(null);

        JLabel passwordLabel1 = new JLabel("New Password: ");
        passwordLabel1.setFont(new Font("Arial",Font.BOLD,17));
        passwordLabel1.setForeground(Color.WHITE);
        passwordLabel1.setBounds(40,40,350,30);
        newPasswordPanel.add(passwordLabel1);

        JPasswordField passwordText1 = new JPasswordField();
        passwordText1.setFont(new Font("Arial",Font.BOLD,17));
        Border border = BorderFactory.createLineBorder(Color.WHITE,3);
        passwordText1.setBorder(border);
        passwordText1.setBounds(260,40,300,30);
        newPasswordPanel.add(passwordText1);

        JLabel passwordLabel2 = new JLabel("Confirm New Password: ");
        passwordLabel2.setFont(new Font("Arial",Font.BOLD,17));
        passwordLabel2.setForeground(Color.WHITE);
        passwordLabel2.setBounds(40,80,350,30);
        newPasswordPanel.add(passwordLabel2);

        JPasswordField passwordText2 = new JPasswordField();
        passwordText2.setFont(new Font("Arial",Font.BOLD,17));
        passwordText2.setBorder(border);
        passwordText2.setBounds(260,80,300,30);
        newPasswordPanel.add(passwordText2);

        JButton confirmButton = new JButton("Confirm");
        confirmButton.setBounds(40,140,100,50);
        confirmButton.setBackground(Color.BLACK);
        confirmButton.setForeground(Color.WHITE);
        confirmButton.setFont(new Font("Arial",Font.BOLD,17));
        newPasswordPanel.add(confirmButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBounds(460,140,100,50);
        cancelButton.setBackground(Color.BLACK);
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFont(new Font("Arial",Font.BOLD,17));
        newPasswordPanel.add(cancelButton);

        JFrame newPasswordFrame = new JFrame();
        newPasswordFrame.add(newPasswordPanel);
        newPasswordFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        newPasswordFrame.setBounds(dim[0]/2-300, dim[1]/2-125,600,250);
        newPasswordFrame.setVisible(true);

        confirmButton.addActionListener(e->{
            char[] inputPassword1Char = passwordText1.getPassword();
            String inputPassword1 = new String(inputPassword1Char);

            char[] inputPassword2Char = passwordText2.getPassword();
            String inputPassword2 = new String(inputPassword2Char);

            if(inputPassword1.isBlank()){
                JOptionPane.showMessageDialog(newPasswordFrame,"Passwords Cannot be Empty", "Error", JOptionPane.ERROR_MESSAGE);
            }
            else if (!inputPassword1.equals(inputPassword2)){
                JOptionPane.showMessageDialog(newPasswordFrame,"Passwords does not match", "Error", JOptionPane.ERROR_MESSAGE);
            }
            else{
                Files.ResetPasswordRequests.setPassword(userNameTextBox.getText(), inputPassword1);
                Files.ResetPasswordRequests.updateLogin();
                newPasswordFrame.dispose();
                JOptionPane.showMessageDialog(newPasswordFrame,"Password Reset was Successful", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        cancelButton.addActionListener(e->newPasswordFrame.dispose());
    }

    private class VerifyLogin extends Files.LoginInfo{
        private String checkLogin(){
            return Files.LoginInfo.verifyLoginInfo(inputUsername,inputPassword);
        }
    }

    private static class UserHistory extends Files.UserHistory{
        static ArrayList <String[]> history = Files.UserHistory.userHistoryList;
        private static void getUserHistory(){
            Files.UserHistory.readFromUserHistory(currentLogin);
        }
    }
}
