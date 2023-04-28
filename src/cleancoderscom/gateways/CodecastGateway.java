package cleancoderscom.gateways;

import cleancoderscom.entities.Codecast;

import java.util.List;

public interface CodecastGateway {
    List<Codecast> findAllCodecastsSortedChronologically();

    Codecast findCodecastByTitle(String codecastTitle);

    Codecast save(Codecast codecast);

    void delete(Codecast codecast);

    Codecast findCodecastByPermalink(String permalink);
}