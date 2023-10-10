package ADesk;

import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import billCollector.MySqlConnector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

public class admindeskViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    
    
    
    

    URL url;
   	Media media;
   	MediaPlayer mediaplayer;
   	AudioClip audio;
   	void playSound(){
       	url=getClass().getResource("clickk.mp3");
   		audio=new AudioClip(url.toString());
   		audio.play();
       }
    
    

    @FXML
    void doBillColl(ActionEvent event) {
    	playSound();
    	try
   	 	{
   		 	Parent root=FXMLLoader.load(getClass().getResource("/billCollector/bcView.fxml")); 
   		 	Scene scene=new Scene(root);
   		 	Stage stage=new Stage();
   		 	stage.setScene(scene);
   		 	stage.show();
   	 	}
   	 	catch(Exception e)
   	 	{
   	 		e.printStackTrace();
   	 	}
    }

    

    @FXML
    void doBillGen(ActionEvent event) {
    	playSound();
    	try
   	 	{
   		 	Parent root=FXMLLoader.load(getClass().getResource("/BillModule/BillingGenView.fxml")); 
   		 	Scene scene=new Scene(root);
   		 	Stage stage=new Stage();
   		 	stage.setScene(scene);
   		 	stage.show();
   	 	}
   	 	catch(Exception e)
   	 	{
   	 		e.printStackTrace();
   	 	}
    }

    
    

    @FXML
    void doBillStatus(ActionEvent event) {
    	playSound();
    	try
   	 	{
   		 	Parent root=FXMLLoader.load(getClass().getResource("/BillBoard/BBView.fxml")); 
   		 	Scene scene=new Scene(root);
   		 	Stage stage=new Stage();
   		 	stage.setScene(scene);
   		 	stage.show();
   	 	}
   	 	catch(Exception e)
   	 	{
   	 		e.printStackTrace();
   	 	}

    }

    

    @FXML
    void doCustomerGoggler(ActionEvent event) {
    	playSound();
    	try
   	 	{
   		 	Parent root=FXMLLoader.load(getClass().getResource("/CustomerDash/CDView.fxml")); 
   		 	Scene scene=new Scene(root);
   		 	Stage stage=new Stage();
   		 	stage.setScene(scene);
   		 	stage.show();
   	 	}
   	 	catch(Exception e)
   	 	{
   	 		e.printStackTrace();
   	 	}
    }

    @FXML
    void doCustomerManager(ActionEvent event) {
    	playSound();
    	try
   	 	{
   		 	Parent root=FXMLLoader.load(getClass().getResource("/Customer/CustomerManagerView.fxml")); 
   		 	Scene scene=new Scene(root);
   		 	Stage stage=new Stage();
   		 	stage.setScene(scene);
   		 	stage.show();
   	 	}
   	 	catch(Exception e)
   	 	{
   	 		e.printStackTrace();
   	 	}
    }


    @FXML
    void doDisplayHawker(ActionEvent event) {
    	playSound();
    	try
   	 	{
   		 	Parent root=FXMLLoader.load(getClass().getResource("/table/tableView.fxml")); 
   		 	Scene scene=new Scene(root);
   		 	Stage stage=new Stage();
   		 	stage.setScene(scene);
   		 	stage.show();
   	 	}
   	 	catch(Exception e)
   	 	{
   	 		e.printStackTrace();
   	 	}
    }


    @FXML
    void doHawkerManager(ActionEvent event) {
    	playSound();
    	try
   	 	{
   		 	Parent root=FXMLLoader.load(getClass().getResource("/Hawker/HawkerManagerView.fxml")); 
   		 	Scene scene=new Scene(root);
   		 	Stage stage=new Stage();
   		 	stage.setScene(scene);
   		 	stage.show();
   	 	}
   	 	catch(Exception e)
   	 	{
   	 		e.printStackTrace();
   	 	}
    }


    @FXML
    void doMeetDevp(ActionEvent event) {
    	playSound();
    	try
   	 	{
    		
    		
    		
   		 	Parent root=FXMLLoader.load(getClass().getResource("/TheDeveloper/TDView.fxml")); 
   		 	Scene scene=new Scene(root);
   		 	Stage stage=new Stage();
   		 	stage.setScene(scene);
   		 	stage.show();
   	 	}
   	 	catch(Exception e)
   	 	{
   	 		e.printStackTrace();
   	 	}
    	
    	//playSound();
    }
    @FXML
    void doPaperMaster(ActionEvent event) {
    	playSound();
    	try
   	 	{
   		 	Parent root=FXMLLoader.load(getClass().getResource("/myProject/CustomerManagerView.fxml")); 
   		 	Scene scene=new Scene(root);
   		 	Stage stage=new Stage();
   		 	stage.setScene(scene);
   		 	stage.show();
   	 	}
   	 	catch(Exception e)
   	 	{
   	 		e.printStackTrace();
   	 	}
    }

Connection con;
    @FXML
    void initialize() {
    	con = MySqlConnector.doConnect();
    }

}
