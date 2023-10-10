package Hawker;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class HawkerManagerViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    Connection con;
    PreparedStatement pst;
    

    @FXML
    private ComboBox<String> comboAreas;

    @FXML
    private ComboBox<String> comboName;

    @FXML
    private Label picpath;
    
    @FXML
    private ImageView picPrev;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtMobile;
    
    @FXML
    private Label lblResp;

    @FXML
    private TextField txtalloareas;
    @FXML
    void doEnroll(ActionEvent event) {
        String name = comboName.getValue().toString();
        String mobile = txtMobile.getText();
        String address = txtAddress.getText();
        String Area= comboAreas.getValue().toString();
        String alloareas = txtalloareas.getText();
       

        try {
            String query = "INSERT INTO hawkers (hname, mobile, address,Area, alloareas, picpath) VALUES (?, ?, ?, ?, ?,?)";
            pst = con.prepareStatement(query);
            pst.setString(1, name);
            pst.setString(2, mobile);
            pst.setString(3, address);
            pst.setString(4, Area);
            pst.setString(5, alloareas);
            pst.setString(6, picpath.getText());

            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                lblResp.setText("Enrollment successful");

            } else {
                lblResp.setText("Enrollment failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
            lblResp.setText("Enrollment failed");
        }
    }


    @FXML
    void doFire(ActionEvent event) 
    {
    	try {
        	String selname = comboName.getValue();
        	pst=con.prepareStatement("delete from hawkers where hname=?");
        	pst.setString(1, selname);
        	int count=pst.executeUpdate();
        	if(count!=0)
        		lblResp.setText(count+ " Records Deleted");
        	
        	else
        		lblResp.setText("Invalid ID");
        	}
        	catch(Exception exp)
        	{
        		System.out.println(exp.toString());
        	}
           }

    @FXML
    void doNew(ActionEvent event) {
        comboName.getSelectionModel().clearSelection();
        comboName.setValue(null);
        txtMobile.clear();
        txtAddress.clear();
        comboAreas.setValue(null);
        txtalloareas.clear();
        picpath.setText("");
        picPrev.setImage(null); 
        lblResp.setText(""); 
    }

    @FXML
    void doSearch(ActionEvent event) {
        try {
            pst = con.prepareStatement("SELECT * FROM hawkers WHERE hname = ?");
            String name = comboName.getSelectionModel().getSelectedItem();
            pst.setString(1, name);

            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()) {
                String sname = resultSet.getString("hname");
                String mobile = resultSet.getString("mobile");
                String address = resultSet.getString("address");
                String Area = resultSet.getString("Area");
                
                String alloareas = resultSet.getString("alloareas");
                String picPath = resultSet.getString("picpath");

                txtMobile.setText(mobile);
                txtAddress.setText(address);
                comboAreas.setValue(Area);
                txtalloareas.setText(alloareas);
                picpath.setText(picPath);
                picPrev.setImage(new Image(new FileInputStream(picPath)));

                System.out.println("Name: " + sname);
                System.out.println("Mobile: " + mobile);
                System.out.println("Address: " + address);
                System.out.println("Area: " + Area);
                
                System.out.println("Alloareas: " + alloareas);
                System.out.println("Pic Path: " + picPath);
            } else {
                System.out.println("No records found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Search failed");
        }
    }


    @FXML
    void doUpdate(ActionEvent event) {
        String name = comboName.getValue().toString();
        String mobile = txtMobile.getText();
        String address = txtAddress.getText();
        String area = comboAreas.getValue().toString();
        String alloareas = txtalloareas.getText();

        try {
            String query = "UPDATE hawkers SET mobile = ?, address = ?, Area = ?, alloareas = ? WHERE hname = ?";
            pst = con.prepareStatement(query);
            pst.setString(1, mobile);
            pst.setString(2, address);
            pst.setString(3, area);
            pst.setString(4, alloareas);
            pst.setString(5, name);

            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                lblResp.setText("Update successful");
            } else {
                lblResp.setText("Update failed");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            lblResp.setText("Update failed");
        }
    }


    @FXML
    void doUploadPic(ActionEvent event) throws FileNotFoundException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
            new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
        );
        File selectedFile = fileChooser.showOpenDialog(null);
        
        if (selectedFile != null) {
            picpath.setText(selectedFile.getPath());
            Image img = new Image(selectedFile.toURI().toString());
            picPrev.setImage(img);
        } else {
            picpath.setText("nopic.jpg");
        }
    }
    
    
    void doFillRolls() {
        try {
            pst = con.prepareStatement("SELECT DISTINCT hname FROM hawkers");
            ResultSet table = pst.executeQuery();

            while (table.next()) {
                String paper = table.getString("hname");
              //  System.out.println(paper);
                comboName.getItems().add(paper);
            }

            pst = con.prepareStatement("SELECT DISTINCT Area FROM hawkers");
            ResultSet table1 = pst.executeQuery();

            while (table1.next()) {
                String paper1 = table1.getString("Area");
                //System.out.println(paper1);
                comboAreas.getItems().add(paper1);
            }
        } catch (Exception exp) {
            System.out.println(exp);
        }
    }

    	
    	  @FXML
    	    void initialize() {
    	    	
    	    	con=MySqlConnector.doConnect();
    	    	if(con==null)
    	    			System.out.println("Connection Problem");
    	    	else
    	    		System.out.println("Connected");
    	    	
    	    	doFillRolls();

    	    

    	
    
    
        assert comboAreas != null : "fx:id=\"comboAreas\" was not injected: check your FXML file 'HawkerManagerView.fxml'.";
        assert comboName != null : "fx:id=\"comboName\" was not injected: check your FXML file 'HawkerManagerView.fxml'.";
        assert picpath != null : "fx:id=\"picpath\" was not injected: check your FXML file 'HawkerManagerView.fxml'.";
        assert txtAddress != null : "fx:id=\"txtAddress\" was not injected: check your FXML file 'HawkerManagerView.fxml'.";
        assert txtMobile != null : "fx:id=\"txtMobile\" was not injected: check your FXML file 'HawkerManagerView.fxml'.";
        assert txtalloareas != null : "fx:id=\"txtalloareas\" was not injected: check your FXML file 'HawkerManagerView.fxml'.";

    }

}


  
