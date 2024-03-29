package u.extension;

import org.apache.commons.io.FileUtils;
import u.script.BuiltPaths;
import u.script.InitPaths;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ExtensionManager {

    private Set<String> extensions;

    private static String STORED_FILE = "extensions";

    private void getExtFromInit() {
        extensions = new HashSet<>();
        try (Stream<String> stream = Files.lines(Paths.get("init"))) {
            Set<String> full = stream
                    .filter(line -> line.startsWith("using:"))
                    //.map(String::toUpperCase)
                    .collect(Collectors.toSet());
            full.forEach(ext -> extensions.add(ext.substring(6)));
        } catch (IOException e) {
            System.out.println("Warning! Init file not found, default settings used");
        }
    }

    private void makeStoredExt() throws IOException {
        Files.write(Paths.get(InitPaths.PROJECT_BIN_DIR.getPath() + "/" + STORED_FILE), extensions);
    }

    private void getExtFromStored() {
        try (Stream<String> stream = Files.lines(Paths.get("bin/" + STORED_FILE))) {
            extensions = stream
                    .collect(Collectors.toSet());
        } catch (IOException e) {
            System.out.println("Warning! Stored extension file not found, no extension used");
            extensions = new HashSet<>();
        }
    }

    private List<UExtension> getExtClasses() {
        List<UExtension> extClasses = new ArrayList<>();
        for (String ext : extensions) {
            String className = "uExt." + ext + ".UExtensionMain";
            try {
                Class extClass = Class.forName(className);
                if (UExtension.class.isAssignableFrom(extClass)) {
                    UExtension extension = (UExtension)extClass.newInstance();
                    extClasses.add(extension);
                }
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
                extensions.remove(ext);
            }
        }
        return extClasses;
    }

    public void init() {

        getExtFromInit();
        for (UExtension extension : getExtClasses()) {
            extension.init();
            File extDir = new File(InitPaths.DIST_DIR, extension.getName());
            try {
                FileUtils.copyDirectory(extDir, InitPaths.getProjectExtLibDir(extension.getName()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            makeStoredExt();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void preprocess() {
        BuiltPaths.EXT_STORE_DIR.mkdir();
        getExtFromStored();
        for (UExtension extension : getExtClasses()) {
            BuiltPaths.getExtStore(extension.getName(), "").mkdir();
            extension.preprocess();
        }
    }

    public void postbuild() {
        getExtFromStored();
        getExtClasses().forEach(UExtension::postbuild);
    }

    public void onStart() {
        getExtFromStored();
        getExtClasses().forEach(UExtension::startServer);
    }

    public void onStop() {
        getExtFromStored();
        getExtClasses().forEach(UExtension::stopServer);
    }

}
