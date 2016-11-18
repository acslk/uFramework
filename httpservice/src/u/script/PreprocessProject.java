package u.script;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class PreprocessProject {

    public static void main(String[] args) throws IOException {
        File genDir = new File("gen");
        genDir.mkdir();
        File input = new File(InitProject.HANDLER_FILE);
        File output = new File(genDir, InitProject.HANDLER_FILE);
        FileUtils.write(output, "package u.generated;\n", true);
        FileUtils.write(output, FileUtils.readFileToString(input), true);
    }

}
