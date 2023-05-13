package cleancoderscom.usecases.codecastSummaries;

import cleancoderscom.Context;
import cleancoderscom.entities.User;
import cleancoderscom.http.Controller;
import cleancoderscom.http.ParsedRequest;

public class CodecastSummariesController implements Controller {
    private final CodecastSummariesInputBoundary useCase;
    private final CodecastSummariesOutputBoundary presenter;
    private final CodecastSummariesView view;

    public CodecastSummariesController(CodecastSummariesInputBoundary useCase,
                                       CodecastSummariesOutputBoundary presenter,
                                       CodecastSummariesView view) {
        this.useCase = useCase;
        this.presenter = presenter;
        this.view = view;
    }

    @Override
    public String handle(ParsedRequest request) {
        final User user = Context.gateKeeper.getLoggedInUser();
        useCase.summarizeCodecasts(user, presenter);
        String content = view.generateView(presenter.getViewModel());
        return Controller.makeResponse(content);
    }
}
