package uExt.template;

import org.apache.commons.io.FileUtils;
import u.extension.UExtension;
import u.script.InitPaths;

import java.io.File;
import java.io.IOException;

/**
 * Created by Richarddi on 2016-11-20.
 */
public class UExtensionMain  implements UExtension {


    public String render(String viewName, String model)throws IOException {

        return "";
    }


    @Override
    public void init() {
        File controllerDir = new File(InitPaths.PROJECT_APP_DIR, "controllers");
        controllerDir.mkdir();
        System.out.println("made controller directory");
        try {
            FileUtils.copyDirectory(InitPaths.getExtResource("routing", ""), controllerDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void preprocess() {

    }

    @Override
    public void postbuild() {

    }

    @Override
    public void startServer() {

    }

    @Override
    public void stopServer() {

    }

    @Override
    public String getName() {
        return "routing";
    }

}
