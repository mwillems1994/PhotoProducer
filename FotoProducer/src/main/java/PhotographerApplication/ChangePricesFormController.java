/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PhotographerApplication;

import Json.JsonToClass;
import Shared.Login;
import Shared.Order;
import Shared.Photographer;
import Shared.PhotographerOrder;
import Shared.Product;
import Shared.ProductCopy;
import Shared.ProductPhotographer;
import SocketClient.SocketManager;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Thijs
 */
public class ChangePricesFormController implements Initializable {

    @FXML
    Label lblTotal;

    @FXML
    Label lblSucces;

    @FXML
    Label lblError;

    @FXML
    Label lblTotalPrice;

    @FXML
    Button btnChangePrice;

    @FXML
    Label lblSelectProduct;

    @FXML
    TableView tvProductPrices;

    @FXML
    TextField tfNewPrice;

    @FXML
    TableView<PhotographerOrder> tbOrders;

    @FXML
    TableView<ProductCopy> tbProducts;

    @FXML
    Label lblNewPrice;

    PhotographerOrder po = new PhotographerOrder();
    ObservableList<PhotographerOrder> orders = FXCollections.observableArrayList();
    ObservableList<ProductPhotographer> products = FXCollections.observableArrayList();
    private ProductPhotographer productPhotographer;
    Photographer photographer;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tbProducts.setVisible(false);
        photographer = (Photographer) Login.loggedInUser;
        productPhotographer = null;
        lblError.setText("");
        lblNewPrice.setText("");
        lblTotal.setText("");
        lblTotalPrice.setText("");
        tfNewPrice.setEditable(false);
        btnChangePrice.setDisable(false);
        lblSucces.setText("");

        orders = po.getAllPhotographerOrdes(photographer.getID());

        //TableColumn tbOrderID = new TableColumn("OrderId");
        TableColumn tbOrderCustomerName = new TableColumn("Klant");
        TableColumn tbOrderNrOfProducts = new TableColumn("Aantal producten");
        TableColumn tbOrderPayed = new TableColumn("Betaald");
        TableColumn tbOrderPrice = new TableColumn("Totaal bedrag €");

        TableColumn tbName = new TableColumn("Naam");
        TableColumn tbDescrpition = new TableColumn("Beschrijving");
        TableColumn tbProducer = new TableColumn("Producent");
        TableColumn tbPrice = new TableColumn("Prijs");
        TableColumn tbProfit = new TableColumn("Winst");

        //tbOrderID.setCellValueFactory(new PropertyValueFactory<PhotographerOrder, String>("orderID"));
        tbOrderCustomerName.setCellValueFactory(new PropertyValueFactory<PhotographerOrder, String>("CustomerName"));
        tbOrderNrOfProducts.setCellValueFactory(new PropertyValueFactory<PhotographerOrder, String>("nrproducts"));
        tbOrderPayed.setCellValueFactory(new PropertyValueFactory<PhotographerOrder, String>("payed"));
        tbOrderPrice.setCellValueFactory(new PropertyValueFactory<PhotographerOrder, String>("totalPrice"));

        tbName.setCellValueFactory(new PropertyValueFactory<ProductPhotographer, String>("name"));
        tbDescrpition.setCellValueFactory(new PropertyValueFactory<ProductPhotographer, String>("description"));
        tbProducer.setCellValueFactory(new PropertyValueFactory<ProductPhotographer, String>("producer"));
        tbPrice.setCellValueFactory(new PropertyValueFactory<ProductPhotographer, Double>("price"));
        tbProfit.setCellValueFactory(new PropertyValueFactory<ProductPhotographer, Double>("profitPrice"));

        String jsonString = "";
        try {
            jsonString = SocketManager.sendCommand("getProductsForPhotographer|" + photographer.getID());
        } catch (Exception ex) {
            Logger.getLogger(ChangePricesFormController.class.getName()).log(Level.SEVERE, null, ex);
        }
        JsonToClass json = new JsonToClass();
        for (ProductPhotographer p : json.JsonToProductsPhotograper(jsonString)) {
            products.add(p);
        }

        tbOrders.setItems(orders);
        tbOrders.getColumns().addAll(tbOrderCustomerName, tbOrderNrOfProducts, tbOrderPayed, tbOrderPrice);

        tvProductPrices.setItems(products);
        tvProductPrices.getColumns().addAll(tbName, tbDescrpition, tbProducer, tbPrice, tbProfit);
    }

    public void btnChangeProfitPriceClicked() {
        if (productPhotographer == null) {
            lblError.setText("Select a product first");
        } else {
            try {
                double newPrice = Double.parseDouble(tfNewPrice.getText().replace(',', '.'));
                int newTotalInt = (int) (newPrice * 100);
                newPrice = (double) newTotalInt / 100;
                SocketManager.sendCommand("changePrice|" + productPhotographer.getPhotograperProductId() + "|" + newPrice);
                int index = products.indexOf(productPhotographer);
                productPhotographer.setProfitPrice(newPrice);
                products.set(index, productPhotographer);
                lblSucces.setText("Price has been updated");
            } catch (Exception ex) {
                Logger.getLogger(ChangePricesFormController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void listViewClicked() {
        lblSucces.setText("");
        productPhotographer = (ProductPhotographer) (tvProductPrices.getSelectionModel().getSelectedItem());
        tfNewPrice.setText(Double.toString(productPhotographer.getPrice()));
        lblError.setText("");
        tfNewPrice.setEditable(true);
        lblNewPrice.setText("New (Profit)Price: ");
        tfNewPrice.setText(Double.toString(productPhotographer.getProfitPrice()));
        lblTotal.setText("Total: ");
        lblTotalPrice.setText(String.format("%.2f", (productPhotographer.getProfitPrice() + productPhotographer.getPrice())));
    }

    public void updateTotalPrice() {
        lblSucces.setText("");
        if (productPhotographer == null) {
            lblError.setText("Select a product first");
        } else {
            if (!tfNewPrice.getText().equals("")) {
                double newTotal = 0;
                try {
                    btnChangePrice.setDisable(true);
                    newTotal = Double.parseDouble(tfNewPrice.getText().replace(',', '.'));
                    if (newTotal != Double.parseDouble(tfNewPrice.getText().replace(',', '.').replace("-", ""))) {
                        newTotal = Double.parseDouble(tfNewPrice.getText().replace(',', '.').replace("-", ""));
                        tfNewPrice.setText(Double.toString(newTotal));
                    }
                    int newTotalInt = (int) (newTotal * 100);
                    newTotal = (double) newTotalInt / 100;
                    lblError.setText("");
                    btnChangePrice.setDisable(false);
                } catch (NumberFormatException ex) {
                    lblError.setText("Only numeric values allowed");
                }
                newTotal += productPhotographer.getPrice();
                lblTotalPrice.setText(Double.toString(newTotal));
            } else {
                lblTotalPrice.setText(Double.toString(productPhotographer.getPrice()));
            }
        }
    }

    @FXML
    private void specifyProducts(MouseEvent event) {
        tbProducts.getColumns().clear();
        tbProducts.setVisible(true);
        TablePosition pos = tbOrders.getSelectionModel().getSelectedCells().get(0);
        int row = pos.getRow();
        String oid = tbOrders.getItems().get(row).getOrderID();
        ObservableList<ProductCopy> pol = po.productsOfOrder(Integer.parseInt(oid));
        /*TableColumn productIdCollumn = new TableColumn("ProductId");
         productIdCollumn.setMinWidth(100);
         productIdCollumn.setCellValueFactory(
         new PropertyValueFactory<ProductCopy, String>("productID"))*/;

        TableColumn productNameColumn = new TableColumn("Naam");
        productNameColumn.setMinWidth(100);
        productNameColumn.setCellValueFactory(
                new PropertyValueFactory<ProductCopy, String>("name"));

        TableColumn DescriptionColumn = new TableColumn("Beschrijving");
        DescriptionColumn.setMinWidth(100);
        DescriptionColumn.setCellValueFactory(
                new PropertyValueFactory<ProductCopy, String>("description"));

        TableColumn PriceColumn = new TableColumn("Prijs €");
        PriceColumn.setMinWidth(75);
        PriceColumn.setCellValueFactory(
                new PropertyValueFactory<ProductCopy, String>("price"));

        tbProducts.setItems(pol);
        tbProducts.getColumns().addAll(productNameColumn, DescriptionColumn, PriceColumn);

    }

}
