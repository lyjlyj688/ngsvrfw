package person.lyjyy.core.factory;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import person.lyjyy.core.cache.CoreDao4Gs;
import person.lyjyy.core.cache.GsCache;
import person.lyjyy.core.cache.ThreadData;

import java.lang.reflect.Method;

/**
 * Created by yujie.li on 14-8-11.
 */
public class GsAround implements Around{

    @Override
    public Object around(Class clazz) {
        return null;
    }

    class TransInterceptor implements MethodInterceptor {

        ThreadLocal<ThreadStatus> status = new ThreadLocal<ThreadStatus>();

        @Override
        public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
            try {
                start();
                return methodProxy.invokeSuper(o,objects);
            }catch (Exception e) {
                status.get().fail = true;
                throw e;
            }finally {
                try {
                    commit();
                }catch (Exception e) {
                    throw e;
                }finally {
                    status.get().clear();
                }
            }
        }

        void start() {
            if(status.get() == null) {
                ThreadStatus s1 = new ThreadStatus();
                status.set(s1);
            }
            if(status.get().count > 0) {
                throw new RuntimeException("已经开启事务 不支持事务传递");
            }
            status.get().addcount();
            if(GsCache.tl.get() == null) {
                GsCache.tl.set(new ThreadData());
            }
            GsCache.tl.get().startTrans();
        }

        void commit() {
            if(status.get().isFail()) {
                GsCache.tl.get().rollback();
            }else {
                GsCache.tl.get().commit();
            }
        }
    }

    class ThreadStatus {
        int count;
        boolean fail = false;

        boolean isFail() {
            return fail;
        }

        void addcount() {
            count++;
        }

        int decr() {
            return ++count;
        }

        void clear() {
            count = 0;
        }
    }
}
