package Storage;

import Shared.User;
import java.util.*;
import java.sql.*;

class ConnectionPoolManager {
    String databaseUrl = "jdbc:mysql://192.168.13.16:1433/pts4g3";
    String userName = "root";
    String password = "password1";

    Vector connectionPool = new Vector();

    public ConnectionPoolManager() {
        initialize();
    }

    public ConnectionPoolManager(
            //String databaseName,
            String databaseUrl,
            String userName,
            String password
    ) {
        this.databaseUrl = databaseUrl;
        this.userName = userName;
        this.password = password;
        initialize();
    }

    private void initialize() {
        //Here we can initialize all the information that we need
        initializeConnectionPool();
    }

    private void initializeConnectionPool() {
        System.out.println("initializing connection pool");
        while (!checkIfConnectionPoolIsFull()) {
            //Adding new connection instance until the pool is full
            connectionPool.addElement(createNewConnectionForPool());
        }
        System.out.println("Connection Pool is initialized.");
    }

    private synchronized boolean checkIfConnectionPoolIsFull() {
        final int MAX_POOL_SIZE = 10;
        
        //Check if the pool size
        if (connectionPool.size() < MAX_POOL_SIZE) {
            return false;
        }

        return true;
    }

    //Creating a connection
    private Connection createNewConnectionForPool() {
        Connection connection = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(databaseUrl, userName, password);
            System.out.println("Connection: " + connection);
        } catch (SQLException sqle) {
            System.err.println("SQLException: " + sqle);
            return null;
        } catch (ClassNotFoundException cnfe) {
            System.err.println("ClassNotFoundException: " + cnfe);
            return null;
        }

        return connection;
    }

    public synchronized Connection getConnectionFromPool() {
        Connection connection = null;

        //Check if there is a connection available. There are times when all the connections in the pool may be used up
        if (connectionPool.size() > 0) {
            connection = (Connection) connectionPool.firstElement();
            connectionPool.removeElementAt(0);
        }
        //Giving away the connection from the connection pool
        return connection;
    }

    public synchronized void returnConnectionToPool(Connection connection) {
        //Adding the connection from the client back to the connection pool
        connectionPool.addElement(connection);
    }
}
