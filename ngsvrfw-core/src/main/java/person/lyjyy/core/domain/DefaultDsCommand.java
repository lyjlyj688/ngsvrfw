package person.lyjyy.core.domain;

import io.netty.buffer.ByteBuf;
import person.lyjyy.core.conf.RemoteObjConfig;
import person.lyjyy.core.protocol.ProtocolHandler;
import person.lyjyy.core.util.ByteUtil;

/**
 * Created by yujie.li on 14-8-6.
 */
public class DefaultDsCommand extends DsCommand<ByteBuf> {


    @Override
    public ByteBuf encode(ByteBuf buff) {
        obj.encode(buff);
        ByteUtil.putString2Buff(sql,buff);
        return buff;
    }

    @Override
    public void decode(ByteBuf buff) {
        obj = RemoteObjConfig.getObj(buff.readShort());
        obj.decode(buff);
        sql = ByteUtil.getBuffString(buff);
    }

}
