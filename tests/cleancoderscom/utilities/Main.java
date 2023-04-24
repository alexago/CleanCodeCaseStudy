package cleancoderscom.utilities;

import cleancoderscom.TestSetup;
import cleancoderscom.socketserver.SocketServer;
import cleancoderscom.view.ViewTemplate;

import java.io.IOException;

public class Main {
    public static void main (String[] args) throws Exception {
//        Path absolutePath = Paths.get("resources/html/frontpage.html").toAbsolutePath();
//        System.out.println(Files.readString(absolutePath));
        System.out.println("\n[DEBUG] " + getFrontPage());
        TestSetup.setupContext();
        SocketServer server = new SocketServer(8081, socket -> {
            try {
                String frontPage = getFrontPage();
                String response = makeResponse(frontPage);
                socket.getOutputStream().write(response.getBytes());
            } catch (IOException e) {
                //e.printStackTrace();
            }
        });
        server.start();
    }

    private static String makeResponse(String content) {
        return "HTTP/1.1 200 OK\n" +
                "Content-Type: text/html; charset=utf-8\n" +
                "Transfer-Encoding: chunked\n" +
                //MessageFormat.format("Content-Length: {0}\n", content.length()) +
                "\n" +
                content;
    }

    private static String getFrontPage() {
        try {
            final ViewTemplate frontpageTemplate = ViewTemplate.create("resources/html/frontpage.html");
            final ViewTemplate codecastTemplate = ViewTemplate.create("resources/html/codecast.html");

            codecastTemplate.replace("title", "Episode 1: The Beginning");
            codecastTemplate.replace("author", "Uncle Bob");
            codecastTemplate.replace("publicationDate", "Uncle Bob");


            final String codecastView = codecastTemplate.getContent();
            frontpageTemplate.replace("codecasts", codecastView);

            return frontpageTemplate.getChunkedContent();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

}
