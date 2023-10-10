package BillModule;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ResourceBundle;

import Customer.MySqlConnector;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class BillingGenViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    Connection con;
    PreparedStatement pst;

    @FXML
    private DatePicker BillUpto;

    @FXML
    private DatePicker LastDate;

    @FXML
    private ComboBox<String> comboMobile;

    @FXML
    private Label lblResp;

    @FXML
    private TextField txtMissDay;

    @FXML
    private TextField txtPaper;

    @FXML
    private TextField txtPrice;

    @FXML
    private TextField txtTotalPrice;

    @FXML
    void DoGenBill(ActionEvent event) {
        String selectedMobile = comboMobile.getValue();
        LocalDate lastDate = LastDate.getValue();
        LocalDate billUpto = BillUpto.getValue();
        int missingDays = Integer.parseInt(txtMissDay.getText());
        float totalPrice = Float.parseFloat(txtTotalPrice.getText());

        if (selectedMobile != null && lastDate != null && billUpto != null) {
            long daysBetween = billUpto.toEpochDay() - lastDate.toEpochDay() + 1; // Add 1 to include both the start and end dates

            float netBill = totalPrice * daysBetween - missingDays;
            lblResp.setText(String.valueOf(netBill));

            try {
                pst = con.prepareStatement("INSERT INTO bills (mobile, datefrom, dateto, bill, billstatus) VALUES (?, ?, ?, ?, 0)");
                pst.setString(1, selectedMobile);
                pst.setDate(2, Date.valueOf(lastDate));
                pst.setDate(3, Date.valueOf(billUpto));
                pst.setFloat(4, netBill);

                int rowsAffected = pst.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Data added successfully.");
                } else {
                    System.out.println("Failed to add data ");
                }
            } catch (Exception exp) {
                System.out.println(exp);
            }
        } else {
            lblResp.setText("Please select the last date and bill upto date.");
        }
    }


    @FXML
    void doFetchData(ActionEvent event) {
        String selectedMobile = comboMobile.getValue();
        if (selectedMobile != null) {
            try {
                pst = con.prepareStatement("SELECT spapers, sprices, DOS FROM customers WHERE mobile = ? ORDER BY DOS DESC");
                pst.setString(1, selectedMobile);
                ResultSet table = pst.executeQuery();

                float totalPrice = 0;
                int recordCount = 0;

                while (table.next()) {
                    String sppr = table.getString("spapers");
                    String price = table.getString("sprices");
                    LocalDate doss = table.getDate("DOS").toLocalDate();

                    String[] prices = price.split(",\\s*"); 
                    for (String prc : prices) {
                        totalPrice += Float.parseFloat(prc);
                    }

                    System.out.println(sppr + "\t" + price + "\t" + doss + "\t");

                    recordCount++;
                    if (recordCount == 1) {
                        txtPaper.setText(sppr);
                        txtPrice.setText(price);
                        LastDate.setValue(doss);
                    }
                }

                if (totalPrice > 0) {
                    txtTotalPrice.setText(String.valueOf(totalPrice));
                } else {
                    txtTotalPrice.clear();
                }

                if (recordCount == 0) {
                    txtPaper.clear();
                    txtPrice.clear();
                    LastDate.setValue(null);
                }
            } catch (Exception exp) {
                System.out.println(exp);
            }
        } else {
            txtPaper.clear();
            txtPrice.clear();
            LastDate.setValue(null);
            txtTotalPrice.clear();
        }
    }


void doFillRolls() {
        
        try {
            pst = con.prepareStatement("SELECT DISTINCT mobile FROM customers");
            ResultSet table = pst.executeQuery();

            while (table.next()) {
                String contact = table.getString("mobile");
                System.out.println(contact);
                comboMobile.getItems().add(contact);
            }

        } catch (Exception exp) {
            System.out.println(exp);
        }
    }

@FXML
void doClear(ActionEvent event) {
	
	  comboMobile.getSelectionModel().clearSelection();
     // txt.setValue(null);
      txtPaper.clear();
      txtPrice.clear();
      txtTotalPrice.clear();
      LastDate.setValue(null);
      BillUpto.setValue(null);
      lblResp.setText(""); 

}

    @FXML
    void initialize() {
    	con=MySqlConnector.doConnect();
    	if(con==null)
    			System.out.println("Connection Problem");
    	else
    		System.out.println("Connected");
    	
    	doFillRolls();
       

    }

}
