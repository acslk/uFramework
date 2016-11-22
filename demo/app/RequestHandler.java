import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpRequest;
import uExt.routing.Routing;
import uExt.statics.Statics;
import uExt.template.HandlebarsTemplateEngine;
import uExt.template.Template;

public class RequestHandler {

    public RequestHandler () {
        Template.use(new HandlebarsTemplateEngine());
    }

    public FullHttpResponse handle(HttpRequest request, HttpContent content) {
        FullHttpResponse response;
        response = Statics.get(request);
        if (response != null)
            return response;

        return Routing.route(request, content);
    }

}
