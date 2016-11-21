package u.script;

import java.io.File;
import java.io.FileNotFoundException;


// paths relative to init script, that is outside new package
public class InitPaths {

    public static final File PROJECT_DIR = new File("newProject");
    public static final File PROJECT_SRC_DIR = new File(PROJECT_DIR, "app");
    public static final File PROJECT_LIB_DIR = new File(PROJECT_DIR, "lib");
    public static final File PROJECT_BIN_DIR = new File(PROJECT_DIR, "bin");

    public static final File DIST_DIR = new File("dist");

    public static File getExtResource(String ext, String resourcePath) throws FileNotFoundException {
        return PathsCommon.getExtResource(DIST_DIR, ext, resourcePath);
    }

    public static File getProjectExtDir(String ext) {
        File extDir = new File(PROJECT_LIB_DIR, "extension");
        extDir.mkdir();
        return new File(extDir, ext);
    }


}
