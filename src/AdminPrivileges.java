import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;

import java.time.LocalDate;

public class AdminPrivileges extends UserInterfaces{
    JFrame adminFrame = new JFrame();
    private final JButton markAsCompletedBtn = new JButton();
    private JTable transferTable;
    private DefaultTableModel model;
    private final int width = 1500;
    private final int height = 800;
    private final int[] dim = new Home().getScreenDimensions();

    private static String[][] data = null;

    @Override
    JPanel createPanel() {
        JPanel adminPanel = new JPanel();
        adminPanel.setLayout(null);
        adminPanel.setSize(width,height);
        adminPanel.setBackground(Color.BLACK);

        adminPanel.add(createTitle());
        adminPanel.add(createJTable());
        adminPanel.add(createJButton(markAsCompletedBtn,"Mark As Completed",width/2-125,500));

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
        String[] columns = {"Request ID", "Posted By", "Telephone", "Reserved By", "Telephone", "Transferred Province", "Transferred District", "Transferred City"};

        Files.Reserved.readFromReservedFile();
        ArrayList<String[]> dataList = Files.Reserved.getTransactionList();
        data = new String[dataList.size()][8];
        for(int i=0; i<dataList.size(); i++){
            for(int j=0; j<8; j++){
                data[i][j] = dataList.get(i)[j];
            }
        }

        model = new DefaultTableModel(data, columns);
        transferTable = new JTable(model);
        transferTable.setBounds(50,100,width-100,height-300);
        transferTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
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
        btn.setText("Mark As Completed");
        btn.setBounds(xDistance,150+yDistance,250,50);
        btn.setForeground(Color.WHITE);
        btn.setBackground(Color.DARK_GRAY);
        btn.setFont(new Font("Arial",Font.BOLD,18));

        btn.addActionListener(e->{
            LocalDate date = LocalDate.now();

            int row = transferTable.getSelectedRow();
            Files.Reserved.deleteFromReservedFile(data[row][0]);

            Files.Requests.readFromRequestsFile(data[row][0]);
            int lineIndex = Files.Requests.getLineNumber();
            Files.Requests.deleteFromRequests(data[row][0]);

            if(Home.Left.placeMarkNameList.size() > 0){
                Home.Left.addToCompletedActions(date.toString(), Files.Requests.deletedItemName, Files.Requests.deletedQuantity);

                Files.PlaceMarkDetails.readFromPlaceMarkDetails(lineIndex);
                Home.mp.removePlaceMark(new Files.PlaceMarkDetails().getSelectedLat(), new Files.PlaceMarkDetails().getSelectedLon());
                Home.Left.placeMarkNameList.remove(lineIndex);
                Home.Left.placeMarkLatList.remove(lineIndex);
                Home.Left.placeMarkLonList.remove(lineIndex);
                Home.Left.locationList.removeItem(Home.Left.locationList.getItemAt(lineIndex));
            }

            Files.UserHistory.writeToUserHistory(data[row][3],date.toString(),"CLAIMED",Files.Requests.getCurrentItemName(),Files.Requests.getCurrentQuantity(),true);
            Files.UserHistory.writeToUserHistory(data[row][1],date.toString(),"GAVE",Files.Requests.getCurrentItemName(),Files.Requests.getCurrentQuantity(),true);

            model.removeRow(row);
        });
        return btn;
    }

    @Override
    JLabel createJLabel(String labelName, int y) {return null;}
    @Override
    JTextField createJTextField(JTextField textField, int y) {return null;}
    @Override
    JPasswordField createJPasswordField(JPasswordField passField, int y) {return null;}
    @Override
    JComboBox<String> createJComboBox(String[] list, int yHeight) {return null;}
}
