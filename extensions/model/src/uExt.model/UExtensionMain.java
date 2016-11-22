package uExt.model;

import u.extension.UExtension;
import u.script.InitPaths;
import java.io.File;

public class UExtensionMain implements UExtension
{
    private static final String NAME = "model";
    @Override
    public void init()
    {
        File controllerDir = new File(InitPaths.PROJECT_APP_DIR, "models");
        controllerDir.mkdir();
        System.out.println("made model directory");
    }

    @Override
    public void preprocess()
    {

    }

    @Override
    public void postbuild()
    {

    }

    @Override
    public void startServer()
    {

    }

    @Override
    public void stopServer()
    {

    }

    @Override
    public String getName()
    {
        return NAME;
    }
}
