package uExt.template;

import io.netty.handler.codec.http.FullHttpResponse;

import java.io.IOException;

public class Template {

    private static TemplateEngine engine = new HandlebarsTemplateEngine();

    public static void use(TemplateEngine e) {
        engine = e;
    }

    public static String render(String viewName, String model)throws IOException {
        return engine.render(viewName, model);
    }

    public static FullHttpResponse renderHtml(String viewName, String model) throws IOException {
        return engine.renderHtml(viewName, model);
    }

    public static String renderInLine(String inlineTemplate, String model) throws IOException {
        return engine.renderInLine(inlineTemplate, model);
    }

}
