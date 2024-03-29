package cleancoderscom.usecases.codecastSummaries;

import cleancoderscom.view.ViewTemplate;

import java.io.IOException;
import java.util.List;

public class CodecastSummariesViewImpl implements CodecastSummariesView {
    public String toHTML(List<CodecastSummariesViewModel.ViewableCodecastSummary> presentableCodecasts) {
        try {
            final ViewTemplate frontpageTemplate = ViewTemplate.create("resources/html/frontpage.html");
            final StringBuilder codecastLines = new StringBuilder();
            for (CodecastSummariesViewModel.ViewableCodecastSummary viewableCodecastSummary : presentableCodecasts) {
                final ViewTemplate codecastTemplate = ViewTemplate.create("resources/html/codecast.html");

                codecastTemplate.replace("title", viewableCodecastSummary.title);
                codecastTemplate.replace("author", "Uncle Bob");
                codecastTemplate.replace("publicationDate", viewableCodecastSummary.publicationDate);
                codecastTemplate.replace("duration", "57 min.");
                codecastTemplate.replace("thumbnail", "https://web.archive.org/web/20140603043802im_/https://d26o5k45lnmm4v.cloudfront.net/YmluYXJ5OjIxNzA1Nw");
                codecastTemplate.replace("contentActions", "Buying options go here.");
                codecastTemplate.replace("licenseOptions", "License options go here.");
                codecastTemplate.replace("permalink", viewableCodecastSummary.permalink);
                codecastLines.append(codecastTemplate.getContent());
            }

            frontpageTemplate.replace("codecasts", codecastLines.toString());

            return frontpageTemplate.getChunkedContent();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    public String generateView(CodecastSummariesViewModel responseModel) {
        return toHTML(responseModel.getViewableCodecasts());
    }
}
