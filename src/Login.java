import java.io.File;
import java.io.FileNotFoundException;

import java.util.Scanner;

public class Login extends UserDetails{
    protected String inputUsername;
    protected String inputPassword;
    private String storedUsername;
    private String storedPassword;
    private String storedPrivilegeType;
    private String msg;

    abstract class GetData{
        abstract void verifyStoredLoginDetails();
        GetData(){
            try{
                File loginFile = new File("data/LoginInfo.csv");
                Scanner scanner = new Scanner(loginFile);
                while (scanner.hasNextLine()){
                    String data[] = scanner.nextLine().split(",");
                    storedUsername = data[0];
                    storedPassword = data[1];
                    storedPrivilegeType = data[2];

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
            };
        }
    }

    private class ViewData extends GetData{
        public void verifyStoredLoginDetails(){
            try{
                switch (msg){
                    case ("ADMIN PRIVILEGES"):
                        //System.out.println(msg);
                        break;

                    case ("USER PRIVILEGES"):
                        break;

                    default:
                        System.out.println(msg);
                }
            }catch(Exception e){
                System.out.println("AN UNEXPECTED ERROR OCCURRED");
                e.printStackTrace();
            };
        }
    }
    public void outLogin(){
        new ViewData().verifyStoredLoginDetails();
    }
}
