package myProject;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class CustomerManagerViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> ComboPaper;
    @FXML
    private TextField txtPrice;
    
    @FXML
    private Label lblPath;

    @FXML
    private Label lblResp;
    
    Connection con;
    PreparedStatement pst;

    @FXML
    void doSearch(ActionEvent event) {
        String searchPaper = ComboPaper.getValue();

        if (searchPaper != null) {
            try {
                pst = con.prepareStatement("SELECT price FROM papers WHERE papername = ?");
                pst.setString(1, searchPaper);
                ResultSet resultSet = pst.executeQuery();

                if (resultSet.next()) {
                    float price = resultSet.getFloat("price");
                    txtPrice.setText(String.valueOf(price));
                  
                } else {
                    System.out.println("Paper not found.");
                   
                    txtPrice.clear();
                }

                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No paper selected.");
            lblResp.setText("No paper selected."); 
        }
    }




    @FXML
    void doWithdraw(ActionEvent event) 
    {
    	try {
    	String selectedPaper = ComboPaper.getValue();
    	pst=con.prepareStatement("delete from papers where papername=?");
    	pst.setString(1, selectedPaper);
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
    void doavail(ActionEvent event) {
        String selectedPaper = ComboPaper.getValue();

        if (selectedPaper != null) {
            try {
                pst = con.prepareStatement("SELECT price FROM papers WHERE papername = ?");
                pst.setString(1, selectedPaper);
                ResultSet resultSet = pst.executeQuery();

                txtPrice.clear();

                while (resultSet.next()) {
                    float price = resultSet.getFloat("price");
                    txtPrice.setText(String.valueOf(price));
                    
                }
                lblResp.setText("Record Saved........");
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No paper selected.");
        }
    }




    @FXML
    void doedit(ActionEvent event) 
    {
    	 String selectedNewspaper = ComboPaper.getValue();
    	String updatedNewspaperName = selectedNewspaper;
        float updatedPrice = Float.parseFloat(txtPrice.getText());
        try {
        	pst = con.prepareStatement("UPDATE papers SET price = ? WHERE papername = ?");
            pst.setFloat(1, updatedPrice);
            pst.setString(2, updatedNewspaperName);
            int count = pst.executeUpdate();
            lblResp.setText(count+" Records Updated........");
			
		} 
	catch (SQLException e) 
		{
		  e.printStackTrace();
		}
        
    }
    
    
    void doFillRolls() {
        
        try {
            pst = con.prepareStatement("SELECT DISTINCT papername FROM papers");
            ResultSet table = pst.executeQuery();

            while (table.next()) {
                String paper = table.getString("papername");
                System.out.println(paper);
                ComboPaper.getItems().add(paper);
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
    	
    	
        assert ComboPaper != null : "fx:id=\"ComboPaper\" was not injected: check your FXML file 'CustomerManagerView.fxml'.";
        assert txtPrice != null : "fx:id=\"txtPrice\" was not injected: check your FXML file 'CustomerManagerView.fxml'.";

    }

}
