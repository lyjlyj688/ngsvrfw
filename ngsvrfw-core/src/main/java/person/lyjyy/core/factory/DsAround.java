package person.lyjyy.core.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by yujie.li on 14-8-11.
 */
public class DsAround implements Around{
    private static final Logger log = LoggerFactory.getLogger(DsAround.class);
    @Override
    public Object around(Class clazz) {
        try {
            return clazz.newInstance();
        } catch (Exception e) {
            log.error("ds around error",e);
        }
        return null;
    }
}
