package u.script;

import org.apache.commons.io.FileUtils;
import u.extension.ExtensionManager;

import java.io.File;
import java.io.IOException;

public class PreprocessProject {

    public static void main(String[] args) throws IOException {
        File input = new File(InitProject.HANDLER_FILE);
        File output = new File(BuiltPaths.GEN_DIR, InitProject.HANDLER_FILE);
        FileUtils.write(output, "package u.generated;\n", true);
        FileUtils.write(output, FileUtils.readFileToString(input), true);

        BuiltPaths.EXT_STORE_DIR.mkdir();

        ExtensionManager extensionManager = new ExtensionManager();
        extensionManager.preprocess();
    }

}
