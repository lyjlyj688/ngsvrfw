package person.lyjyy.core.worker;

import person.lyjyy.core.protocol.InProtocol;

/**
 * Created by yujie.li on 14-8-4.
 */
public class MsgDispatcher<T extends InProtocol> extends ThreadModel<T> {

    protected AbstractProtocolWorkThread<T>[] threadList;

    public MsgDispatcher(AbstractProtocolWorkThread<T>[] list) {
        threadList = new AbstractProtocolWorkThread[list.length];
        for(int i = 0; i < threadList.length; i++) {
            threadList[i] = list[i];
        }
    }

    @Override
    public void run() {
        while(true) {
            try {
                T in = queue.take();
                threadList[in.getThreadIndex(threadList.length)].add(in);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
