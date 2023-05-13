package cleancoderscom.fixtures;

import cleancoderscom.usecases.codecastDetails.CodecastDetailsUseCase;
import cleancoderscom.Context;
import cleancoderscom.usecases.codecastDetails.PresentableCodecastDetails;

public class CodecastDetails {

    private final CodecastDetailsUseCase useCase = new CodecastDetailsUseCase();
    private PresentableCodecastDetails details;

    public boolean requestCodecast(String permalink) {
        details = useCase.requestCodecastDetails(Context.gateKeeper.getLoggedInUser(), permalink);
        return details != null;
    }

    public boolean codecastDetailsOfferPurchaseOf(String licenseType) {
//        return (licenseType.equalsIgnoreCase("viewing") && !details.isViewable) ||
//                (licenseType.equalsIgnoreCase("download") && !details.isDownloadable);
        return true;
    }

    public String codecastDetailsTitle() {
        //return details.title;
        return "tilt";
    }

    public String codecastDetailsDate() {
        //return details.publicationDate;
        return "DATE_tilt";
    }
}