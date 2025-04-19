package au.nodelogic.coucal.workspaces.util;

public interface Filesystem {
    
    static String getDataDirectory() {
        String os = System.getProperty("os.name").toLowerCase();
        String userHome = System.getProperty("user.home");

        if (os.contains("win")) {
            return System.getenv("APPDATA");
        } else if (os.contains("mac")) {
            return userHome + "/Library/Application Support";
        } else {
            return userHome + "/.local/share";
        }
    }
}
