package cleancoderscom.utilities;

import cleancoderscom.Context;
import cleancoderscom.TestSetup;
import cleancoderscom.entities.User;
import cleancoderscom.http.Controller;
import cleancoderscom.http.ParsedRequest;
import cleancoderscom.http.RequestParser;
import cleancoderscom.http.Router;
import cleancoderscom.socketserver.SocketServer;
import cleancoderscom.usecases.codecastSummaries.CodecastSummariesUseCase;
import cleancoderscom.usecases.codecastSummaries.PresentableCodecastSummary;
import cleancoderscom.view.ViewTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class Main {
    public static void main (String[] args) throws Exception {
        Router router = new Router();
        router.addPath("", new CodecastSummariesController());
        router.addPath("episode", new CodecastDetailsController());

        TestSetup.setupSampleData();
        SocketServer server = new SocketServer(8081, socket -> {
            try {
                StringBuilder textBuilder = new StringBuilder();
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                ParsedRequest request = new RequestParser().parse(reader.readLine());
                String response = router.route(request);

//                String frontPage = getFrontPage();;
//                String response = makeResponse(frontPage);
//                System.out.println("[DEBUG] Size with chunk data: " + frontPage.length());
//                System.out.println(frontPage)
                if (response == null) {
                    socket.getOutputStream().write("HTTP/1.1 404 OK\n".getBytes());
                } else {
                    socket.getOutputStream().write(response.getBytes(), 0 , response.getBytes().length);
                }

                socket.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
                //e.printStackTrace();
            }
        });
        server.start();
    }

    static class CodecastSummariesController implements Controller {
        @Override
        public String handle(ParsedRequest request) {
            String frontPage = getFrontPage();;
            return makeResponse(frontPage);
        }
    }

    static class CodecastDetailsController implements Controller {

        @Override
        public String handle(ParsedRequest request) {
            return null;
        }
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
        CodecastSummariesUseCase useCase = new CodecastSummariesUseCase();
        TestSetup.setupSampleData();
        User bob = Context.userGateway.findUserByName("Bob");
        List<PresentableCodecastSummary> presentableCodecasts =  useCase.presentCodecasts(bob);

        try {
            final ViewTemplate frontpageTemplate = ViewTemplate.create("resources/html/frontpage.html");
            final StringBuilder codecastLines = new StringBuilder();
            for (PresentableCodecastSummary presentableCodecast : presentableCodecasts) {
                final ViewTemplate codecastTemplate = ViewTemplate.create("resources/html/codecast.html");

                codecastTemplate.replace("title", presentableCodecast.title);
                codecastTemplate.replace("author","Uncle Bob");
                codecastTemplate.replace("publicationDate", presentableCodecast.publicationDate);
                codecastTemplate.replace("duration","57 min.");
                codecastTemplate.replace("thumbnail", "https://web.archive.org/web/20140603043802im_/https://d26o5k45lnmm4v.cloudfront.net/YmluYXJ5OjIxNzA1Nw");
                codecastTemplate.replace("contentActions","Buying options go here.");
                codecastTemplate.replace("licenseOptions","License options go here.");
                codecastTemplate.replace("permalink", presentableCodecast.permalink);
                codecastLines.append(codecastTemplate.getContent());
            }

            frontpageTemplate.replace("codecasts", codecastLines.toString());

            return frontpageTemplate.getChunkedContent();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

}
