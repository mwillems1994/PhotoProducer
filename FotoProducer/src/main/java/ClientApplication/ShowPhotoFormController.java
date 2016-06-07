/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClientApplication;

import Shared.Photo;
import SocketClient.SocketManager;
import com.google.gson.Gson;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javafx.embed.swing.SwingFXUtils.toFXImage;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.imageio.ImageIO;

/**
 * FXML Controller class
 *
 * @author marcowillems
 */
public class ShowPhotoFormController implements Initializable {

    @FXML
    private ImageView pbPicture;
    Gson gson = new Gson();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setPicture(ClientCartFormController.selectedPath);
    }

    public void setPicture(String path) {
        String json = SocketManager.sendCommand("getPhoto|" + path);
        if (!json.equals("photo not found")) {
            try {
                byte[] bytes = gson.fromJson(json, byte[].class);
                InputStream ian = new ByteArrayInputStream(bytes);
                BufferedImage bImage = ImageIO.read(ian);
                Image image = toFXImage(bImage, null);
                pbPicture.setImage(image);
                ian.close();
            } catch (IOException ex) {
                Logger.getLogger(ClientPhotosFormController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
