/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClientApplication;

import LanguageSupport.LanguageHelper;
import ProducerApplication.ProducerFormController;
import Shared.Order;
import Shared.OrderShow;
import Shared.Photo;
import Shared.Product;
import ShoppingCart.ShoppingCart;
import ShoppingCart.ShoppingCartItem;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Ruud
 */
public class ClientCartFormController implements Initializable {

    @FXML
    private Button btnShop;
    @FXML
    private TableView<ShoppingCartItem> tabCart;
    @FXML
    private Label lblTotalPrice;
    @FXML
    private Button btnPay;
    private Order order;
    @FXML
    private Button btRemove;
    ShoppingCartItem cartItemsaved = null;
    private int nrtimesclicked = 0;
    public static String selectedPath;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        CreateColumns();
        loadLanguage();
    }

    public void backToShop() throws IOException {
        Stage currentStage = (Stage) tabCart.getScene().getWindow();
        currentStage.close();
        URL location = getClass().getResource("ClientApplication/PhotosOverviewForm.fxml");
        PhotosOverviewFormController pofc = new PhotosOverviewFormController();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(location);
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        fxmlLoader.setController(pofc);
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("ClientApplication/PhotosOverviewForm.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    private String convertDouble(double price) {
        String returnString = "";
        String[] arr = String.valueOf(price).split("\\.");
        if (arr[1].length() <= 1) {
            returnString = arr[0] + "." + arr[1] + "0";
        } else {
            returnString = arr[0] + "." + arr[1];
        }
        return returnString;
    }

    private void loadLanguage() {
        try {
            btnShop.setText(LanguageHelper.selectedLanguage().getString("ClientCartFormController.Button.btnShop"));
            btnPay.setText(LanguageHelper.selectedLanguage().getString("ClientCartFormController.Button.btnPay"));
            lblTotalPrice.setText(LanguageHelper.selectedLanguage().getString("ClientCartFormController.Label.lblTotalPrice") + convertDouble(ShoppingCart.sumTotalPrice()));
        } catch (Exception ex) {
            Logger.getLogger(ClientCartFormController.class.getName()).log(Level.SEVERE, null, ex);
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

        tabCart.setItems((ObservableList) ShoppingCart.getShoppingCart());
        tabCart.getColumns().addAll(productNameColumn, priceColumn, photographerNameColumn, amountColumn);
    }

    public void btnPayClicked() {
        try {
            URL location = getClass().getResource("ClientApplication/ClientPayForm.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(location);
            fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
            fxmlLoader.setController(new ClientPayFormController());
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("ClientApplication/ClientPayForm.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void doubleClickOnRow() throws IOException {
        long currenttime = System.currentTimeMillis();
        long savedtime = 0;
        if (nrtimesclicked == 0) {
            cartItemsaved = tabCart.getSelectionModel().getSelectedItem();
            savedtime = currenttime;
            nrtimesclicked++;
        } else {
            if (nrtimesclicked == 1) {
                ShoppingCartItem cartItem = tabCart.getSelectionModel().getSelectedItem();
                if (cartItem.getProductID() != cartItemsaved.getProductID()) {
                    currenttime = 0;
                    savedtime = 0;
                    cartItemsaved = cartItem;
                    nrtimesclicked = 1;
                } else {
                    savedtime = currenttime;
                    currenttime = System.currentTimeMillis();
                    nrtimesclicked++;
                }
            }
            if (currenttime - savedtime < 500 && nrtimesclicked == 2) {
                ShoppingCartItem cartItem = tabCart.getSelectionModel().getSelectedItem();
                if (cartItem.getProductID() != cartItemsaved.getProductID()) {
                    currenttime = 0;
                    savedtime = 0;
                    cartItemsaved = cartItem;
                    nrtimesclicked = 1;
                } else {
                    loadShowPhotoForm(cartItem.getPhoto());
                    currenttime = 0;
                    savedtime = 0;
                    nrtimesclicked = 0;
                }
            }
        }
    }

    private void loadShowPhotoForm(Photo photo) throws IOException {
        selectedPath = photo.getFilePath();
        Stage currentStage = (Stage) tabCart.getScene().getWindow();
        URL location = getClass().getResource("ClientApplication/ShowPhotoForm.fxml");
        ShowPhotoFormController spfc = new ShowPhotoFormController();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(location);
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        fxmlLoader.setController(spfc);
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("ClientApplication/ShowPhotoForm.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    public void removeProduct() throws Exception {
        ShoppingCartItem cartItem = tabCart.getSelectionModel().getSelectedItem();
        ShoppingCart.removeItem(cartItem);
        ShoppingCart.save();
        lblTotalPrice.setText(LanguageHelper.selectedLanguage().getString("ClientCartFormController.Label.lblTotalPrice") + convertDouble(ShoppingCart.sumTotalPrice()));
    }
}
