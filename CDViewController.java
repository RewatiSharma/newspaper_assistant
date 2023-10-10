package CustomerDash;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.Writer;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.io.File;
import billCollector.MySqlConnector;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
public class CDViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    Connection con;
    PreparedStatement pst;  

    @FXML
    private ComboBox<String> comboArea;

    @FXML
    private ComboBox<String> comboPaper;
    @FXML
    private Label lblResp;

    @FXML
    private TableView<customerBean> tabledata;
    
 //---------------------------------------------------------------

    @FXML
    void doExport(ActionEvent event) throws Exception
    {
    	
         writeExcel();
    }
    
        
        void writeExcel() throws Exception
    	 {
        	ObservableList<customerBean> list = FXCollections.observableArrayList();
           Writer writer = null;
           try {
           	File file = new File("hawkers.csv");
               writer = new BufferedWriter(new FileWriter(file));
               String text="name,spapers \n";
               writer.write(text);
               for (customerBean p : list)
               {
    				text = p.getName()+ "," + p.getSpapers()+"\n";
                   writer.write(text);
               }
               System.out.println("Exported Successfully");
               
               
               
           } catch (Exception ex) {
               ex.printStackTrace();
               
           }
           finally {
        	   
               writer.flush();
                writer.close();
           }
       }
   
    ObservableList<customerBean> list;
    
    //-------------------------------------------
    @FXML
    void doFetch(ActionEvent event) {
        String ppr = comboPaper.getValue();

        if (ppr == null || ppr.isEmpty()) {
            return;
        }

        ObservableList<String> customers = FetchAllCustomers2(ppr);

        if (customers.isEmpty()) {
            System.out.println("No customers found for the selected paper.");
            return;
        }

        tabledata.getColumns().clear();
        tabledata.getItems().clear();

        TableColumn<customerBean, String> name = new TableColumn<>("Customer Name");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        name.setMinWidth(150);
        tabledata.getColumns().add(name);

        for (String customerName : customers) {
            customerBean customer = new customerBean(customerName, "");
            tabledata.getItems().add(customer);
        }
    }

    ObservableList<String> FetchAllCustomers2(String ppr) {
        ObservableList<String> names = FXCollections.observableArrayList();
        try {
            pst = con.prepareStatement("SELECT name FROM customers WHERE spapers LIKE ?");
            pst.setString(1, "%" + ppr + "%");

            ResultSet resultSet1 = pst.executeQuery();
            while (resultSet1.next()) {
                String name1 = resultSet1.getString("name");
                names.add(name1);
            }
            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return names;
    }
//--------------------------------------------------------------------------
    
    
    @FXML
    void doFilter(ActionEvent event) {
    	String selectedArea = comboArea.getValue();

        if (selectedArea == null || selectedArea.isEmpty()) {
            return;
        }

        tabledata.getColumns().clear();
        tabledata.getItems().clear();

        TableColumn<customerBean, String> name = new TableColumn<>("Customer Name");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        name.setMinWidth(150);
        tabledata.getColumns().add(name);

        ObservableList<customerBean> customers = FetchAllCustomers1(selectedArea);
        tabledata.setItems(customers);
    }

    ObservableList<customerBean> FetchAllCustomers1(String selectedArea) {
        ObservableList<customerBean> ary = FXCollections.observableArrayList();
        try {
            pst = con.prepareStatement("SELECT name FROM customers WHERE area = ?");
            pst.setString(1, selectedArea);

            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                customerBean ref = new customerBean(name, name);
                ary.add(ref);
            }
            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ary;
    }
    //-----------------------------------------------
    
void doFillRolls() {
        
        try {
            pst = con.prepareStatement("SELECT DISTINCT area FROM customers");
            ResultSet table = pst.executeQuery();

            while (table.next()) {
                String area = table.getString("area");
                System.out.println(area);
                comboArea.getItems().add(area);
            }
            
            
            pst = con.prepareStatement("SELECT DISTINCT papername FROM papers");
            ResultSet table1 = pst.executeQuery();

            while (table1.next()) {
                String paper = table1.getString("papername");
                System.out.println(paper);
                comboPaper.getItems().add(paper);
            
            }
        }

         catch (Exception exp) {
            System.out.println(exp);
        }
    }

//--------------------------------------------------------------

    @FXML
    void initialize() {
    	con = MySqlConnector.doConnect();
    	doFillRolls();
   
    }

}
