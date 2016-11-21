package u.script;

import java.io.File;
import java.io.FileNotFoundException;


// paths relative to init script, that is outside new package
public class InitPaths {

    public static final File PROJECT_DIR = new File("newProject");
    public static final File PROJECT_APP_DIR = new File(PROJECT_DIR, "app");
    public static final File PROJECT_LIB_DIR = new File(PROJECT_DIR, "lib");
    public static final File PROJECT_BIN_DIR = new File(PROJECT_DIR, "bin");
    public static final File PROJECT_EXT_LIB_DIR = new File(PROJECT_LIB_DIR, "extension");

    public static final File DIST_DIR = new File("dist");

    public static File getExtResource(String ext, String resourcePath) throws FileNotFoundException {
        return PathsCommon.getExtResource(DIST_DIR, ext, resourcePath);
    }

    public static File getProjectExtLibDir(String ext) {
        return new File(PROJECT_EXT_LIB_DIR, ext);
    }


}
