package u.extension;

public interface UExtension {

    void init();

    void preprocess();

    void postbuild();

    void startServer();

    void stopServer();

    String getName();

}
