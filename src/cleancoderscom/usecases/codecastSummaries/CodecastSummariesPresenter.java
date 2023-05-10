package cleancoderscom.usecases.codecastSummaries;

import java.text.SimpleDateFormat;

import static cleancoderscom.usecases.codecastSummaries.CodecastSummariesViewModel.ViewableCodecastSummary;

public class CodecastSummariesPresenter implements CodecastSummariesOutputBoundary {
    public static SimpleDateFormat dateFormat = new SimpleDateFormat("M/dd/yyyy");
    private CodecastSummariesResponseModel responseModel;
    private CodecastSummariesViewModel viewModel;

//    public static void formatSummaryFields(User loggedInUser, Codecast codecast, CodecastSummariesResponseModel cc) {
//        cc.title = codecast.getTitle();
//        cc.publicationDate = dateFormat.format(codecast.getPublicationDate());
//        cc.isViewable = CodecastSummariesUseCase.isLicensedFor(VIEWING, loggedInUser, codecast);
//        cc.isDownloadable = CodecastSummariesUseCase.isLicensedFor(DOWNLOADING, loggedInUser, codecast);
//        cc.permalink = codecast.getPermalink();
//    }

//    public static CodecastSummariesResponseModel formatCodecast(User loggedInUser, Codecast codecast) {
//        CodecastSummariesResponseModel cc = new CodecastSummariesResponseModel();
//        CodecastSummariesPresenter.formatSummaryFields(loggedInUser, codecast, cc);
//        return cc;
//    }

    @Override
    public CodecastSummariesViewModel getViewModel() {
        return viewModel;
    }

    @Override
    public void present(CodecastSummariesResponseModel responseModel) {
        viewModel = new CodecastSummariesViewModel();
        for (CodecastSummary codecastSummary : responseModel.getCodecastSummaries()) {
            viewModel.addModel(makeViewable(codecastSummary));
        }
    }

    private ViewableCodecastSummary makeViewable(CodecastSummary codecastSummary) {
        ViewableCodecastSummary summary = new ViewableCodecastSummary();
        summary.title = codecastSummary.title;
        summary.permalink = codecastSummary.permalink;
        summary.publicationDate = dateFormat.format(codecastSummary.publicationDate);
        summary.isViewable = codecastSummary.isViewable;
        summary.isDownloadable = codecastSummary.isDownloadable;
        return summary;
    }
}
