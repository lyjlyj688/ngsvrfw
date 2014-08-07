package person.lyjyy.core.domain;

/**
 * Created by yujie.li on 14-8-3.
 */
public abstract class InstanceObj extends BaseObj implements Cloneable{
    public static final byte TYPE_LOCAL = 0;
    public static final byte TYPE_NOL = 1;
    public static final byte OPT_NA = 0;
    public static final byte OPT_ADD = 1;
    public static final byte OPT_UPDATE = 2;
    public static final byte OPT_DEL = 3;

    protected long guid;


    protected byte optType;

    public byte getOptType() {
        return optType;
    }

    public void setOptType(byte optType) {
        this.optType = optType;
    }




    public abstract int getObjType();

    public abstract int getStoreType();

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
