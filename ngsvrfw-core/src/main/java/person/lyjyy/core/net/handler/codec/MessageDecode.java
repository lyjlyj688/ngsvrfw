package person.lyjyy.core.net.handler.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Created by yujie.li on 14-7-22.
 */
public class MessageDecode extends ChannelInboundHandlerAdapter{

    private int max = 0xfffff;//默认最大1m

    public MessageDecode(int maxInputLength) {
        max = maxInputLength;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf in = (ByteBuf) msg;
        if(in.readableBytes() < 4) {
            return;
        }
        int readIndex = in.readerIndex();
        int length = in.readInt();
        if(length > max) {
            throw new RuntimeException("消息长度过大：" + length);
        }
        if(in.readableBytes() < length) {
            in.readerIndex(readIndex);
            return;
        }
        ByteBuf ret = ctx.alloc().heapBuffer(length);
        in.readBytes(ret);
        ctx.fireChannelRead(ret);
    }
}
