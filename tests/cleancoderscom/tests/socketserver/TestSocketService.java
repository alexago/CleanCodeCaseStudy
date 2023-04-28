package cleancoderscom.tests.socketserver;

import java.io.IOException;
import java.net.Socket;

public abstract class TestSocketService implements SocketService {
    @Override
    public void serve(Socket socket) {
        try {
            doServe(socket);
            synchronized (this) {
                notify();
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    protected abstract void doServe(Socket socket) throws IOException;
}
