import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.JButton;
import javax.swing.JFrame;

import java.util.Scanner;

public class Login extends UserInterfaces{
    static JFrame loginFrame = new JFrame();
    protected String inputUsername;
    protected String inputPassword;
    private String msg;

    public void createLoginInterface(){
        setFieldNames(new String[]{"Username", "Password"});
        createWindow(500,430, "Login", getFieldNames(),200,250);
    }

    abstract class GetData{
        abstract void verifyStoredLoginDetails();
        GetData(){
            try{
                File loginFile = new File("data/LoginInfo.csv");
                Scanner scanner = new Scanner(loginFile);
                while (scanner.hasNextLine()){
                    String[] data = scanner.nextLine().split(",");
                    String storedUsername = data[0];
                    String storedPassword = data[1];
                    String storedPrivilegeType = data[2];

                    if(inputUsername.equals(storedUsername) && inputPassword.equals(storedPassword) && storedPrivilegeType.equals("admin")){
                        msg = "ADMIN PRIVILEGES";
                    }
                    else if(inputUsername.equals(storedUsername) && inputPassword.equals(storedPassword) && storedPrivilegeType.equals("user")){
                        msg = "USER PRIVILEGES";
                    }
                    else{
                        msg = "ACCESS DENIED";
                    }
                }
                scanner.close();
            }
            catch(FileNotFoundException e){
                System.out.println("AN UNEXPECTED ERROR OCCURRED");
                e.printStackTrace();
            }
        }
    }

    private class ViewData extends GetData{
        public void verifyStoredLoginDetails(){
            try{
                switch (msg){
                    case ("ADMIN PRIVILEGES"):
                        addAdminPrivileges();
                        loginFrame.dispose();
                        break;

                    case ("USER PRIVILEGES"):
                        //System.out.println(msg);
                        break;

                    default:
                        System.out.println(msg);
                        break;
                }
            }catch(Exception e){
                System.out.println("AN UNEXPECTED ERROR OCCURRED");
                e.printStackTrace();
            }
        }
    }

    private void addAdminPrivileges(){
        JButton markAsCompleted = new JButton("Mark as Completed");
        markAsCompleted.setBounds(50,dim[1]-350,200,40);
        new Home().addLeftPanel().add(markAsCompleted);
    }
    public void outLogin(){
        new ViewData().verifyStoredLoginDetails();
    }
}
