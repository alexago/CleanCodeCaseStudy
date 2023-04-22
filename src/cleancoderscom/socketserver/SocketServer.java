package cleancoderscom.socketserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SocketServer {
    private final SocketService service;
    private final int port;
    private boolean running;
    private ServerSocket serverSocket;
    private ExecutorService executor;

    public SocketServer(int port, SocketService service) {
        this.port = port;
        this.service = service;
        executor = Executors.newFixedThreadPool(4);
    }

    public long getPort() {
        return port;
    }

    public SocketService getService() {
        return service;
    }

    public void stop() throws Exception {
        // Should be called in the order it is called
        running = false;
        serverSocket.close();
        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.MILLISECONDS);
    }

    public void start() throws IOException {
        serverSocket = new ServerSocket(port);
        Runnable connectionHandler =  new Runnable() {
            @Override
            public void run() {
                try {
                    while (running) {
                        Socket serviceSocket = serverSocket.accept();
                        executor.execute(() -> service.serve(serviceSocket));
                    }
                } catch (IOException e) {
                    if (running) {
                        e.printStackTrace();
                    }
                }
            }
        };
        executor.submit(connectionHandler);

        this.running = true;
    }

    public boolean isRunning() {
        return running;
    }

}
