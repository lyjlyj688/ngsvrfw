package person.lyjyy.common.factory;

/**
 * Created by yujie.li on 14-7-21.
 */
public interface BeanFactory<T> {
    public T getBean(String T);
}
