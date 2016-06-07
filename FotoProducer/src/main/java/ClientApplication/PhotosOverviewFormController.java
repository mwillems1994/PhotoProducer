/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClientApplication;

import Json.JsonToClass;
import LanguageSupport.LanguageHelper;
import Shared.Album;
import Shared.Login;
import Shared.Photo;
import Shared.User;
import ShoppingCart.ShoppingCart;
import SocketClient.SocketManager;
import com.google.gson.Gson;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingNode;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * FXML Controller class
 *
 * @author Lex
 */
public class PhotosOverviewFormController implements Initializable {
    User loggedin = (User)Login.loggedInUser;
    
    @FXML
    ComboBox cbAlbums;
    @FXML
    Button btCart;
    @FXML
    Label lbError;
    @FXML
    ScrollPane testPane;
    @FXML
    SwingNode swingNode;
    Album[] albums;
    Gson gson = new Gson();
    JPanel panel = new JPanel();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lbError.setVisible(false);
        panel.setLayout(new GridLayout(0, 4));
        String response = SocketManager.sendCommand("getAlbumCustomerId|" + loggedin.getUserID() );
        albums = JsonToClass.JsonToAlbums(response);
        ObservableList<Album> albumsobs = FXCollections.observableArrayList(albums);
        cbAlbums.setItems(albumsobs);
        
        // Add listener for cbAlbums
        cbAlbums.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                Album a = (Album)newValue;
                getPhotos(a.getID());
            }
        });   
        cbAlbums.getSelectionModel().selectFirst();
        languageLoad();
    }                
    
        /**
         * loads language files where needed
         */
        private void languageLoad(){
        btCart.setText(LanguageHelper.selectedLanguage().getString("ClientPhotosFormController.Button.btCart"));
        lbError.setText(LanguageHelper.selectedLanguage().getString("PhotosOverviewFormController.Label.lbError"));
    }
    
    /**
     * get all photos from the selected album
     * @param albumid id of the album the photos have to be loaded
     */
    private void getPhotos(int albumid)
    {
        lbError.setVisible(false);
        panel.removeAll();
        String response = SocketManager.sendCommand("getPhotos|" + albumid);     
        Photo[] photos = JsonToClass.JsonToPhotos(response); 
        if(photos.length > 0)
        {
            for(Photo p : photos)
            {
                loadPicture(p.getFilePath());
            }
        }
        swingNode.setContent(panel);
    }
    
    /**
     * loads the photo from the server and shows it on the screen
     * @param path path on the server where the photo can be found
     */
    private void loadPicture(String path)
    {
        String json = SocketManager.sendCommand("getPhoto|" + path);
        if(!json.equals("photo not found"))
        {
            try {
                byte[] bytes = gson.fromJson(json, byte[].class);
                InputStream ian = new ByteArrayInputStream(bytes);
                BufferedImage bImage = ImageIO.read(ian);
                ian.close();
                Image img = bImage;
                img = img.getScaledInstance(100, 75, Image.SCALE_SMOOTH);
                ImageIcon pic = new ImageIcon(img);
                JButton button = new JButton();
                button.addActionListener(new ButtonListener(path));
                button.setIcon(pic);
                button.setPreferredSize(new Dimension(-1, -1));
                panel.add(button);
            } catch (IOException ex) {
                lbError.setVisible(true);
            }
        }
    }
        
    /**
     * load shoppingcart form
     */
    @FXML
    private void showCart()
    {
        try {
            URL location = getClass().getResource("ClientApplication/ClientCartForm.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(location);
            fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("ClientApplication/ClientCartForm.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage)cbAlbums.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }    
}

/**
 * Listener to load the right photo when the button is clicked
 * @author Lex
 */
class ButtonListener implements ActionListener {
    String path;
    public ButtonListener(String path)
    {
        this.path = path;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        Login.selectedPath = this.path;
        Platform.runLater(new Runnable() {
                @Override public void run() {
                    try {
                        URL location = getClass().getResource("ClientApplication/ClientPhotosForm.fxml");
                        FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation(location);
                        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("ClientApplication/ClientPhotosForm.fxml"));
                        Scene scene = new Scene(root);
                        Stage stage = new Stage();
                        stage.setScene(scene);
                        stage.show();
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        });
    }
}
