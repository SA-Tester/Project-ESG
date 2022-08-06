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

        protected static void updateCurrentLogin(String username){
            try{
                FileWriter currentLoginFile = new FileWriter("data/Current_Login.csv",true);
                String formattedString = String.format("%s\n",username);
                currentLoginFile.append(formattedString);
                currentLoginFile.close();

            }catch (IOException e){
                e.printStackTrace();
            }
        }

        protected static String getCurrentLogin(){
            String data = null;
            try{
                File currentLoginFile = new File("data/Current_Login.csv");
                Scanner scanner = new Scanner(currentLoginFile);
                while(scanner.hasNextLine()){
                    data = scanner.nextLine();
                }
                scanner.close();
            }catch (IOException e){
                e.printStackTrace();
            }
            return data;
        }
    }

    protected static class SignUpDetails{
        private static ArrayList <String> storedUsernames = new ArrayList<>();
        private static String userProvince;
        private static String userDistrict;
        private static String userCity;
        static void readSignUpDetails(String username){
            try{
                File signUpFile = new File("data/SignUpInfo.csv");
                Scanner scanner = new Scanner(signUpFile);
                while(scanner.hasNextLine()){
                    String[] data = scanner.nextLine().split(",");
                    storedUsernames.add(data[3]);
                    if(data[3].equals(username)){
                        userProvince = data[4];
                        userDistrict = data[5];
                        userCity = data[6];
                    }
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }

        static ArrayList<String> checkValidityOfUsernames(){
            try{
                File signUpFile = new File("data/SignUpInfo.csv");
                Scanner scanner = new Scanner(signUpFile);
                while(scanner.hasNextLine()){
                    String[] data = scanner.nextLine().split(",");
                    storedUsernames.add(data[3]);
                }
            }catch (IOException e){
                e.printStackTrace();
            }
            return storedUsernames;
        }
        static String getUserProvince(){
            return userProvince;
        }
        static String getUserDistrict(){
            return userDistrict;
        }
        static String getUserCity(){
            return userCity;
        }

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
        static ArrayList <String> users = new ArrayList<>();
        static ArrayList <String> placeMarkNameList = new ArrayList<>();
        static ArrayList <String> placeMarkLatList = new ArrayList<>();
        static ArrayList <String> placeMarkLonList = new ArrayList<>();
        static void readFromPlaceMarkDetails(){
            try {
                File placeMarks = new File("data/PlaceMarkDetails.csv");
                Scanner scanner = new Scanner(placeMarks);
                while(scanner.hasNextLine()){
                    String[] data = scanner.nextLine().split(",");
                    users.add(data[0]);
                    placeMarkNameList.add(data[1]);
                    placeMarkLatList.add(data[2]);
                    placeMarkLonList.add(data[3]);
                }
                scanner.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }

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

    protected static class Requests{
        protected static void writeToRequestFile(String username, String item, String quantity, String province, String district, String city, String price, String needOrHave){
            try{
                FileWriter requestsFile = new FileWriter("data/Requests.csv", true);
                String data = String.format("%s,%s,%s,%s,%s,%s,%s,%s\n", username, item, quantity,province,district,city,price,needOrHave);
                requestsFile.append(data);
                requestsFile.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }

        //delete from requests file
    }
}
