package u.http;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;

import java.lang.reflect.Method;

import static io.netty.handler.codec.http.HttpResponseStatus.CONTINUE;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

public class MainServerHandler extends SimpleChannelInboundHandler<Object> {

    private Class requestHandler;
    private Object requestHandlerInstance;
    private Method handlerFn;

    public MainServerHandler() {
        try {
            requestHandler = Class.forName("u.generated.RequestHandler");
            requestHandlerInstance = requestHandler.newInstance();
            handlerFn = requestHandler.getMethod("handle", HttpRequest.class, HttpContent.class);
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {

        if (msg instanceof HttpRequest && msg instanceof HttpContent) {
            HttpRequest request = (HttpRequest) msg;
            HttpContent content = (HttpContent) msg;
            boolean keepAlive = HttpUtil.isKeepAlive(request);

            if (HttpUtil.is100ContinueExpected(request)) {
                FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, CONTINUE);
                ctx.write(response);
            }

            FullHttpResponse response = (FullHttpResponse)handlerFn.invoke(requestHandlerInstance, request, content);
            //FullHttpResponse response = RequestHandler.handle(request, content);

            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain; charset=UTF-8");

            if (keepAlive) {
                response.headers().setInt(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
                response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
            }

            ctx.writeAndFlush(response);

            if (!keepAlive) {
                ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
            }
        }
    }
}
