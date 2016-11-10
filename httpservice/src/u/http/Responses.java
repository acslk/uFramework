package u.http;

import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.util.CharsetUtil;

import static io.netty.handler.codec.http.HttpResponseStatus.BAD_REQUEST;
import static io.netty.handler.codec.http.HttpResponseStatus.NOT_FOUND;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

public class Responses {

    private static FullHttpResponse response(HttpResponseStatus status) {
        return new DefaultFullHttpResponse(HTTP_1_1, status);
    }

    private static FullHttpResponse response(HttpResponseStatus status, String msg) {
        return new DefaultFullHttpResponse(HTTP_1_1, status, Unpooled.copiedBuffer(msg, CharsetUtil.UTF_8));
    }

    public static FullHttpResponse ok() {
        return response(OK);
    }

    public static FullHttpResponse ok(String msg) {
        return response(OK, msg);
    }

    public static FullHttpResponse notFound() {
        return response(NOT_FOUND);
    }

    public static FullHttpResponse notFound(String msg) {
        return response(NOT_FOUND, msg);
    }
}
