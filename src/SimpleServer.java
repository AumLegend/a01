import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleServer {
    private int serverPort;
    private ServerSocket serverSocket;
    private Socket connection;
    private DataInputStream input;
    private DataOutputStream output;

    // Constructor
    public SimpleServer(int serverPort) {
        this.serverPort = serverPort;
    }

    // Method to start the server
    public void runServer() {
        try {
            serverSocket = new ServerSocket(serverPort);
            System.out.println("Server is running on port " + serverPort);

            while (true) {
                waitForConnection();
                getStreams();
                processConnection();
                closeConnection();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to wait for a connection from a client
    private void waitForConnection() throws IOException {
        System.out.println("Waiting for a connection...");
        connection = serverSocket.accept();
        System.out.println("Connection established with " + connection.getInetAddress());
    }

    // Method to set up input and output streams
    private void getStreams() throws IOException {
        input = new DataInputStream(connection.getInputStream());
        output = new DataOutputStream(connection.getOutputStream());
        System.out.println("I/O streams created");
    }

    // Method to process the connection (customize according to your needs)
    private void processConnection() throws IOException {
        String message = input.readUTF();
        System.out.println("Received message from client: " + message);

        // Process the message or perform any other necessary tasks

        // Respond to the client
        output.writeUTF("Message received successfully");
        output.flush();
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
        // Create an instance of Server with the desired port number
        SimpleServer server = new SimpleServer(1254);

        // Run the server
        server.runServer();
    }


}