package cleancoderscom.gateways;

import cleancoderscom.entities.Codecast;
import cleancoderscom.entities.License;
import cleancoderscom.entities.User;

import java.util.List;

public interface LicenseGateway {
    List<License> findLicensesForUserAndCodecast(User user, Codecast codecast);

    License save(License viewLicense);
}
