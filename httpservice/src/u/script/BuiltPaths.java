package u.script;

import java.io.File;
import java.io.FileNotFoundException;

// paths relative to project root
public class BuiltPaths {
    public static final File APP_DIR = new File("app");
    public static final File LIB_DIR = new File("lib");
    public static final File BIN_DIR = new File("bin");
    public static final File GEN_DIR = new File("gen");
    public static final File EXT_STORE_DIR = new File("store");

    public static File getExtResource(String ext, String resourcePath) throws FileNotFoundException {
        return PathsCommon.getExtResource(LIB_DIR, ext, resourcePath);
    }

    public static File getExtStore(String ext, String path) {
        File extStoreDir = new File(EXT_STORE_DIR, ext);
        return new File(extStoreDir, path);
    }

}
