package cleancoderscom.socketserver;

import java.io.*;
import java.net.Socket;

public class FakeSocketService implements SocketService {
    public int connections;
    public String message;
    private Socket socket;

    @Override
    public void serve(Socket socket) {
        this.socket = socket;
        connections++;
        try {
            readMessage();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readMessage() throws IOException {
        InputStream is = socket.getInputStream();
        InputStreamReader reader = new InputStreamReader(is);
        BufferedReader bis = new BufferedReader(reader);
        message = bis.readLine();
    }
}
