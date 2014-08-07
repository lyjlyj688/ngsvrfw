package person.lyjyy.core.conf;

import person.lyjyy.core.domain.RemoteObj;

/**
 * Created by yujie.li on 14-8-6.
 */
public class RemoteObjConfig {

    private static RemoteObjConfig config0;

    public static void init(RemoteObjConfig config) {
        config0 = config;
    }

    protected  RemoteObj arr[] = new RemoteObj[1000];

    public static RemoteObj getObj(int id) {
        return config0.arr[id].newInstance();
    }
}
