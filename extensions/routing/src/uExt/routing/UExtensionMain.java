package uExt.routing;

import u.extension.UExtension;

import java.io.File;

public class UExtensionMain implements UExtension {

    @Override
    public void init() {
        File projectDir = new File("newProject");
        File controllerDir = new File(projectDir, "controllers");
        controllerDir.mkdir();
        System.out.println("made controller directory");
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
}
