package cleancoderscom.usecases.codecastSummaries;

import cleancoderscom.Context;
import cleancoderscom.TestSetup;
import cleancoderscom.http.ParsedRequest;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CodecastSummariesControllerTest {

    private CodecastSummaryInputBoundarySpy useCaseSpy;
    private CodecastSummaryOutputBoundarySpy presenterSpy;
    private CodecastSummariesViewSpy viewSpy;
    private CodecastSummariesController controller;

    @Before
    public void setUp() {
        TestSetup.setupSampleData();
        useCaseSpy = new CodecastSummaryInputBoundarySpy();
        presenterSpy = new CodecastSummaryOutputBoundarySpy();
        viewSpy = new CodecastSummariesViewSpy();
        controller = new CodecastSummariesController(useCaseSpy, presenterSpy, viewSpy);
    }

    @Test
    public void testInputBoundaryInvocation() {
        ParsedRequest request = new ParsedRequest("GET", "/");
        controller.handle(request);

        assertTrue(CodecastSummaryInputBoundarySpy.summarizeCodecastCalled);
        final String loggedInUserId = Context.userGateway.findUserByName("Bob").getId();
        assertEquals(loggedInUserId, useCaseSpy.requestedUser.getId());
        assertSame(presenterSpy, useCaseSpy.presenter);
    }

    @Test
    public void controllerSendsTheViewModelToTheView() {
        presenterSpy.viewModel = new CodecastSummariesViewModel();

        ParsedRequest request = new ParsedRequest("GET", "/");
        controller.handle(request);

        assertTrue(viewSpy.generateViewWasCalled);
        assertSame(presenterSpy.viewModel, viewSpy.viewModel);
    }
}
