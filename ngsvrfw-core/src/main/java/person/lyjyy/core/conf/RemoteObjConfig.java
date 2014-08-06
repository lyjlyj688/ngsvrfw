package person.lyjyy.core.conf;

import person.lyjyy.core.domain.RemoteObj;

/**
 * Created by yujie.li on 14-8-6.
 */
public class RemoteObjConfig {
    protected static RemoteObj arr[] = new RemoteObj[1000];

    public static RemoteObj getObj(int id) {
        return (RemoteObj) arr[id].newInstance();
    }
}
