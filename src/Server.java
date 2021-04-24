import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Server {
    private final ServerSocket serverSocket;
    private Thread serverThread;
    private final int port;
    BlockingQueue<SocketProcessor> queue = new LinkedBlockingQueue<>();

    public Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        this.port = port;
    }

    void run() {
        System.out.println("Server started on port " + port + "\nMessages in session:\n");
        serverThread = Thread.currentThread();
        while (true) {
            Socket s = getNewConn();
            if (serverThread.isInterrupted()) {
                break;
            } else if (s != null){
                try {
                    final SocketProcessor processor = new SocketProcessor(s);
                    final Thread thread = new Thread(processor);
                    thread.setDaemon(true);
                    thread.start();
                    queue.offer(processor);
                }
                catch (IOException ignored) {}
            }
        }
    }

    private Socket getNewConn() {
        Socket s = null;
        try {
            s = serverSocket.accept();
        } catch (IOException e) {
            shutdownServer();
        }
        return s;
    }

    private synchronized void shutdownServer() {
        for (SocketProcessor s: queue) {
            s.close();
        }
        if (!serverSocket.isClosed()) {
            try {
                serverSocket.close();
            } catch (IOException ignored) {}
        }
    }

    public static void main(String[] args) throws IOException {
        new Server(45000).run();
    }

    private class SocketProcessor implements Runnable{
        Socket socket;
        BufferedReader reader;
        BufferedWriter writer;

        SocketProcessor(Socket socketParam) throws IOException {
            socket = socketParam;
            reader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            writer = new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8) );
        }

        public void run() {
            while (!socket.isClosed()) {
                String line = null;
                try {
                    line = reader.readLine();
                } catch (IOException e) {
                    close();
                }

                if (line == null) {
                    close();
                } else if ("shutdown".equals(line)) {
                    serverThread.interrupt();
                    try {
                        new Socket("localhost", port);
                    } catch (IOException ignored) {
                    } finally {
                        shutdownServer();
                    }
                } else {
                    System.out.println(line);
                    for (SocketProcessor sp: queue) {
                        sp.send(line);
                    }
                }
            }
        }

        public synchronized void send(String line) {
            try {
                writer.write(line);
                writer.write("\n");
                writer.flush();
            } catch (IOException e) {
                close();
            }
        }

        public synchronized void close() {
            queue.remove(this);
            if (!socket.isClosed()) {
                try {
                    socket.close();
                } catch (IOException ignored) {}
            }
        }

        @Override
        protected void finalize() throws Throwable {
            super.finalize();
            close();
        }
    }
}