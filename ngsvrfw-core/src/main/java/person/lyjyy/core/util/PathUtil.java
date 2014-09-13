package person.lyjyy.core.util;

/**
 * Created by yujie.li on 14-9-10.
 */
public class PathUtil {
    private final static ClassLoader loader = Thread.currentThread().getContextClassLoader();

    public static String getFilePath(String path) {
        return loader.getResource(path).getFile();
    }
}
