package TheDeveloper;

import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

import billCollector.MySqlConnector;
import javafx.fxml.FXML;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class TDViewController {

    @FXML
    private ResourceBundle resources;
    

    

    @FXML
    private URL location;
Connection con;
    @FXML
    void initialize() {
    	con = MySqlConnector.doConnect();
    }

}
