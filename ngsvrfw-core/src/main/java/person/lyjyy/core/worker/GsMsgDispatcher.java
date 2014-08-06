package person.lyjyy.core.worker;

import person.lyjyy.core.protocol.InProtocol;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by yujie.li on 14-8-4.
 */
public class GsMsgDispatcher<T extends InProtocol> extends WorkThread<T>{

    protected WorkThread<T>[] threadList;

    public GsMsgDispatcher(WorkThread<T>[] list) {
        threadList = new WorkThread[list.length];
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
