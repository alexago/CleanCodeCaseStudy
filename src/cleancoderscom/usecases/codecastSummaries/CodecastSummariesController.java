package cleancoderscom.usecases.codecastSummaries;

import cleancoderscom.Context;
import cleancoderscom.entities.User;
import cleancoderscom.http.Controller;
import cleancoderscom.http.ParsedRequest;

public class CodecastSummariesController implements Controller {
    @Override
    public String handle(ParsedRequest request) {
        CodecastSummariesUseCase useCase = new CodecastSummariesUseCase();
        User bob = Context.userGateway.findUserByName("Bob");
        CodecastSummariesView view = new CodecastSummariesView();
        String html = view.toHTML(useCase.presentCodecasts(bob));
        return Controller.makeResponse(html);
    }
}
