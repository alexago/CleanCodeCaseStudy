package cleancoderscom.utilities;

import cleancoderscom.TestSetup;
import cleancoderscom.socketserver.SocketServer;

import java.io.IOException;
import java.text.MessageFormat;

public class Main {
    public static void main (String[] args) throws Exception {
        TestSetup.setupContext();
        SocketServer server = new SocketServer(8080, socket -> {
            try {
                String frontPage = getFrontPage();
                String response = makeResponse(frontPage);
                socket.getOutputStream().write(response.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        server.start();
    }

    private static String makeResponse(String content) {
        return "HTTP/1.1 200 OK\n" +
                MessageFormat.format("Content-Length: {0}\n", content.length()) +
                "\n" +
                content;
    }

    private static String getFrontPage() {
        return "<h1>Pumpkin</h1>";
    }
}
