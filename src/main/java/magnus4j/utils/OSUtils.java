package magnus4j.utils;

/**
 * Operating system utilities.
 */
public final class OSUtils {

    private static String os = null;

    public static String getOsName() {
        if (os == null) {
            os = System.getProperty("os.name");
        }
        return os;
    }

    public static boolean isWindows() {
        return getOsName().startsWith("Windows");
    }

    public static boolean isLinux() {
        return getOsName().startsWith("Linux");
    }
}
