package cleancoderscom.usecases.codecastDetails;

import cleancoderscom.Context;
import cleancoderscom.entities.Codecast;
import cleancoderscom.entities.User;
import cleancoderscom.usecases.codecastSummaries.CodecastSummariesPresenter;

public class CodecastDetailsUseCase {
    public PresentableCodecastDetails requestCodecastDetails(User loggedInUser, String permalink) {
        PresentableCodecastDetails details = new PresentableCodecastDetails();
        Codecast codecast = Context.codecastGateway.findCodecastByPermalink(permalink);
        details.wasFound = false;
        if (codecast != null) {
            details.wasFound = true;
            CodecastSummariesPresenter.formatSummaryFields(loggedInUser, codecast, details);
        }
        return details;
    }
}
