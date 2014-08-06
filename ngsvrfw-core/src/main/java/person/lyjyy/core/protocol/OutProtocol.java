package person.lyjyy.core.protocol;

/**
 * Created by yujie.li on 14-8-4.
 */
public interface OutProtocol<T> extends IProtocol{

    public T encode(T buff);

}
