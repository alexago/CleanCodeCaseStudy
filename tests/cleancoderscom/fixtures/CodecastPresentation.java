package cleancoderscom.fixtures;

import cleancoderscom.Context;
import cleancoderscom.TestSetup;
import cleancoderscom.entities.Codecast;
import cleancoderscom.entities.License;
import cleancoderscom.entities.User;
import cleancoderscom.usecases.codecastSummaries.CodecastSummariesPresenter;
import cleancoderscom.usecases.codecastSummaries.CodecastSummariesUseCase;
import cleancoderscom.usecases.codecastSummaries.CodecastSummariesViewModel;

import java.util.ArrayList;
import java.util.List;

import static cleancoderscom.entities.License.LicenseType.DOWNLOADING;
import static cleancoderscom.entities.License.LicenseType.VIEWING;

public class CodecastPresentation {
    private CodecastSummariesUseCase useCase = new CodecastSummariesUseCase();

    public CodecastPresentation() {
        TestSetup.setupContext();
    }

    public static List<CodecastSummariesViewModel.ViewableCodecastSummary> loadViewableCodecasts() {
        User loggedInUser = Context.gateKeeper.getLoggedInUser();
        final CodecastSummariesUseCase useCase = new CodecastSummariesUseCase();
        final CodecastSummariesPresenter presenter = new CodecastSummariesPresenter();
        useCase.summarizeCodecasts(loggedInUser, presenter);
        return presenter.getViewModel().getViewableCodecasts();
    }

    public boolean addUser(String username) {
        Context.userGateway.save(new User(username));
        return true;
    }

    public boolean loginUser(String username) {
        User user = Context.userGateway.findUserByName(username);
        if (user != null) {
            Context.gateKeeper.setLoggedInUser(user);
            return true;
        } else {
            return false;
        }
    }

    public boolean createLicenseForViewing(String username, String codecastTitle) {
        License.LicenseType type = VIEWING;
        return createLicenseForType(username, codecastTitle, type);
    }

    public boolean createLicenseForDownloading(String username, String codecastTitle) {
        License.LicenseType type = DOWNLOADING;
        return createLicenseForType(username, codecastTitle, type);
    }

    private boolean createLicenseForType(String username, String codecastTitle, License.LicenseType type) {
        User user = Context.userGateway.findUserByName(username);
        Codecast codecast = Context.codecastGateway.findCodecastByTitle(codecastTitle);
        License license = new License(type, user, codecast);
        Context.licenseGateway.save(license);
        return useCase.isLicensedFor(type, user, codecast);
    }

    public String presentationUser() {
        return Context.gateKeeper.getLoggedInUser().getUserName();
    }

    public boolean clearCodecasts() {
        List<Codecast> codecasts = Context.codecastGateway.findAllCodecastsSortedChronologically();
        for (Codecast codecast : new ArrayList<Codecast>(codecasts)) {
            Context.codecastGateway.delete(codecast);
        }
        return Context.codecastGateway.findAllCodecastsSortedChronologically().size() == 0;
    }

    public int countOfCodecastsPresented() {
        return loadViewableCodecasts().size();
    }
}