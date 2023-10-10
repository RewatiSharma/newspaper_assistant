package billCollector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import Customer.MySqlConnector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class bcViewController {

    @FXML
    private Label lblResp;

    @FXML
    private TextField txtAmount;

    @FXML
    private DatePicker EDate;

    @FXML
    private DatePicker SDate;

    @FXML
    private TextField txtMobile;

    Connection con;
    PreparedStatement pst;

    @FXML
    void doDetails(ActionEvent event) {
        String searchNumber = txtMobile.getText();

        if (searchNumber != null && !searchNumber.isEmpty()) {
            try {
                pst = con.prepareStatement("SELECT bill, datefrom, dateto FROM bills WHERE mobile = ?ORDER BY datefrom DESC");
                pst.setString(1, searchNumber);
                ResultSet resultSet = pst.executeQuery();

                if (resultSet.next()) {
                    float bill = resultSet.getFloat("bill");
                    LocalDate datefrom = resultSet.getDate("datefrom").toLocalDate();
                    LocalDate dateTo = resultSet.getDate("dateto").toLocalDate();

                    txtAmount.setText(String.valueOf(bill));
                    SDate.setValue(datefrom);
                    EDate.setValue(dateTo);
                } else {
                    System.out.println("Bill not found.");
                    txtAmount.clear();
                    SDate.setValue(null);
                    EDate.setValue(null);
                }

                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Search number is empty.");
            lblResp.setText("Please enter a search number.");
        }
    }

    @FXML
    void doDone(ActionEvent event) {
        String searchNumber = txtMobile.getText();
        String amount = txtAmount.getText();
        LocalDate startDate = SDate.getValue();
        LocalDate endDate = EDate.getValue();

        if (!searchNumber.isEmpty() && !amount.isEmpty() &&  startDate != null && endDate != null) {
            lblResp.setText("Payment added successfully.");
        } else {
            lblResp.setText("Please fill in all the details.");
        }
    }

    @FXML
    void initialize() {
        con = MySqlConnector.doConnect();
        if (con == null)
            System.out.println("Connection Problem");
        else
            System.out.println("Connected");
    }

}
