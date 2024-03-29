package cleancoderscom.usecases.codecastDetails;

import cleancoderscom.Context;
import cleancoderscom.TestSetup;
import cleancoderscom.entities.Codecast;
import cleancoderscom.entities.User;
import cleancoderscom.usecases.codecastSummaries.CodecastSummariesPresenter;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.text.ParseException;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class CodecastDetailsUseCaseTest {

    private User user;

    @Before
    public void setUp() {
        TestSetup.setupContext();
        user = Context.userGateway.save(new User("User"));
    }
    //TODO: Start here. Get this passing
    @Test
    @Ignore
    public void createsCodecastDetailsPresentation() throws ParseException {
        final Codecast codecast = new Codecast();
        codecast.setId("0");
        codecast.setTitle("Codecast");
        codecast.setPermalink("permalink-a");
        final Date date = CodecastSummariesPresenter.dateFormat.parse("1/2/2019");
        codecast.setPublicationDate(date);
        Context.codecastGateway.save(codecast);

        PresentableCodecastDetails details = new CodecastDetailsUseCase().requestCodecastDetails(user, "permalink-a");

//        assertEquals("Codecast", details.title);
//        assertEquals("1/02/2019", details.publicationDate);
    }

    @Test
    public void doesentCrashOnMissingCodecast() {
        CodecastDetailsUseCase useCase = new CodecastDetailsUseCase();
        PresentableCodecastDetails details = useCase.requestCodecastDetails(user, "missing");

        assertEquals(false, details.wasFound);
    }
}
