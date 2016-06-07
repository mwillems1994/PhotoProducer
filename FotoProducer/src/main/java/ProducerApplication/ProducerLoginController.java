/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProducerApplication;

import Shared.Login;
import Shared.Producer;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Ruud
 */
public class ProducerLoginController implements Initializable {

    private static Producer loggedInProducer;
    /**
     * Initializes the controller class.
     */
    @FXML
    private Label lblUsername;

    @FXML
    private TextField tfUsername;

    @FXML
    private Label lblPassword;

    @FXML
    private PasswordField tfPassword;

    @FXML
    private Label lblMessage;

    @FXML
    private Button btnLogin;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            Shared.Login.login(this.tfUsername.getText(), this.tfPassword.getText(), "Producer");
        } catch (Exception e) {
            e.getMessage();
        }
        lblMessage.setVisible(false);
    }

    @FXML
    private void LoginAction(ActionEvent event) {
        boolean loggedIn;
        try {
            loggedIn = Shared.Login.login(this.tfUsername.getText(), this.tfPassword.getText(), "Producer");
            if (loggedIn) {
                lblMessage.setText("Succes");
                lblMessage.setTextFill(Color.GREEN);
                lblMessage.setVisible(true);

                Stage currentStage = (Stage) tfPassword.getScene().getWindow();
                currentStage.close();

                URL location = getClass().getResource("ProducerApplication/ProducerForm.fxml");
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(location);
                fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                fxmlLoader.setController(new ProducerFormController());
                Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("ProducerApplication/ProducerForm.fxml"));
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();
                
                Producer producer = (Producer) Login.loggedInUser;
            } else {
                lblMessage.setText("Error: Username or password is incorrect");
                lblMessage.setTextFill(Color.RED);
                lblMessage.setVisible(true);
            }
        } catch (Exception ex) {
            lblMessage.setText("Error: " + ex.getMessage());
        }
    }
}
