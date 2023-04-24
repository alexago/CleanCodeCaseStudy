package cleancoderscom.view;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ViewTemplate {
    private String template;

    public ViewTemplate(String template) {
        this.template = template;
    }

    public static ViewTemplate create(String templateResource) throws IOException {
        Path pagePath = Paths.get(templateResource).toAbsolutePath();
        return new ViewTemplate(new String(Files.readAllBytes(pagePath)));
    }

    public void replace(String tagName, String content) {
        template = template.replace("${"+tagName+"}", content);
    }

    public String getContent() {
        return template;
    }

    public String getChunkedContent() {
        final List<String> pageLines = new ArrayList<>();
        this.template.lines().forEach(s -> pageLines.add(s));
        return linesToChunks(pageLines);
    }

    private static String linesToChunks(List<String> allLines) {
        final var stringBuilder = new StringBuilder();
//        int totalLength = 0;
//        int totalLineNumber = 0;
        for (String line : allLines) {
            final String noEol = line.stripTrailing();
            final String noSpace = noEol.strip();
            final int length = noSpace.length();
            if (length == 0) continue;
//            totalLength += length;
//            totalLineNumber += 2;
            stringBuilder.append(String.format("%X", length) + "\r\n");
            stringBuilder.append(noSpace + "\r\n");
        }
        stringBuilder.append(0 + "\r\n");
        stringBuilder.append("\r\n");
//        totalLineNumber += 2;
//        System.out.println("[DEBUG] Total length : " + totalLength);
//        System.out.println("[DEBUG] Total line number : " + totalLineNumber);
        return stringBuilder.toString();
    }
}
