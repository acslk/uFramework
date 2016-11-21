package uExt.routing;

import org.apache.commons.io.FileUtils;
import u.extension.UExtension;
import u.script.InitPaths;

import java.io.File;
import java.io.IOException;

public class UExtensionMain implements UExtension {

    @Override
    public void init() {
        File controllerDir = new File(InitPaths.PROJECT_SRC_DIR, "controllers");
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
