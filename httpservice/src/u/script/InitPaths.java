package u.script;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;


// paths relative to init script, that is outside new package
public class InitPaths {

    public static File PROJECT_DIR = new File("newProject");
    public static File PROJECT_APP_DIR = new File(PROJECT_DIR, "app");
    public static File PROJECT_LIB_DIR = new File(PROJECT_DIR, "lib");
    public static File PROJECT_BIN_DIR = new File(PROJECT_DIR, "bin");
    public static File PROJECT_EXT_LIB_DIR = new File(PROJECT_LIB_DIR, "extension");

    public static final File DIST_DIR = new File("dist");

    public static final void LOAD_CONFIG () {
        try (Stream<String> stream = Files.lines(Paths.get("init"))) {
            List<String> full = stream
                    .filter(line -> line.startsWith("project:"))
                    .collect(Collectors.toList());
            if (!full.isEmpty()) {
                PROJECT_DIR = new File(full.get(0).substring(8));
                PROJECT_APP_DIR = new File(PROJECT_DIR, "app");
                PROJECT_LIB_DIR = new File(PROJECT_DIR, "lib");
                PROJECT_BIN_DIR = new File(PROJECT_DIR, "bin");
                PROJECT_EXT_LIB_DIR = new File(PROJECT_LIB_DIR, "extension");
            }
        } catch (IOException e) {
            System.out.println("Warning! Init file not found, default settings used");
        }
    }

    public static File getExtResource(String ext, String resourcePath) throws FileNotFoundException {
        return PathsCommon.getExtResource(DIST_DIR, ext, resourcePath);
    }

    public static File getProjectExtLibDir(String ext) {
        return new File(PROJECT_EXT_LIB_DIR, ext);
    }


}
