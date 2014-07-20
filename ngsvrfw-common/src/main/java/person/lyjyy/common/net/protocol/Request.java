package person.lyjyy.common.net.protocol;

import java.nio.ByteBuffer;

/**
 * Created by yujie.li on 14-7-21.
 */
public interface Request<T> {
    public void decode(T buff);
}
