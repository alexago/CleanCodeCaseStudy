package cleancoderscom.usecases.codecastSummaries;

import cleancoderscom.entities.User;

public class CodecastSummaryInputBoundarySpy implements CodecastSummariesInputBoundary {
    public static boolean summarizeCodecastCalled = false;
    public User requestedUser;
    public CodecastSummariesOutputBoundary presenter;

    @Override
    public void summarizeCodecasts(User loggedInUser, CodecastSummariesOutputBoundary presenter) {
        summarizeCodecastCalled = true;
        requestedUser = loggedInUser;
        this.presenter = presenter;
    }
}
