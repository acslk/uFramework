import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpRequest;
import u.http.Responses;
import uExt.routing.Routing;
import uExt.template.HandlebarsTemplateEngine;

import java.io.IOException;

public class RequestHandler {

    public FullHttpResponse handle(HttpRequest request, HttpContent content) {

        System.out.println(request.uri());

        HandlebarsTemplateEngine a = new HandlebarsTemplateEngine();
        String ret = "failed";
        try {
            ret = a.render("a.html", "a");
        } catch (IOException e) {
            e.printStackTrace();
        }

        //return Routing.routing(request, content);
        return Responses.ok(ret);
    }

}
