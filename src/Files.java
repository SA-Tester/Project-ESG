import javax.swing.JOptionPane;

import java.util.Scanner;
import java.util.ArrayList;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.time.LocalDate;

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
                        Login.currentLogin = inputUsername;
                    }
                    else if(inputUsername.equals(data[0]) && inputPassword.equals(data[1]) && data[2].equals("user")){
                        msg = "USER PRIVILEGES";
                        Login.currentLogin = inputUsername;
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
        private static final ArrayList <String> storedUsernames = new ArrayList<>();
        private static String name;
        private static String telephoneNumber;
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
                        name = data[0];
                        telephoneNumber = data[2];
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
        static String getName(){return name;}
        static String getTelephoneNumber(){return telephoneNumber;}
        static String getUserProvince(){
            return userProvince;
        }
        static String getUserDistrict(){
            return userDistrict;
        }
        static String getUserCity(){
            return userCity;
        }

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
        static ArrayList <String> placeMarkNameList = new ArrayList<>();
        static ArrayList <String> placeMarkLatList = new ArrayList<>();
        static ArrayList <String> placeMarkLonList = new ArrayList<>();
        static private double selectedLat;
        static private double selectedLon;

        static void readFromPlaceMarkDetails(){
            try {
                File placeMarks = new File("data/PlaceMarkDetails.csv");
                Scanner scanner = new Scanner(placeMarks);
                while(scanner.hasNextLine()){
                    String[] data = scanner.nextLine().split(",");
                    placeMarkNameList.add(data[0]);
                    placeMarkLatList.add(data[1]);
                    placeMarkLonList.add(data[2]);
                }
                scanner.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }

        static void readFromPlaceMarkDetails(int index){
            int lineNo = 0;
            try{
                File placeMarks = new File("data/PlaceMarkDetails.csv");
                Scanner scanner = new Scanner(placeMarks);

                while (scanner.hasNextLine()){
                    String[] data = scanner.nextLine().split(",");
                    if(index == lineNo){
                        selectedLat = Double.parseDouble(data[1]);
                        selectedLon = Double.parseDouble(data[2]);
                    }
                    lineNo++;
                }
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

        public double getSelectedLat(){
            return selectedLat;
        }

        public double getSelectedLon(){
            return selectedLon;
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
        static ArrayList <String> requests = new ArrayList<>();
        private static String currentRequestID = null;
        private static String currentUsername = null;
        private static String currentItemName = null;
        private static String currentQuantity = null;
        private static String currentProvince = null;
        private static String currentDistrict = null;
        private static String currentCity = null;
        private static String currentPrice = null;
        private static String currentStatus = null;
        private static int lineNumber = 0;
        static String deletedItemName = null;
        static String deletedQuantity = null;
        private static int haveItRequests = 0;
        private static int wantItRequests = 0;
        protected static void writeToRequestFile(String reqID, String username, String item, String quantity, String province, String district, String city,
                                                 String price, String needOrHave){
            try{
                FileWriter requestsFile = new FileWriter("data/Requests.csv", true);
                String data = String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s\n", reqID, username, item, quantity,province,district,city,price,needOrHave);
                requestsFile.append(data);
                requestsFile.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }

        protected static void readFromRequestsFile(int selectedIndex){
            int lineNumber = 0;
            try{
                File requestsFile = new File("data/Requests.csv");
                Scanner scanner = new Scanner(requestsFile);
                while(scanner.hasNextLine()){
                    String[] data = scanner.nextLine().split(",");
                    if(selectedIndex == lineNumber){
                        currentRequestID = data[0];
                        currentUsername = data[1];
                        currentItemName = data[2];
                        currentQuantity = data[3];
                        currentProvince = data[4];
                        currentDistrict = data[5];
                        currentCity = data[6];
                        currentPrice = data[7];
                        currentStatus = data[8];
                    }
                    lineNumber++;
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }

        protected static void readFromRequestsFile(String reqID){
            int lineNo = 0;
            try{
                File requestsFile = new File("data/Requests.csv");
                Scanner scanner = new Scanner(requestsFile);
                while (scanner.hasNextLine()){
                    String[] data = scanner.nextLine().split(",");
                    if (data[0].equals(reqID)){
                        lineNumber = lineNo;
                    }
                    lineNo ++;
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }

        protected static void getRequestCount(){
            try{
                File requestsFile = new File("data/Requests.csv");
                Scanner scanner = new Scanner(requestsFile);
                while(scanner.hasNextLine()){
                    String[] data = scanner.nextLine().split(",");
                    if(data[8].equals("HAVE")){
                        haveItRequests++;
                    }
                    else{
                        wantItRequests++;
                    }
                }
                scanner.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }

        protected static void deleteFromRequests(String reqID){
            try{
                File requestsFileR = new File("data/Requests.csv");
                File newRequestsFileR = new File("data/newRequests.csv");

                FileWriter completedFile = new FileWriter("data/Completed.csv",true);
                FileWriter newRequestsFileW = new FileWriter("data/newRequests.csv",true);

                Scanner scanner = new Scanner(requestsFileR);
                LocalDate date = LocalDate.now();
                while(scanner.hasNextLine()){
                    String[] data = scanner.nextLine().split(",");
                    if(reqID.equals(data[0])){
                        deletedItemName = data[2];
                        deletedQuantity = data[3];
                        String deletedLine = String.format("%s,%s,%s\n", date, data[2], data[3]);
                        completedFile.append(deletedLine);
                    }
                    else {
                        String dataLine = String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s\n", data[0], data[1], data[2], data[3], data[4], data[5], data[6], data[7], data[8]);
                        newRequestsFileW.append(dataLine);
                    }
                }
                scanner.close();
                newRequestsFileW.close();
                completedFile.close();
                newRequestsFileR.renameTo(requestsFileR);
                newRequestsFileR.delete();

            }catch (IOException e){
                e.printStackTrace();
            }
        }

        protected static int getNoOfHaveItRequests(){
            return haveItRequests;
        }
        protected static int getNoOfWantItRequests(){
            return wantItRequests;
        }
        public static String getCurrentRequestID() {return currentRequestID;}
        public static String getCurrentUsername(){return currentUsername;}
        public static String getCurrentItemName() {return currentItemName;}
        public static String getCurrentQuantity(){return  currentQuantity;}
        public static String getCurrentProvince(){return currentProvince;}
        public static String getCurrentDistrict(){return currentDistrict;}
        public static String getCurrentCity(){return currentCity;}
        public static String getCurrentPrice(){return currentPrice;}
        public static String getCurrentStatus(){return currentStatus;}
        public static int getLineNumber(){return lineNumber;}
    }

    protected static class Completed{
        static int nLines = 0;
        static ArrayList <String> dates = new ArrayList<>();
        static ArrayList <String> items = new ArrayList<>();
        static ArrayList <String> quantities = new ArrayList<>();
        protected static void readFromCompletedFile(){
            try{
                File completedFile = new File("data/Completed.csv");
                Scanner scanner = new Scanner(completedFile);
                while(scanner.hasNextLine()){
                    nLines++;
                    String[] data = scanner.nextLine().split(",");
                    dates.add(data[0]);
                    items.add(data[1]);
                    quantities.add(data[2]);
                }
                scanner.close();
            }catch(IOException e){e.printStackTrace();}
        }
    }

    protected static class UserHistory{
        static ArrayList <String[]> userHistoryList = new ArrayList<>();//username,date, msg,itemName, qty
        protected static void writeToUserHistory(String username, String date,String msg, String itemName, String qty, boolean writeToFile){
            if(writeToFile){
                try{
                    FileWriter userHistoryFile = new FileWriter("data/User History.csv",true);
                    String dataLine = String.format("%s,%s,%s,%s,%s\n",username,date,msg,itemName,qty);
                    userHistoryFile.append(dataLine);
                    userHistoryFile.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }

        protected static void readFromUserHistory(String username){
            try {
                File userHistoryFile = new File("data/User History.csv");
                Scanner scanner = new Scanner(userHistoryFile);
                while (scanner.hasNextLine()){
                    String[] data = scanner.nextLine().split(",");
                    if(data[0].equals(username)){
                        userHistoryList.add(data);
                    }
                }
                scanner.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    protected static class ResetPasswordRequests{
        private static String newPassword;
        private static String inputUsername;

        //update login info file
        static void updateLogin(){
            String dataLine;

            try{
                FileWriter newLoginFileWrite = new FileWriter("data/LoginInfo2.csv",true);
                File loginFile = new File("data/LoginInfo.csv");
                Scanner scanner = new Scanner(loginFile);

                while (scanner.hasNextLine()){
                    String[] data = scanner.nextLine().split(",");

                    if(data[0].equals(inputUsername)){
                        dataLine = String.format("%s,%s,%s\n", inputUsername, newPassword, data[2]);
                    }
                    else{
                        dataLine = String.format("%s,%s,%s\n", data[0], data[1], data[2]);
                    }
                    newLoginFileWrite.append(dataLine);
                }
                scanner.close();
                newLoginFileWrite.close();

                File newLoginFileRead = new File("data/LoginInfo2.csv");
                newLoginFileRead.renameTo(loginFile);
                newLoginFileRead.delete();

            }catch(IOException e){
                e.printStackTrace();
            }
        }

        public static void setPassword(String username, String pwd){
            inputUsername = username;
            newPassword = pwd;
        }
    }

    protected static class Reserved{
        private static final ArrayList<String[]> transactionList = new ArrayList<>();
        private static String reqID = null;
        private static String postedByUsername = null;
        private static String postedByTelephone = null;
        private static String reservedByUsername = null;
        private static String reservedByTelephone = null;
        private static String postedByProvince = null;
        private static String postedByDistrict = null;
        private static String postedByCity = null;
        static boolean state = true;

        public static void writeToReservedFile(){
            readFromReservedFile();
            for (String[] strings : transactionList) {
                if (strings[0].equals(reqID)) {
                    state = false;
                    break;
                } else {
                    state = true;
                }
            }

            if(!state){
                Home.Right.reserve.setEnabled(false);
                JOptionPane.showMessageDialog(Home.homeFrame,"This item is already reserved", "Error", JOptionPane.ERROR_MESSAGE);
            }
            else{
                try{
                    FileWriter reservedFile = new FileWriter("data/Reserved.csv", true);
                    String dataLine = String.format("%s,%s,%s,%s,%s,%s,%s,%s\n",
                            reqID,postedByUsername,postedByTelephone,reservedByUsername,reservedByTelephone,postedByProvince,postedByDistrict,postedByCity);
                    reservedFile.append(dataLine);
                    reservedFile.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }

        public static void readFromReservedFile(){
            try{
                transactionList.clear();
                File reservedFile = new File("data/Reserved.csv");
                Scanner scanner = new Scanner(reservedFile);

                while (scanner.hasNextLine()){
                    String[] data = scanner.nextLine().split(",");
                    transactionList.add(data);
                }
                scanner.close();
            }catch (IOException e){e.printStackTrace();}
        }

        public static void deleteFromReservedFile(String reqID){
            File reservedFileR = new File("data/Reserved.csv");

            try{
                FileWriter reservedFileTempW = new FileWriter("data/ReservedTemp.csv",true);

                Scanner scanner = new Scanner(reservedFileR);
                while (scanner.hasNextLine()){
                    String[] data = scanner.nextLine().split(",");
                    String dataLine = String.format("%s,%s,%s,%s,%s,%s,%s,%s\n", data[0],data[1],data[2],data[3],data[4],data[5],data[6],data[7]);

                    if(!data[0].equals(reqID)){
                        reservedFileTempW.append(dataLine);
                    }
                }
                scanner.close();
                reservedFileTempW.close();

                File reservedFileTemp = new File("data/ReservedTemp.csv");
                reservedFileTemp.renameTo(reservedFileR);
                reservedFileTemp.delete();

            }catch (IOException e){
                e.printStackTrace();
            }
        }

        protected static ArrayList<String[]> getUserReservedTransactions(String reservedByUsername){
            //Item Name, Posted By Name, Posted By Telephone
            ArrayList<String[]> userReservedTransactions = new ArrayList<>();

            try{
                File transactionFile = new File("data/Reserved.csv");
                File requestsFile = new File("data/Requests.csv");
                Scanner transactionScanner = new Scanner(transactionFile);
                Scanner requestScanner = new Scanner(requestsFile);

                while (transactionScanner.hasNextLine()){
                    String[] transactionInfo = transactionScanner.nextLine().split(",");

                    if(transactionInfo[3].equals(reservedByUsername)){
                        String reqID = transactionInfo[0];

                        while(requestScanner.hasNextLine()){
                            String[] requestInfo = requestScanner.nextLine().split(",");
                            if(requestInfo[0].equals(reqID)){
                                String[] postedData = {requestInfo[2], transactionInfo[1], transactionInfo[2]};
                                userReservedTransactions.add(postedData);
                                break;
                            }
                        }
                    }
                }
                transactionScanner.close();
                requestScanner.close();

            }catch (IOException e){
                e.printStackTrace();
            }
            return userReservedTransactions;
        }

        public static void setReqID(String reqID){
            Reserved.reqID = reqID;
        }

        public static void setPostedByUsername(String username){
            Reserved.postedByUsername = username;
        }

        public static void setPostedByTelephone(String telephone){
            Reserved.postedByTelephone = telephone;
        }

        public static void setReservedByUsername(String username){
            Reserved.reservedByUsername = username;
        }

        public static void setReservedByTelephone(String telephone){
            Reserved.reservedByTelephone = telephone;
        }

        public static void setPostedByProvince(String province){
            Reserved.postedByProvince = province;
        }

        public static void setPostedByDistrict(String district){
            Reserved.postedByDistrict = district;
        }

        public static void setPostedByCity(String city){
            Reserved.postedByCity = city;
        }

        public static ArrayList<String[]> getTransactionList(){
            return transactionList;
        }
    }
}
