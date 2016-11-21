package u.script;

import java.io.File;
import java.io.FileNotFoundException;

public class PathsCommon {

    static File getExtResource(File libDir, String ext, String resourcePath) throws FileNotFoundException {
        File extDir = new File(libDir, ext);
        File extResourceDir = new File(extDir, "resources");
        File resource = new File(extResourceDir, resourcePath);
        if (!resource.exists())
            throw new FileNotFoundException(resource.getPath() + " is not found");
        return resource;
    }
}
