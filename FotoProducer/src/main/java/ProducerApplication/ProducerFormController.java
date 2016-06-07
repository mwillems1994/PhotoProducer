
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProducerApplication;

import Json.JsonToClass;
import Shared.*;
import SocketClient.SocketManager;
import Storage.DbController;
import com.google.gson.Gson;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

/**
 * FXML Controller class
 *
 * @author Lex
 */
public class ProducerFormController implements Initializable {

    Producer loggedInProducer = (Producer) Login.loggedInUser;
    private Photographer selectedPhotograph;
    private Photographer selectedPhotograph2;
    private Album selectedAlbumAssign;
    private Customer selectedCustomer;

    // tab Add Product
    @FXML
    private TableView<OrderShow> tbOrders = new TableView<>();
    @FXML
    private TextField tfName;
    @FXML
    private TextField tfProductPrice;
    @FXML
    private TextArea taDescription;
    @FXML
    private Button btnAddProduct;
    @FXML
    private ListView lvProducts;
    @FXML
    private Label lblErrorAddProduct;
    // tab Add Pictures
    @FXML
    private ComboBox cbPhotographer;
    @FXML
    private ComboBox cbAlbum;
    @FXML
    private TextField tfPath;
    @FXML
    private Button btnSelect;
    @FXML
    private Button btnAdd;
    @FXML
    private ComboBox cbPhotographerNewAlbum;
    @FXML
    private TextField tfAlbumName;
    @FXML
    private Button btNewAlbum;
    @FXML
    private Label lbWarning;
    @FXML
    private ComboBox cbPhotographerAssign;

    // tab Orders
    @FXML
    private TableView<ProductCopy> tbOrderProducts;

    //tab Change price 
    @FXML
    private Label lblSuccesPrice;

    @FXML
    private Label lblErrorPrice;

    @FXML
    private Button btnChangePrice;

    @FXML
    private Label lblSelectProduct;

    @FXML
    private TableView tvProductPrices;

    @FXML
    private TextField tfNewPrice;

    @FXML
    private Label lblNewPrice;

    OrderShow ods = new OrderShow();

    ObservableList<Product> products = FXCollections.observableArrayList();
    ObservableList<String> productStrings = FXCollections.observableArrayList();
    ObservableList<Photographer> photographers = FXCollections.observableArrayList();
    ObservableList<Album> albums = FXCollections.observableArrayList();
    ObservableList<Customer> customers = FXCollections.observableArrayList();
    List<File> photos;
    private Product product;
    private Album selectedAlbum;
    Gson gson = new Gson();
    public static final ObservableList outputs = FXCollections.observableArrayList();
    @FXML
    private Label lbWarning2;
    @FXML
    private ComboBox cbAlbumAssign;
    @FXML
    private ComboBox cbClient;
    @FXML
    private Button btAddClient;
    @FXML
    private TextField tfClientName;
    @FXML
    private Color x1;
    @FXML
    private Button btAssignCustomertoAlbum;
    @FXML
    private Label lbAssignWarning;
    @FXML
    private Label lbAssignWarning2;
    @FXML
    private Label lbCreateClientWarning;
    @FXML
    private Label lbCreateClientSucces;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        product = null;
        lblErrorPrice.setText("");
        lblSuccesPrice.setText("");

        TableColumn tbName = new TableColumn("Naam");
        TableColumn tbDescrpition = new TableColumn("Beschrijving");
        TableColumn tbPrice = new TableColumn("Prijs");

        tbName.setCellValueFactory(new PropertyValueFactory<ProductPhotographer, String>("name"));
        tbDescrpition.setCellValueFactory(new PropertyValueFactory<ProductPhotographer, String>("description"));
        tbPrice.setCellValueFactory(new PropertyValueFactory<ProductPhotographer, Double>("price"));
        tvProductPrices.getColumns().addAll(tbName, tbDescrpition, tbPrice);

        try {
            loadPhotographers(loggedInProducer.getID());
            loadProducts(loggedInProducer.getID());
            loadCustomers();
            //printOrders(null);
            tbOrderProducts.setVisible(false);
        } catch (SQLException ex) {
            Logger.getLogger(ProducerFormController.class.getName()).log(Level.SEVERE, null, ex);
        }
        // Add listener for cbAlbum
        cbAlbum.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                Album album = (Album) newValue;
                selectedAlbum = album;
            }
        });
        // Add listener for cbPhotographer
        cbPhotographer.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                Photographer p = (Photographer) newValue;
                try {
                    loadAlbums(p.getID());
                    cbAlbum.getSelectionModel().selectFirst();
                } catch (SQLException ex) {
                    Logger.getLogger(ProducerFormController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        cbPhotographer.getSelectionModel().selectFirst();

        // Add listener for cbPhotographersnewAlbum
        cbPhotographerNewAlbum.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                selectedPhotograph = (Photographer) newValue;
            }
        });
        cbPhotographerNewAlbum.getSelectionModel().selectFirst();

        // Add listener for cbPhotographerAssign
        cbPhotographerAssign.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                Photographer p = (Photographer) newValue;
                try {
                    loadAlbumsAssign(p.getID());
                    cbAlbumAssign.getSelectionModel().selectFirst();
                } catch (SQLException ex) {
                    Logger.getLogger(ProducerFormController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        // Add listener for cbAlbumAssign
        cbAlbumAssign.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                selectedAlbumAssign = (Album) newValue;
            }
        });

        // Add listener for cbClientAssign
        cbClient.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                selectedCustomer = (Customer) newValue;
            }
        });
        cbClient.getSelectionModel().selectFirst();

        printOrders();
    }

    @FXML
    private void addProduct(ActionEvent event) throws SQLException {
        String name = tfName.getText();
        String description = taDescription.getText();
        double price = Double.parseDouble(tfProductPrice.getText().replace(',', '.'));
        String response = SocketManager.sendCommand("insertProduct|" + loggedInProducer.getID() + "|" + name + "|" + description + "|" + price);
        System.out.println(response);
    }

    @FXML
    private void addProductTextEdited() {
        if (!tfProductPrice.getText().equals("")) {
            try {
                double price = Double.parseDouble(tfProductPrice.getText().replace(',', '.'));
                lblErrorAddProduct.setText("");
            } catch (NumberFormatException e) {
                lblErrorAddProduct.setText("Only numeric values allowed");
            }
        }
    }

    /**
     * Select a list of photo's to upload
     *
     * @param event
     */
    @FXML
    private void selectPicture(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Pictures files", "*.png", "*.jpg");
        fileChooser.getExtensionFilters().add(extFilter);
        photos = fileChooser.showOpenMultipleDialog(null);
        StringBuilder s = new StringBuilder();
        if (!photos.isEmpty()) {
            for (File f : photos) {
                s.append(f.getPath());
            }
        }
        tfPath.setText(s.toString());
    }

    /**
     * uploads all selected photo's
     *
     * @param event
     * @throws IOException
     * @throws InterruptedException
     */
    @FXML
    private void addPicture(ActionEvent event) throws IOException, InterruptedException {
        if (!photos.isEmpty()) {
            int wrongcount = 0;
            for (File photo : photos) {
                String uploadmessage = writePicture(photo.getPath(), "jpg");
                if (!uploadmessage.equals("Photo uploaded")) {
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Upload of picture " + photo.getPath() + " faile  d");
                    alert.setHeaderText(photo.getName());
                    alert.setContentText(uploadmessage);
                    alert.show();
                    wrongcount++;
                }
            }
            if (wrongcount == 0) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Upload succesful");
                alert.setHeaderText("Upload succesful");
                alert.setContentText("Upload succesful");
                alert.show();
            }

        }
    }

    /**
     * Uploads a photo to the database and server
     *
     * @param path
     * @param format
     * @return result of uploading photo
     * @throws IOException
     * @throws InterruptedException
     */
    private String writePicture(String path, String format) throws IOException, InterruptedException {
        byte[] bytes;
        Gson gson = new Gson();

        BufferedImage buffi = ImageIO.read(new File(path));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageOutputStream ios = ImageIO.createImageOutputStream(baos);

        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName(format);
        ImageWriter writer = writers.next();

        ImageWriteParam param = writer.getDefaultWriteParam();
        param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        param.setCompressionQuality(0.08f);

        writer.setOutput(ios);
        writer.write(null, new IIOImage(buffi, null, null), param);

        bytes = baos.toByteArray();

        writer.dispose();

        String json = gson.toJson(bytes);

        if (json.length() > 65000) {
            return "Photo too big";
        }
        Path p = Paths.get(path);
        String filename = selectedAlbum.getID() + " " + p.getFileName().toString();
        filename = filename.substring(0, filename.indexOf("."));
        return SocketManager.sendCommand("sendPhoto|" + json + "|" + filename + "|" + selectedAlbum.getID());
    }

    private void printOrders() {
        ObservableList<OrderShow> osl = ods.getAllOrderShow(loggedInProducer.getID());
        TableColumn OrderIdColumn = new TableColumn("OrderID");
        OrderIdColumn.setMinWidth(100);
        OrderIdColumn.setCellValueFactory(
                new PropertyValueFactory<OrderShow, String>("orderID"));

        TableColumn CustomerNameColumn = new TableColumn("Customer");
        CustomerNameColumn.setMinWidth(100);
        CustomerNameColumn.setCellValueFactory(
                new PropertyValueFactory<OrderShow, String>("CustomerName"));

        TableColumn NrofOrdersColumn = new TableColumn("NrOfProducts");
        NrofOrdersColumn.setMinWidth(100);
        NrofOrdersColumn.setCellValueFactory(
                new PropertyValueFactory<OrderShow, String>("nrproducts"));

        tbOrders.setItems(osl);
        tbOrders.getColumns().addAll(OrderIdColumn, CustomerNameColumn, NrofOrdersColumn);

    }

    @FXML
    private void specifyProducts(MouseEvent event) {
        tbOrderProducts.setVisible(true);
        try {
            TablePosition pos = tbOrders.getSelectionModel().getSelectedCells().get(0);
            int row = pos.getRow();
            String oid = tbOrders.getItems().get(row).getOrderID();
            ObservableList<ProductCopy> pol = ods.productsOfOrder(Integer.parseInt(oid));

            TableColumn productIdCollumn = new TableColumn("ProductId");
            productIdCollumn.setMinWidth(100);
            productIdCollumn.setCellValueFactory(
                    new PropertyValueFactory<ProductCopy, String>("productID"));

            TableColumn productNameColumn = new TableColumn("Name");
            productNameColumn.setMinWidth(100);
            productNameColumn.setCellValueFactory(
                    new PropertyValueFactory<ProductCopy, String>("name"));

            TableColumn DescriptionColumn = new TableColumn("Description");
            DescriptionColumn.setMinWidth(100);
            DescriptionColumn.setCellValueFactory(
                    new PropertyValueFactory<ProductCopy, String>("description"));

            TableColumn PriceColumn = new TableColumn("Price");
            PriceColumn.setMinWidth(75);
            PriceColumn.setCellValueFactory(
                    new PropertyValueFactory<ProductCopy, String>("price"));

            tbOrderProducts.setItems(pol);
            tbOrderProducts.getColumns().addAll(productIdCollumn, productNameColumn, DescriptionColumn, PriceColumn);
        } catch (Exception ex) {
        }
    }

    /**
     * Loads all photographers working for the logged in producer
     *
     * @param producerID
     * @throws SQLException
     */
    private void loadPhotographers(int producerID) throws SQLException {
        String photographersJson = DbController.getPhotographersFromProducer(producerID);
        Photographer[] photographer = JsonToClass.JsonToPhotographerAll(photographersJson);
        photographers.addAll(Arrays.asList(photographer));
        cbPhotographer.setItems(photographers);
        cbPhotographerAssign.setItems(photographers);
        cbPhotographerNewAlbum.setItems(photographers);
    }

    /**
     * loads all albums the selected photographer has made
     *
     * @param photographerID
     * @throws SQLException
     */
    private void loadAlbums(int photographerID) throws SQLException {
        albums.clear();
        String albumsJson = SocketManager.sendCommand("getPhotographerAlbumId|" + photographerID);
        Album[] albumarray = JsonToClass.JsonToAlbums(albumsJson);
        albums.addAll(Arrays.asList(albumarray));
        cbAlbum.setItems(albums);
    }

    private void loadAlbumsAssign(int photographerID) throws SQLException {
        albums.clear();
        String albumsJson = SocketManager.sendCommand("getPhotographerAlbumId|" + photographerID);
        Album[] albumarray = JsonToClass.JsonToAlbums(albumsJson);
        albums.addAll(Arrays.asList(albumarray));
        cbAlbumAssign.setItems(albums);
    }

    private void loadProducts(int producerID) {
        String productJson = "";
        try {
            productJson = DbController.getProductsForProducer(producerID);
        } catch (SQLException ex) {
            Logger.getLogger(ProducerFormController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Product[] DBproducts = JsonToClass.JsonToProducts(productJson);
        for (Product p : DBproducts) {
            products.add(p);
            productStrings.add(p.getName() + ", " + p.getDescription());
        }
        lvProducts.setItems(productStrings);
        tvProductPrices.setItems(products);
    }

    private void loadCustomers() throws SQLException {
        customers.clear();
        String customerJson = SocketManager.sendCommand("getCustomers");
        Customer[] customerArray = JsonToClass.JsonToCustomerList(customerJson);
        customers.addAll(Arrays.asList(customerArray));
        cbClient.setItems(customers);
    }

    @FXML
    private void btnChangePrice() {
        if (product == null) {
            lblErrorPrice.setText("Select a product first");
        } else {
            double newPrice = Double.parseDouble(tfNewPrice.getText().replace(',', '.'));
            int newTotalInt = (int) (newPrice * 100);
            newPrice = (double) newTotalInt / 100;
            SocketManager.sendCommand("changePriceProducer|" + product.getProductID() + "|" + newPrice);
            int index = products.indexOf(product);
            product.setPrice(newPrice);
            products.set(index, product);
            lblSuccesPrice.setText("Price has been updated");
        }
    }

    @FXML
    private void btNewAlbum() {
        String givenAlbumName = tfAlbumName.getText();
        if (!givenAlbumName.equals("")) {
            lbWarning.setVisible(false);
            lbWarning2.setVisible(false);
            SocketManager.sendCommand("createAlbum|" + selectedPhotograph.getID() + "|" + 19 + "|" + givenAlbumName);
            try {
                loadAlbums(selectedPhotograph.getID());
                lbWarning2.setVisible(true);
                lbWarning2.setText("Album succesvol toegevoegd.");
            } catch (SQLException ex) {
                lbWarning.setVisible(true);
                lbWarning.setText("Er ging iets fout probeer het opnieuw.");
                Logger.getLogger(ProducerFormController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            lbWarning2.setVisible(false);
            lbWarning.setVisible(true);
            lbWarning.setText("Voeg een naam in voor het album.");
        }
    }

    @FXML
    private void listViewPricesClicked() {
        lblSuccesPrice.setText("");
        try {
            product = (Product) (tvProductPrices.getSelectionModel().getSelectedItem());
            tfNewPrice.setText(Double.toString(product.getPrice()));
            lblErrorPrice.setText("");
            tfNewPrice.setEditable(true);
            lblNewPrice.setText("New Price: ");
            tfNewPrice.setText(Double.toString(product.getPrice()));
        } catch (Exception ex) {
        }
    }

    @FXML
    private void updatePriceTextEdited() {
        lblSuccesPrice.setText("");
        if (product == null) {
            lblErrorPrice.setText("Select a product first");
        } else if (!tfNewPrice.getText().equals("")) {
            double newTotal = 0;
            try {
                btnChangePrice.setDisable(true);
                newTotal = Double.parseDouble(tfNewPrice.getText().replace(',', '.'));
                if (newTotal != Double.parseDouble(tfNewPrice.getText().replace(',', '.').replace("-", ""))) {
                    newTotal = Double.parseDouble(tfNewPrice.getText().replace(',', '.').replace("-", ""));
                    tfNewPrice.setText(Double.toString(newTotal));
                }
                lblErrorPrice.setText("");
                btnChangePrice.setDisable(false);
            } catch (NumberFormatException ex) {
                lblErrorPrice.setText("Only numeric values allowed");
            }
        }
    }

    @FXML
    private void createClient() {
        String clientName = tfClientName.getText();
        if (!clientName.isEmpty()) {
            SocketManager.sendCommand("insertCustomer|" + clientName);
            try {
                lbCreateClientSucces.setVisible(true);
                lbCreateClientWarning.setVisible(false);
                loadCustomers();        
            } catch (SQLException ex) {
                Logger.getLogger(ProducerFormController.class.getName()).log(Level.SEVERE, null, ex);
                lbCreateClientSucces.setVisible(false);
                lbCreateClientWarning.setVisible(true);
            }
        } else {
            lbCreateClientSucces.setVisible(false);
            lbCreateClientWarning.setVisible(true);
        }
    }

    @FXML
    public void AssignCustomertoAlbum() {
        Album album = selectedAlbumAssign;
        Customer customer = selectedCustomer;
        if (album != null && customer != null) {
            SocketManager.sendCommand("assignCustomertoAlbum|" + album.getID() + "|" + customer.getId());
            lbAssignWarning2.setVisible(true);
            lbAssignWarning.setVisible(false);
        } else {
            lbAssignWarning2.setVisible(false);
            lbAssignWarning.setVisible(true);
        }
    }
}
