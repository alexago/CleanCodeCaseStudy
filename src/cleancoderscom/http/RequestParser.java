package cleancoderscom.http;

public class RequestParser {
    public ParsedRequest parse(String requestString) {
        if (requestString == null) {
            return new ParsedRequest();
        }

        ParsedRequest request = new ParsedRequest();
        String[] parts = requestString.split(" ");
        request.method = parts[0];
        if (parts.length > 1) {
            request.path = parts[1];
        }
        return request;
    }
}
