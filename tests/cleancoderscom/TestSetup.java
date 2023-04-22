package cleancoderscom;

import cleancoderscom.doubles.InMemoryCodecastGateway;
import cleancoderscom.doubles.InMemoryLicenseGateway;
import cleancoderscom.doubles.InMemoryUserGateway;

public class TestSetup {
    public static void setupContext() {
        Context.userGateway = new InMemoryUserGateway();
        Context.licenseGateway = new InMemoryLicenseGateway();
        Context.codecastGateway = new InMemoryCodecastGateway();
        Context.gateKeeper = new GateKeeper();
    }
}
