package person.lyjyy.core.domain;

/**
 * Created by yujie.li on 14-8-3.
 */
public abstract class InstanceObj extends BaseObj implements Cloneable{
    public static final int TYPE_LOCAL = 0;
    public static final int TYPE_NOL = 1;
    public static final int OPT_NA = 0;
    public static final int OPT_ADD = 1;
    public static final int OPT_UPDATE = 2;
    public static final int OPT_DEL = 3;

    protected int storeType;

    protected long guid;

    public abstract int getObjType();

    public int getStoreType() {
        return storeType;
    }

    public void setStoreType(int storeType) {
        this.storeType = storeType;
    }

    public long getGuid() {
        return guid;
    }

    public void setGuid(long guid) {
        this.guid = guid;
    }

    public InstanceObj clone() throws CloneNotSupportedException {
        return (InstanceObj) super.clone();
    }



}
