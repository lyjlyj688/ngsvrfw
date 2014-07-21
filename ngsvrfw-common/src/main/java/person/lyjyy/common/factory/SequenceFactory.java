package person.lyjyy.common.factory;

import person.lyjyy.common.conf.ServerInfo;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by yujie.li on 14-7-21.
 */
public class SequenceFactory {

    private final static AtomicInteger inc = new AtomicInteger(0);

    /**
     * 支持17年 每秒生成65536个序列
     * 最多支持32000个独立groupId
     * type 15个
     * @param type
     * @param groupId
     * @return
     */
    public static long getSequence(int type, int groupId) {
        if(type > 15 || type < 0 || groupId > 0x7fff || groupId < 0) {
            throw new RuntimeException("net legal param");
        }
        long time = (System.currentTimeMillis() >> 10) & 0xfffffff;
        return (type << 60) | (groupId << 45) | (time << 16) | inc.addAndGet(1) & 0xffff;
    }

}
