package person.lyjyy.core.domain;

import io.netty.buffer.ByteBuf;
import person.lyjyy.core.protocol.ProtocolHandler;

/**
 * Created by yujie.li on 14-8-6.
 */
public class DefaultDsCommandBatch extends DsCommandBatch<ByteBuf>{
    @Override
    public ByteBuf encode(ByteBuf buff) {
        buff.writeShort(commandList.size());
        for(int i = 0; i < commandList.size(); i++) {
            commandList.get(i).encode(buff);
        }
        return buff;
    }

    @Override
    public void decode(ByteBuf buff) {
        int count = buff.readShort();
        for(int i = 0; i < count; i++) {
            DsCommand<ByteBuf> command = new DefaultDsCommand();
            command.encode(buff);
            commandList.add(command);
        }
    }






}
