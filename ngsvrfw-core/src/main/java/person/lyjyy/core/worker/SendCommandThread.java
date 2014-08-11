package person.lyjyy.core.worker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import person.lyjyy.core.domain.DsCommandBatch;
import person.lyjyy.core.net.client.Client;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yujie.li on 14-8-4.
 */
public class SendCommandThread<T extends DsCommandBatch> extends ThreadModel<T> {

    public void init(Client client) {
        this.client = client;
    }

    protected Client client;

    protected List<T> list = new ArrayList<T>();

    protected static final Logger log = LoggerFactory.getLogger(SendCommandThread.class);

    @Override
    public void run() {
        while(true) {
            try {
                queue.drainTo(list);
                if(client.isConnect()) {
                    for(int i = 0; i < list.size(); i++) {
                        if(i > 0 && i % 2000 == 0) {
                            client.flush();
                        }
                        client.write(list.get(i));
                    }
                    client.flush();
                    list.clear();
                }
                Thread.sleep(500);
            }catch (Exception e){
                log.error("exec send command error",e);
            }finally {
                if(list.size() >= 200000) {
                    log.error("can not connect ds");
                }
            }
        }
    }
}
