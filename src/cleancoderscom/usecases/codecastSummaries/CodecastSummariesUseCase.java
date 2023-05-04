package cleancoderscom.usecases.codecastSummaries;

import cleancoderscom.Context;
import cleancoderscom.entities.Codecast;
import cleancoderscom.entities.License;
import cleancoderscom.entities.User;

import java.util.ArrayList;
import java.util.List;

public class CodecastSummariesUseCase {

    public List<PresentableCodecastSummary> presentCodecasts(User loggedInUser) {
        ArrayList<PresentableCodecastSummary> presentableCodecasts = new ArrayList<PresentableCodecastSummary>();
        List<Codecast> allCodecasts = Context.codecastGateway.findAllCodecastsSortedChronologically();

        for (Codecast codecast : allCodecasts)
            presentableCodecasts.add(CodecastSummariesPresenter.formatCodecast(loggedInUser, codecast));

        return presentableCodecasts;
    }

    public static boolean isLicensedFor(License.LicenseType licenseType, User user, Codecast codecast) {
        List<License> licenses = Context.licenseGateway.findLicensesForUserAndCodecast(user, codecast);
        for (License l : licenses) {
            if (l.getType() == licenseType)
                return true;
        }
        return false;
    }

}

