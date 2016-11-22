package u.http;

import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.QueryStringDecoder;

import java.util.List;
import java.util.Map;

public class Requests {

    public static String getQueryParam (HttpRequest request, String name) {
        QueryStringDecoder queryStringDecoder = new QueryStringDecoder(request.uri());
        Map<String, List<String>> params = queryStringDecoder.parameters();
        if (!params.containsKey(name))
            return null;
        if (params.get(name).size() == 0)
            return null;
        return params.get(name).get(0);
    }

}
