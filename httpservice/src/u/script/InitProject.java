package u.script;

import org.apache.commons.io.FileUtils;
import u.extension.ExtensionManager;

import java.io.File;
import java.io.IOException;

public class InitProject {

    public static final String UJAR = "dist/u.jar";
    public static final String RESOURCE_URL = "dist/resources";
    public static final String LIB_URL = "lib";
    public static final String CONFIG_FILE = "config.properties";

    public static void main (String[] args) {

        InitPaths.LOAD_CONFIG();
        InitPaths.PROJECT_DIR.mkdir();
        InitPaths.PROJECT_LIB_DIR.mkdir();
        InitPaths.PROJECT_EXT_LIB_DIR.mkdir();
        InitPaths.PROJECT_BIN_DIR.mkdir();

        File libDest = new File(InitPaths.PROJECT_LIB_DIR, "u.jar");
//        File handlerDest = new File(projectDir, HANDLER_FILE);
//        File configDest = new File(projectDir, CONFIG_FILE);
        try {
            FileUtils.copyFile(new File(UJAR), libDest);
            FileUtils.copyDirectory(new File(RESOURCE_URL), InitPaths.PROJECT_DIR);
            FileUtils.copyDirectory(new File(LIB_URL), InitPaths.PROJECT_LIB_DIR);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ExtensionManager extensionManager = new ExtensionManager();
        extensionManager.init();
    }

}
