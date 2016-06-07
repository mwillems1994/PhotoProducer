package Server;

import Shared.Photo;
import Storage.DbController;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MultiThreadedSocketServer {

    ServerSocket myServerSocket;
    boolean ServerOn = true;

    public MultiThreadedSocketServer() {
        int port = 80;
        try {
            myServerSocket = new ServerSocket(port);
        } catch (IOException ioe) {
            System.out.println("Could not create server socket on port: " + port + ". Quitting." + ioe.getMessage());
            //System.exit(-1);
        }

        Calendar now = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("E yyyy.MM.dd 'at' hh:mm:ss a zzz");
        System.out.println("It is now : " + formatter.format(now.getTime()));

        // Successfully created Server Socket. Now wait for connections. 
        while (ServerOn) {
            try {
                // Accept incoming connections. 
                Socket clientSocket = myServerSocket.accept();

                // accept() will block until a client connects to the server. 
                // If execution reaches this point, then it means that a client 
                // socket has been accepted. 
                // For each client, we will start a service thread to 
                // service the client requests. This is to demonstrate a 
                // Multi-Threaded server. Starting a thread also lets our 
                // MultiThreadedSocketServer accept multiple connections simultaneously. 
                // Start a Service thread 
                System.out.println("New thread created...");
                ClientServiceThread cliThread = new ClientServiceThread(clientSocket);
                cliThread.start();

            } catch (IOException ioe) {
                System.out.println("Exception encountered on accept. Ignoring. Stack Trace :");
                ioe.printStackTrace();
            }

        }

        try {
            myServerSocket.close();
            System.out.println("Server Stopped");
        } catch (Exception ioe) {
            System.out.println("Problem stopping server socket");
            System.exit(-1);
        }

    }

    public static void main(String[] args) {
        new MultiThreadedSocketServer();
    }

    class ClientServiceThread extends Thread {

        Socket myClientSocket;
        boolean m_bRunThread = true;

        public ClientServiceThread() {
            super();
        }

        ClientServiceThread(Socket s) {
            myClientSocket = s;

        }

        private String getSomeData() throws SQLException {
            //return DbController.getAllCustomers().getFirst().getName();
            return "";
        }

        /**
         * function for listening to commands.
         */
        public void run() {
            BufferedReader in = null;
            PrintWriter out = null;
            System.out.println("Accepted Client Address - " + myClientSocket.getInetAddress().getHostName());

            try {
                in = new BufferedReader(new InputStreamReader(myClientSocket.getInputStream()));
                out = new PrintWriter(new OutputStreamWriter(myClientSocket.getOutputStream()));

                /**
                 * Run in a loop until m_bRunThread is set to false
                 */
                while (m_bRunThread) {
                    // read incoming stream 
                    m_bRunThread = runnableThread(in, out);
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Client has closed application");
            } finally {
                // Clean up 
                try {
                    in.close();
                    out.close();
                    myClientSocket.close();
                    System.out.println("Server stopped due connection lost.");
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        }
    }

    private boolean runnableThread(BufferedReader in, PrintWriter out) throws IOException, SQLException, SocketException {
        String clientCommand = in.readLine();
        if (clientCommand != null) {
            System.out.println("Client Says :" + clientCommand);

            //terminate connection
            if (!ServerOn) {
                System.out.print("Server has already stopped");
                out.println("Server has already stopped");
                out.flush();
                return true;
            }
            boolean commandResult = processCommand(clientCommand);
            if (commandResult) {
                executeCommandAndFlushToClient(clientCommand, out);
            } else {
                return commandResult;
            }
        }
        return true;
    }

    /**
     * pre process command checks for command quit and end
     *
     * @param command
     * @return false if command was quit and end, true when other command.
     */
    private boolean processCommand(String command) {
        switch (command) {
            case "quit":
                return false;
            case "end":
                return quitClientAndEndServer();
            default:
                return true;
        }
    }

    private void executeCommandAndFlushToClient(String command, PrintWriter out) throws SQLException, IOException {
        out.println(getJsonFromCommand(command));
        out.flush();
    }

    private boolean quitClientAndEndServer() {
        System.out.print("Stopping client thread for client: ");
        ServerOn = false;
        return false;
    }

    /**
     * Split command by Pipe char(|) into commandArray
     *
     * @param command
     * @return commandArray
     */
    private static String[] splitarg(String command) {
        String[] parts = command.split("\\|");
        return parts;
    }

    /**
     * function that puts command into a switch and gets the corresponding json
     *
     * @param command
     * @return corresponding json
     * @throws SQLException
     * @throws IOException
     */
    private String getJsonFromCommand(String command) throws SQLException, IOException {
        String returnValue = "";
        String command2nd = "";
        String arg = "";
        String[] argarray = splitarg(command);
        switch (argarray[0]) {
            case "getCustomers":
                returnValue = DbController.getAllCustomers();
                break;
            case "getProducerFromID":
                returnValue = DbController.getProducerFromIDString(Integer.parseInt(argarray[1]));
                break;
            case "getPhotographerFromID":
                returnValue = DbController.getPhotgrapherFromIDString(Integer.parseInt(argarray[1]));
                break;
            case "getAllUsers":
                returnValue = DbController.getAllUsers();
                break;
            case "getPhotographersFromProducer":
                returnValue = DbController.getPhotographersFromProducer(Integer.parseInt(argarray[1]));
                break;
            case "getOrdersFromProducer":
                returnValue = DbController.getOrdersFromProducer(Integer.parseInt(argarray[1]));
                break;
            case "getPhotos":
                returnValue = DbController.getPhotoAlbum(Integer.parseInt(argarray[1]));
                break;

            case "getPhoto":
                returnValue = Photo.loadPhoto(argarray[1]);
                break;

            case "getProductsForPhotographer":
                returnValue = DbController.getProductsForPhotographer(Integer.parseInt(argarray[1]));
                break;

//            case "getProductsForOrder" : returnValue = DbController.getProductsForOrder(Integer.parseInt(argarray[1]));
//                break;
            case "insertProduct":
                DbController.addProduct(Integer.parseInt(argarray[1]), argarray[2], argarray[3], Double.parseDouble(argarray[4]));
                break;
            case "sendPhoto":
                try {
                    DbController.addPhoto(Photo.savePhoto(argarray[1], argarray[2]), Integer.parseInt(argarray[3]));
                    returnValue = "Photo uploaded";
                } catch (IOException ex) {
                    returnValue = "Photo couldn't be saved on the server";
                } catch (SQLException ex) {
                    returnValue = "Photo couldn't be added to the database";
                }

                break;
            case "login":
                returnValue = DbController.login(argarray[1], argarray[2], argarray[3]);
                break;
            case "changePrice":
                DbController.updateProfitPricePhotographer(Integer.parseInt(argarray[1]), Double.parseDouble(argarray[2]));
                break;
            case "changePriceProducer":
                DbController.updatePriceProducer(Integer.parseInt(argarray[1]), Double.parseDouble(argarray[2]));
                break;
            case "getPhotographerAlbumId":
                returnValue = DbController.getPhotographerAlbumId(Integer.parseInt(argarray[1]));
                break;
            case "getAlbumCustomerId":
                returnValue = DbController.getAlbumCustomerId(Integer.parseInt(argarray[1]));
                break;
            case "getOrdersFromPhotographer":
                returnValue = DbController.getOrdersFromPhotographer(Integer.parseInt(argarray[1]));
                break;
            case "createAlbum":
                DbController.createAlbum(Integer.parseInt(argarray[1]), Integer.parseInt(argarray[2]), argarray[3]);
                break;
            case "getAvailableProducts":
                DbController.getAllAvailableProducts(Integer.parseInt(argarray[1]));
                break;
            case "insertCustomer":
                DbController.insertCustomer(argarray[1]);
                break;
            case "assignCustomertoAlbum":
                DbController.assignCustomertoAlbum(Integer.parseInt(argarray[1]), Integer.parseInt(argarray[2]));
                break;
            case "addNewOrder":
                DbController.addNewOrder(Integer.parseInt(argarray[1]), Integer.parseInt(argarray[2]), Integer.parseInt(argarray[3]), Integer.parseInt(argarray[4]));
                break;
            case "insertProductCopy":
                DbController.insertProductCopy(Integer.parseInt(argarray[1]), Integer.parseInt(argarray[2]), Integer.parseInt(argarray[3]));
                break;
            default:
                returnValue = "Command unknown";
                System.out.println("Command unkown");
        }
        return returnValue;
    }
}
