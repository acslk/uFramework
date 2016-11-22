package uExt.statics;

import u.extension.UExtension;
import u.script.InitPaths;

import java.io.File;

public class UExtensionMain implements UExtension{
    @Override
    public void init() {
        File staticsDir = new File(InitPaths.PROJECT_APP_DIR, Statics.STATIC_DIR);
        staticsDir.mkdir();
        File jsDir = new File(staticsDir, "js");
        jsDir.mkdir();
        File cssDir = new File(staticsDir, "css");
        cssDir.mkdir();
        System.out.println("made statics directory");
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
        return "statics";
    }
}
