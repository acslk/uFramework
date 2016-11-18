package u.utils;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class UUtils {

    public static void appendFront(String value, File infile, File outfile) throws IOException {
        Charset charset = StandardCharsets.UTF_8;
        List<String> lines = new ArrayList<>();
        lines.add(value);
        Files.write(outfile.toPath(), lines, charset, StandardOpenOption.CREATE,
                StandardOpenOption.APPEND);
        lines = Files.readAllLines(infile.toPath());
        Files.write(outfile.toPath(), lines, charset, StandardOpenOption.CREATE,
                StandardOpenOption.APPEND);
    }

    static public void ExportResource(String to, String resourceName) throws Exception {
        String jarFolder;
        try (InputStream stream = UUtils.class.getResourceAsStream(resourceName);
             OutputStream resStreamOut = new FileOutputStream(to + resourceName)) {
            if (stream == null) {
                throw new Exception("Cannot get resource \"" + resourceName + "\" from Jar file.");
            }
            int readBytes;
            byte[] buffer = new byte[4096];
            while ((readBytes = stream.read(buffer)) > 0) {
                resStreamOut.write(buffer, 0, readBytes);
            }
        } catch (Exception ex) {
            throw ex;
        }
    }

}