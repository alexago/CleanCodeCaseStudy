package cleancoderscom.http;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RequestParserTest {
    @Test
    public void emptyRequest() throws Exception {
        RequestParser parser = new RequestParser();
        ParsedRequest parsed = parser.parse("");

        assertEquals("", parsed.method);
        assertEquals("", parsed.path);
    }

    @Test
    public void nullRequest() throws Exception {
        RequestParser parser = new RequestParser();
        ParsedRequest parsed = parser.parse(null);

        assertEquals("", parsed.method);
        assertEquals("", parsed.path);
    }

    @Test
    public void nonEmptyRequest() throws   Exception {
        RequestParser parser = new RequestParser();
        ParsedRequest parsed = parser.parse("GET /some/path HTTP/1.1");

        assertEquals("GET", parsed.method);
        assertEquals("/some/path", parsed.path);
    }

    @Test
    public void partialRequest() throws   Exception {
        RequestParser parser = new RequestParser();
        ParsedRequest parsed = parser.parse("GET");

        assertEquals("GET", parsed.method);
        assertEquals("", parsed.path);
    }
}
