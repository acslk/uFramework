package u.http;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.QueryStringDecoder;

import java.util.List;
import java.util.Map;

public class RoutingServerHandler extends SimpleChannelInboundHandler<Object> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        StringBuilder buf = new StringBuilder();
        if (msg instanceof HttpRequest) {
            HttpRequest request = (HttpRequest) msg;
            buf.append("instanceof HttpRequest\n");
            buf.append(request.method()).append(" ").append(request.uri()).append("\n");
            HttpHeaders headers = request.headers();
            if (!headers.isEmpty()) {
                for (Map.Entry<String, String> h: headers)
                {
                    buf.append("HEADER: ").append(h.getKey()).append(" = ").append(h.getValue()).append("\n");
                }
            }

            QueryStringDecoder queryStringDecoder = new QueryStringDecoder(request.uri());
            Map<String, List<String>> params = queryStringDecoder.parameters();
            if (!params.isEmpty()) {
                for (Map.Entry<String, List<String>> p: params.entrySet()) {
                    List<String> values = p.getValue();
                    for (String val: values) {
                        buf.append("PARAM: ").append(p.getKey()).append(" = ").append(val).append("\n");
                    }
                }
            }
        }
    }
}
