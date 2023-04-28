package cleancoderscom;

public class CodecastDetailsUseCase {
    public PresentableCodecastDetails requestCodecastDetails(User loggedInUser, String permalink) {
        PresentableCodecastDetails details = new PresentableCodecastDetails();
        Codecast codecast = Context.codecastGateway.findCodecastByPermalink(permalink);
        details.wasFound = false;
        if (codecast != null) {
            details.wasFound = true;
            CodecastSummaryUseCase.formatSummaryFields(loggedInUser, codecast, details);
        }
        return details;
    }
}
