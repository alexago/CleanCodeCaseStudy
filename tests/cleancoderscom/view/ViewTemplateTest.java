package cleancoderscom.view;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ViewTemplateTest {
    @Test
    public void noReplacement() {
        ViewTemplate template = new ViewTemplate("some static content");

        assertEquals("some static content", template.getContent());
    }

    @Test
    public void noReplacementChunked() {
        ViewTemplate template = new ViewTemplate("some static content");

        assertEquals("13\r\n" +
                "some static content\r\n" +
                "0\r\n\r\n", template.getChunkedContent());
    }

    @Test
    public void simpleReplacement() {
        ViewTemplate template = new ViewTemplate("some ${tag} content");
        template.replace("tag", "replacement");

        assertEquals("some replacement content", template.getContent());
    }

    @Test
    public void simpleReplacementChunked() {
        ViewTemplate template = new ViewTemplate("some ${tag} content");
        template.replace("tag", "replacement");

        assertEquals("18\r\n" +
                "some replacement content\r\n" +
                "0\r\n\r\n", template.getChunkedContent());
    }
}
