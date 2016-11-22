package uExt.template;

import io.netty.handler.codec.http.FullHttpResponse;

import java.io.IOException;

public interface TemplateEngine {

    String render(String viewName, String model)throws IOException;

    FullHttpResponse renderHtml(String viewName, String model)throws IOException;

    String renderInLine(String inlineTemplate, String model) throws IOException;
}
