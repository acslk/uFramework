package u.script;

import java.io.File;
import java.io.FileNotFoundException;

// paths relative to project root
public class BuiltPaths {
    public static final File SRC_DIR = new File("app");
    public static final File LIB_DIR = new File("lib");

    public static File getExtResource(String ext, String resourcePath) throws FileNotFoundException {
        return PathsCommon.getExtResource(LIB_DIR, ext, resourcePath);
    }

}
