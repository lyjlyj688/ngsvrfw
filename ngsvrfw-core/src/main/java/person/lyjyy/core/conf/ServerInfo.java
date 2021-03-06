package person.lyjyy.core.conf;

import person.lyjyy.core.util.PathUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by yujie.li on 14-7-21.
 */
public class ServerInfo {
    public static int gsPort;
    public static String dsIp;
    public static int dsPort;

    public static void init(String file) throws IOException {
        Properties p = new Properties();
        p.load(new FileInputStream(new File(PathUtil.getFilePath(file))));
        gsPort = Integer.valueOf(p.getProperty("gs.port"));
        dsIp = p.getProperty("ds.ip");
        dsPort = Integer.valueOf(p.getProperty("ds.port"));
    }
}
