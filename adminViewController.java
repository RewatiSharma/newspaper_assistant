package adminlogin;

import java.io.IOException;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import javafx.scene.media.AudioClip;

public class adminViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private PasswordField txtpass;
    
    
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
    void doLogin(ActionEvent event)
    {	
    	 playSound();
        String enteredPasscode = txtpass.getText().trim();
        String correctPasscode = "admin123"; 

        if (enteredPasscode.equals(correctPasscode)) {
            try {
                URL url = getClass().getResource("/ADesk/admindeskView.fxml");
                Parent root = FXMLLoader.load(url);
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();

                
                Stage pass = (Stage) txtpass.getScene().getWindow();
                pass.close();
            } catch (IOException e) {
                e.printStackTrace();
               
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
               
            }
            
        } 
        else 
        {
          
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("Incorrect passcode. Please try again.");
            alert.showAndWait();
        }
       
       
    }

    //Connection con;

    @FXML
    void initialize() {
       // con = MySqlConnector.doConnect();
    }

}
