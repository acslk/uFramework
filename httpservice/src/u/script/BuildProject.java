package u.script;

import u.extension.ExtensionManager;

public class BuildProject {

    public static void main(String[] args) {
        ExtensionManager extensionManager = new ExtensionManager();
        extensionManager.postbuild();
    }

}
