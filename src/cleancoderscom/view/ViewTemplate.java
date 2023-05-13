package cleancoderscom.view;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
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

    public String getChunkedContent_() {
        final List<String> pageLines = new ArrayList<>();
        this.template.lines().forEach(s -> pageLines.add(s.strip().replaceAll("\r\n", "").replaceAll("\n", "")));
        return linesToChunks(pageLines);
    }

    public String getChunkedContent() {
        int chunkSize = 900;
        template = template.replaceAll("\r\n", "");
        template = template.replaceAll("\n", "");
        List<String> chunks = Arrays.stream(template.split("(?<=\\G.{" + chunkSize + "})")).toList();
        return linesToChunks(chunks);
    }


    private static String linesToChunks(List<String> allLines) {
        final var stringBuilder = new StringBuilder();
        for (String line : allLines) {
            final String noEol = line.stripTrailing();
            final String noSpace = noEol.strip();
            final int length = noSpace.length();
            if (length == 0) continue;
            stringBuilder.append(String.format("%X", length) + "\r\n");
            stringBuilder.append(noSpace + "\r\n");
        }
        stringBuilder.append(0 + "\r\n");
        stringBuilder.append("\r\n");
        return stringBuilder.toString();
    }
}
