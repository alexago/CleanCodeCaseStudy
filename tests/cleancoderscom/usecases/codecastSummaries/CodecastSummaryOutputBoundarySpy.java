package cleancoderscom.usecases.codecastSummaries;

public class CodecastSummaryOutputBoundarySpy implements CodecastSummariesOutputBoundary {
    public CodecastSummariesViewModel viewModel;
    public CodecastSummariesResponseModel responseModel;

    @Override
    public CodecastSummariesViewModel getViewModel() {
        return viewModel;
    }

    @Override
    public void present(CodecastSummariesResponseModel responseModel) {
        this.responseModel = responseModel;
    }
}
