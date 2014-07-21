package person.lyjyy.common.conf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by yujie.li on 14-7-22.
 */
public enum  DefaultLogger {

    SYS_ERR,SYS_INFO,APP_ERR,APP_INFO;

    private Logger logger;

    private DefaultLogger() {
        logger = LoggerFactory.getLogger(name());
    }

    public Logger getLogger() {
        return logger;
    }

}
