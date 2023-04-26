package cleancoderscom.utilities;

import cleancoderscom.*;
import cleancoderscom.socketserver.SocketServer;
import cleancoderscom.view.ViewTemplate;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main (String[] args) throws Exception {
        TestSetup.setupSampleData();
        SocketServer server = new SocketServer(8081, socket -> {
            try {
                String frontPage = getFrontPage();;
                String response = makeResponse(frontPage);
//                System.out.println("[DEBUG] Size with chunk data: " + frontPage.length());
//                System.out.println(frontPage)
                socket.getOutputStream().write(response.getBytes(), 0 , response.getBytes().length);
                socket.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
                //e.printStackTrace();
            }
        });
        server.start();
    }

    private static String makeResponse(String content) {
        return "HTTP/1.1 200 OK\r\n" +
                "Content-Type: text/html; charset=utf-8\r\n" +
                "Transfer-Encoding: chunked\r\n" +
                //MessageFormat.format("Content-Length: {0}\n", content.length()) +
                "\r\n" +
                content;
    }

    private static String getFrontPage() {
        PresentCodecastUseCase useCase = new PresentCodecastUseCase();
        TestSetup.setupSampleData();
        User bob = Context.userGateway.findUserByName("Bob");
        List<PresentableCodecast> presentableCodecasts =  useCase.presentCodecasts(bob);

        try {
            final ViewTemplate frontpageTemplate = ViewTemplate.create("resources/html/frontpage.html");
            final StringBuilder codecastLines = new StringBuilder();
            for (PresentableCodecast presentableCodecast : presentableCodecasts) {
                final ViewTemplate codecastTemplate = ViewTemplate.create("resources/html/codecast.html");

                codecastTemplate.replace("title", presentableCodecast.title);
                codecastTemplate.replace("author","Uncle Bob");
                codecastTemplate.replace("publicationDate", presentableCodecast.publicationDate);
                codecastTemplate.replace("duration","57 min.");
                codecastTemplate.replace("thumbnail", "https://web.archive.org/web/20140603043802im_/https://d26o5k45lnmm4v.cloudfront.net/YmluYXJ5OjIxNzA1Nw");
                codecastTemplate.replace("contentActions","Buying options go here.");
                codecastTemplate.replace("licenseOptions","License options go here.");

                codecastLines.append(codecastTemplate.getContent());
            }

            frontpageTemplate.replace("codecasts", codecastLines.toString());

            return frontpageTemplate.getChunkedContent();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

}
