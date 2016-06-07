/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClientApplication;

import LanguageSupport.LanguageHelper;
import Shared.Customer;
import Shared.Login;
import Shared.Order;
import Shared.Product;
import ShoppingCart.ShoppingCart;
import ShoppingCart.ShoppingCartItem;
import SocketClient.SocketManager;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.ResourceBundle;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Thijs
 */
public class ClientPayFormController implements Initializable {

    @FXML
    Button btnPay;

    @FXML
    Button btnBack;

    @FXML
    TableView tvCartItems;

    @FXML
    ComboBox cbPayMethod;

    @FXML
    Label lblPayMethod;

    @FXML
    ComboBox cbBank;

    @FXML
    Label lblBank;

    Customer customer = (Customer) Login.loggedInUser;
    ObservableList<String> paymentMehods = FXCollections.observableList(new LinkedList<String>());
    ObservableList<String> banks = FXCollections.observableList(new LinkedList<String>());
    ObservableList<ShoppingCartItem> sc = ShoppingCart.getShoppingCart();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnBack.setText(LanguageHelper.selectedLanguage().getString("ClientPayFormController.Button.btnBack"));
        btnPay.setText(LanguageHelper.selectedLanguage().getString("ClientPayFormController.Button.btnPay"));
        lblPayMethod.setText(LanguageHelper.selectedLanguage().getString("ClientPayFormController.Label.lblPayMethod"));
        cbBank.setVisible(false);
        lblBank.setVisible(false);
        paymentMehods.add("PayPal");
         paymentMehods.add("Ideal");
         paymentMehods.add("Creditcard");
        cbPayMethod.setItems(paymentMehods);
         banks.add("Rabobank");
         banks.add("ABN-AMRO");
         banks.add("ING");
        cbBank.setItems(banks);

        CreateColumns();
        
    }
    public void OpenPayment() throws IOException, URISyntaxException, Exception
    {
        buyProducts();
        String paymentmethod = cbPayMethod.getSelectionModel().getSelectedItem().toString();
        if(paymentmethod=="PayPal")
        {
            Desktop.getDesktop().browse(new URI("http://www.paypal.com"));
        }
        if(paymentmethod=="Ideal")
        {
            String bank = cbBank.getSelectionModel().getSelectedItem().toString();
            if(bank=="Rabobank")
            {
                Desktop.getDesktop().browse(new URI("https://bankieren.rabobank.nl/klanten"));
            }
            if(bank=="ABN-AMRO")
            {
                Desktop.getDesktop().browse(new URI("https://www.abnamro.nl/nl/prive/internet-bankieren/index.html"));
            }
            if(bank=="ING")
            {
                Desktop.getDesktop().browse(new URI("https://mijn.ing.nl/internetbankieren/SesamLoginServlet"));
            }
        }
        if(paymentmethod=="Creditcard")
        {
            Desktop.getDesktop().browse(new URI("https://www.visa.nl/"));
        }
    }
    
        private void CreateColumns() {
        TableColumn productNameColumn = new TableColumn(LanguageHelper.selectedLanguage().getString("ClientCartFormController.table.column.product"));
        productNameColumn.setMinWidth(100);;
        productNameColumn.setCellValueFactory(
                new PropertyValueFactory<Product, String>("name"));

        TableColumn priceColumn = new TableColumn(LanguageHelper.selectedLanguage().getString("ClientCartFormController.table.column.price"));
        priceColumn.setMinWidth(100);
        priceColumn.setCellValueFactory(
                new PropertyValueFactory<Product, Double>("price"));

        TableColumn photographerNameColumn = new TableColumn(LanguageHelper.selectedLanguage().getString("ClientCartFormController.table.column.photographer"));
        photographerNameColumn.setMinWidth(100);
        photographerNameColumn.setCellValueFactory(
                new PropertyValueFactory<Order, String>("photographer"));

        TableColumn amountColumn = new TableColumn(LanguageHelper.selectedLanguage().getString("ClientCartFormController.table.column.amount"));
        amountColumn.setMinWidth(100);
        amountColumn.setCellValueFactory(
                new PropertyValueFactory<ShoppingCartItem, Integer>("amount"));

        tvCartItems.setItems((ObservableList) ShoppingCart.getShoppingCart());
        tvCartItems.getColumns().addAll(productNameColumn, priceColumn, photographerNameColumn, amountColumn);
    }

    public boolean buyProducts() throws SQLException, Exception {
        if (sc.size() > 0) {
            ShoppingCartItem tempSC = sc.get(0);

            SocketManager.sendCommand("addNewOrder|" + customer.getId() + "|" + tempSC.getProducer().getID() + "|" + /*tempSC.getPhoto(). photographer.getID()*/11 + "|" + 1);

            for (ShoppingCartItem sci : ShoppingCart.getShoppingCart()) {
                SocketManager.sendCommand("insertProductCopy|" + sci.getProductID() + "|" + sci.getPhoto().getId() + "|" + 0 /*filterid*/);
            }
            return true;
        } else {
            throw new Exception("Shoppigcart is empty");
        }
    }

    public void Back() throws IOException {
        // Photoform laden
        try {
            URL location = getClass().getResource("ClientApplication/ClientCartForm.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(location);
            fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
            fxmlLoader.setController(new ClientCartFormController());
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("ClientApplication/ClientCartForm.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) btnBack.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void cbPaymentMethodSelect() {
        String selecteditem = cbPayMethod.getSelectionModel().getSelectedItem().toString();
        if (selecteditem!="Ideal")
        {
            cbBank.setVisible(false);
            lblBank.setVisible(false);
        } else {
            cbBank.setVisible(true);
            lblBank.setVisible(true);
        }
    }
}
