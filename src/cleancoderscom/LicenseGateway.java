package cleancoderscom;

import java.util.List;

public interface LicenseGateway {
    List<License> findLicensesForUserAndCodecast(User user, Codecast codecast);

    License save(License viewLicense);
}
