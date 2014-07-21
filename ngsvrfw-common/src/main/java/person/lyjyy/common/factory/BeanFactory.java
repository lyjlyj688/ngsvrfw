package person.lyjyy.common.factory;

/**
 * Created by yujie.li on 14-7-21.
 */
public class BeanFactory<T> {
    public T getBean(String T) {
        return null;
    }

    public T around(Around around,T src) {
        return src;
    }
}
