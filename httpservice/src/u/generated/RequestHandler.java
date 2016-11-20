package u.generated;

import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpRequest;
import u.http.Responses;

// to be overridden by user defined class

public class RequestHandler {

    public FullHttpResponse handle(HttpRequest request, HttpContent content) {
        return Responses.ok("generated default");
    }

}
