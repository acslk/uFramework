package uExt.template;

import java.io.IOException;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.FileTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpResponse;
import u.http.Responses;
import u.script.BuiltPaths;

/**
 * Renders HTML from Route output using
 * https://github.com/jknack/handlebars.java.
 * Defaults to the 'templates' directory under the resource path.
 */
public class HandlebarsTemplateEngine implements TemplateEngine {

    protected Handlebars handlebars;

    /**
     * Constructs a handlebars template engine
     */
    public HandlebarsTemplateEngine() {
        this(BuiltPaths.APP_DIR + "/views");
    }

    /**
     * Constructs a handlebars template engine
     *
     * @param resourceRoot the resource root
     */
    public HandlebarsTemplateEngine(String resourceRoot) {
        TemplateLoader templateLoader = new FileTemplateLoader(resourceRoot);
        templateLoader.setSuffix(null);

        handlebars = new Handlebars(templateLoader);

    }

    /**
     *
     * @param viewName the file name of the view
     * @param model the json containing the information needed for rendering
     * @return
     * @throws IOException
     */
    public String render(String viewName, String model)throws IOException {
        try {
            Template template = handlebars.compile(viewName);
            return template.apply(model);
        } catch (IOException e) {
            throw e;
        }
    }

    public FullHttpResponse renderHtml(String viewName, String model)throws IOException {
        try {
            Template template = handlebars.compile(viewName);
            String html = template.apply(model);
            FullHttpResponse response = Responses.ok(html);
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html; charset=UTF-8");
            return response;
        } catch (IOException e) {
            throw e;
        }
    }

    /**
     *
     * @param inlineTemplate "Hello {{this}}!"
     * @param model            "I am Richard"
     * @return
     * @throws IOException
     */
    public String renderInLine(String inlineTemplate, String model) throws IOException {
        try {
            Template template = handlebars.compileInline(inlineTemplate);
            return (template.apply(model));
        } catch (IOException e) {
            throw e;
        }
    }
}