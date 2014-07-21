package person.lyjyy.common.conf;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by yujie.li on 14-7-21.
 */
public class ServerInfo {
    public static int svrId;
    private static Properties svrProperty;
    private static Properties dbProperty;

    static {
        try {
            svrProperty = new Properties();
            svrProperty.load(new FileInputStream(new File("./conf/serverinfo.cf")));
            svrId = Integer.valueOf(svrProperty.getProperty("svrId"));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public static void initSvrProperty() {

    }

    public static void initDbProperty() {

    }
}
