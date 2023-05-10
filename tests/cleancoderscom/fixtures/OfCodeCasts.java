package cleancoderscom.fixtures;

import cleancoderscom.Context;
import cleancoderscom.entities.User;
import cleancoderscom.usecases.codecastSummaries.CodecastSummariesPresenter;
import cleancoderscom.usecases.codecastSummaries.CodecastSummariesUseCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static cleancoderscom.usecases.codecastSummaries.CodecastSummariesViewModel.*;

//OrderedQuery
public class OfCodeCasts {
    private List<Object> list(Object... objects) {
        return Arrays.asList(objects);
    }

    public List<Object> query() {
        User loggedInUser = Context.gateKeeper.getLoggedInUser();
        final CodecastSummariesUseCase useCase = new CodecastSummariesUseCase();
        final CodecastSummariesPresenter presenter = new CodecastSummariesPresenter();
        useCase.summarizeCodecasts(loggedInUser, presenter);
        List<Object> queryResponse = new ArrayList<Object>();
        for (ViewableCodecastSummary summary : presenter.getViewModel().getViewableCodecasts())
            queryResponse.add(makeRow(summary));
        return queryResponse;

    }

    private List<Object> makeRow(ViewableCodecastSummary summary) {
        return list(
                list("title", summary.title),
                list("publication date", summary.publicationDate),
                list("picture", summary.title),
                list("description", summary.title),
                list("viewable", summary.isViewable ? "+" : "-"),
                list("downloadable", summary.isDownloadable ? "+" : "-"));
    }

}