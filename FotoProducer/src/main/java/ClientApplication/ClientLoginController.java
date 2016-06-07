/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClientApplication;

import LanguageSupport.LanguageHelper;
import PhotographerApplication.ChangePricesFormController;
import Shared.Base64;
import Shared.Login;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Thijs
 */
public class ClientLoginController implements Initializable {

    @FXML
    TextField tfLoginCode;

    @FXML
    Button btnLogin;

    @FXML
    Label lblError;

    @FXML
    Label lblLanguage;

    @FXML
    ComboBox cbLanguage;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lblError.setVisible(true);
        LanguageHelper.setSelectedLanguage(LanguageHelper.Dutch());
        btnLogin.setText(LanguageHelper.selectedLanguage().getString("ClientLoginController.Button.btnLogin"));
        lblLanguage.setText(LanguageHelper.selectedLanguage().getString("ClientLoginController.Label.lblLanguage"));
        tfLoginCode.promptTextProperty().set(LanguageHelper.selectedLanguage().getString("ClientLoginController.Textfield.tfLogin"));
        loadLanguageFormComponents();
        cbLanguage.getSelectionModel().selectFirst();
    }

    private void loadLanguageFormComponents() {
        List<String> list = new ArrayList<String>();
        ObservableList<String> cbLanguages = FXCollections.observableList(list);
        cbLanguages.add("Nederlands");
        cbLanguages.add("English");
        cbLanguage.setItems(cbLanguages);
        try {
            ShoppingCart.ShoppingCart.load();
        } catch (IOException ex) {
            Logger.getLogger(ClientLoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void btnLoginClick() {
        String clientCode = "";
        try {
            clientCode = Base64.Decode(tfLoginCode.getText());
        } catch (Exception ex) {
            lblError.setText(LanguageHelper.selectedLanguage().getString("ClientLoginController.error.base64Decoding") + ": " + ex.getMessage());
            lblError.setTextFill(Color.RED);
            lblError.setVisible(true);
        }
        try {
            if (checkLanguage()) {
                login(clientCode);
            }
        } catch (Exception ex) {
            lblError.setText(LanguageHelper.selectedLanguage().getString("ClientLoginController.error.base64Decoding") + ": " + ex.getMessage());
            lblError.setTextFill(Color.RED);
            lblError.setVisible(true);
        }
    }

    public boolean checkLanguage() {
        String selectedItem = (String) cbLanguage.getValue();
        if (selectedItem != null) {
            if (selectedItem.equals("Nederlands")) {
                LanguageHelper.setSelectedLanguage(LanguageHelper.Dutch());
                btnLogin.setText(LanguageHelper.selectedLanguage().getString("ClientLoginController.Button.btnLogin"));
                lblLanguage.setText(LanguageHelper.selectedLanguage().getString("ClientLoginController.Label.lblLanguage"));
                tfLoginCode.promptTextProperty().set(LanguageHelper.selectedLanguage().getString("ClientLoginController.Textfield.tfLogin"));
            } else if (selectedItem.equals("English")) {
                LanguageHelper.setSelectedLanguage(LanguageHelper.English());
                btnLogin.setText(LanguageHelper.selectedLanguage().getString("ClientLoginController.Button.btnLogin"));
                lblLanguage.setText(LanguageHelper.selectedLanguage().getString("ClientLoginController.Label.lblLanguage"));
                tfLoginCode.promptTextProperty().set(LanguageHelper.selectedLanguage().getString("ClientLoginController.Textfield.tfLogin"));
            } else {
                lblError.setText("Error: " + LanguageHelper.selectedLanguage().getString("ClientLoginController.error.NoLanguageSelected"));
                return false;
            }
        } else {
            lblError.setText("Error: " + LanguageHelper.selectedLanguage().getString("ClientLoginController.error.NoLanguageSelected"));
            return false;
        }
        return true;
    }

    private void login(String clientCode) {
        if (Login.login(clientCode)) {
            lblError.setText(LanguageHelper.selectedLanguage().getString("ClientLoginController.message.loginSucces"));
            lblError.setTextFill(Color.GREEN);
            lblError.setVisible(true);
            try {
                LoadMainForm();
            } catch (IOException ex) {
                lblError.setText(LanguageHelper.selectedLanguage().getString("ClientLoginController.error.base64Decoding") + ": " + ex.getMessage());
            }
        } else {
            lblError.setText(LanguageHelper.selectedLanguage().getString("ClientLoginController.message.loginFailure"));
            lblError.setTextFill(Color.RED);
            lblError.setVisible(true);
        }
    }

    private void LoadMainForm() throws IOException {
        Stage currentStage = (Stage) tfLoginCode.getScene().getWindow();
        currentStage.close();

        URL location = getClass().getResource("ClientApplication/PhotosOverviewForm.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(location);
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        fxmlLoader.setController(new ChangePricesFormController());
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("ClientApplication/PhotosOverviewForm.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }
}
