package cleancoderscom.usecases.codecastSummaries;

import java.util.ArrayList;
import java.util.List;

public class CodecastSummariesViewModel {

    public ArrayList<ViewableCodecastSummary> viewableCodecastSummaries = new ArrayList<>();;

    public void addModel(ViewableCodecastSummary summary) {
        viewableCodecastSummaries.add(summary);
    }

    public List<ViewableCodecastSummary> getViewableCodecasts() {
        return viewableCodecastSummaries;
    }

    public static class ViewableCodecastSummary {

        public String title;
        public String permalink;
        public String publicationDate;
        public Boolean isViewable;
        public Boolean isDownloadable;
    }
}
