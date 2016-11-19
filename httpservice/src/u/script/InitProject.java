package u.script;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InitProject {

    public static final String UJAR = "dist/u.jar";
    public static final String RESOURCE_URL = "resources/";
    public static final String LIB_URL = "lib/";
    public static final String CONFIG_FILE = "config.properties";
    public static final String HANDLER_FILE = "RequestHandler.java";

    public static void main (String[] args) {

        Set<String> extensions = new HashSet<>();

        try (Stream<String> stream = Files.lines(Paths.get("init"))) {
            extensions = stream
                    .filter(line -> !line.startsWith("using:"))
                    //.map(String::toUpperCase)
                    .collect(Collectors.toSet());
        } catch (IOException e) {
            System.out.println("Warning! Init file not found, default settings used");
        }

//        //TODO : remove
//        extensions.add("routing");

//        for (String ext : extensions) {
//            String className = "uExt." + ext + ".UExtensionMain";
//            try {
//                Class extClass = Class.forName(className);
//            } catch (ClassNotFoundException e) {
//                e.printStackTrace();
//            }
//        }

        File projectDir = new File("newProject");
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
    }

}
