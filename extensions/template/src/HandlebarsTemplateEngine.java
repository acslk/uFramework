import java.io.IOException;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;

/**
 * Renders HTML from Route output using
 * https://github.com/jknack/handlebars.java.
 * Defaults to the 'templates' directory under the resource path.
 */
public class HandlebarsTemplateEngine extends UTemplateEngine {

    protected Handlebars handlebars;

    /**
     * Constructs a handlebars template engine
     */
    public HandlebarsTemplateEngine() {
        this("/templates");
    }

    /**
     * Constructs a handlebars template engine
     *
     * @param resourceRoot the resource root
     */
    public HandlebarsTemplateEngine(String resourceRoot) {
        TemplateLoader templateLoader = new ClassPathTemplateLoader();
        templateLoader.setPrefix(resourceRoot);
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
    @Override
    public String render(String viewName, String model)throws IOException {

        try {
            Template template = handlebars.compile(viewName);
            return template.apply(model);
        } catch (IOException e) {
            throw e;
        }
    }
}