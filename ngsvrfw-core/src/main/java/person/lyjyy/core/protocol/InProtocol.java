package person.lyjyy.core.protocol;

/**
 * Created by yujie.li on 14-7-21.
 */
public interface InProtocol<T> extends IProtocol{

    public void decode(T buff);

    public int getThreadIndex(int para);

    public InProtocol<T> newInstance();

    public String getHandler();
}
