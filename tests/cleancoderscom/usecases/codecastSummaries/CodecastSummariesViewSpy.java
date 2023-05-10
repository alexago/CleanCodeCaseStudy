package cleancoderscom.usecases.codecastSummaries;

public class CodecastSummariesViewSpy implements CodecastSummariesView {
    public boolean generateViewWasCalled = false;
    public CodecastSummariesViewModel viewModel;

    @Override
    public String generateView(CodecastSummariesViewModel responseModel) {
        generateViewWasCalled = true;
        this.viewModel = responseModel;
        return null;
    }
}
