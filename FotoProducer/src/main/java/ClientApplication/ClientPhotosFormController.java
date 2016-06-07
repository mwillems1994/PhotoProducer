/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClientApplication;

import Json.JsonToClass;
import LanguageSupport.LanguageHelper;
import ProducerApplication.ProducerFormController;
import Shared.Customer;
import Shared.Filter;
import Shared.Login;
import Shared.Photo;
import Shared.Product;
import Shared.ProductCopy;
import ShoppingCart.ShoppingCart;
import SocketClient.SocketManager;
import Storage.DbController;
import com.google.gson.Gson;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import static javafx.embed.swing.SwingFXUtils.toFXImage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

/**
 * FXML Controller class
 *
 * @author Lex
 */
public class ClientPhotosFormController implements Initializable {

    Gson gson = new Gson();

    @FXML
    private Button btCart;
    @FXML
    private ImageView ivPicture;
    @FXML
    private Button btProduct;
    @FXML
    private Button btInCart;
    @FXML
    private Label lbError;
    @FXML
    private ComboBox cbOptions;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btProduct.setVisible(false);
        languageLoad();
        try {
            setAvailableProducts();
        } catch (SQLException ex) {
            Logger.getLogger(ClientPhotosFormController.class.getName()).log(Level.SEVERE, null, ex);
        }
        loadPicture(Login.selectedPath);
        cbOptions.getSelectionModel().selectFirst();
    }

    private void languageLoad() {
        //LanguageHelper.setSelectedLanguage(LanguageHelper.Dutch());
        btCart.setText(LanguageHelper.selectedLanguage().getString("ClientPhotosFormController.Button.btCart"));
        btProduct.setText(LanguageHelper.selectedLanguage().getString("ClientPhotosFormController.Button.btProduct"));
        btInCart.setText(LanguageHelper.selectedLanguage().getString("ClientPhotosFormController.Button.btInCart"));
        lbError.setText(LanguageHelper.selectedLanguage().getString("ClientPhotosFormController.Label.lbError"));
    }

    /**
     * load shoppingcart form
     */
    @FXML
    private void showCart() {
        try {
            Stage currentStage = (Stage) ivPicture.getScene().getWindow();
            currentStage.close();
            URL location = getClass().getResource("ClientApplication/ClientCartForm.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(location);
            fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
            fxmlLoader.setController(new ClientCartFormController());
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("ClientApplication/ClientCartForm.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) cbOptions.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void setAvailableProducts() throws SQLException {
        Customer customer = (Customer) Login.loggedInUser;
        String json = "";
        json = DbController.getAllAvailableProducts(customer.getId());
        Product[] DBproducts = JsonToClass.JsonToProducts(json);
        for (Product p : DBproducts) {
            cbOptions.getItems().add(p.getName());
        }
    }

    @FXML
    private void onProduct() {
    }

    @FXML
    private void inCart() throws SQLException {
        Product selectedProduct = null;
        Customer customer = (Customer) Login.loggedInUser;
        String productName = (String) cbOptions.getValue();
        String json = "";
        json = DbController.getAllAvailableProducts(customer.getId());
        Product[] DBproducts = JsonToClass.JsonToProducts(json);
        for (Product p : DBproducts) {
            if (productName.equals(p.getName())) {
                selectedProduct = p;
            }
        }
        ShoppingCart.addItem(new ProductCopy(selectedProduct.getProductID(),
                selectedProduct.getProducer(),
                selectedProduct.getName(),
                selectedProduct.getDescription(),
                selectedProduct.getPrice(),
                new Photo(0, Login.selectedPath), null), 1);
        try {
            ShoppingCart.save();
        } catch (Exception ex) {
            lbError.setVisible(true);
            lbError.setText(ex.getMessage());
        }
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Winkelwagen");
        alert.setHeaderText("Product toegevoegd aan winkelwagen");
        alert.setContentText(selectedProduct.getName() + " toegevoegd");

        alert.showAndWait();

    }

    /**
     * load the picture selected in the combobox
     *
     * @param path
     * @throws IOException
     */
    private void loadPicture(String path) {
        String json = SocketManager.sendCommand("getPhoto|" + path);
        if (!json.equals("photo not found")) {
            try {
                byte[] bytes = gson.fromJson(json, byte[].class);
                InputStream ian = new ByteArrayInputStream(bytes);
                BufferedImage bImage = ImageIO.read(ian);
                Image image = toFXImage(bImage, null);
                ivPicture.setImage(image);
                lbError.setVisible(false);
                ian.close();
            } catch (IOException ex) {
                Logger.getLogger(ClientPhotosFormController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            lbError.setVisible(true);
            ivPicture.setImage(null);
        }
    }
}
