package person.lyjyy.core.util;

import io.netty.buffer.ByteBuf;

import java.nio.ByteBuffer;

/**
 * Created by yujie.li on 14-8-4.
 */
public class ByteUtil {

    public static void putString2Buff(String str,ByteBuf buf) {
        byte[] src = str.getBytes();
        buf.writeShort(src.length);
        buf.writeBytes(src);
    }

    public static String getBuffString(ByteBuf buf) {
        byte[] src = new byte[buf.readShort()];
        buf.readBytes(src);
        return new String(src);
    }
}
