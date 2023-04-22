package cleancoderscom.doubles;

import cleancoderscom.Codecast;
import cleancoderscom.License;
import cleancoderscom.LicenseGateway;
import cleancoderscom.User;

import java.util.ArrayList;
import java.util.List;

public class InMemoryLicenseGateway extends GatewayUtilities<License> implements LicenseGateway {
    @Override
    public List<License> findLicensesForUserAndCodecast(User user, Codecast codecast) {
        List<License> results = new ArrayList<License>();
        for (License license : getEntities()) {
            if (license.getUser().isSame(user) && license.getCodecast().isSame(codecast))
                results.add(license);
        }
        return results;
    }
}
