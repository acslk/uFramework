package u.script;

import u.extension.ExtensionManager;

public class PostBuildProject {

    public static void main(String[] args) {
        ExtensionManager extensionManager = new ExtensionManager();
        extensionManager.postbuild();
    }

}
