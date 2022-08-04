import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JComboBox;

import java.util.ArrayList;

abstract class UserInterfaces {
    public final String[] provinces = {"Northern", "North-Central", "North-Western", "Eastern", "Central", "Western", "Uva","Southern","Sabaragamuwa"};
    private final String[] districtsNorthern = {"Jaffna","Killinochchi","Mullaitivu","Vavuniya","Mannar"};
    private final String[] districtsNorthCentral = {"Anuradhapura", "Polonnaruwa"};
    private final String[] districtsNorthWestern = {"Kurunegala","Puttalam"};
    private final String[] districtsEastern = {"Trincomalee","Ampara","Batticaloa"};
    private final String[] districtsCentral = {"Kandy", "Matale", "Nuwara-Eliya"};
    private final String[] districtsWestern = {"Colombo","Gampaha","Kalutara"};
    private final String[] districtsUva = {"Badulla","Monaragala"};
    private final String[] districtsSouthern = {"Galle","Matara","Hambantota"};
    private final String[] districtsSabaragamuwa = {"Kegalle","Ratnapura"};

    abstract JLabel createTitle();
    abstract JPanel createPanel();
    abstract void createJFrame();
    abstract JLabel createJLabel(String labelName, int y);
    abstract JTextField createJTextField(JTextField textField, int y);
    abstract JPasswordField createJPasswordField(JPasswordField passField, int y);
    abstract JButton createJButton(JButton btn, String btnName, int xDistance, int yDistance);
    abstract JComboBox<String> createJComboBox(String[] list, int yHeight);

    public String[] getDistricts(int selectedProvince){
        return switch (selectedProvince) {
            case 0 -> districtsNorthern;
            case 1 -> districtsNorthCentral;
            case 2 -> districtsNorthWestern;
            case 3 -> districtsEastern;
            case 4 -> districtsCentral;
            case 5 -> districtsWestern;
            case 6 -> districtsUva;
            case 7 -> districtsSouthern;
            case 8 -> districtsSabaragamuwa;
            default -> throw new IllegalStateException("Unexpected value: " + selectedProvince);
        };
    }

    public String[] getCities(String cityName){
        String capitalizedCityName = cityName.substring(0,1).toUpperCase() + cityName.substring(1);
        return new CitiesInDistricts().getCityName(capitalizedCityName);
    }

    private static class CitiesInDistricts extends Files.CitiesInDistricts{
        String[] getCityName(String cityName){
            Files.CitiesInDistricts.readFromCities(cityName);
            ArrayList <String> cityNamesList = Files.CitiesInDistricts.getCityNames();

            String[] cityNameArray = new String[cityNamesList.size()];
            for(int i=0; i<cityNamesList.size(); i++){
                cityNameArray[i] = cityNamesList.get(i);
            }
            return cityNameArray;
        }
    }
}
