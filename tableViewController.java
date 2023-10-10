package table;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import billCollector.*;

public class tableViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<HawkerBean> tableData;

    @FXML
    void doFetch(ActionEvent event) {
    	TableColumn<HawkerBean, String> name=new TableColumn<HawkerBean, String>("Hawker Name");//any thing
    	name.setCellValueFactory(new PropertyValueFactory<>("hname"));
    	name.setMinWidth(150);
    	tableData.getColumns().add(name);
    	tableData.setItems(FetchAllHawkers());
    	
    	TableColumn<HawkerBean, String> mobile = new TableColumn<>("Mobile No.");
    	mobile.setCellValueFactory(new PropertyValueFactory<>("mobile"));
    	mobile.setMinWidth(150);
    	tableData.getColumns().add(mobile);
    	tableData.setItems(FetchAllHawkers());

    	TableColumn<HawkerBean, String> alloareas = new TableColumn<>("Allowed Areas");
    	alloareas.setCellValueFactory(new PropertyValueFactory<>("alloareas"));
    	alloareas.setMinWidth(150);
    	tableData.getColumns().add(alloareas);
    	tableData.setItems(FetchAllHawkers());


    	TableColumn<HawkerBean, String> doj = new TableColumn<>("Joining Date");
    	doj.setCellValueFactory(new PropertyValueFactory<>("doj"));
    	doj.setMinWidth(150);
    	tableData.getColumns().add(doj);
    	tableData.setItems(FetchAllHawkers());

    }
    
    PreparedStatement pst;  
    
    ObservableList<HawkerBean> FetchAllHawkers() 
    {
    	ObservableList<HawkerBean>	ary=FXCollections.observableArrayList();
    	try {
    	
    	
    		pst = con.prepareStatement("select * from hawkers");
    		
    		ResultSet table=pst.executeQuery();
    	
    	
    		while(table.next()) {
	    		String mno=table.getString("mobile");
	    		String name = table.getString("hname");
	    		String DOJ = String.valueOf(table.getDate("doj").toLocalDate());
	    		String alloarea=table.getString("alloareas");
	    		System.out.println(name);
	    		HawkerBean ref=new HawkerBean(name, mno, alloarea, DOJ);
	    		System.out.println(ref.getHname());
	    		ary.add(ref);
	    		
    		}
    	

    	}
    	catch(Exception ex) { ex.printStackTrace(); }
    		return ary;
    }
    Connection con;
    @FXML
    void initialize() {
    	con = MySqlConnector.doConnect();
    	
    	
    }

}
