package uExt.statics;

import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpRequest;
import org.apache.commons.io.FilenameUtils;
import u.http.Responses;
import u.script.BuiltPaths;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Statics {

    public static final String STATIC_DIR = BuiltPaths.APP_DIR + "/statics";

    public static FullHttpResponse get(HttpRequest request) {
        File resource = new File(STATIC_DIR + request.uri());
        if (!resource.exists())
            return null;
        String ext = FilenameUtils.getExtension(resource.getName());
        if (!ext.equals("js") && !ext.equals("css"))
            return null;
        if (ext.equals("js"))
            ext = "javascript";
        try {
            FullHttpResponse response = Responses.ok(new String(Files.readAllBytes(Paths.get(resource.getPath()))));
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/" + ext + "; charset=UTF-8");
            return response;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
