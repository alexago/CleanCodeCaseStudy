package cleancoderscom.http;

public interface Controller {
    default String handle(ParsedRequest request) {
        return "HTTP/1.1 404 OK\n";
    }

    static String makeResponse(String content) {
        return "HTTP/1.1 200 OK\r\n" +
                "Content-Type: text/html; charset=utf-8\r\n" +
                "Transfer-Encoding: chunked\r\n" +
                //MessageFormat.format("Content-Length: {0}\n", content.length()) +
                "\r\n" +
                content;
    }
}
