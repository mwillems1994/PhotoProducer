/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Storage;

import Shared.User;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import Shared.*;
import ShoppingCart.ShoppingCartItem;
import SocketClient.SocketManager;
import com.google.gson.Gson;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author marcowillems
 */
public class DbController {

    private static ConnectionPoolManager cpManager = new ConnectionPoolManager();
    private static Gson gson = new Gson();

    public static String getAllUsers() throws SQLException {
        LinkedList<User> users = new LinkedList<User>();
        Connection connection = cpManager.getConnectionFromPool();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("{CALL getUsers()}");

        while (resultSet.next()) {
            User user = new User(
                    resultSet.getInt("UserID"),
                    resultSet.getString("Name")
            );
            users.add(user);
        }
        String json = gson.toJson(users);
        cpManager.returnConnectionToPool(connection);
        return json;
    }

    public static String getAllCustomers() throws SQLException {
        LinkedList<Customer> customers = new LinkedList<Customer>();
        Connection connection = cpManager.getConnectionFromPool();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("{CALL getCustomers()}");
        while (resultSet.next()) {
            Customer customer = new Customer(
                    resultSet.getInt("CustomerID"),
                    resultSet.getString("Name")
            );
            customers.add(customer);
        }
        String json = gson.toJson(customers);
        cpManager.returnConnectionToPool(connection);
        return json;
    }

    public static String getAllAvailableProducts(int userid) throws SQLException {
        LinkedList<Product> products = new LinkedList<>();
        Connection connection = cpManager.getConnectionFromPool();
        Statement statement = connection.createStatement();
        int producerid = 0;
        ResultSet resultSet = statement.executeQuery("{CALL GetAvailableUserProducts(" + userid + ")}");
        while (resultSet.next()) {
            producerid = resultSet.getInt("ProducerID");
            Product p = new Product(
                    resultSet.getInt("ProductID"),
                    getProducerFromID(producerid),
                    resultSet.getString("Name"),
                    resultSet.getString("Description"),
                    resultSet.getDouble("MinimumPrice"));
            products.add(p);

        }
        String json = gson.toJson(products);
        cpManager.returnConnectionToPool(connection);
        return json;
    }

    public static String getPhotographersFromProducer(int ProducerID) throws SQLException {
        LinkedList<Photographer> photographers = new LinkedList<Photographer>();
        Connection connection = cpManager.getConnectionFromPool();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("{CALL getPhotographersFromProducer(" + ProducerID + ")}");

        while (resultSet.next()) {
            Photographer photographer = new Photographer(
                    resultSet.getInt("PhotographerID"),
                    resultSet.getString("PhotographerName")
            );
            photographers.add(photographer);
        }
        String json = gson.toJson(photographers);
        cpManager.returnConnectionToPool(connection);

        return json;

    }

    public static Producer getProducerFromID(int ProducerID) throws SQLException {
        Connection connection = cpManager.getConnectionFromPool();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("{CALL getProducerFromID(" + ProducerID + ")}");

        while (resultSet.next()) {
            String producerName = resultSet.getString("ProducerName");
            String companyName = resultSet.getString("CompanyName");
            Producer producer = new Producer(
                    ProducerID,
                    producerName,
                    companyName
            );
            cpManager.returnConnectionToPool(connection);

            return producer;
        }
        cpManager.returnConnectionToPool(connection);

        return null;

    }

    public static String getPhotgrapherFromIDString(int PhotographerID) throws SQLException {
        Connection connection = cpManager.getConnectionFromPool();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("{CALL getPhotographerFromID(" + PhotographerID + ")}");

        while (resultSet.next()) {
            String photgrapherName = resultSet.getString("PhotographerName");
            Photographer photgrapher = new Photographer(
                    PhotographerID,
                    photgrapherName
            );
            String json = gson.toJson(photgrapher);
            cpManager.returnConnectionToPool(connection);

            return json;
        }
        cpManager.returnConnectionToPool(connection);

        return null;

    }

    public static String getProducerFromIDString(int ProducerID) throws SQLException {
        Connection connection = cpManager.getConnectionFromPool();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("{CALL getProducerFromID(" + ProducerID + ")}");

        while (resultSet.next()) {
            String producerName = resultSet.getString("ProducerName");
            String companyName = resultSet.getString("CompanyName");
            Producer producer = new Producer(
                    ProducerID,
                    producerName,
                    companyName
            );
            String json = gson.toJson(producer);
            cpManager.returnConnectionToPool(connection);

            return json;
        }
        return null;

    }

    public static String getOrdersFromPhotographer(int PhotographerId) throws SQLException {
        LinkedList<Order> orders = new LinkedList<>();
        Connection connection = cpManager.getConnectionFromPool();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("{CALL getOrdersFromPhotographer(" + PhotographerId + ")}");

        while (resultSet.next()) {
            int customerID = resultSet.getInt("CustomerID");
            int orderID = resultSet.getInt("OrderID");
            int photograpgerID = PhotographerId;
            int p = resultSet.getInt("Payed");
            boolean payed = false;
            if (p == 0) {
                payed = false;
            } else {
                payed = true;
            }
            Order order = new Order(orderID, customerID, photograpgerID, payed, null);
            orders.add(order);
        }
        //TODO:Implement shoppingcart
        String json = gson.toJson(orders);
        cpManager.returnConnectionToPool(connection);

        return json;
    }

    public static String getOrdersFromProducer(int ProducerID) throws SQLException {
        LinkedList<Order> orders = new LinkedList<Order>();
        Connection connection = cpManager.getConnectionFromPool();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("{CALL getOrdersFromProducer(" + ProducerID + ")}");

        while (resultSet.next()) {
            int customerID = resultSet.getInt("CustomerID");
            int orderID = resultSet.getInt("OrderID");
            int producerID = ProducerID;
            Order order = new Order(
                    orderID,
                    producerID,
                    customerID
            );
            orders.add(order);
        }
        //TODO: Implement shoppingcart
        String json = gson.toJson(orders);
        cpManager.returnConnectionToPool(connection);

        return json;
    }

    public static LinkedList<Product> getProductsForOrder(int OrderID) throws SQLException {
        LinkedList<Product> products = new LinkedList<Product>();
        Connection connection = cpManager.getConnectionFromPool();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("{CALL getProductsForOrder(" + OrderID + ")}");

        while (resultSet.next()) {
            int productID = resultSet.getInt("ProductID");
            int producerID = resultSet.getInt("ProducerID");
            Product product = new Product(
                    productID,
                    getProducerFromID(producerID),
                    resultSet.getString("Name"),
                    resultSet.getString("Description"),
                    resultSet.getDouble("MinimumPrice")
            );
            products.add(product);
        }
        cpManager.returnConnectionToPool(connection);

        return products;
    }

    public static String getProductsForPhotographer(int photographerID) throws SQLException {
        LinkedList<ProductPhotographer> products = new LinkedList<ProductPhotographer>();
        Connection connection = cpManager.getConnectionFromPool();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("{CALL getProductsFromPhotographer(" + photographerID + ")}");

        while (resultSet.next()) {
            int productID = resultSet.getInt("ProductID");
            int producerID = resultSet.getInt("ProducerID");
            ProductPhotographer product = new ProductPhotographer(
                    productID,
                    getProducerFromID(producerID),
                    resultSet.getString("Name"),
                    resultSet.getString("Description"),
                    resultSet.getDouble("MinimumPrice"),
                    resultSet.getDouble("ProfitPrice"),
                    resultSet.getInt("PhotographerID"),
                    resultSet.getInt("PhotographerProductID")
            );
            products.add(product);
        }
        String json = gson.toJson(products);
        cpManager.returnConnectionToPool(connection);

        return json;
    }

    public static String getProductsForProducer(int producerID) throws SQLException {
        LinkedList<Product> products = new LinkedList<Product>();
        Connection connection = cpManager.getConnectionFromPool();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("{CALL getProductsFromProducer(" + producerID + ")}");

        while (resultSet.next()) {
            int productID = resultSet.getInt("ProductID");
            Product product = new Product(
                    productID,
                    getProducerFromID(producerID),
                    resultSet.getString("Name"),
                    resultSet.getString("Description"),
                    resultSet.getDouble("MinimumPrice")
            );
            products.add(product);
        }
        cpManager.returnConnectionToPool(connection);

        String json = gson.toJson(products);
        return json;
    }

    public static void addProduct(int ProducerID, String name, String description, Double price) throws SQLException {
        Connection connection = cpManager.getConnectionFromPool();
        Statement statement = connection.createStatement();
        String sql = "{CALL insertNewProduct(" + ProducerID + "," + "'" + name + "'" + "," + "'" + description + "'" + "," + price + ")}";
        statement.executeUpdate(sql);
        cpManager.returnConnectionToPool(connection);

    }

    public static void addPhoto(String filePath, int AlbumID) throws SQLException {
        Connection connection = cpManager.getConnectionFromPool();
        Statement statement = connection.createStatement();
        String sql = "{CALL insertPhotoToAlbum(" + "'" + filePath + "'" + "," + "'" + AlbumID + "'" + ")}";
        statement.executeUpdate(sql);
        cpManager.returnConnectionToPool(connection);

    }

    public static void updateProfitPricePhotographer(int productphotographerid, double newPrice) throws SQLException {
        Connection connection = cpManager.getConnectionFromPool();
        Statement statement = connection.createStatement();
        String sql = "{CALL updateProfitPriceFromPhotographerProduct(" + "'" + productphotographerid + "'" + "," + "'" + newPrice + "'" + ")}";
        statement.executeUpdate(sql);
        cpManager.returnConnectionToPool(connection);

    }

    public static void updatePriceProducer(int productid, double newPrice) throws SQLException {
        Connection connection = cpManager.getConnectionFromPool();
        Statement statement = connection.createStatement();
        String sql = "{CALL updateMinimumPriceFromProduct(" + "'" + productid + "'" + "," + "'" + newPrice + "'" + ")}";
        statement.executeUpdate(sql);
        cpManager.returnConnectionToPool(connection);

    }

    public static String getPhotoAlbum(int AlbumID) throws SQLException {
        Connection connection = cpManager.getConnectionFromPool();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("{CALL getAlbumId(" + AlbumID + ")}");
        ArrayList<Photo> photos = new ArrayList<>();
        while (resultSet.next()) {
            int photoID = resultSet.getInt("PhotoID");
            String photoPath = resultSet.getString("FilePath");
            Photo a = new Photo(photoID, photoPath);
            photos.add(a);
        }
        Photo[] p = new Photo[photos.size()];
        p = photos.toArray(p);
        String jasonString = gson.toJson(p);
        cpManager.returnConnectionToPool(connection);
        return jasonString;
    }

    public static String getPhotographerAlbumId(int PhotographerID) throws SQLException {
        Connection connection = cpManager.getConnectionFromPool();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("{CALL getPhotographerAlbumId(" + PhotographerID + ")}");
        ArrayList<Album> albums = new ArrayList<>();
        while (resultSet.next()) {
            int albumID = resultSet.getInt("AlbumID");
            String naam = resultSet.getString("AlbumName");
            Album a = new Album(naam, albumID);
            albums.add(a);
        }
        Album[] albumarray = new Album[albums.size()];
        albumarray = albums.toArray(albumarray);
        String jasonString = gson.toJson(albumarray);
        cpManager.returnConnectionToPool(connection);
        return jasonString;
    }

    public static String getAlbumCustomerId(int CustomerID) throws SQLException {
        Connection connection = cpManager.getConnectionFromPool();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("{CALL getAlbumCustomerId(" + CustomerID + ")}");
        ArrayList<Album> albums = new ArrayList<>();
        while (resultSet.next()) {
            int albumID = resultSet.getInt("AlbumID");
            String naam = resultSet.getString("AlbumName");
            Album a = new Album(naam, albumID);
            albums.add(a);
        }
        Album[] albumarray = new Album[albums.size()];
        albumarray = albums.toArray(albumarray);
        String jasonString = gson.toJson(albumarray);
        cpManager.returnConnectionToPool(connection);
        return jasonString;
    }

    public static String login(String Username, String Password, String userType) throws SQLException {
        Connection connection = cpManager.getConnectionFromPool();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("{CALL login(" + "'" + Username + "'" + "," + "'" + Password + "'" + "," + "'" + userType + "'" + ")}");
        while (resultSet.next()) {
            int loginCode = resultSet.getInt("@loginCode");
            int userID = resultSet.getInt("@uid");
            if (loginCode == 1) {
                switch (userType) {
                    case "Producer": //call getProducer
                        cpManager.returnConnectionToPool(connection);
                        return getProducerFromIDString(userID);
                    case "Photographer": //call getPhotogrsapher
                        cpManager.returnConnectionToPool(connection);
                        return getPhotgrapherFromIDString(userID);
                    default: //Type not found
                }
            } else if (loginCode == 0) {
                cpManager.returnConnectionToPool(connection);
                return "";
            }
        }
        cpManager.returnConnectionToPool(connection);
        return "";
    }

    public static String getLoginCode(int AlbumID) throws SQLException, UnsupportedEncodingException {
        Connection connection = cpManager.getConnectionFromPool();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("{CALL generateCodeForAlbum(" + AlbumID + ")}");
        String AlbumCode = "";
        while (resultSet.next()) {
            AlbumCode = resultSet.getString("AlbumCode");
        }
        String jasonString = gson.toJson(Base64.Encode(AlbumCode));
        cpManager.returnConnectionToPool(connection);
        return jasonString;
    }

    public static void createAlbum(int photographerID, int customerId, String Albumname) throws SQLException {
        Connection connection = cpManager.getConnectionFromPool();
        Statement statement = connection.createStatement();
        String sql = "{CALL createAlbum(" + "'" + photographerID + "'" + "," + "'" + customerId + "'" + "," + "'" + Albumname + "'" + ")}";
        statement.executeUpdate(sql);
        cpManager.returnConnectionToPool(connection);
    }

    public static void insertCustomer(String customerName) throws SQLException {
        Connection connection = cpManager.getConnectionFromPool();
        Statement statement = connection.createStatement();
        String sql = "{CALL insertCustomer(" + "'" + customerName + "'" + ")}";
        statement.executeUpdate(sql);
        cpManager.returnConnectionToPool(connection);
    }

    public static void assignCustomertoAlbum(int albumID, int customerID) throws SQLException {
        Connection connection = cpManager.getConnectionFromPool();
        Statement statement = connection.createStatement();
        String sql = "{CALL assignCustomertoAlbum(" + "'" + albumID + "'" + "," + "'" + customerID + "'" + ")}";
        statement.executeUpdate(sql);
        cpManager.returnConnectionToPool(connection);
    }

    public static void addNewOrder(int customerID, int producerID, int photographerID, int payed) throws SQLException {
        Connection connection = cpManager.getConnectionFromPool();
        Statement statement = connection.createStatement();
        String sql = "{CALL insertOrder(" + "'" + customerID + "'" + "," + "'" + producerID + "'" + "," + "'" + photographerID + "'" + "," + "'" + payed + "'" + ")}";
        statement.executeUpdate(sql);
        cpManager.returnConnectionToPool(connection);
    }

    public static void insertProductCopy(int productID, int photoID, int filterID) throws SQLException {
        Connection connection = cpManager.getConnectionFromPool();
        Statement statement = connection.createStatement();
        String sql = "{CALL insertProductCopy(" + "'" + productID + "'" + "," + "'" + photoID + "'" + "," + "'" + filterID + "'" + ")}";
        statement.executeUpdate(sql);
        cpManager.returnConnectionToPool(connection);
    }

}
