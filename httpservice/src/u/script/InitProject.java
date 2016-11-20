package u.script;

import org.apache.commons.io.FileUtils;
import u.extension.ExtensionManager;

import java.io.File;
import java.io.IOException;

public class InitProject {

    public static final String UJAR = "dist/u.jar";
    public static final String RESOURCE_URL = "resources/";
    public static final String LIB_URL = "lib/";
    public static final String CONFIG_FILE = "config.properties";
    public static final String HANDLER_FILE = "RequestHandler.java";
    public static final String PROJECT_DIR = "newProject";

    public static void main (String[] args) {
        File projectDir = new File(PROJECT_DIR);
        projectDir.mkdir();
        File libDir = new File(projectDir, "lib");
        libDir.mkdir();
        File libDest = new File(libDir, "u.jar");
//        File handlerDest = new File(projectDir, HANDLER_FILE);
//        File configDest = new File(projectDir, CONFIG_FILE);
        try {
            FileUtils.copyFile(new File(UJAR), libDest);
            FileUtils.copyDirectory(new File(RESOURCE_URL), projectDir);
            FileUtils.copyDirectory(new File(LIB_URL), libDir);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ExtensionManager extensionManager = new ExtensionManager();
        extensionManager.init();
    }

}
