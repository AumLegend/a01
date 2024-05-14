import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class SimpleClient {
    private int serverPort;
    private String hostServer;
    private Socket connection;
    private DataInputStream input;
    private DataOutputStream output;

    // Constructors
    public SimpleClient(String hostServer) {
        this.hostServer = hostServer;
        this.serverPort = 1254; // Assuming default port
    }

    public SimpleClient(String hostServer, int serverPort) {
        this.hostServer = hostServer;
        this.serverPort = serverPort;
    }

    // Method to start the client
    public void runClient() {
        try {
            connectToServer();
            getStream();
            processConnection();
            closeConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to connect to the server
    private void connectToServer() throws IOException {
        System.out.println("Connecting to server...");
        connection = new Socket(hostServer, serverPort);
        System.out.println("Connected to server at " + hostServer + ":" + serverPort);
    }

    // Method to set up input and output streams
    private void getStream() throws IOException {
        input = new DataInputStream(connection.getInputStream());
        output = new DataOutputStream(connection.getOutputStream());
        System.out.println("I/O streams created");
    }

    // Method to process the connection
    private void processConnection() throws IOException {
        Scanner scanner = new Scanner(System.in);

        // Continue communication until user enters "stop" or "quit"
        while (true) {
            System.out.print("Enter a message to send to the server (type 'stop' to quit): ");
            String message = scanner.nextLine();

            // Send the message to the server
            output.writeUTF(message);

            if ("stop".equalsIgnoreCase(message) || "quit".equalsIgnoreCase(message)) {
                System.out.println("Client has requested to stop. Closing connection...");
                break;
            }

            // Receive a response from the server
            String response = input.readUTF();
            System.out.println("Received response from server: " + response);
        }

        scanner.close();
    }

    // Method to close the connection and resources
    private void closeConnection() {
        try {
            System.out.println("Closing connection");
            input.close();
            output.close();
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Create an instance of SimpleClient with the server's IP address or hostname
        SimpleClient simpleClient = new SimpleClient("localhost"); // Change to the actual server's address

        // Run the client
        simpleClient.runClient();
    }
}