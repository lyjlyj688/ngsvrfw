package person.lyjyy.common.net.protocol;

/**
 * Created by yujie.li on 14-7-21.
 */
public interface Response<T> {
    public T decode();
}
