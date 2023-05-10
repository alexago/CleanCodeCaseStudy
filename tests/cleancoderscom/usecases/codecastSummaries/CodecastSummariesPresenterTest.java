package cleancoderscom.usecases.codecastSummaries;

import cleancoderscom.usecases.codecastSummaries.CodecastSummariesViewModel.ViewableCodecastSummary;
import org.junit.Test;

import java.util.GregorianCalendar;

import static org.junit.Assert.assertEquals;

public class CodecastSummariesPresenterTest {

    @Test
    public void validateViewModel() {
        CodecastSummariesResponseModel model = new CodecastSummariesResponseModel();
        CodecastSummary summary = new CodecastSummary();
        summary.title = "Title";
        summary.publicationDate = new GregorianCalendar(2015, 10, 3).getTime();
        summary.permalink = "permalink";
        summary.isViewable = true;
        summary.isDownloadable = true;
        model.addCodecastSummary(summary);

        CodecastSummariesPresenter presenter = new CodecastSummariesPresenter();
        presenter.present(model);

        CodecastSummariesViewModel viewModel = presenter.getViewModel();
        ViewableCodecastSummary viewableSummary = viewModel.viewableCodecastSummaries.get(0);
        assertEquals(summary.title, viewableSummary.title);
    }
}
