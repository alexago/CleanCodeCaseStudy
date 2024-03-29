package cleancoderscom.utilities;

import cleancoderscom.TestSetup;
import cleancoderscom.http.Controller;
import cleancoderscom.http.ParsedRequest;
import cleancoderscom.http.RequestParser;
import cleancoderscom.http.Router;
import cleancoderscom.socketserver.SocketServer;
import cleancoderscom.usecases.codecastSummaries.CodecastSummariesController;
import cleancoderscom.usecases.codecastSummaries.CodecastSummariesPresenter;
import cleancoderscom.usecases.codecastSummaries.CodecastSummariesUseCase;
import cleancoderscom.usecases.codecastSummaries.CodecastSummariesViewImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main (String[] args) throws Exception {
        Router router = new Router();
        //TODO: replace null with the object. Added to pass compilation
        CodecastSummariesPresenter presenter = new CodecastSummariesPresenter();
        CodecastSummariesViewImpl view = new CodecastSummariesViewImpl();
        CodecastSummariesUseCase useCase = new CodecastSummariesUseCase();
        router.addPath("", new CodecastSummariesController(useCase, presenter, view));
        router.addPath("episode", new CodecastDetailsController());

        TestSetup.setupSampleData();
        SocketServer server = new SocketServer(8888, socket -> {
            try {
                StringBuilder textBuilder = new StringBuilder();
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                ParsedRequest request = new RequestParser().parse(reader.readLine());
                String response = router.route(request);
                socket.getOutputStream().write(response.getBytes(), 0 , response.getBytes().length);

                //socket.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        });
        server.start();
    }

    static class CodecastDetailsController implements Controller {
    }



}
