import java.net.Socket;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class Client {
    final Socket socket;
    final BufferedReader socketReader;
    final BufferedWriter socketWriter;
    final BufferedReader userInput;
    protected static String username;

    public Client(String host, int port) throws IOException {
        socket = new Socket(host, port);
        socketReader = new BufferedReader(
                new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));

        socketWriter = new BufferedWriter(
                new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));

        userInput = new BufferedReader(new InputStreamReader(System.in));
        new Thread(new Receiver()).start();
        System.out.println("Enter your name: ");
        username = userInput.readLine();
    }

    public void run() {
        System.out.println("Enter your message (send empty message to disconnect)");
        while (true) {
            String userString = null;
            try {
                userString = userInput.readLine();
            } catch (IOException ignored) {}
            if (userString == null || userString.length() == 0 || socket.isClosed()) {
                close();
                break;
            } else {
                try {
                    socketWriter.write(username + ": " + userString);
                    socketWriter.write("\n");
                    socketWriter.flush();
                } catch (IOException e) {
                    close();
                }
            }
        }
    }

    public synchronized void close() {
        if (!socket.isClosed()) {
            try {
                socket.close();
                System.exit(0);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args)  {
        try {
            Client client = new Client("localhost", 45000);
            client.run();
        } catch (IOException e) {
            System.out.println("Server is not running");
        }
    }

    private class Receiver implements Runnable{
        public void run() {
            while (!socket.isClosed()) {
                String line = null;
                try {
                    line = socketReader.readLine();
                } catch (IOException e) {
                    if ("Socket closed".equals(e.getMessage())) {
                        break;
                    }
                    System.out.println("Connection Lost");
                    close();
                }
                if (line == null) {
                    System.out.println("Disconnected");
                    close();
                } else {
                    if (line.split(":")[0].equals(username)) {
                        System.out.println("You:" + line.split(":")[1]);
                    }
                    else {
                        System.out.println(line);
                    }
                }
            }
        }
    }
}