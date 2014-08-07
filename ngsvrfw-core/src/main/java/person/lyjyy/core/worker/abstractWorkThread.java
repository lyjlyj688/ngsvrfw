package person.lyjyy.core.worker;

/**
 * Created by yujie.li on 14-8-6.
 */
public abstract class AbstractWorkThread<T> extends ThreadModel<T> {


    protected abstract void before(T t);
    protected abstract void exec(T t);
    protected abstract void after(T t);

    public void run() {
        while(true) {
            T t = null;
            try {
                t = queue.take();
                before(t);
                exec(t);
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                after(t);
            }
        }
    }
}
