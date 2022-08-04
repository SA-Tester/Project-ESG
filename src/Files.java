import java.util.Scanner;
import java.util.ArrayList;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Files {
    protected static class LoginInfo{

        protected static String verifyLoginInfo(String inputUsername, String inputPassword){
            String msg = "ACCESS DENIED";

            try{
                File loginInfoFile = new File("data/LoginInfo.csv");
                Scanner scanner = new Scanner(loginInfoFile);

                while (scanner.hasNextLine()){
                    String[] data = scanner.nextLine().split(",");
                    if(inputUsername.equals(data[0]) && inputPassword.equals(data[1]) && data[2].equals("admin")){
                        msg = "ADMIN PRIVILEGES";
                    }
                    else if(inputUsername.equals(data[0]) && inputPassword.equals(data[1]) && data[2].equals("user")){
                        msg = "USER PRIVILEGES";
                    }
                }
                scanner.close();
            }catch (IOException e){e.printStackTrace();}

            return msg;
        }

        protected static void writeToLoginInfo(String username, String password, String privilegeType){
            try{
                FileWriter loginInfoFile = new FileWriter("data/LoginInfo.csv",true);
                String formattedString = String.format("%s,%s,%s\n",username,password,privilegeType);
                loginInfoFile.append(formattedString);
                loginInfoFile.close();

            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    protected static class SignUpDetails{
        //static void readSignUpDetails(){}

        //name,address,mobile,username,password1,province, district, city, gNOffice,privilegeType
        protected static void writeToSignUpDetails(String name, String address, String mobile, String username, String province, String district, String city , String gNOffice){
            String formattedString = String.format("%s,%s,%s,%s,%s,%s,%s,%s\n",name,address,mobile,username,province,district,city,gNOffice);

            try{
                FileWriter signUpInfoFile = new FileWriter("data/SignUpInfo.csv",true);
                signUpInfoFile.append(formattedString);
                signUpInfoFile.close();

            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    protected static class AdminDetails{

        protected static String readAdminDetails(String inputEmpCode, String inputOfficeCode){
            String msg = "INVALID";
            try {
                File adminInfoFile = new File("data/AdminDetails.csv");
                Scanner scanner = new Scanner(adminInfoFile);

                while (scanner.hasNextLine()){
                    String[] data = scanner.nextLine().split(",");
                    if(inputEmpCode.equals(data[0]) && inputOfficeCode.equals(data[1])){
                        msg = "VALID";
                    }
                }
                scanner.close();
            }catch (IOException e){
                e.printStackTrace();
            }

            return msg;
        }
    }
    protected static class PlaceMarkDetails{
        //static void readPlaceMarkDetails(){};

        static void writeToPlaceMarkDetails(ArrayList<String> placeMarkData){
            try{
                FileWriter placeMarkFile = new FileWriter("data/PlaceMarkDetails.csv");
                for(String d: placeMarkData){
                    placeMarkFile.write(d);
                }
                placeMarkFile.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    protected static class CitiesInDistricts{
        private static final ArrayList <String> cityNames = new ArrayList<>();
        private static final ArrayList <String> cityLatitudes = new ArrayList<>();
        private static final ArrayList <String> cityLongitudes = new ArrayList<>();
        static void readFromCities(String districtName){
            String pathName = "data/cities/" + districtName + ".csv";
            cityNames.clear();
            cityLatitudes.clear();
            cityLongitudes.clear();

            try {
                File cityFile = new File(pathName);
                Scanner scanner = new Scanner(cityFile);
                while(scanner.hasNextLine()){
                    String[] data = scanner.nextLine().split(",");
                    cityNames.add(data[0]);
                    cityLatitudes.add(data[1]);
                    cityLongitudes.add(data[2]);
                }
                scanner.close();

            }catch (IOException e){
                e.printStackTrace();
            }
        }

        static ArrayList<String> getCityNames(){
            return cityNames;
        }

        static ArrayList<String> getCityLatitudes(){
            return cityLatitudes;
        }

        static ArrayList<String> getCityLongitudes(){
            return cityLongitudes;
        }
    }
}
