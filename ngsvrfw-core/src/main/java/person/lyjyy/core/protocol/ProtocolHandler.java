package person.lyjyy.core.protocol;

/**
 * Created by yujie.li on 14-8-4.
 */
public interface ProtocolHandler<T extends InProtocol> {
    public void handle(T protocol);
}
