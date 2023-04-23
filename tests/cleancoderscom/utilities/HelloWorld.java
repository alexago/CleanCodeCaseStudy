package cleancoderscom.utilities;

import cleancoderscom.socketserver.SocketServer;
import cleancoderscom.socketserver.SocketService;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.text.MessageFormat;

public class HelloWorld implements SocketService {
    public static void main(String[] args) throws Exception {
        SocketServer server = new SocketServer(8022, new HelloWorld());
        server.start();
    }

    @Override
    public void serve(Socket socket) {
        try {
            OutputStream os = socket.getOutputStream();
            final String content = getContent();

            String response = "HTTP/1.1 200 OK\n" +
                    MessageFormat.format("Content-Length: {0}\n", content.length()) +
                    "\n"+
                    content;
            os.write(response.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private static String getContent() {
        return "<h1>Hello, world</h1></br><h2>I am a server!</h2>";
    }
}
