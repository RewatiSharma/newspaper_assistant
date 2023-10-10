package BillBoard;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import CustomerDash.customerBean;
import billCollector.MySqlConnector;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;

public class BBViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    Connection con;
    PreparedStatement pst;

    @FXML
    private RadioButton paidRad;

    @FXML
    private RadioButton penRad;
    @FXML
    private ToggleGroup rad;

    @FXML
    private TableView<BBBean> tabledata;

    @FXML
    private TextField txtMob;

    @FXML
    private TextField txtamt;

    @FXML
    void doSearch(ActionEvent event) {
        String status = "";
        if (paidRad.isSelected()) {
            status = "1";
        } else if (penRad.isSelected()) {
            status = "0";
        } else {

            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("Please select a status (Paid or Pending).");
            alert.showAndWait();
            return;
        }

        try {
            String sql = "SELECT * FROM bills WHERE billstatus = ?";
            pst = con.prepareStatement(sql);
            pst.setString(1, status);

            ResultSet rs = pst.executeQuery();

            ObservableList<BBBean> data = FXCollections.observableArrayList();
            TableColumn<BBBean, String> mobileCol = new TableColumn<>("Mobile");
            mobileCol.setCellValueFactory(new PropertyValueFactory<>("mobile"));

            TableColumn<BBBean, String> billCol = new TableColumn<>("Bill");
            billCol.setCellValueFactory(new PropertyValueFactory<>("bill"));

            TableColumn<BBBean, String> statusCol = new TableColumn<>("Status");
            statusCol.setCellValueFactory(new PropertyValueFactory<>("billStatus"));

            
            tabledata.getColumns().clear();

          
            tabledata.getColumns().addAll(mobileCol, billCol, statusCol);

            while (rs.next()) {
                String mobile = rs.getString("mobile");
                String bill = rs.getString("bill");
                String billStatus = rs.getString("billstatus");

                data.add(new BBBean(mobile, bill, billStatus, billStatus, billStatus));
            }

            tabledata.setItems(data);
            rs.close();
            pst.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void doBillHis(ActionEvent event) {

        String mobile = txtMob.getText().trim();

        if (mobile.isEmpty()) {

            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a mobile number.");
            alert.showAndWait();
            return;
        }

        try {
            String sql = "SELECT * FROM bills WHERE mobile = ?";
            pst = con.prepareStatement(sql);
            pst.setString(1, mobile);

            ResultSet rs = pst.executeQuery();

            ObservableList<BBBean> data = FXCollections.observableArrayList();
            TableColumn<BBBean, String> mobileCol = new TableColumn<>("Mobile");
            mobileCol.setCellValueFactory(new PropertyValueFactory<>("mobile"));

            TableColumn<BBBean, String> billCol = new TableColumn<>("Bill");
            billCol.setCellValueFactory(new PropertyValueFactory<>("bill"));

            TableColumn<BBBean, String> statusCol = new TableColumn<>("Status");
            statusCol.setCellValueFactory(new PropertyValueFactory<>("billStatus"));

       
            tabledata.getColumns().clear();

          
            tabledata.getColumns().addAll(mobileCol, billCol, statusCol);

            double totalAmount = 0.0;

            while (rs.next()) {
                String bill = rs.getString("bill");
                String billStatus = rs.getString("billstatus");
                double billAmount = Double.parseDouble(bill);

                data.add(new BBBean(mobile, bill, billStatus, billStatus, billStatus));

                totalAmount += billAmount;
            }

            tabledata.setItems(data);

            rs.close();
            pst.close();

            txtamt.setText(String.valueOf(totalAmount));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void doExport(ActionEvent event) throws Exception {

        writeExcel();
    }

    void writeExcel() throws Exception {
        ObservableList<BBBean> list = FXCollections.observableArrayList();
        Writer writer = null;
        try {
            File file = new File("hawkers.csv");
            writer = new BufferedWriter(new FileWriter(file));
            String text = "mobile,datefrom,dateto,bill,billstatus \n";
            writer.write(text);
            for (BBBean p : list) {
                text = p.getMobile() + "," + p.getDatefrom() + "," + p.getDateto() + "," + p.getBill() + ","
                        + p.getBillStatus() + "\n";
                writer.write(text);
            }
            System.out.println("Exported Successfully");

        } catch (Exception ex) {
            ex.printStackTrace();

        } finally {

            writer.flush();
            writer.close();
        }
    }

    ObservableList<customerBean> list;

    @FXML
    void initialize() {
        con = MySqlConnector.doConnect();
    }

}
